package fr.uga.miage.m1;

import fr.uga.miage.m1.commands.Command;
import fr.uga.miage.m1.commands.Editor;
import fr.uga.miage.m1.commands.Undo;

import java.awt.event.*;
import javax.swing.*;

/**
 *  @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class GUIHelper {

    // private constructor to avoid instantiation
    private GUIHelper() { // empty constructor
    }

    public static void showOnFrame(String frameName) {
        JDrawingFrame frame = new JDrawingFrame(frameName);

        frame = addKeyboardListener(frame);

        WindowAdapter wa = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(wa);
        frame.pack();
        frame.setVisible(true);
    }

    private static JDrawingFrame addKeyboardListener(JDrawingFrame frame) {

        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "removeShape");
        frame.getRootPane().getActionMap().put("removeShape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command command = new Undo(frame);
                Editor invoker = new Editor();
                invoker.addCommand(command);
                invoker.play();

            }
        });

        return frame;
    }

}
