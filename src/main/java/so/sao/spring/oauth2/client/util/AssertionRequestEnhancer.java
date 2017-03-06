package so.sao.spring.oauth2.client.util;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.util.MultiValueMap;

public class AssertionRequestEnhancer implements RequestEnhancer {

    public void enhance(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form,
            HttpHeaders headers) {
        System.out.println("XXXXX");
    }
}
