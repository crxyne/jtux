package org.crayne.jtux.ui.grid;

import org.crayne.jtux.text.color.ansi.TextColor;
import org.crayne.jtux.text.util.TextUtil;
import org.crayne.jtux.util.vector.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;

public class AnsiCharacterGrid extends CharacterGrid {

    @NotNull
    private final PrintStream out;

    @NotNull
    private final Map<Integer, List<Vec2i>> writtenTo;

    public AnsiCharacterGrid(@NotNull final PrintStream out) {
        super();
        this.out = out;
        this.writtenTo = new HashMap<>();
    }

    public AnsiCharacterGrid(@NotNull final PrintStream out,
                             final int width, final int height) {
        super(width, height);
        this.out = out;
        this.writtenTo = new HashMap<>();
    }

    public AnsiCharacterGrid(@NotNull final PrintStream out,
                             final int width, final int height,
                             final int offsetX, final int offsetY) {
        super(width, height, offsetX, offsetY);
        this.out = out;
        this.writtenTo = new HashMap<>();
    }

    @NotNull
    public PrintStream out() {
        return out;
    }

    @NotNull
    public CharacterGrid createChunk(final int offsetX, final int offsetY, final int width, final int height) {
        return new AnsiCharacterGrid(out, width, height, offsetX() + offsetX, offsetY() + offsetY);
    }

    public void updateSize(final int width, final int height) {
        super.updateSize(width, height);
        writtenTo.clear();
    }

    public void textColor(@NotNull final TextColor color) {
        out.print(color);
    }

    public void resetTextColor() {
        out.print(TextColor.RESET_ANSI_STRING);
    }

    public void putCharacterRaw(@NotNull final Vec2i coord, final char character) {
        jump(coord);
        out.print(character);
    }

    private int writtenTo(final int end, final int start, final int coordY) {
        if (end < 0 || start < 0 || coordY < 0) return end;

        writtenTo.putIfAbsent(coordY, new ArrayList<>());
        writtenTo.get(coordY).add(Vec2i.of(start, end));

        return end;
    }

    public int writeLineFast(@NotNull final Vec2i coord, @NotNull final String str) {
        return writtenTo(super.writeLineFast(coord, str), coord.x(), coord.y());
    }

    public int fillLine(@NotNull final Vec2i coord,
                         @NotNull final Function<Integer, Character> characterByIndex,
                         final int width) {

        return writtenTo(super.fillLine(coord, characterByIndex, width), coord.x(), coord.y());
    }

    private static int skipAhead(final int pos, @NotNull final List<Vec2i> slices) {
        final Optional<Vec2i> containedWithin = slices.stream()
                .filter(v -> pos >= v.x() && pos < v.y())
                .findAny();

        return containedWithin.map(Vec2i::y).orElse(-1);
    }

    private void clearLine(final int x, final int y, final int amount) {
        // super because we do not want this coordinate to add to writtenTo
        super.writeLineFast(Vec2i.of(x, y), TextUtil.blank(amount));
    }

    public void cleanUp() {
        textColor(TextColor.RESET);
        if (writtenTo.isEmpty()) return;

        final int height = height();
        for (int y = 0; y < height; y++) cleanUpLine(y);

        writtenTo.clear();
    }

    public void cleanUpLine(final int y) {
        final int width = width();
        final List<Vec2i> writtenTo = this.writtenTo.get(y);

        if (writtenTo == null) {
            clearLine(0, y, width);
            return;
        }

        int currentStart = 0;
        int currentPosition = 0;

        while (currentPosition < width) {
            final int skipAhead = skipAhead(currentPosition, writtenTo);
            if (skipAhead == -1) {
                currentPosition++;
                continue;
            }
            if (currentPosition - currentStart > 0)
                clearLine(currentStart, y, currentPosition - currentStart);

            currentStart = skipAhead;
            currentPosition = skipAhead;
        }
        if (width - currentStart > 0)
            clearLine(currentStart, y, width - currentStart);
    }

    public void flush() {
        out.flush();
    }

    public void jump(@NotNull final Vec2i coord) {
        out.print("\33[" + (coord.y() + 1) + ";" + (coord.x() + 1) + "H");
    }

    public void clear() {
        textColor(TextColor.RESET);
        final int width = width(), height = height();
        for (int y = 0; y < height; y++) super.writeLineFast(Vec2i.of(0, y), TextUtil.blank(width));
    }

    public void putStringRaw(@NotNull final Vec2i coord, @NotNull final String str) {
        jump(coord);
        out.print(str);
    }

}
