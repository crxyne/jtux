package org.crayne.jtux.ui.border;

import org.crayne.jtux.ui.title.Title;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface AbstractBorder {

    @NotNull
    BorderCharacter topEdge();

    @NotNull
    BorderCharacter bottomEdge();

    @NotNull
    BorderCharacter leftEdge();

    @NotNull
    BorderCharacter rightEdge();

    @NotNull
    BorderCharacter topLeftCorner();

    @NotNull
    BorderCharacter topRightCorner();

    @NotNull
    BorderCharacter bottomLeftCorner();

    @NotNull
    BorderCharacter bottomRightCorner();

    @NotNull
    Optional<Title> topTitle();

    @NotNull
    Optional<Title> bottomTitle();

}
