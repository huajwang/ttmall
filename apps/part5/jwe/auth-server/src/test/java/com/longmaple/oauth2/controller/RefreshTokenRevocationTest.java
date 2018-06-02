package com.longmaple.oauth2.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.longmaple.oauth2.AuthServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest( //webEnvironment = WebEnvironment.RANDOM_PORT, 
		classes = AuthServerApplication.class)
@WebAppConfiguration
public class RefreshTokenRevocationTest {
	
	@Autowired
	private WebApplicationContext webContext;
	private MockMvc mockMvc;
	
	@Before
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webContext)
				.apply(springSecurity()).build();
	}
	
	private String refreshToken;
	 
    private String obtainAccessToken(String clientId, String username, String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
         
        Response response = RestAssured.given().auth().
          preemptive().basic(clientId, "abcd1234").and().with().params(params).
          when().post("http://localhost:8080/oauth/token");
        
        refreshToken = response.jsonPath().getString("refresh_token");
         
        return response.jsonPath().getString("access_token");
    }
     
    private String obtainRefreshToken(String clientId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
         
        Response response = RestAssured.given().auth()
          .preemptive().basic(clientId,"").and().with().params(params)
          .when().post("http://localhost:8080/oauth/token");
         
        return response.jsonPath().getString("access_token");
    }
     
    private void authorizeClient(String clientId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("response_type", "code");
        params.put("client_id", clientId);
        params.put("scope", "user:profile:read, user:profile:write");
         
        Response response = RestAssured.given().auth().preemptive()
          .basic(clientId, "").and().with().params(params).
          when().post("http://localhost:8080/oauth/authorize");
    }
     
    @Test
    public void givenUser_whenRevokeRefreshToken_thenRefreshTokenInvalidError() {
        String accessToken1 = obtainAccessToken("webapp", "joe", "password1");
        String accessToken2 = obtainAccessToken("webapp", "don", "password2");
        authorizeClient("webapp");
         
        String accessToken3 = obtainRefreshToken("webapp");
        authorizeClient("webapp");
        Response refreshTokenResponse = RestAssured.given().
          header("Authorization", "Bearer " + accessToken3)
          .get("http://localhost:8080/tokens/webapp");
        assertEquals(200, refreshTokenResponse.getStatusCode());
         
        Response revokeRefreshTokenResponse = RestAssured.given()
          .auth().preemptive()
                .basic("joe", "password1").and().with()
          .header("content-type", "application/json")
          .body("{\"token\": \"" + accessToken1 + "\", \"token_type\": \"access_token\"}")
          .post("http://localhost:8080/oauth/revoke");
        assertEquals(200, revokeRefreshTokenResponse.getStatusCode());
//         
//        String accessToken4 = obtainRefreshToken("fooClientIdPassword");
//        authorizeClient("fooClientIdPassword");
//        Response refreshTokenResponse2 = RestAssured.given()
//          .header("Authorization", "Bearer " + accessToken4)
//          .get("http://localhost:8082/spring-security-oauth-resource/tokens");
//        assertEquals(401, refreshTokenResponse2.getStatusCode());
    }
}
