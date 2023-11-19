import fr.uga.miage.m1.commands.AddShape;
import fr.uga.miage.m1.commands.Editor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EditorTest {

    @Mock
    private AddShape commandMock;

    // test de la classe editor
    @Test
    @DisplayName("Test Editor")
    void testEditor() {
        commandMock = mock(AddShape.class);
        Editor editor = new Editor();
        editor.addCommand(commandMock);
        editor.play();
        // Verify that the AddShape command has been executed
        verify(commandMock).execute();
    }
}
