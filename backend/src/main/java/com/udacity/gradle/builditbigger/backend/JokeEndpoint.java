package com.udacity.gradle.builditbigger.backend;

import com.goldencrow.android.jokesupplier.JokeSmith;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public JokeBean sayHi(@Named("name") String name) {
        JokeBean response = new JokeBean();

        response.setJoke(JokeSmith.getJoke(name));

        return response;
    }

    /** A simple endpoint method that replies with a joke of a specific category. */
    @ApiMethod(name = "deliverJoke")
    public JokeBean deliverJoke(@Named("category") String category) {
        JokeBean response = new JokeBean();

        response.setJoke(JokeSmith.getJoke(category));

        return response;
    }

}
