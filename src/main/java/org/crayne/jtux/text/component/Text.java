package org.crayne.jtux.text.component;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.color.ansi.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Text {

    @NotNull
    private final List<TextPart> parts;

    public Text(@NotNull final TextPart... parts) {
        this.parts = new ArrayList<>(Arrays.stream(parts).toList());
    }

    public Text(@NotNull final Collection<TextPart> parts) {
        this.parts = new ArrayList<>(parts);
    }

    @NotNull
    public static Text text(@NotNull final TextColor color, @NotNull final String text) {
        return new Text(TextPart.of(color, text));
    }

    @NotNull
    public static Text text(@NotNull final Color color, @NotNull final String text) {
        return text(color, text, true);
    }

    @NotNull
    public static Text text(@NotNull final Color color, @NotNull final String text, final boolean foreground) {
        return new Text(TextPart.of(color, text, foreground));
    }

    @NotNull
    public static Text text(@NotNull final TextPart... parts) {
        return new Text(parts);
    }

    @NotNull
    public static Text text(@NotNull final Collection<TextPart> parts) {
        return new Text(parts);
    }

    @NotNull
    public static Text empty() {
        return new Text();
    }

    @NotNull
    public static Text text(@NotNull final String text) {
        return new Text(TextPart.of(text));
    }

    @NotNull
    public Text prepend(@NotNull final TextPart part) {
        final List<TextPart> parts = new ArrayList<>(this.parts);
        parts.add(0, part);
        return new Text(parts);
    }

    @NotNull
    public Text prepend(@NotNull final TextColor part) {
        final List<TextPart> parts = new ArrayList<>(this.parts);
        parts.add(0, new TextPart(part, null));
        return new Text(parts);
    }

    @NotNull
    public Text prepend(@NotNull final String plain) {
        final List<TextPart> parts = new ArrayList<>(this.parts);
        parts.add(0, TextPart.of(plain));
        return new Text(parts);
    }

    @NotNull
    public Text prepend(@NotNull final Text comp) {
        final List<TextPart> parts = new ArrayList<>(this.parts);
        parts.addAll(0, comp.parts);
        return new Text(parts);
    }

    @NotNull
    public Text append(@NotNull final TextPart part) {
        final List<TextPart> parts = new ArrayList<>(this.parts);
        parts.add(part);
        return new Text(parts);
    }

    @NotNull
    public Text append(@NotNull final TextColor part) {
        final List<TextPart> parts = new ArrayList<>(this.parts);
        parts.add(new TextPart(part, null));
        return new Text(parts);
    }

    @NotNull
    public Text append(@NotNull final String plain) {
        final List<TextPart> parts = new ArrayList<>(this.parts);
        parts.add(TextPart.of(plain));
        return new Text(parts);
    }

    @NotNull
    public Text append(@NotNull final Text comp) {
        final List<TextPart> parts = new ArrayList<>(this.parts);
        parts.addAll(comp.parts);
        return this;
    }

    @NotNull
    public Text replace(@NotNull final String find, @NotNull final String replace) {
        final List<TextPart> replaced = parts.stream().map(c -> new TextPart(c.color().orElse(null), c.text().replace(find, replace))).toList();
        return new Text(replaced);
    }

    @NotNull
    public Text replaceAll(@NotNull final String regex, @NotNull final String replace) {
        final List<TextPart> replaced = parts.stream().map(c -> new TextPart(c.color().orElse(null), c.text().replaceAll(regex, replace))).toList();
        return new Text(replaced);
    }

    @NotNull
    public List<TextPart> parts() {
        return parts;
    }

    public boolean isEmpty() {
        return parts.isEmpty();
    }

    public boolean invisible() {
        return parts.stream().allMatch(TextPart::invisible);
    }

    @NotNull
    public String text() {
        return parts.stream().map(TextPart::text).collect(Collectors.joining());
    }

    @NotNull
    public String toString(final boolean autoResetColor) {
        return parts.stream().map(TextPart::toString).collect(Collectors.joining()) + (autoResetColor ? TextColor.RESET_ANSI_STRING : "");
    }

    @NotNull
    public TextBuilder builder() {
        return new TextBuilder(this.parts).next().reset(true).next();
    }

    @NotNull
    public Text createCopy() {
        return new Text(parts.stream().map(TextPart::createCopy).toList());
    }

    @NotNull
    public String toString() {
        return toString(true);
    }

}
