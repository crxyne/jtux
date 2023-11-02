package org.crayne.jtux.ui.border;

import org.crayne.jtux.ui.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class BorderBuilder {

    private final BorderCharacter @NotNull [] characters;

    @Nullable
    private Title topTitle, bottomTitle;

    public BorderBuilder() {
        characters = new BorderCharacter[8];
    }

    @NotNull
    public Border build() {
        if (Arrays.stream(characters).anyMatch(Objects::isNull))
            throw new UnsupportedOperationException("Cannot create Border object; Not all border characters were specified.");

        return new Border(topTitle, bottomTitle, characters);
    }

    @NotNull
    public static BorderBuilder builder() {
        return new BorderBuilder();
    }

    @Nullable
    public Title bottomTitle() {
        return bottomTitle;
    }

    @Nullable
    public Title topTitle() {
        return topTitle;
    }

    @NotNull
    public BorderBuilder bottomTitle(@Nullable final Title bottomTitle) {
        this.bottomTitle = bottomTitle;
        return this;
    }

    @NotNull
    public BorderBuilder topTitle(@Nullable final Title topTitle) {
        this.topTitle = topTitle;
        return this;
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
    public BorderBuilder horizontalEdge(@NotNull final BorderCharacter character) {
        topEdge(character);
        bottomEdge(character);
        return this;
    }

    @NotNull
    public BorderBuilder verticalEdge(@NotNull final BorderCharacter character) {
        leftEdge(character);
        rightEdge(character);
        return this;
    }

    @NotNull
    public BorderBuilder topEdge(@NotNull final BorderCharacter character) {
        characters[0] = character;
        return this;
    }

    @NotNull
    public BorderBuilder bottomEdge(@NotNull final BorderCharacter character) {
        characters[1] = character;
        return this;
    }

    @NotNull
    public BorderBuilder leftEdge(@NotNull final BorderCharacter character) {
        characters[2] = character;
        return this;
    }

    @NotNull
    public BorderBuilder rightEdge(@NotNull final BorderCharacter character) {
        characters[3] = character;
        return this;
    }

    @NotNull
    public BorderBuilder topLeftCorner(@NotNull final BorderCharacter character) {
        characters[4] = character;
        return this;
    }

    @NotNull
    public BorderBuilder topRightCorner(@NotNull final BorderCharacter character) {
        characters[5] = character;
        return this;
    }

    @NotNull
    public BorderBuilder bottomLeftCorner(@NotNull final BorderCharacter character) {
        characters[6] = character;
        return this;
    }

    @NotNull
    public BorderBuilder bottomRightCorner(@NotNull final BorderCharacter character) {
        characters[7] = character;
        return this;
    }

}
