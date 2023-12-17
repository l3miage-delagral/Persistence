import fr.uga.miage.m1.JDrawingFrame;
import fr.uga.miage.m1.commands.AddShape;
import fr.uga.miage.m1.commands.Editor;
import fr.uga.miage.m1.commands.MoveShape;
import fr.uga.miage.m1.shapes.SimpleShape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

class EditorTest {

    @Mock
    private SimpleShape shapeMock;

    @Mock
    private JDrawingFrame jDrawingFrame;

    // test de la classe editor
    @Test
    @DisplayName("Test Editor addShape")
    void testEditor() {
        jDrawingFrame = mock(JDrawingFrame.class);

        Editor editor = new Editor();
        shapeMock = mock(SimpleShape.class);
        AddShape command = new AddShape(jDrawingFrame, shapeMock);
        editor.addCommand(command);
        editor.play();
        // Verify that the AddShape command has been executed
        verify(jDrawingFrame).addShape(shapeMock);

        // Undo
        editor.getLastCommand().undo();
        verify(jDrawingFrame).removeShape(shapeMock);
    }

    @Test
    @DisplayName("Test Editor moveShape")
    void testEditorMove() {
        jDrawingFrame = mock(JDrawingFrame.class);
        Editor editor = new Editor();
        shapeMock = mock(SimpleShape.class);
        when(shapeMock.getX()).thenReturn(10);
        when(shapeMock.getY()).thenReturn(10);
        MoveShape command = new MoveShape(jDrawingFrame, shapeMock, 10, 10);
        editor.addCommand(command);
        editor.play();
        // Verify that the MoveShape command has been executed
        verify(shapeMock).move(45, 45);

        // Undo
        editor.getLastCommand().undo();
        verify(shapeMock).move(10, 10);
    }

}
