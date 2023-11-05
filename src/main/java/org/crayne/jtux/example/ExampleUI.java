package org.crayne.jtux.example;

import org.crayne.jtux.event.keyboard.KeyEvent;
import org.crayne.jtux.event.keyboard.Keycode;
import org.crayne.jtux.ui.border.BorderDefault;
import org.crayne.jtux.ui.border.BorderTitle;
import org.crayne.jtux.ui.component.Container;
import org.crayne.jtux.ui.component.layout.ComponentOrder;
import org.crayne.jtux.ui.interactive.Application;
import org.crayne.jtux.ui.interactive.WindowManager;
import org.crayne.jtux.util.lib.JTuxLibrary;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExampleUI {

    @NotNull
    private final Application application;

    public ExampleUI() {
        final Container parent = new Container(BorderDefault.NORMAL
                .builder()
                .topTitle(BorderTitle.of("root panel"))
                .build(), ComponentOrder.LEFT_TO_RIGHT);

        parent.fullGrid(JTuxLibrary.out);

        final ExamplePanel examplePanel1 = new ExamplePanel();
        final ExamplePanel examplePanel2 = new ExamplePanel();

        parent.addComponentSilent(examplePanel1);
        parent.addComponentSilent(examplePanel2);

        final WindowManager windowManager = new WindowManager(List.of(
                examplePanel1, examplePanel2
        ));

        windowManager.select(0);
        application = Application.interactiveApplication(parent, windowManager, this::handleKeyEvent);
    }

    public void start() {
        application.start();
    }

    private void handleKeyEvent(@NotNull final KeyEvent event) {
        if (!event.keyDown()) return;

        if (event.keycode() == Keycode.C) {
            JTuxLibrary.shutdown();
            System.exit(0);
        }
    }

}
