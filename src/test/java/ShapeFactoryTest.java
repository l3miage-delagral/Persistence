import fr.uga.miage.m1.shapes.ShapeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class ShapeFactoryTest {

    @Test
    @DisplayName("getInstance")
    void testGetInstance() {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        assertEquals(shapeFactory, ShapeFactory.getInstance());
    }

    @Test
    @DisplayName("create a form")
    void testCreateForm(){
ShapeFactory shapeFactory = ShapeFactory.getInstance();
        assertEquals("circle", shapeFactory.createCircle(1, 2).getShapeName());
        assertEquals("square", shapeFactory.createSquare(1, 2).getShapeName());
        assertEquals("triangle", shapeFactory.createTriangle(1, 2).getShapeName());
    }

    @Test
    @DisplayName("create a form")
    void testCreateSimpleShape(){
ShapeFactory shapeFactory = ShapeFactory.getInstance();
        assertEquals("circle", shapeFactory.createSimpleShape(ShapeFactory.Shapes.CIRCLE, 1, 2, 0).getShapeName());
        assertEquals("square", shapeFactory.createSimpleShape(ShapeFactory.Shapes.SQUARE, 1, 2, 0).getShapeName());
        assertEquals("triangle", shapeFactory.createSimpleShape(ShapeFactory.Shapes.TRIANGLE, 1, 2, 0).getShapeName());
    }
}
