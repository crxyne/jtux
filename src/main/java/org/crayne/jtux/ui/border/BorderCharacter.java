package org.crayne.jtux.ux.border;

import org.crayne.jtux.text.color.ansi.TextColor;
import org.jetbrains.annotations.NotNull;

public class BorderCharacter {

    private final char character;

    @NotNull
    private final TextColor color;

    public BorderCharacter(final char character, @NotNull final TextColor color) {
        this.character = character;
        this.color = color;
    }

    public char character() {
        return character;
    }

    @NotNull
    public TextColor color() {
        return color;
    }

}
