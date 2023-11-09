import fr.uga.miage.m1.JDrawingFrame;

import junit.framework.TestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class JDrawingFrameTest {

    // test du constructeur
    @Test
    @DisplayName("Test JDrawingFrame")
    void testJDrawingFrame() {
        JDrawingFrame frame = new JDrawingFrame("test");
        // verifier que frame a bien été créé
        assertNotNull(frame);
    }

    // test de la méthode undo
    @Test
    @DisplayName("Test JDrawingFrame undo")
    void testUndoFrame() {
        JDrawingFrame frame = new JDrawingFrame("test");
        boolean res = frame.undo();
        boolean excpectedRes = false;
        // verifier que frame a bien été créé
        TestCase.assertEquals(excpectedRes, res);

    }

}
