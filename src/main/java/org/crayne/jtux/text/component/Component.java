package org.crayne.jtux.text.component;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.color.ansi.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Component {

    @NotNull
    private final List<ComponentPart> parts;

    public Component(@NotNull final ComponentPart... parts) {
        this.parts = new ArrayList<>(Arrays.stream(parts).toList());
    }

    public Component(@NotNull final Collection<ComponentPart> parts) {
        this.parts = new ArrayList<>(parts);
    }

    @NotNull
    public static Component text(@NotNull final TextColor color, @NotNull final String text) {
        return new Component(ComponentPart.of(color, text));
    }

    @NotNull
    public static Component text(@NotNull final Color color, @NotNull final String text) {
        return text(color, text, true);
    }

    @NotNull
    public static Component text(@NotNull final Color color, @NotNull final String text, final boolean foreground) {
        return new Component(ComponentPart.of(color, text, foreground));
    }

    @NotNull
    public static Component text(@NotNull final ComponentPart... parts) {
        return new Component(parts);
    }

    @NotNull
    public static Component text(@NotNull final Collection<ComponentPart> parts) {
        return new Component(parts);
    }

    @NotNull
    public static Component empty() {
        return new Component();
    }

    @NotNull
    public static Component text(@NotNull final String text) {
        return new Component(ComponentPart.of(text));
    }

    @NotNull
    public Component prepend(@NotNull final ComponentPart part) {
        parts.add(0, part);
        return this;
    }

    @NotNull
    public Component prepend(@NotNull final TextColor part) {
        parts.add(0, new ComponentPart(part, null));
        return this;
    }

    @NotNull
    public Component prepend(@NotNull final String plain) {
        parts.add(0, ComponentPart.of(plain));
        return this;
    }

    @NotNull
    public Component prepend(@NotNull final Component comp) {
        parts.addAll(0, comp.parts);
        return this;
    }

    @NotNull
    public Component append(@NotNull final ComponentPart part) {
        parts.add(part);
        return this;
    }

    @NotNull
    public Component append(@NotNull final TextColor part) {
        parts.add(new ComponentPart(part, null));
        return this;
    }

    @NotNull
    public Component append(@NotNull final String plain) {
        parts.add(ComponentPart.of(plain));
        return this;
    }

    @NotNull
    public Component append(@NotNull final Component comp) {
        parts.addAll(comp.parts);
        return this;
    }

    @NotNull
    public Component replace(@NotNull final String find, @NotNull final String replace) {
        final List<ComponentPart> replaced = parts.stream().map(c -> new ComponentPart(c.color().orElse(null), c.text().replace(find, replace))).toList();
        parts.clear();
        parts.addAll(replaced);
        return this;
    }

    @NotNull
    public Component replaceAll(@NotNull final String regex, @NotNull final String replace) {
        final List<ComponentPart> replaced = parts.stream().map(c -> new ComponentPart(c.color().orElse(null), c.text().replaceAll(regex, replace))).toList();
        parts.clear();
        parts.addAll(replaced);
        return this;
    }

    @NotNull
    public List<ComponentPart> parts() {
        return parts;
    }

    public boolean isEmpty() {
        return parts.isEmpty();
    }

    public boolean invisible() {
        return parts.stream().allMatch(ComponentPart::invisible);
    }

    @NotNull
    public String text() {
        return parts.stream().map(ComponentPart::text).collect(Collectors.joining());
    }

    @NotNull
    public String toString(final boolean autoResetColor) {
        return parts.stream().map(ComponentPart::toString).collect(Collectors.joining()) + (autoResetColor ? TextColor.RESET_ANSI_STRING : "");
    }

    @NotNull
    public ComponentBuilder builder() {
        return new ComponentBuilder(this.parts).next().reset(true).next();
    }

    @NotNull
    public Component createCopy() {
        return new Component(parts.stream().map(ComponentPart::createCopy).toList());
    }

    @NotNull
    public String toString() {
        return toString(true);
    }

}
