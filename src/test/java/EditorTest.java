import fr.uga.miage.m1.JDrawingFrame;
import fr.uga.miage.m1.commands.AddShape;
import fr.uga.miage.m1.commands.Editor;
import fr.uga.miage.m1.commands.MoveShape;
import fr.uga.miage.m1.shapes.Group;
import fr.uga.miage.m1.shapes.SimpleShape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;
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
        // mock startingShapeList
        List<SimpleShape> startingShapeList = new ArrayList<>();
        startingShapeList.add(shapeMock);
        // mock selectedGroup
        Group selectedGroup = mock(Group.class);
        when(selectedGroup.getListGroup()).thenReturn(startingShapeList);
        // mock evtX and evtY
        int evtX = 10;
        int evtY = 10;
        // mock deltaX and deltaY
        int deltaX = 10;
        int deltaY = 10;

        MoveShape command = new MoveShape(jDrawingFrame, startingShapeList, selectedGroup, deltaX, deltaY, evtX, evtY);
        editor.addCommand(command);
        editor.play();
        // Verify that the shape has been moved
        verify(jDrawingFrame).removeShape(shapeMock);
        verify(jDrawingFrame).addShape(shapeMock);

        // undo
        editor.getLastCommand().undo();
        verify(jDrawingFrame, times(2)).removeShape(shapeMock);
        verify(jDrawingFrame, times(2)).addShape(shapeMock);


    }
}
