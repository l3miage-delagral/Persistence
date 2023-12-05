import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import fr.uga.miage.m1.shapes.ShapeFactory;
import org.junit.jupiter.api.Assertions;
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

    @Test
    @DisplayName("Test Group moveThis")
    void testGroupMoveThis() {
        SimpleShape shape = ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.CIRCLE, 1, 2, 0);
        Group group = new Group();
        group.add(shape);
        //group.moveThis(shape, 10, 10);
        Assertions.assertEquals(-15, shape.getX());
        Assertions.assertEquals(-15, shape.getY());
    }
    
}
