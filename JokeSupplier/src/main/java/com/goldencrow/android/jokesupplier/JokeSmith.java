package com.goldencrow.android.jokesupplier;

import java.util.Random;

public class JokeSmith {

    /**
     * Returns a joke of a specific category, if that category exists.
     *
     * @param category  the category from which the joke will be supplied.
     * @return          the joke from that category.
     */
    public static String getJoke(String category) {
        String joke = "No joke found :(";

        switch (category) {
            case "Physics":
                joke = getRandomValue(getPhysicsJokes());
                break;
            case "Computer Science":
                joke = getRandomValue(getComputerScienceJokes());
                break;
            case "Software Pick Up Line":
                joke = getRandomValue(getComputerSciencePickUpLines());
                break;
        }

        return joke;
    }

    /**
     * Returns a random value from an array.
     *
     * @param values    the set of values.
     * @return          a random item in it.
     */
    private static String getRandomValue(String[] values) {
        Random random = new Random();
        int max = values.length - 1;
        int min = 0;

        int pos = random.nextInt((max - min) + 1) + min;

        return values[pos];
    }

    /**
     * Contains all Physics Jokes.
     */
    private static String[] getPhysicsJokes() {
        return new String[] {
                "Atom_1: 'I think I lost an electron.',\nAtom_2: 'Are you positive?'",
                "A neutron walks into a bar and asks how much for a drink.\n" +
                        "The bartender replies: 'For you, no charge.'"
        };
    }

    /**
     * Contains all Computer Science Jokes.
     */
    private static String[] getComputerScienceJokes() {
        return new String[] {
                "Q: How many programmers does it take to change a light bulb?\n" +
                        "A: None, that's a hardware problem.",
                "I love pressing F5. It's so refreshing."
        };
    }

    /**
     * Contains all Computer Science Pick Up Lines.
     */
    private static String[] getComputerSciencePickUpLines() {
        return new String[] {
                "You make my software turn into hardware.",
                "Hey --, my servers never go down, But I do.",
                "Hey --, I hope you're an ISO-file. Because I'd like to mount you.",
                "Are your pants a compressed file? Because I'd like to unzip them."
        };
    }

}
