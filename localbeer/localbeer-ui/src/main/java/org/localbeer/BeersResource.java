package org.localbeer;

import io.quarkus.oidc.IdToken;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.URI;
import java.util.List;

@Path("/beers")
@Authenticated
public class BeersResource {

    @Inject
    Template beers;

    @Inject
    JsonWebToken accessToken;

    @Inject
    @IdToken
    JsonWebToken idToken;

    @Inject
    @RestClient
    LocalBeerApi localBeerApi;

    @GET
    public TemplateInstance beersPage() {
        var name = idToken.getClaim("name");
        var email = idToken.getClaim("email");
        var isAdmin = idToken.getGroups().contains("admin");

        // calling backend API with the token
        List<LocalBeerApi.Beer> availableBeers = localBeerApi.getStarobrnoBears("Bearer " + accessToken.getRawToken());

        return beers.data("name", name)
                .data("email", email)
                .data("isAdmin", isAdmin)
                .data("beers", availableBeers);
    }

    @GET
    @Path("reveal-new")
    @RolesAllowed("admin")
    public Response revealNewBeer() {
        localBeerApi.revealNewBeer("Bearer " + accessToken.getRawToken());
        return Response.status(Response.Status.FOUND).location(URI.create("/beers")).build();
    }
}
