package org.crayne.jtux.ui.interactive;

import org.crayne.jtux.event.keyboard.KeyEvent;
import org.jetbrains.annotations.NotNull;

public interface Interactable extends Focusable {

    void whenInteracted(@NotNull final KeyEvent keyEvent);

}
