import fr.uga.miage.m1.JDrawingFrame;
import fr.uga.miage.m1.commands.Undo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UndoTest {

    @Mock
    private JDrawingFrame frameMock;

    @Test
    @DisplayName("Test Undo")
    void testUndo() {
        frameMock = mock(JDrawingFrame.class);
        Undo undo = new Undo(frameMock);
        undo.execute();

        // Verify that the undo command has been executed
        verify(frameMock).undo();

    }


}
