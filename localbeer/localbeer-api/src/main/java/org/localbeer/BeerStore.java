package org.localbeer;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.ArrayList;
import java.util.List;

@Path("/api")
public class BeerStore {

    private final List<Beer> STAROBRNO_BEERS = new ArrayList<>(List.of(
            new Beer("Starobrno Medium", 11, "https://webadmin.starobrno.cz/media/nase-pivo/starobrno-product_bg_8-dark.png?width=900&height=0&rmode=min&format=webp&quality=75&token=DvmxIOqQIhf3vJVoOM58yVjtK817mbEoCBiO0GhikkA%3D"),
            new Beer("Starobrno Bitr", 11, "https://webadmin.starobrno.cz/media/nase-pivo/starobrno-product_bg_8-light.png?width=900&height=0&rmode=min&format=webp&quality=75&token=DvmxIOqQIhf3vJVoOM58yVjtK817mbEoCBiO0GhikkA%3D"),
            new Beer("Starobrno Tradiční", 10, "https://webadmin.starobrno.cz/media/nase-pivo/tradicni-product_bg_9-dark.png?width=900&height=0&rmode=min&format=webp&quality=75&token=DvmxIOqQIhf3vJVoOM58yVjtK817mbEoCBiO0GhikkA%3D"))
    );

    @GET
    @Path("/beers/starobrno")
    @Authenticated
    public List<Beer> starobrnoBeers() {
        return STAROBRNO_BEERS;
    }

    @GET
    @Path("/beers/starobrno/reveal")
    @Authenticated
    public Beer revealNewStarobrnoBeer() {
        var statl = new Beer("Starobrno Štatl", 12, "https://webadmin.starobrno.cz/media/nase-pivo/statl_image-content_2.png?width=900&height=0&rmode=min&format=webp&quality=75&token=DvmxIOqQIhf3vJVoOM58yVjtK817mbEoCBiO0GhikkA%3D");
        STAROBRNO_BEERS.add(statl);
        return statl;
    }

    public record Beer(String name, int degree, String photoUrl) {
    }

}
