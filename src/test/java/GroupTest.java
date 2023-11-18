import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.uga.miage.m1.shapes.Group;
import fr.uga.miage.m1.shapes.SimpleShape;


public class GroupTest {

    
    @Mock
    private SimpleShape simpleShape;

    @Test
    @DisplayName("Test Group")
    void testGroup() {
        simpleShape = mock(SimpleShape.class);
        Group group = new Group();
        group.add(simpleShape);
        assertEquals(true,group.isInGroup(simpleShape));

    }
    
    
}
