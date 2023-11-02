package org.crayne.jtux.text.util;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RandomString {

    private RandomString() {}

    @NotNull
    public static String randomString(final int length, final int min, final int max) {
        final Random random = new Random();
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) result.append((char) (min + random.nextInt(max)));

        return result.toString();
    }


}
