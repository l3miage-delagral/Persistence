package fr.uga.miage.m1;

import java.awt.event.*;

/**
 *  @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class GUIHelper {

    // private constructor to avoid instantiation
    private GUIHelper() { // empty constructor
    }

    public static void showOnFrame(String frameName) {
        JDrawingFrame frame = new JDrawingFrame(frameName);

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

}
