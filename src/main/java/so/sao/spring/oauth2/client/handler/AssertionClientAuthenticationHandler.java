package so.sao.spring.oauth2.client.handler;

import java.security.KeyPair;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.auth.ClientAuthenticationHandler;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.MultiValueMap;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class AssertionClientAuthenticationHandler implements ClientAuthenticationHandler {

    public void authenticateTokenRequest(OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form,
            HttpHeaders headers) {
        if (resource.isAuthenticationRequired()) {

            form.set("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
            ResourceLoader resourceLoader = new DefaultResourceLoader();

            String clientSecret = resource.getClientSecret();

            try {
                KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                        resourceLoader.getResource("file:/F:/my-client-with-secret.key"), clientSecret.toCharArray());

                String clientId = resource.getClientId();
                KeyPair keyPair = keyStoreKeyFactory.getKeyPair(clientId, clientSecret.toCharArray());

                JWSHeader header = new JWSHeader(JWSAlgorithm.RS256);
                JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder();
                claimsSetBuilder.issuer(clientId).audience("authserver")
                        .expirationTime(Date.from(Instant.now(Clock.systemUTC()).plus(15L, ChronoUnit.DAYS))).subject(clientId)
                        .jwtID(UUID.randomUUID().toString());
                SignedJWT jwt = new SignedJWT(header, claimsSetBuilder.build());
                jwt.sign(new RSASSASigner(keyPair.getPrivate()));

                form.set("client_assertion", jwt.serialize());

            } catch (JOSEException e) {
                e.printStackTrace();
            }
        }
    }

}
