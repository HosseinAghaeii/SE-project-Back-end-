package ir.segroup.unipoll.config.document;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class OpenApiConf {
    private final Environment environment;

    public OpenApiConf(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI openApiConfig() {
        Server server = new Server()
                .url(environment.getProperty("doc.server.url"))
                .description(environment.getProperty("doc.server.description"));

        Contact contact = new Contact()
                .name(environment.getProperty("doc.contact.name"))
                .url(environment.getProperty("doc.contact.url"));

        Info info = new Info()
                .title(environment.getProperty("doc.info.title"))
                .description(environment.getProperty("doc.info.description"))
                .version(environment.getProperty("doc.info.version"))
                .contact(contact);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}

