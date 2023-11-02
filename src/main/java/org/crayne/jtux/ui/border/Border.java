package org.crayne.jtux.ux.border;

import org.crayne.jtux.ux.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Border implements AbstractBorder {

    @NotNull
    private final BorderCharacter @NotNull [] characters;

    @Nullable
    private final Title topTitle, bottomTitle;

    protected Border(@Nullable final Title topTitle,
                  @Nullable final Title bottomTitle,
                  @NotNull final BorderCharacter @NotNull [] characters) {

        this.characters = characters;
        this.topTitle = topTitle;
        this.bottomTitle = bottomTitle;
    }

    @NotNull
    public BorderCharacter topEdge() {
        return characters[0];
    }

    @NotNull
    public BorderCharacter bottomEdge() {
        return characters[1];
    }

    @NotNull
    public BorderCharacter leftEdge() {
        return characters[2];
    }

    @NotNull
    public BorderCharacter rightEdge() {
        return characters[3];
    }

    @NotNull
    public BorderCharacter topLeftCorner() {
        return characters[4];
    }

    @NotNull
    public BorderCharacter topRightCorner() {
        return characters[5];
    }

    @NotNull
    public BorderCharacter bottomLeftCorner() {
        return characters[6];
    }

    @NotNull
    public BorderCharacter bottomRightCorner() {
        return characters[7];
    }

    @NotNull
    public Optional<Title> topTitle() {
        return Optional.ofNullable(topTitle);
    }

    @NotNull
    public Optional<Title> bottomTitle() {
        return Optional.ofNullable(bottomTitle);
    }

}
