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
    private final Container parent;

    @NotNull
    private final Vec2f format;

    @Nullable
    private final AbstractBorder border;

    private boolean ready;

    public Component(@Nullable final Container parent, @NotNull final Vec2f format,
                     @Nullable final AbstractBorder border) {
        this.parent = parent;
        this.format = format;
        this.border = border;
        this.contentGrid = null;
        this.fullGrid = null;
        this.ready = false;
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

    @NotNull
    public Vec2f format() {
        return format;
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

        render();
        contentGrid.cleanUp();
        contentGrid.flush();
    }

    private void updateContentGridSize(final int width, final int height) {
        if (contentGrid == null) return;

        contentGrid.updateSize(width, height);
        if (ready) updateContent();
    }

    public void updateFullSize(final int width, final int height) {
        fullGrid().orElseThrow().updateSize(width, height);
        updateSize();
    }

    public void updateSize() {
        final Optional<CharacterGrid> fullGridOptional = fullGrid();
        if (fullGridOptional.isEmpty()) return;

        final CharacterGrid fullGrid = fullGridOptional.get();
        final Optional<CharacterGrid> contentGridOptional = contentGrid();

        if (contentGridOptional.isEmpty()){
            final CharacterGrid newContentGrid = fullGrid.createContentGrid(border().isPresent());
            contentGrid(newContentGrid);
            updateContent();
            return;
        }

        final CharacterGrid contentGrid = contentGridOptional.get();
        if (border().isEmpty()) {
            contentGrid.offsetX(fullGrid.offsetX());
            contentGrid.offsetY(fullGrid.offsetY());
            updateContentGridSize(fullGrid.width(), fullGrid.height());
            updateContent();
            return;
        }
        contentGrid.offsetX(fullGrid.offsetX() + 1);
        contentGrid.offsetY(fullGrid.offsetY() + 1);
        updateContentGridSize(fullGrid.width() - 2, fullGrid.height() - 2);
    }

    public void updateBorder() {
        if (!ready()) return;

        border().ifPresent(b -> fullGrid().ifPresent(c -> {
            c.drawBorder(b);
            c.flush();
        }));
    }

    public void update() {
        updateContent();
        updateBorder();
    }

}
