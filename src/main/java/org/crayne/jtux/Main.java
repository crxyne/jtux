package org.crayne.jtux;

import org.crayne.jtux.example.ExampleUI;
import org.crayne.jtux.util.lib.JTuxLibrary;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Main {

    private Main() {}

    public static void main(@NotNull final String... args) {
        JTuxLibrary.init(new File("src/main/native", "jtux.so"));
        final ExampleUI ui = new ExampleUI();
        ui.start();
    }

}
