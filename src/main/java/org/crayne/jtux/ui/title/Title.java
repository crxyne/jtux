package org.crayne.jtux.ui.title;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.color.ansi.TextColor;
import org.crayne.jtux.text.component.Text;
import org.jetbrains.annotations.NotNull;

public class Title {

    @NotNull
    private final Text title;

    private final float alignment;

    public Title(@NotNull final Text title, @NotNull final TitleAlignment alignment) {
        this.title = title;
        this.alignment = alignment.floatValue();
    }

    public Title(@NotNull final Text title, final float alignment) {
        this.title = title;
        this.alignment = alignment;
    }

    @NotNull
    public static Title of(@NotNull final Text title, @NotNull final TitleAlignment alignment) {
        return new Title(title, alignment);
    }

    @NotNull
    public static Title of(@NotNull final Text title, final float alignment) {
        return new Title(title, alignment);
    }

    @NotNull
    public static Title of(@NotNull final Text title) {
        return new Title(title, TitleAlignment.CENTER);
    }

    @NotNull
    public static Title of(@NotNull final String title) {
        return new Title(Text.text(title), TitleAlignment.CENTER);
    }

    @NotNull
    public static Title of(@NotNull final Color titleColor, @NotNull final String titleText, @NotNull final TitleAlignment alignment) {
        return new Title(Text.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static Title of(@NotNull final Color titleColor, @NotNull final String titleText, final float alignment) {
        return new Title(Text.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static Title of(@NotNull final Color titleColor, @NotNull final String titleText) {
        return of(titleColor, titleText, TitleAlignment.CENTER);
    }

    @NotNull
    public static Title of(@NotNull final TextColor titleColor, @NotNull final String titleText, @NotNull final TitleAlignment alignment) {
        return new Title(Text.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static Title of(@NotNull final TextColor titleColor, @NotNull final String titleText, final float alignment) {
        return new Title(Text.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static Title of(@NotNull final TextColor titleColor, @NotNull final String titleText) {
        return of(titleColor, titleText, TitleAlignment.CENTER);
    }

    @NotNull
    public Text title() {
        return title;
    }

    public float alignment() {
        return alignment;
    }

}
