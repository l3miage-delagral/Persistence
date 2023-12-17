import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import fr.uga.miage.m1.shapes.ShapeFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import fr.uga.miage.m1.shapes.Group;
import fr.uga.miage.m1.shapes.SimpleShape;


class GroupTest {

    private Group group;

    @BeforeEach
    void setUp() {
        group = new Group();
    }

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
        group.move(10, 10);
        Assertions.assertEquals(-39, shape.getX());
        Assertions.assertEquals(-38, shape.getY());
    }

    @Test
    void testAddShape() {
        SimpleShape circle = ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.CIRCLE, 1, 2, 0);
        group.add(circle);
        assertTrue(group.isInGroup(circle));
    }

    @Test
    void testRemoveShape() {
        SimpleShape square = ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.SQUARE, 1, 2, 0);
        group.add(square);
        group.remove(square);
        assertFalse(group.isInGroup(square));
    }

    @Test
    void testMoveGroup() {
        SimpleShape triangle = ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.TRIANGLE, 50, 50, 0);
        SimpleShape square = ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.SQUARE, 100, 150, 0);
        group.add(triangle);
        group.add(square);
        group.move(50, 50);
        assertEquals(square.getX(), 100);
        assertEquals(square.getY(), 150);
    }
    
}
