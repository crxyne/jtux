package org.crayne.jtux.example;

import org.crayne.jtux.text.component.Text;
import org.crayne.jtux.ui.content.Content;
import org.crayne.jtux.util.math.vec.Vec2i;
import org.jetbrains.annotations.NotNull;

public class SimpleTextContent extends Content {

    private boolean wrap;

    @NotNull
    private Text text;

    public SimpleTextContent(@NotNull final Text text, final boolean wrap) {
        this.wrap = wrap;
        this.text = text;
    }

    public void run() {
        characterGrid().ifPresent(grid -> grid.writeLine(Vec2i.origin(), text, wrap));
    }

    public boolean wrap() {
        return wrap;
    }

    @NotNull
    public Text component() {
        return text;
    }

    public void component(@NotNull final Text text) {
        this.text = text;
    }

    public void wrap(final boolean wrap) {
        this.wrap = wrap;
    }

}
