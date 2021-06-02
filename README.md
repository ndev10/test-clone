# Oauth Assignment updated

Following are the steps to run the applicaiton::
  1) build gateway-service, oauth-service and secured-service with command mvnw clean install
  2) run command docker-compose build 
  3) run command docker-compose up -d (It will run both the application inside docker container)
  
 ## Oauth generate token API details
 
  URL: http://localhost:9000/api/oauth-service/oauth/token <br/>
  Method: Post <br/>
  Basic Authentication <br/>
  &nbsp;&nbsp;username: assignment  <br/>
  &nbsp;&nbsp;password: assignmentSecret#$  <br/>
  Request paramteres (x-www-form-urlencoded) <br/>
  &nbsp;&nbsp;grant_type = password  <br/>
  &nbsp;&nbsp;username = john  <br/>
  &nbsp;&nbsp;password = password  <br/>
    
  Response example:  <br/>
  {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiJKb2huIiwibGFzdE5hbWUiOiJEb2UiLCJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU3NzYxNDA0MywidXNlcklkIjoxLCJqdGkiOiI5YWQ4ODFkZi0xZGFmLTQwN2MtODU2Yi1jYzM2NDRkYjY1ODAiLCJlbWFpbCI6Inh5ekBlbWFpbC5jb20iLCJjbGllbnRfaWQiOiJhc3NpZ25tZW50In0.xy1wmfr3oiCHDB3SJ5hQ-jSMrrVCcNtaDoFacOYE7kw",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiJKb2huIiwibGFzdE5hbWUiOiJEb2UiLCJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjlhZDg4MWRmLTFkYWYtNDA3Yy04NTZiLWNjMzY0NGRiNjU4MCIsImV4cCI6MTU3NzYyMTIyMywidXNlcklkIjoxLCJqdGkiOiIwMGFiYjhlYi0xNjg2LTRmYjctYTcxNS1hODlkMGY1YTMzODUiLCJlbWFpbCI6Inh5ekBlbWFpbC5jb20iLCJjbGllbnRfaWQiOiJhc3NpZ25tZW50In0.lmLPsJlBVvuO3cqbVNJVY_sh-5sEpU3-etEvnJQwlN8",
    "expires_in": 299,
    "scope": "read write"
}  <br/>

 ## Oauth refresh token API details

URL: http://localhost:9000/api/oauth-service/oauth/token  <br/>
Method: Post  <br/>
Basic Authentication  <br/>
&nbsp;&nbsp;username: assignment  <br/>
&nbsp;&nbsp;password: assignmentSecret#$  <br/>
Request paramteres (x-www-form-urlencoded)  <br/>
&nbsp;&nbsp;grant_type = refresh_token  <br/>
&nbsp;&nbsp;refresh_token = <refresh token got in oauth token api response>  <br/>

Response example:  <br/>
      {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiJKb2huIiwibGFzdE5hbWUiOiJEb2UiLCJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU3NzYxNDQyMSwidXNlcklkIjoxLCJqdGkiOiJiZDExZDM0NS1lNzhmLTRjYTQtYjFhYS0xYjZiZjkyODBmZTEiLCJlbWFpbCI6Inh5ekBlbWFpbC5jb20iLCJjbGllbnRfaWQiOiJhc3NpZ25tZW50In0.PkESfc3Oj7JjcDO9WFJdzJm-l6lljAWKiDHWLHFTkt4",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiJKb2huIiwibGFzdE5hbWUiOiJEb2UiLCJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6ImJkMTFkMzQ1LWU3OGYtNGNhNC1iMWFhLTFiNmJmOTI4MGZlMSIsImV4cCI6MTU3NzYyMTQzOCwidXNlcklkIjoxLCJqdGkiOiI3ZDI2MmYxNC01MmIyLTRiMmYtYTVkNC02OTMwMzA0NmQ2ZTAiLCJlbWFpbCI6Inh5ekBlbWFpbC5jb20iLCJjbGllbnRfaWQiOiJhc3NpZ25tZW50In0.ZBR7IPsO8knHJGENW7LFiSMayPefUG65pR5KoLgVrro",
    "expires_in": 299,
    "scope": "read write"
}


 ## Secured API with OAUTH token

URL: http://localhost:9000/api/secured-service/v1/users/user/info  <br/>
Method: GET  <br/>
Header  <br/>
&nbsp;&nbsp; Authroization : bearer <access_token got from oauth api> <br/>

Response example:  <br/>
{
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "xyz@email.com"
}
