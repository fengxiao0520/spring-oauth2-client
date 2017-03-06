package so.sao.spring.oauth2.client.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test {

    private String baseUrl = "http://localhost:8080";

    @Autowired
    private OAuth2RestOperations oAuth2RestTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        ResponseEntity<?> responseEntity = new ResponseEntity<String>("welcome", HttpStatus.OK);
        oAuth2RestTemplate.getForEntity(baseUrl + "/api/v1/test", Void.class);
        return responseEntity;
    }

    @GetMapping("/testPassword")
    public ResponseEntity<?> testPassword() {
        ResponseEntity<OAuth2AccessToken> responseEntity = oAuth2RestTemplate.getForEntity(
                baseUrl + "/api/v1/oauth/token?grant_type=password&username=user1&password=password", OAuth2AccessToken.class);
        final Object idToken = responseEntity.getBody().getAdditionalInformation().get("id_token");
        RequestCallback requestCallback = new RequestCallback() {

            public void doWithRequest(ClientHttpRequest request) throws IOException {
                request.getHeaders().set("Authorization", "Bearer " + idToken);
            }
        };
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Authorization", "Bearer " + idToken);

        responseEntity = restTemplate.execute(baseUrl + "/api/v1/testPassword", HttpMethod.GET, requestCallback, null);
        return responseEntity;
    }

}
