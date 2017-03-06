package so.sao.spring.oauth2.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.auth.ClientAuthenticationHandler;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;

import so.sao.spring.oauth2.client.handler.AssertionClientAuthenticationHandler;

@EnableOAuth2Client
@Configuration
public class OAuth2ClientConfig extends OAuth2ClientConfiguration {

    private String tokenUrl = "http://localhost:8080/api/v1/oauth/token";

    @Autowired
    private OAuth2ClientContext oauth2Context;

    @Bean
    protected OAuth2RestOperations oAuth2RestTemplate() {

        ClientCredentialsAccessTokenProvider accessTokenProvider = new ClientCredentialsAccessTokenProvider();
        accessTokenProvider.setAuthenticationHandler(clientAuthenticationHandler());

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource(), oauth2Context);
        restTemplate.setAccessTokenProvider(accessTokenProvider);

        return restTemplate;
    }

    @Bean
    protected OAuth2ProtectedResourceDetails resource() {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(tokenUrl);
        resource.setClientId("my-client-with-secret");
        resource.setClientSecret("my-client-with-secret");
        resource.setAuthenticationScheme(AuthenticationScheme.header);
        return resource;
    }

    @Bean
    public ClientAuthenticationHandler clientAuthenticationHandler() {
        return new AssertionClientAuthenticationHandler();
    }

}
