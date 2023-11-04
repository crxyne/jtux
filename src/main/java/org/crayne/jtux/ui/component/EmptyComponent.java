package org.crayne.jtux.ui.component;

import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.component.layout.Alignment;
import org.crayne.jtux.ui.grid.CharacterGrid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EmptyComponent extends Component {

    public EmptyComponent(final float sizeProportion, final float sizePercentage, final float alignment, @Nullable final AbstractBorder border, final boolean hidden) {
        super(sizeProportion, sizePercentage, alignment, border, hidden);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage, final boolean hidden) {
        super(sizeProportion, sizePercentage, hidden);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage, final float alignment, final boolean hidden) {
        super(sizeProportion, sizePercentage, alignment, hidden);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage, @NotNull final Alignment alignment, final boolean hidden) {
        super(sizeProportion, sizePercentage, alignment, hidden);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage, @Nullable final AbstractBorder border) {
        super(sizeProportion, sizePercentage, border);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage, final float alignment, @Nullable final AbstractBorder border) {
        super(sizeProportion, sizePercentage, alignment, border);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage, @NotNull final Alignment alignment, @Nullable final AbstractBorder border) {
        super(sizeProportion, sizePercentage, alignment, border);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage) {
        super(sizeProportion, sizePercentage);
    }

    public EmptyComponent(final float sizeProportion) {
        super(sizeProportion, 1.0f);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage, final float alignment) {
        super(sizeProportion, sizePercentage, alignment);
    }

    public EmptyComponent(final float sizeProportion, final float sizePercentage, @NotNull final Alignment alignment) {
        super(sizeProportion, sizePercentage, alignment);
    }

    public EmptyComponent(@Nullable final AbstractBorder border, final boolean hidden) {
        super(border, hidden);
    }

    public EmptyComponent(@Nullable final AbstractBorder border) {
        super(border);
    }

    public EmptyComponent(final boolean hidden) {
        super(hidden);
    }

    public EmptyComponent() {

    }

    public void render(@NotNull final CharacterGrid contentGrid) {

    }

}
