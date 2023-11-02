package org.crayne.jtux.ui.content;

import org.crayne.jtux.ui.content.grid.CharacterGrid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class Content implements Runnable {
    
    @Nullable
    private CharacterGrid characterGrid;

    private boolean ready;
    
    public Content() {
        this.characterGrid = null;
    }
    
    public Content(@Nullable final CharacterGrid characterGrid) {
        this.characterGrid = characterGrid;
    }

    public void ready(final boolean ready) {
        this.ready = ready;
    }

    public boolean ready() {
        return ready;
    }

    public void characterGrid(@Nullable final CharacterGrid characterGrid) {
        this.characterGrid = characterGrid;
    }

    public void fullUpdate() {
        if (characterGrid == null || !ready) return;

        run();
        characterGrid.cleanUp();
        characterGrid.flush();
    }

    public void updateSize(final int width, final int height) {
        if (characterGrid == null) return;

        characterGrid.updateSize(width, height);
        if (ready) fullUpdate();
    }

    @NotNull
    public Optional<CharacterGrid> characterGrid() {
        return Optional.ofNullable(characterGrid);
    }

    public abstract void run();

}
