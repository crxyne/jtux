package org.crayne.jtux.ux.title;

import org.crayne.jtux.text.color.Color;
import org.crayne.jtux.text.color.ansi.TextColor;
import org.crayne.jtux.text.component.Component;
import org.jetbrains.annotations.NotNull;

public class Title {

    @NotNull
    private final Component title;

    @NotNull
    private final TitleAlignment alignment;

    public Title(@NotNull final Component title, @NotNull final TitleAlignment alignment) {
        this.title = title;
        this.alignment = alignment;
    }

    @NotNull
    public static Title of(@NotNull final Component title, @NotNull final TitleAlignment alignment) {
        return new Title(title, alignment);
    }

    @NotNull
    public static Title of(@NotNull final Component title) {
        return new Title(title, TitleAlignment.CENTER);
    }

    @NotNull
    public static Title of(@NotNull final String title) {
        return new Title(Component.text(title), TitleAlignment.CENTER);
    }

    @NotNull
    public static Title of(@NotNull final Color titleColor, @NotNull final String titleText, @NotNull final TitleAlignment alignment) {
        return new Title(Component.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static Title of(@NotNull final Color titleColor, @NotNull final String titleText) {
        return of(titleColor, titleText, TitleAlignment.CENTER);
    }

    @NotNull
    public static Title of(@NotNull final TextColor titleColor, @NotNull final String titleText, @NotNull final TitleAlignment alignment) {
        return new Title(Component.text(titleColor, titleText), alignment);
    }

    @NotNull
    public static Title of(@NotNull final TextColor titleColor, @NotNull final String titleText) {
        return of(titleColor, titleText, TitleAlignment.CENTER);
    }

    @NotNull
    public Component title() {
        return title;
    }

    @NotNull
    public TitleAlignment alignment() {
        return alignment;
    }

}
