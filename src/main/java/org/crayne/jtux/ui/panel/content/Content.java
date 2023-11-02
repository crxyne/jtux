package org.crayne.jtux.ui.panel.content;

import org.crayne.jtux.ui.panel.content.grid.CharacterGrid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Content {

    @Nullable
    private CharacterGrid characterGrid;

    @NotNull
    private ContentRenderer renderer;

    public Content(@Nullable final CharacterGrid characterGrid, @NotNull final ContentRenderer renderer) {
        this.characterGrid = characterGrid;
        this.renderer = renderer;
    }

    public void renderer(@NotNull final ContentRenderer renderer) {
        this.renderer = renderer;
    }

    @NotNull
    public ContentRenderer renderer() {
        return renderer;
    }

    @NotNull
    public Optional<CharacterGrid> characterGrid() {
        return Optional.ofNullable(characterGrid);
    }

    public void characterGrid(@Nullable final CharacterGrid characterGrid) {
        this.characterGrid = characterGrid;
        this.renderer.characterGrid(characterGrid);
    }

    public void fullUpdate() {
        if (characterGrid == null) return;
        renderer.run();
        characterGrid.cleanUp();
        characterGrid.flush();
    }

    public void updateSize(final int width, final int height) {
        if (characterGrid == null) return;

        characterGrid.updateSize(width, height);
        if (renderer().ready()) fullUpdate();
    }

}
