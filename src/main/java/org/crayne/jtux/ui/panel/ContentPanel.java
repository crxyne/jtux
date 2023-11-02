package org.crayne.jtux.ux.panel;

import org.crayne.jtux.util.math.vec.Vec2f;
import org.crayne.jtux.ux.border.AbstractBorder;
import org.crayne.jtux.ux.panel.content.Content;
import org.crayne.jtux.ux.panel.content.ContentRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContentPanel extends AbstractPanel {

    @NotNull
    private final ContainerPanel container;

    public ContentPanel(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize,
                        @NotNull final ContainerPanel container, @NotNull final ContentRenderer renderer) {
        super(border, formatSize, null, new Content(null, renderer));
        this.container = container;
    }

    @NotNull
    public ContainerPanel container() {
        return container;
    }

}
