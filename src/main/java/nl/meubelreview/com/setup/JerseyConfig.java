package nl.meubelreview.com.setup;

import nl.meubelreview.com.security.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("restservices")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("nl.meubelreview.com.webservices", "nl.meubelreview.com.security");
        register(AuthenticationFilter.class);
    }
}
