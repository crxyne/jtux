package org.crayne.jtux.ui.grid;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.color.ansi.TextColor;
import org.crayne.jtux.text.color.ansi.TextColorBuilder;
import org.crayne.jtux.text.component.Text;
import org.crayne.jtux.text.component.TextPart;
import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.border.BorderCharacter;
import org.crayne.jtux.ui.border.BorderTitle;
import org.crayne.jtux.ui.component.layout.Alignment;
import org.crayne.jtux.util.vector.Vec2i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("UnusedReturnValue")
public abstract class CharacterGrid {

    private int width, height, offsetX, offsetY;

    public CharacterGrid() {
        this.width = -1;
        this.height = -1;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public CharacterGrid(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public CharacterGrid(final int width, final int height, final int offsetX, final int offsetY) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @NotNull
    public abstract CharacterGrid createChunk(final int offsetX, final int offsetY, final int width, final int height);

    public abstract void textColor(@NotNull final TextColor color);

    public abstract void resetTextColor();

    public abstract void putCharacterRaw(@NotNull final Vec2i coord, final char character);

    public abstract void putStringRaw(@NotNull final Vec2i coord, @NotNull final String str);

    public abstract void clear();

    public abstract void flush();

    public void cleanUp() {

    }

    public int width() {
        return width;
    }

    public void width(final int width) {
        this.width = width;
    }

    public int height() {
        return height;
    }

    public void height(final int height) {
        this.height = height;
    }

    public int offsetX() {
        return offsetX;
    }

    public void offsetX(final int offsetX) {
        this.offsetX = offsetX;
    }

    public int offsetY() {
        return offsetY;
    }

    public void offsetY(final int offsetY) {
        this.offsetY = offsetY;
    }

    public void updateSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @NotNull
    public CharacterGrid createContentGrid(final boolean hasBorder) {
        if (!hasBorder) return createChunk(offsetX(), offsetY(), width(), height());

        return createChunk(offsetX() + 1, offsetY() + 1, width() - 2, height() - 2);
    }

    public void textColor(@Nullable final Color foregroundColor, @Nullable final Color backgroundColor) {
        textColor(new TextColorBuilder().foreground(foregroundColor).background(backgroundColor).build());
    }

    public void putCharacter(@NotNull final Vec2i coord, final char character) {
        if (coord.x() >= width() || coord.y() >= height() || coord.x() < 0 || coord.y() < 0) return;
        putCharacterRaw(coord.add(offsetX(), offsetY()), character);
    }

    public void clearCharacter(@NotNull final Vec2i coord) {
        resetTextColor();
        putCharacter(coord, ' ');
    }

    public int printStackTrace(@NotNull final Throwable t) {
        return printStackTrace(Vec2i.origin(), t);
    }

    public int printStackTrace(@NotNull final Vec2i coord, @NotNull final Throwable t) {
        writeLineWrap(coord, Text.text(t.toString()));
        return printStackTrace(coord.add(0, 1), t.getStackTrace());
    }

    public int printStackTrace(@NotNull final Vec2i coord, @NotNull final StackTraceElement[] elements) {
        final List<Text> elementsText = Arrays.stream(elements)
                .map(StackTraceElement::toString)
                .map(s -> "\tat " + s)
                .map(Text::text)
                .toList();

        return writeLinesWrap(coord, elementsText);
    }

    public void clearAndFlush() {
        clear();
        flush();
    }

    public void putString(@NotNull final Vec2i coord, @NotNull final String str) {
        if (coord.x() >= width() || coord.y() >= height() || coord.x() < 0 || coord.y() < 0) return;
        putStringRaw(coord.add(offsetX(), offsetY()), str);
    }

    public void fillLine(@NotNull final Vec2i coord, final char character, final int width) {
        fillLine(coord, i -> character, width);
    }

    public void fillLine(@NotNull final Vec2i coord, @NotNull final String str, final int width) {
        fillLine(coord, i -> str.charAt(i % str.length()), width);
    }

    public void fillLine(@NotNull final Vec2i coord, final char character) {
        fillLine(coord, character, width());
    }

    public void fillLine(@NotNull final Vec2i coord, @NotNull final String str, final boolean wrapAround) {
        fillLine(coord, str, wrapAround ? width() : str.length());
    }

    public void writeLine(@NotNull final Vec2i coord, @NotNull final String str) {
        fillLine(coord, str, false);
    }

    public int writeLineFast(@NotNull final Vec2i coord, @NotNull final String str) {
        final int startX = coord.x();
        final int endX = startX + str.length();
        final int fixedEndX = Math.min(endX, width());
        if (fixedEndX - startX < 0) return -1;

        putString(coord, str.substring(0, fixedEndX - startX));
        return endX;
    }

    public void writeLine(@NotNull final Vec2i coord, @NotNull final Text text) {
        int offset = 0;
        for (final TextPart part : text.parts())  {
            final int length = part.text().length();
            if (length == 0) continue;

            writeLine(Vec2i.of(coord.x() + offset, coord.y()), part);
            offset += length;
        }
        resetTextColor();
    }

    public void writeLine(@NotNull final Vec2i coord, @NotNull final Text text, final boolean wrap) {
        if (wrap) {
            writeLineWrap(coord, text);
            return;
        }
        writeLine(coord, text);
    }

    public int writeLinesWrap(@NotNull final Vec2i coord, @NotNull final Collection<Text> texts) {
        final int coordX = coord.x();
        int coordY = coord.y();

        for (final Text text : texts) {
            final int newOffset = writeLineWrap(Vec2i.of(coordX, coordY), text);
            coordY += newOffset + 1;
        }
        return coordY;
    }

    public int linesPrintedWrap(@NotNull final Vec2i coord, @NotNull final Text text) {
        int offsetX = 0, offsetY = 0;
        final int width = width(), height = height();
        int coordX = coord.x();
        final int coordY = coord.y();

        for (final TextPart part : text.parts())  {
            int length = part.text().length();
            if (length == 0) continue;

            while (offsetX + coordX + length > width) {
                length -= width - offsetX - coordX;
                offsetY++;
                offsetX = 0;
                coordX = 0;

                if (offsetY > height) return offsetY;
            }
            offsetX += length;
        }
        return offsetY;
    }

    public int writeLineWrap(@NotNull final Vec2i coord, @NotNull final Text text) {
        int offsetX = 0, offsetY = 0;
        final int width = width(), height = height();
        int coordX = coord.x();
        final int coordY = coord.y();

        for (final TextPart part : text.parts())  {
            String partText = part.text();
            if (partText.length() == 0) continue;

            part.color().ifPresent(this::textColor);

            while (offsetX + coordX + partText.length() > width) {
                final String toPrint = partText.substring(0, width - offsetX - coordX);
                if (!toPrint.isEmpty()) writeLineFast(Vec2i.of(coordX + offsetX, coordY + offsetY), toPrint);

                partText = partText.substring(toPrint.length());
                offsetY++;
                offsetX = 0;
                coordX = 0;

                if (offsetY > height) {
                    resetTextColor();
                    return offsetY;
                }
            }
            if (!partText.isEmpty()) writeLineFast(Vec2i.of(coordX + offsetX, coordY + offsetY), partText);
            offsetX += partText.length();
        }
        resetTextColor();
        return offsetY;
    }

    public void writeLine(@NotNull final Vec2i coord, @NotNull final TextPart textPart) {
        textPart.color().ifPresent(this::textColor);
        writeLineFast(coord, textPart.text());
    }

    public int fillLine(@NotNull final Vec2i coord,
                  @NotNull final Function<Integer, Character> characterByIndex,
                  final int width) {

        final int endX = coord.x() + width;
        final int fixedEndX = Math.min(endX, width());

        for (int i = 0; i < fixedEndX - coord.x(); i++) {
            putCharacter(Vec2i.of(i + coord.x(), coord.y()), characterByIndex.apply(i));
        }
        return endX;
    }

    public void fillBar(@NotNull final Vec2i coord, final char character, final int height) {
        fillBar(coord, i -> character, height);
    }

    public void fillBar(@NotNull final Vec2i coord, @NotNull final String str, final int height) {
        fillBar(coord, i -> str.charAt(i % str.length()), height);
    }

    public void fillBar(@NotNull final Vec2i coord, final char character) {
        fillBar(coord, character, height());
    }

    public void fillBar(@NotNull final Vec2i coord, @NotNull final String str, final boolean wrapAround) {
        fillBar(coord, str, wrapAround ? height() : str.length());
    }

    public void writeBar(@NotNull final Vec2i coord, @NotNull final String str) {
        fillBar(coord, str, false);
    }
    
    public void fillBar(@NotNull final Vec2i coord,
                 @NotNull final Function<Integer, Character> characterByIndex,
                 final int height) {

        final int endY = coord.y() + height;
        final int fixedEndY = Math.min(endY, height());

        for (int i = 0; i < fixedEndY - coord.y(); i++) {
            putCharacter(Vec2i.of(coord.x(), i + coord.y()), characterByIndex.apply(i));
        }
    }

    public void drawTopEdge(@NotNull final AbstractBorder border) {
        drawHorizontalEdge(border.topEdge(), border.topTitle().orElse(null), 0);
    }

    public void drawBottomEdge(@NotNull final AbstractBorder border) {
        drawHorizontalEdge(border.bottomEdge(), border.bottomTitle().orElse(null), height() - 1);
    }

    public void drawLeftEdge(@NotNull final AbstractBorder border) {
        drawVerticalEdge(border.leftEdge(), 0);
    }

    public void drawRightEdge(@NotNull final AbstractBorder border) {
        drawVerticalEdge(border.rightEdge(), width() - 1);
    }

    public void drawTopLeftCorner(@NotNull final AbstractBorder border) {
        textColor(border.topLeftCorner().color());
        putCharacter(Vec2i.of(0, 0), border.topLeftCorner().character());
    }

    public void drawTopRightCorner(@NotNull final AbstractBorder border) {
        textColor(border.topRightCorner().color());
        putCharacter(Vec2i.of(width() - 1, 0), border.topRightCorner().character());
    }

    public void drawBottomLeftCorner(@NotNull final AbstractBorder border) {
        textColor(border.bottomLeftCorner().color());
        putCharacter(Vec2i.of(0, height() - 1), border.bottomLeftCorner().character());
    }

    public void drawBottomRightCorner(@NotNull final AbstractBorder border) {
        textColor(border.bottomRightCorner().color());
        putCharacter(Vec2i.of(width() - 1, height() - 1), border.bottomRightCorner().character());
    }

    public void drawCorners(@NotNull final AbstractBorder border) {
        drawTopLeftCorner(border);
        drawTopRightCorner(border);
        drawBottomLeftCorner(border);
        drawBottomRightCorner(border);
    }

    public void drawHorizontalEdge(@NotNull final BorderCharacter borderCharacter, @Nullable final BorderTitle borderTitle, final int coordY) {
        textColor(borderCharacter.color());
        final char borderChar = borderCharacter.character();
        final int topEdgeWidth = width() - 2;
        if (topEdgeWidth <= 0) return;

        if (borderTitle == null) {
            fillLine(Vec2i.of(1, coordY), borderChar, topEdgeWidth);
            return;
        }
        final Text titleText = borderTitle.title();
        final int titleWidth = titleText.text().length();
        final float titleAlignment = borderTitle.alignment();

        final int leftBorderWidth = Alignment.align(titleText.text(), topEdgeWidth, titleAlignment);
        final int rightBorderWidth = topEdgeWidth - leftBorderWidth - titleWidth;

        fillLine(Vec2i.of(1, coordY), borderChar, leftBorderWidth);
        writeLine(Vec2i.of(leftBorderWidth + 1, coordY), titleText);
        fillLine(Vec2i.of(leftBorderWidth + 1 + titleWidth, coordY), borderChar, rightBorderWidth);
    }

    public void drawVerticalEdge(@NotNull final BorderCharacter borderCharacter, final int coordX) {
        textColor(borderCharacter.color());
        fillBar(Vec2i.of(coordX, 1), borderCharacter.character(), height() - 2);
    }

    public void drawEdges(@NotNull final AbstractBorder border) {
        drawBottomEdge(border);
        drawTopEdge(border);
        drawRightEdge(border);
        drawLeftEdge(border);
    }

    public void drawBorder(@NotNull final AbstractBorder border) {
        drawEdges(border);
        drawCorners(border);
    }

}
