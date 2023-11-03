package org.crayne.jtux.ui.content.layout;

import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.content.grid.CharacterGrid;
import org.crayne.jtux.util.math.vec.Vec2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class Component {

    @Nullable
    private CharacterGrid contentGrid;

    @Nullable
    private CharacterGrid fullGrid;

    @Nullable
    private Container parent;

    @NotNull
    private final Vec2f format;

    private final float alignment;

    @Nullable
    private AbstractBorder border;

    private boolean ready;

    private boolean hidden;

    public Component(@NotNull final Vec2f format, final float alignment, @Nullable final AbstractBorder border, final boolean hidden) {
        if (format.x() < 0 || format.y() < 0) throw new IllegalArgumentException("Component format cannot have negative components");
        if (alignment < 0.0f || alignment > 1.0f) throw new IllegalArgumentException("Component alignment must be between 0 and 1");

        this.format = format;
        this.alignment = alignment;
        this.border = border;
        this.contentGrid = null;
        this.fullGrid = null;
        this.ready = false;
        this.hidden = hidden;
    }

    public Component(@NotNull final Vec2f format, final boolean hidden) {
        this(format, Alignment.PRIMARY.floatValue(), null, hidden);
    }

    public Component(@NotNull final Vec2f format, final float alignment, final boolean hidden) {
        this(format, alignment, null, hidden);
    }

    public Component(@NotNull final Vec2f format, @NotNull final Alignment alignment, final boolean hidden) {
        this(format, alignment.floatValue(), null, hidden);
    }

    public Component(@NotNull final Vec2f format, @Nullable final AbstractBorder border) {
        this(format, Alignment.PRIMARY.floatValue(), border, false);
    }

    public Component(@NotNull final Vec2f format, final float alignment, @Nullable final AbstractBorder border) {
        this(format, alignment, border, false);
    }

    public Component(@NotNull final Vec2f format, @NotNull final Alignment alignment, @Nullable final AbstractBorder border) {
        this(format, alignment.floatValue(), border, false);
    }

    public Component(@NotNull final Vec2f format) {
        this(format, false);
    }

    public Component(@NotNull final Vec2f format, final float alignment) {
        this(format, alignment, false);
    }

    public Component(@NotNull final Vec2f format, @NotNull final Alignment alignment) {
        this(format, alignment.floatValue(), false);
    }

    public Component(@Nullable final AbstractBorder border, final boolean hidden) {
        this(Vec2f.unary(), Alignment.PRIMARY.floatValue(), border, hidden);
    }

    public Component(@Nullable final AbstractBorder border) {
        this(border, false);
    }

    public Component(final boolean hidden) {
        this(Vec2f.unary(), hidden);
    }

    public Component() {
        this(false);
    }

    public boolean hidden() {
        return hidden;
    }

    public void hidden(final boolean hidden) {
        this.hidden = hidden;
        if (!ready) return;

        if (hidden) {
            fullGrid().ifPresent(CharacterGrid::clear);
            contentGrid().ifPresent(CharacterGrid::clear);
        }
        update();
    }

    protected void parent(@NotNull final Container parent) {
        this.parent = parent;
    }

    protected void setReady() {
        this.ready = true;
    }

    public void makeReady() {
        setReady();
        update();
    }

    public boolean ready() {
        return ready;
    }

    public abstract void render();

    @NotNull
    public Optional<AbstractBorder> border() {
        return Optional.ofNullable(border);
    }

    public void border(@Nullable final AbstractBorder border) {
        this.border = border;
    }

    @NotNull
    public Vec2f format() {
        return format;
    }

    public float alignment() {
        return alignment;
    }

    @NotNull
    public Optional<Container> parent() {
        return Optional.ofNullable(parent);
    }

    @NotNull
    public Optional<CharacterGrid> contentGrid() {
        return Optional.ofNullable(contentGrid);
    }

    @NotNull
    public Optional<CharacterGrid> fullGrid() {
        return Optional.ofNullable(fullGrid);
    }

    public void contentGrid(@Nullable final CharacterGrid contentGrid) {
        this.contentGrid = contentGrid;
    }

    public void fullGrid(@Nullable final CharacterGrid fullGrid) {
        this.fullGrid = fullGrid;
    }

    protected void updateFullGridSize(@NotNull final CharacterGrid parentGrid,
                                      final int offsetX, final int offsetY,
                                      final int width, final int height) {
        final Optional<CharacterGrid> fullGrid = fullGrid();
        if (fullGrid.isEmpty()) {
            fullGrid(parentGrid.createChunk(offsetX, offsetY, width, height));
            return;
        }
        fullGrid.get().offsetX(parentGrid.offsetX() + offsetX);
        fullGrid.get().offsetY(parentGrid.offsetY() + offsetY);
        fullGrid.get().updateSize(width, height);
    }

    public void updateContent() {
        if (contentGrid == null || !ready) return;

        if (!hidden) render();
        contentGrid.cleanUp();
        contentGrid.flush();
    }

    private void updateContentGridSize(final int width, final int height) {
        if (contentGrid == null) return;

        contentGrid.updateSize(width, height);
    }

    public void updateFullSize(final int width, final int height) {
        fullGrid().ifPresent(grid -> {
            grid.updateSize(width, height);
            updateSize();
            grid.clearAndFlush();
        });
    }

    public void updateSize() {
        final Optional<CharacterGrid> fullGridOptional = fullGrid();
        if (fullGridOptional.isEmpty()) return;

        final CharacterGrid fullGrid = fullGridOptional.get();
        final Optional<CharacterGrid> contentGridOptional = contentGrid();

        if (contentGridOptional.isEmpty()){
            final CharacterGrid newContentGrid = fullGrid.createContentGrid(border().isPresent());
            contentGrid(newContentGrid);
            return;
        }

        final CharacterGrid contentGrid = contentGridOptional.get();
        if (border().isEmpty()) {
            contentGrid.offsetX(fullGrid.offsetX());
            contentGrid.offsetY(fullGrid.offsetY());
            updateContentGridSize(fullGrid.width(), fullGrid.height());
            return;
        }
        contentGrid.offsetX(fullGrid.offsetX() + 1);
        contentGrid.offsetY(fullGrid.offsetY() + 1);
        updateContentGridSize(fullGrid.width() - 2, fullGrid.height() - 2);
    }

    public void updateBorder() {
        if (!ready || hidden) return;

        border().ifPresent(b -> fullGrid().ifPresent(c ->  {
            c.drawBorder(b);
            c.flush();
        }));
    }

    public void update() {
        updateContent();
        updateBorder();
    }

}
