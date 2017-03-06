package so.sao.spring.oauth2.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class SpringOAuth2ClientApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringOAuth2ClientApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringOAuth2ClientApplication.class);
    }
}
