package org.crayne.jtux.example;

import org.crayne.jtux.text.component.Component;
import org.crayne.jtux.ui.panel.content.ContentRenderer;
import org.crayne.jtux.util.math.vec.Vec2i;
import org.jetbrains.annotations.NotNull;

public class SimpleTextRenderer extends ContentRenderer {

    private boolean wrap;

    @NotNull
    private Component component;

    public SimpleTextRenderer(@NotNull final Component component, final boolean wrap) {
        this.wrap = wrap;
        this.component = component;
    }

    public void run() {
        characterGrid().ifPresent(grid -> grid.writeLine(Vec2i.origin(), component, wrap));
    }

    public boolean wrap() {
        return wrap;
    }

    @NotNull
    public Component component() {
        return component;
    }

    public void component(@NotNull final Component component) {
        this.component = component;
    }

    public void wrap(final boolean wrap) {
        this.wrap = wrap;
    }

}
