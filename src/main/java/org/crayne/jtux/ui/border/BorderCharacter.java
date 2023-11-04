package org.crayne.jtux.ui.border;

import org.crayne.jtux.text.color.ansi.TextColor;
import org.jetbrains.annotations.NotNull;

public record BorderCharacter(char character, @NotNull TextColor color) {

}
