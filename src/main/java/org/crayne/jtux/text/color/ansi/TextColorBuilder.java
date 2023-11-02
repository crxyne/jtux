package org.crayne.jtux.text.color.ansi;

import org.crayne.jtux.text.color.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TextColorBuilder {

    @Nullable
    private Color fg;

    @Nullable
    private Color bg;

    private final boolean @NotNull [] flags;

    public TextColorBuilder() {
        this.flags = new boolean[9];
    }

    @NotNull
    public TextColorBuilder foreground(@Nullable final Color fg) {
        this.fg = fg;
        return this;
    }

    @NotNull
    public TextColorBuilder background(@Nullable final Color bg) {
        this.bg = bg;
        return this;
    }

    @NotNull
    public TextColorBuilder reset(final boolean b) {
        flags[0] = b;
        return this;
    }

    @NotNull
    public TextColorBuilder bold(final boolean b) {
        flags[1] = b;
        return this;
    }

    @NotNull
    public TextColorBuilder dim(final boolean b) {
        flags[2] = b;
        return this;
    }

    @NotNull
    public TextColorBuilder italic(final boolean b) {
        flags[3] = b;
        return this;
    }

    @NotNull
    public TextColorBuilder underline(final boolean b) {
        flags[4] = b;
        return this;
    }

    @NotNull
    public TextColorBuilder blinking(final boolean b) {
        flags[5] = b;
        return this;
    }

    @NotNull
    public TextColorBuilder inverted(final boolean b) {
        flags[6] = b;
        return this;
    }

    @NotNull
    public TextColorBuilder hidden(final boolean b) {
        flags[7] = b;
        return this;
    }

    @NotNull
    public TextColorBuilder strikethrough(final boolean b) {
        flags[8] = b;
        return this;
    }

    @NotNull
    public Optional<Color> foreground() {
        return Optional.ofNullable(fg);
    }

    @NotNull
    public Optional<Color> background() {
        return Optional.ofNullable(bg);
    }

    public boolean reset() {
        return flags[0];
    }

    public boolean bold() {
        return flags[1];
    }

    public boolean dim() {
        return flags[2];
    }

    public boolean italic() {
        return flags[3];
    }

    public boolean underline() {
        return flags[4];
    }

    public boolean blinking() {
        return flags[5];
    }

    public boolean inverted() {
        return flags[6];
    }

    public boolean hidden() {
        return flags[7];
    }

    public boolean strikethrough() {
        return flags[8];
    }

    @NotNull
    public TextColor build() {
        return new TextColor(fg, bg, flags);
    }

}
