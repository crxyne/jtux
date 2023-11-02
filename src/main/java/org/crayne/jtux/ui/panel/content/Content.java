package org.crayne.jtux.ux.panel.content;

import org.crayne.jtux.ux.panel.content.grid.CharacterGrid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Content {

    @Nullable
    private CharacterGrid characterGrid;

    @NotNull
    private final ContentRenderer renderer;

    public Content(@Nullable final CharacterGrid characterGrid, @NotNull final ContentRenderer renderer) {
        this.characterGrid = characterGrid;
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
    }

    public void fullUpdate() {
        if (characterGrid == null) return;
        renderer.fullUpdate(characterGrid);
        characterGrid.cleanUp();
        characterGrid.flush();
    }

    public void updateSize(final int width, final int height, final boolean ready) {
        if (characterGrid == null) return;

        characterGrid.updateSize(width, height);
        if (ready) fullUpdate();
    }

}
