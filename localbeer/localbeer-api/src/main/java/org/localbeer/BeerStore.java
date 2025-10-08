package org.localbeer;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/api")
public class BeerStore {

    private final List<Beer> STAROBRNO_BEERS = new ArrayList<>(List.of(
            new Beer("Starobrno Medium", 11, "https://webadmin.starobrno.cz/media/nase-pivo/starobrno-product_bg_8-dark.png?width=900&height=0&rmode=min&format=webp&quality=75&token=DvmxIOqQIhf3vJVoOM58yVjtK817mbEoCBiO0GhikkA%3D"),
            new Beer("Starobrno Bitr", 11, "https://webadmin.starobrno.cz/media/nase-pivo/starobrno-product_bg_8-light.png?width=900&height=0&rmode=min&format=webp&quality=75&token=DvmxIOqQIhf3vJVoOM58yVjtK817mbEoCBiO0GhikkA%3D"),
            new Beer("Starobrno Tradiční", 10, "https://webadmin.starobrno.cz/media/nase-pivo/tradicni-product_bg_9-dark.png?width=900&height=0&rmode=min&format=webp&quality=75&token=DvmxIOqQIhf3vJVoOM58yVjtK817mbEoCBiO0GhikkA%3D"))
    );

    private List<Beer> STAROBRNO_BEERS_UNREVEALED = List.of(
            new Beer("Starobrno Štatl", 12, "https://webadmin.starobrno.cz/media/nase-pivo/statl_image-content_2.png?width=900&height=0&rmode=min&format=webp&quality=75&token=DvmxIOqQIhf3vJVoOM58yVjtK817mbEoCBiO0GhikkA%3D")
    );

    @GET
    @Path("/beers/starobrno")
    @Authenticated
    public List<Beer> starobrnoBeers() {
        return STAROBRNO_BEERS;
    }

    @GET
    @Path("/beers/starobrno/reveal")
    @RolesAllowed("admin")
    public boolean areNewBeersAvailable() {
        return !STAROBRNO_BEERS_UNREVEALED.isEmpty();
    }

    @POST
    @Path("/beers/starobrno/reveal")
    @RolesAllowed("admin")
    public List<Beer> revealNewStarobrnoBeers() {
        if (!areNewBeersAvailable()) {
            return Collections.emptyList();
        }
        var revealed = new ArrayList<>(STAROBRNO_BEERS_UNREVEALED);

        STAROBRNO_BEERS.addAll(STAROBRNO_BEERS_UNREVEALED);
        STAROBRNO_BEERS_UNREVEALED = List.of();
        return revealed;
    }

    public record Beer(String name, int degree, String photoUrl) {
    }

}
