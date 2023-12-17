import fr.uga.miage.m1.shapes.ShapeFactory;
import fr.uga.miage.m1.shapes.ShapeFactory.Shapes;
import fr.uga.miage.m1.shapes.SimpleShape;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShapeFactoryTest {

    private ShapeFactory shapeFactory;

    @BeforeEach
    void setUp() {
        shapeFactory = ShapeFactory.getInstance();
    }

    @Test
    @DisplayName("getInstance")
    void testGetInstance() {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        Assertions.assertEquals(shapeFactory, ShapeFactory.getInstance());
    }

    @Test
    @DisplayName("create a form")
    void testCreateForm(){
ShapeFactory shapeFactory = ShapeFactory.getInstance();
        Assertions.assertEquals("circle", shapeFactory.createCircle(1, 2).getShapeName());
        Assertions.assertEquals("square", shapeFactory.createSquare(1, 2).getShapeName());
        Assertions.assertEquals("triangle", shapeFactory.createTriangle(1, 2).getShapeName());
    }

    @Test
    void testCreateSquare() {
        SimpleShape square = shapeFactory.createSquare(50, 50);
        assertEquals(ShapeFactory.Shapes.SQUARE, square.getShapeType());
        assertEquals(25, square.getX());
        assertEquals(25, square.getY());
    }

    @Test
    void testCreateCircle() {
        SimpleShape circle = shapeFactory.createCircle(25, 75);
        assertEquals(ShapeFactory.Shapes.CIRCLE, circle.getShapeType());
    }

    @Test
    void testCreateTriangle() {
        SimpleShape triangle = shapeFactory.createTriangle(100, 150);
        assertEquals(ShapeFactory.Shapes.TRIANGLE, triangle.getShapeType());
    }

    @Test
    void testCreateGroup() {
        List<SimpleShape> list = new ArrayList<>();
        list.add(shapeFactory.createSquare(25, 50));
        list.add(shapeFactory.createCircle(75, 100));
        SimpleShape group = shapeFactory.createGroup(list);
        assertEquals(ShapeFactory.Shapes.GROUP, group.getShapeType());

    }

    @Test
    void testCreateSimpleShape() {
        Shapes selected = ShapeFactory.Shapes.CUBE;
        SimpleShape shape = shapeFactory.createSimpleShape(selected, 125, 200, 50);
        assertEquals(ShapeFactory.Shapes.CUBE, shape.getShapeType());
        assertEquals(100, shape.getX());
        assertEquals(175, shape.getY());
        assertEquals(50, shape.getZ());
    }
}
