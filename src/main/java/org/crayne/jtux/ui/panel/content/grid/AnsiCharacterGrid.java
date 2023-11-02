package org.crayne.jtux.ux.panel.content.grid;

import org.crayne.jtux.text.color.ansi.TextColor;
import org.crayne.jtux.util.math.vec.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AnsiCharacterGrid extends CharacterGrid {

    @NotNull
    private final PrintStream out;

    @NotNull
    private final Map<Integer, Integer> garbageDeletion;

    public AnsiCharacterGrid(@NotNull final PrintStream out) {
        super();
        this.out = out;
        this.garbageDeletion = new HashMap<>();
    }

    public AnsiCharacterGrid(@NotNull final PrintStream out,
                             final int width, final int height) {
        super(width, height);
        this.out = out;
        this.garbageDeletion = new HashMap<>();
    }

    public AnsiCharacterGrid(@NotNull final PrintStream out,
                             final int width, final int height,
                             final int offsetX, final int offsetY) {
        super(width, height, offsetX, offsetY);
        this.out = out;
        this.garbageDeletion = new HashMap<>();
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
        garbageDeletionInitialState();
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

    private int deleteGarbageLater(final int fixedEndX, final int coordY) {
        garbageDeletion.put(coordY, fixedEndX < 0 ? width() : fixedEndX);

        return fixedEndX;
    }

    public int writeLineFast(@NotNull final Vec2i coord, @NotNull final String str) {
        return deleteGarbageLater(super.writeLineFast(coord, str), coord.y());
    }

    public int fillLine(@NotNull final Vec2i coord,
                         @NotNull final Function<Integer, Character> characterByIndex,
                         final int width) {

        return deleteGarbageLater(super.fillLine(coord, characterByIndex, width), coord.y());
    }

    private void garbageDeletionInitialState() {
        for (int y = 0; y < height(); y++) garbageDeletion.putIfAbsent(y, 0);
    }

    public void cleanUp() {
        textColor(TextColor.RESET);
        garbageDeletionInitialState();
        garbageDeletion.forEach((y, x) -> writeLineFast(Vec2i.of(x, y), " ".repeat(width() - x)));
        garbageDeletion.clear();
    }

    public void flush() {
        out.flush();
    }

    public void jump(@NotNull final Vec2i coord) {
        out.print("\33[" + (coord.y() + 1) + ";" + (coord.x() + 1) + "H");
    }

    public void clear() {
        for (int y = 0; y < height(); y++) writeLineFast(Vec2i.of(0, y), " ".repeat(width()));
    }

    public void putStringRaw(@NotNull final Vec2i coord, @NotNull final String str) {
        jump(coord);
        out.print(str);
    }

}
