package org.crayne.jtux.ui.panel.content;

import org.crayne.jtux.ui.panel.content.grid.CharacterGrid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class ContentRenderer implements Runnable {
    
    @Nullable
    private CharacterGrid characterGrid;

    private boolean ready;
    
    public ContentRenderer() {
        this.characterGrid = null;
    }
    
    public ContentRenderer(@Nullable final CharacterGrid characterGrid) {
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

    @NotNull
    public Optional<CharacterGrid> characterGrid() {
        return Optional.ofNullable(characterGrid);
    }

    public abstract void run();

}
