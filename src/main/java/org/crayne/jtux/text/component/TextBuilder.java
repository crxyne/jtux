package org.crayne.jtux.text.component;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.color.ansi.TextColor;
import org.crayne.jtux.text.color.ansi.TextColorBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TextBuilder {

    @NotNull
    private TextColorBuilder textColorBuilder;

    @Nullable
    private String text;

    @NotNull
    private final List<TextPart> parts;

    public TextBuilder() {
        textColorBuilder = new TextColorBuilder();
        textColorBuilder.reset(true);
        parts = new ArrayList<>();
    }

    protected TextBuilder(@NotNull final List<TextPart> parts) {
        this();
        this.parts.addAll(parts);
    }

    public void clearAll() {
        clearCurrent();
        parts.clear();
    }

    public void clearCurrent() {
        textColorBuilder = new TextColorBuilder();
        textColorBuilder.reset(true);
        text = null;
    }

    @NotNull
    public TextBuilder foreground(@Nullable final Color fg) {
        textColorBuilder.foreground(fg);
        textColorBuilder.reset(false);
        return this;
    }

    @NotNull
    public TextBuilder background(@Nullable final Color bg) {
        textColorBuilder.background(bg);
        textColorBuilder.reset(false);
        return this;
    }

    @NotNull
    public TextBuilder reset(final boolean b) {
        textColorBuilder.reset(b);
        return this;
    }

    @NotNull
    public TextBuilder bold(final boolean b) {
        textColorBuilder.bold(b);
        return this;
    }

    @NotNull
    public TextBuilder dim(final boolean b) {
        textColorBuilder.dim(b);
        return this;
    }

    @NotNull
    public TextBuilder italic(final boolean b) {
        textColorBuilder.italic(b);
        return this;
    }

    @NotNull
    public TextBuilder underline(final boolean b) {
        textColorBuilder.underline(b);
        return this;
    }

    @NotNull
    public TextBuilder blinking(final boolean b) {
        textColorBuilder.blinking(b);
        return this;
    }

    @NotNull
    public TextBuilder inverted(final boolean b) {
        textColorBuilder.inverted(b);
        return this;
    }

    @NotNull
    public TextBuilder hidden(final boolean b) {
        textColorBuilder.hidden(b);
        return this;
    }

    @NotNull
    public TextBuilder strikethrough(final boolean b) {
        textColorBuilder.strikethrough(b);
        return this;
    }

    @NotNull
    public Optional<Color> foreground() {
        return textColorBuilder.foreground();
    }

    @NotNull
    public Optional<Color> background() {
        return textColorBuilder.background();
    }

    @NotNull
    public TextColor color() {
        return textColorBuilder.build();
    }

    public boolean reset() {
        return textColorBuilder.reset();
    }

    public boolean bold() {
        return textColorBuilder.bold();
    }

    public boolean dim() {
        return textColorBuilder.dim();
    }

    public boolean italic() {
        return textColorBuilder.italic();
    }

    public boolean underline() {
        return textColorBuilder.underline();
    }

    public boolean blinking() {
        return textColorBuilder.blinking();
    }

    public boolean inverted() {
        return textColorBuilder.inverted();
    }

    public boolean hidden() {
        return textColorBuilder.hidden();
    }

    public boolean strikethrough() {
        return textColorBuilder.strikethrough();
    }

    @NotNull
    public static TextBuilder builder(@NotNull final String text) {
        return builder().text(text);
    }

    @NotNull
    public static TextBuilder builder() {
        return new TextBuilder();
    }

    @NotNull
    public TextBuilder text(@Nullable final String text) {
        this.text = text;
        return this;
    }

    @NotNull
    public Optional<String> text() {
        return Optional.ofNullable(text);
    }

    @NotNull
    public TextPart buildPart() {
        return new TextPart(color(), text);
    }

    @NotNull
    public TextBuilder next() {
        final TextPart part = buildPart();
        if (!part.invisible()) parts.add(part);
        clearCurrent();
        return this;
    }

    @NotNull
    public Text build() {
        next();
        final Text result = Text.text(parts);
        clearAll();
        return result;
    }

}
