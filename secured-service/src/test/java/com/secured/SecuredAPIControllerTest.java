package com.secured;

import helper.JWTHelper;
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
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SecuredServiceApplication.class)
public class SecuredAPIControllerTest {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String EXPIRED_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiJKb2huIiwibGFzdE5hbWUiOiJEb2UiLCJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU3NzQyOTAzOCwidXNlcklkIjoxLCJqdGkiOiIzZTNmZGM1My0xMjBkLTRmZmUtYTUwOC01MTViMmNhOTE3ZTYiLCJlbWFpbCI6Inh5ekBlbWFpbC5jb20iLCJjbGllbnRfaWQiOiJhc3NpZ25tZW50In0.LVNKoyBaN7i9z7kE8bkhBgjttpmjI3Uc6CtNsos8v5k";


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void givenToken_whenGetSecureRequest_thenOk() throws Exception {
         mockMvc.perform(get("/v1/users/user/info")
                .header("Authorization", "Bearer " + JWTHelper.generateDummyUserTokenToken(jwtSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    public void givenExpiredToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        ResultActions result = mockMvc.perform(get("/v1/users/user/info")
                .header("Authorization", "Bearer " + EXPIRED_JWT_TOKEN)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        MockHttpServletResponse response = result.andReturn().getResponse();
        String resultString = response.getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String error =  jsonParser.parseMap(resultString).get("errorCode").toString();
        Assert.assertEquals(error,"token_expired");
        Assert.assertEquals(response.getStatus(), 401);

    }
}
