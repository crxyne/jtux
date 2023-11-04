package org.crayne.jtux.ui.border;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.color.ansi.TextColor;
import org.crayne.jtux.text.component.Text;
import org.crayne.jtux.ui.component.layout.Alignment;
import org.jetbrains.annotations.NotNull;

public class BorderTitle {

    @NotNull
    private final Text title;

    private final float alignment;

    public BorderTitle(@NotNull final Text title, @NotNull final Alignment alignment) {
        this.title = title;
        this.alignment = alignment.floatValue();
    }

    public BorderTitle(@NotNull final Text title, final float alignment) {
        this.title = title;
        this.alignment = alignment;
    }

    @NotNull
    public static BorderTitle of(@NotNull final Text title, @NotNull final Alignment alignment) {
        return new BorderTitle(title, alignment);
    }

    @NotNull
    public static BorderTitle of(@NotNull final Text title, final float alignment) {
        return new BorderTitle(title, alignment);
    }

    @NotNull
    public static BorderTitle of(@NotNull final Text title) {
        return new BorderTitle(title, Alignment.CENTER);
    }

    @NotNull
    public static BorderTitle of(@NotNull final String title) {
        return new BorderTitle(Text.text(title), Alignment.CENTER);
    }

    @NotNull
    public static BorderTitle of(@NotNull final Color titleColor, @NotNull final String titleText, @NotNull final Alignment alignment) {
        return new BorderTitle(Text.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static BorderTitle of(@NotNull final Color titleColor, @NotNull final String titleText, final float alignment) {
        return new BorderTitle(Text.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static BorderTitle of(@NotNull final Color titleColor, @NotNull final String titleText) {
        return of(titleColor, titleText, Alignment.CENTER);
    }

    @NotNull
    public static BorderTitle of(@NotNull final TextColor titleColor, @NotNull final String titleText, @NotNull final Alignment alignment) {
        return new BorderTitle(Text.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static BorderTitle of(@NotNull final TextColor titleColor, @NotNull final String titleText, final float alignment) {
        return new BorderTitle(Text.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static BorderTitle of(@NotNull final TextColor titleColor, @NotNull final String titleText) {
        return of(titleColor, titleText, Alignment.CENTER);
    }

    @NotNull
    public Text title() {
        return title;
    }

    public float alignment() {
        return alignment;
    }

}
