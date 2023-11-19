import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.uga.miage.m1.shapes.Group;
import fr.uga.miage.m1.shapes.SimpleShape;


class GroupTest {

    
    @Mock
    private SimpleShape simpleShape;

    @Test
    @DisplayName("Test Group")
    void testGroup() {
        simpleShape = mock(SimpleShape.class);
        Group group = new Group();
        group.add(simpleShape);
        assertTrue(group.isInGroup(simpleShape));
        group.remove(simpleShape);
        assertFalse(group.isInGroup(simpleShape));
    }
    
    
}
