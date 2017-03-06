package so.sao.spring.oauth2.client.entity;

import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

public class AuthorizationAssertionResourceDetails extends BaseOAuth2ProtectedResourceDetails {

    public AuthorizationAssertionResourceDetails() {
        setGrantType("urn:ietf:params:oauth:grant-type:jwt-bearer");
    }
}
