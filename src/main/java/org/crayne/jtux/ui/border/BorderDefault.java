package org.crayne.jtux.ui.border;

import org.crayne.jtux.text.color.ansi.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public enum BorderDefault {

    NONE(       ' ', ' ', ' ', ' ', ' ', ' '),
    NORMAL(     '─', '│', '┌', '┐', '└', '┘'),
    BOLD(       '━', '┃', '┏', '┓', '┗', '┛'),
    DOUBLE(     '═', '║', '╔', '╗', '╚', '╝'),
    DASHED(     '┄', '┆', '┌', '┐', '└', '┘'),
    DASHED_BOLD('┅', '┇', '┏', '┓', '┗', '┛'),
    ROUND(      '─', '│', '╭', '╮', '╰', '╯'),
    DIAGONAL(   '─', '│', '╱', '╲', '╲', '╱');

    @NotNull
    private final List<BorderCharacter> characters;

    BorderDefault(final int... characters) {
        this.characters = Arrays.stream(characters).mapToObj(i -> new BorderCharacter((char) i, TextColor.RESET)).toList();
    }

    @NotNull
    public BorderCharacter horizontal() {
        return characters.get(0);
    }

    @NotNull
    public BorderCharacter vertical() {
        return characters.get(1);
    }

    @NotNull
    public BorderCharacter topLeft() {
        return characters.get(2);
    }

    @NotNull
    public BorderCharacter topRight() {
        return characters.get(3);
    }

    @NotNull
    public BorderCharacter bottomLeft() {
        return characters.get(4);
    }

    @NotNull
    public BorderCharacter bottomRight() {
        return characters.get(5);
    }

    @NotNull
    public BorderBuilder builder() {
        return BorderBuilder.builder()
                .horizontalEdge(horizontal())
                .verticalEdge(vertical())
                .topLeftCorner(topLeft())
                .topRightCorner(topRight())
                .bottomLeftCorner(bottomLeft())
                .bottomRightCorner(bottomRight());
    }

}
