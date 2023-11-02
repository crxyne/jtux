package org.crayne.jtux.ui.panel;

import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.content.Content;
import org.crayne.jtux.util.math.vec.Vec2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContentPanel extends AbstractPanel {

    @NotNull
    private final ContainerPanel container;

    public ContentPanel(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize,
                        @NotNull final ContainerPanel container, @NotNull final Content renderer) {
        super(border, formatSize, null, renderer);
        this.container = container;
    }

    public void ready(final boolean ready) {
        super.ready(ready);
        if (ready) updatePanel();
    }

    @NotNull
    public ContainerPanel container() {
        return container;
    }

}
