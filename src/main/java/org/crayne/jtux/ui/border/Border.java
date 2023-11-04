package org.crayne.jtux.ui.border;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Border implements AbstractBorder {

    @NotNull
    private final BorderCharacter @NotNull [] characters;

    @Nullable
    private final BorderTitle topBorderTitle, bottomBorderTitle;

    protected Border(@Nullable final BorderTitle topBorderTitle,
                  @Nullable final BorderTitle bottomBorderTitle,
                  @NotNull final BorderCharacter @NotNull [] characters) {

        this.characters = characters;
        this.topBorderTitle = topBorderTitle;
        this.bottomBorderTitle = bottomBorderTitle;
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
    public Optional<BorderTitle> topTitle() {
        return Optional.ofNullable(topBorderTitle);
    }

    @NotNull
    public Optional<BorderTitle> bottomTitle() {
        return Optional.ofNullable(bottomBorderTitle);
    }

}
