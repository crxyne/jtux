package org.crayne.jtux.ui.panel;

import org.crayne.jtux.util.ObjectUtil;
import org.crayne.jtux.util.math.vec.Vec2f;
import org.crayne.jtux.ui.border.AbstractBorder;
import org.crayne.jtux.ui.panel.content.Content;
import org.crayne.jtux.ui.panel.content.grid.CharacterGrid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class AbstractPanel {

    @Nullable
    private AbstractBorder border;

    @NotNull
    private Vec2f formatSize;

    @Nullable
    private CharacterGrid fullCharacterGrid;

    @NotNull
    private final Content content;

    private boolean ready;

    public AbstractPanel(@Nullable final AbstractBorder border, @NotNull final Vec2f formatSize,
                         @Nullable final CharacterGrid fullCharacterGrid, @NotNull final Content content) {
        this.border = border;
        this.formatSize = formatSize;
        this.fullCharacterGrid = fullCharacterGrid;
        this.content = content;
    }

    public AbstractPanel(@NotNull final Vec2f formatSize, @Nullable final CharacterGrid fullCharacterGrid,
                         @NotNull final Content content) {
        this.formatSize = formatSize;
        this.fullCharacterGrid = fullCharacterGrid;
        this.content = content;
    }

    public void ready(final boolean ready) {
        this.ready = ready;
        content.renderer().ready(ready);
    }

    public boolean ready() {
        return ready;
    }

    @NotNull
    public Optional<AbstractBorder> border() {
        return Optional.ofNullable(border);
    }

    public void updateContentGridSize(@NotNull final CharacterGrid fullCharacterGrid) {
        final CharacterGrid contentGrid = content.characterGrid().orElseThrow();
        if (border().isEmpty()) {
            contentGrid.offsetX(fullCharacterGrid.offsetX());
            contentGrid.offsetY(fullCharacterGrid.offsetY());
            content.updateSize(fullCharacterGrid.width(), fullCharacterGrid.height());
            return;
        }
        contentGrid.offsetX(fullCharacterGrid.offsetX() + 1);
        contentGrid.offsetY(fullCharacterGrid.offsetY() + 1);
        content.updateSize(fullCharacterGrid.width() - 2, fullCharacterGrid.height() - 2);
    }

    protected void updateFullGridSize(@NotNull final CharacterGrid parentGrid, final int offsetX, final int offsetY,
                                      final int width, final int height) {
        final Optional<CharacterGrid> fullGrid = fullCharacterGrid();
        if (fullGrid.isEmpty()) {
            fullCharacterGrid(parentGrid.createChunk(offsetX, offsetY, width, height));
            return;
        }
        fullGrid.get().offsetX(parentGrid.offsetX() + offsetX);
        fullGrid.get().offsetY(parentGrid.offsetY() + offsetY);
        fullGrid.get().updateSize(width, height);
    }

    public void createContentGrid() {
        final CharacterGrid fullGrid = fullCharacterGrid().orElse(null);
        if (fullGrid == null) return;

        final Optional<CharacterGrid> contentGrid = content.characterGrid();

        if (contentGrid.isEmpty()){
            final CharacterGrid newContentGrid = fullGrid.createContentGrid(border().isPresent());
            content.characterGrid(newContentGrid);
            if (ready) content.fullUpdate();
            return;
        }
        updateContentGridSize(fullGrid);
    }

    public void border(@Nullable final AbstractBorder border) {
        final boolean sizeChanged = ObjectUtil.nullityDifferent(this.border, border); // a null-border is displayed as using no extra space

        this.border = border;
        if (fullCharacterGrid().isEmpty()) return;

        if (sizeChanged) {
            updateSize();
            updateContent();
        }
        updateBorder();
    }

    public void updateSize() {
        createContentGrid();
    }

    public void updateSize(final int width, final int height) {
        fullCharacterGrid().orElseThrow().updateSize(width, height);
        updateSize();
    }

    @NotNull
    public Vec2f formatSize() {
        return formatSize;
    }

    public void formatSize(@NotNull final Vec2f formatSize) {
        if (formatSize.x() < 0 || formatSize.y() < 0) throw new IllegalArgumentException("Format size must have positive components x and y");
        this.formatSize = formatSize;
    }

    @NotNull
    public Optional<CharacterGrid> fullCharacterGrid() {
        return Optional.ofNullable(fullCharacterGrid);
    }

    public void fullCharacterGrid(@Nullable final CharacterGrid fullCharacterGrid) {
        this.fullCharacterGrid = fullCharacterGrid;
    }

    @NotNull
    public Content content() {
        return content;
    }

    public void updateBorder() {
        if (ready) border().ifPresent(b -> fullCharacterGrid().ifPresent(c -> {
            c.drawBorder(b);
            c.flush();
        }));
    }

    public void updateContent() {
        if (ready) content().fullUpdate();
    }

    public void updatePanel() {
        updateContent();
        updateBorder();
    }

}
