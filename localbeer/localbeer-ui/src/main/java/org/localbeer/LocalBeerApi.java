package org.localbeer;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "localbeer-api", baseUri = "http://localhost:8082/api")
public interface LocalBeerApi {

    @GET
    @Path("/beers/starobrno")
    @Consumes("application/json")
    List<Beer> getStarobrnoBears(@HeaderParam(value = "Authorization") String accessToken);

    @GET
    @Path("/beers/starobrno/reveal")
    @Consumes("application/json")
    Beer revealNewBeer(@HeaderParam(value = "Authorization") String accessToken);

    record Beer(String name, int degree, String photoUrl) {}
}
