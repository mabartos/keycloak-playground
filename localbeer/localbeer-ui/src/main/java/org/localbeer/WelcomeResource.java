package org.localbeer;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class WelcomeResource {

    @Inject
    Template welcome;

    @GET
    public TemplateInstance welcome() {
        return welcome.instance();
    }
}
