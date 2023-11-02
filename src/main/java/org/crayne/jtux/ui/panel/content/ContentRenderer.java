package org.crayne.jtux.ui.panel.content;

import org.crayne.jtux.ui.panel.content.grid.CharacterGrid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class ContentRenderer {
    
    @Nullable
    private CharacterGrid characterGrid;
    
    public ContentRenderer() {
        this.characterGrid = null;
    }
    
    public ContentRenderer(@Nullable final CharacterGrid characterGrid) {
        this.characterGrid = characterGrid;
    }

    public void characterGrid(@Nullable final CharacterGrid characterGrid) {
        this.characterGrid = characterGrid;
    }

    @NotNull
    public Optional<CharacterGrid> characterGrid() {
        return Optional.ofNullable(characterGrid);
    }

    public abstract void fullUpdate();

}
