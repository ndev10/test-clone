package com.oauth;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = OauthServiceApplication.class)
public class OauthRefreshTokenTests {

    @Value("${oauth.client.clientId}")
    private String clientId;

    @Value("${oauth.client.clientSecret}")
    private String clientSecret;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";


    private static final String EXPIRED_REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiJKb2huIiwibGFzdE5hbWUiOiJEb2UiLCJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjNlM2ZkYzUzLTEyMGQtNGZmZS1hNTA4LTUxNWIyY2E5MTdlNiIsImV4cCI6MTU3NzQzNDI0OCwidXNlcklkIjoxLCJqdGkiOiIwNDgzZjI3My1kYWUyLTRiM2EtOWM0ZC0yYzEzNmQ5ODg2ZDAiLCJlbWFpbCI6Inh5ekBlbWFpbC5jb20iLCJjbGllbnRfaWQiOiJhc3NpZ25tZW50In0.r6TeHcojfqWym7FiPM_kNGiv8kklLH81snZ0H0e7W-8";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    public String obtainRefreshToken() throws  Exception {


        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("username", "john");
        params.add("password", "password");


        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId, clientSecret))
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String refreshToken =  jsonParser.parseMap(resultString).get("refresh_token").toString();
        return refreshToken;
    }

    @Test
    public void given_valid_refresh_token_then_generate_valid_access_token() throws Exception {

        String refreshToken = obtainRefreshToken();

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);


        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId, clientSecret))
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token =  jsonParser.parseMap(resultString).get("access_token").toString();
        Assert.assertNotNull(token);

    }

    @Test
    public void given_invalid_refresh_token_then_401_error() throws Exception {

        String refreshToken = "Invlaid Token";

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);


        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId, clientSecret))
                .accept(CONTENT_TYPE))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(CONTENT_TYPE));

        MockHttpServletResponse response = result.andReturn().getResponse();
        String resultString = response.getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String error =  jsonParser.parseMap(resultString).get("error").toString();
        Assert.assertEquals(error,"invalid_token");
        Assert.assertEquals(response.getStatus(), 401);


    }

    @Test
    public void given_expired_refresh_token_then_401_error() throws Exception {



        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", EXPIRED_REFRESH_TOKEN);


        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId, clientSecret))
                .accept(CONTENT_TYPE))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(CONTENT_TYPE));

        MockHttpServletResponse response = result.andReturn().getResponse();
        String resultString = response.getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String error =  jsonParser.parseMap(resultString).get("error").toString();
        Assert.assertEquals(error,"invalid_token");
        Assert.assertEquals(response.getStatus(), 401);


    }

}
