package org.crayne.jtux.text.component;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.color.ansi.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TextPart {

    @Nullable
    private TextColor color;

    @NotNull
    private final String text;

    public TextPart(@Nullable final TextColor color, @Nullable final String text) {
        this.color = color;
        this.text = text == null ? "" : text;
    }

    @NotNull
    public static TextPart empty() {
        return new TextPart(null, null);
    }

    @NotNull
    public static TextPart of(@NotNull final String text) {
        return new TextPart(null, text);
    }

    @NotNull
    public static TextPart of(@Nullable final TextColor color, @NotNull final String text) {
        return new TextPart(color, text);
    }

    @NotNull
    public static TextPart of(@NotNull final Color color, @NotNull final String text) {
        return of(color, text, true);
    }

    @NotNull
    public static TextPart of(@NotNull final Color color, @NotNull final String text, final boolean foreground) {
        return new TextPart(foreground ? TextColor.foreground(color) : TextColor.background(color), text);
    }

    public boolean invisible() {
        return color().map(c -> !c.reset()).orElse(true) && text.isEmpty();
    }

    @NotNull
    public String text() {
        return text;
    }

    @NotNull
    public Optional<TextColor> color() {
        return Optional.ofNullable(color);
    }

    @NotNull
    public TextPart color(@Nullable final TextColor color) {
        this.color = color;
        return this;
    }

    @NotNull
    public TextPart createCopy() {
        return new TextPart(color, text);
    }

    @NotNull
    public String toString() {
        return color == null ? text : (color + text);
    }
}
