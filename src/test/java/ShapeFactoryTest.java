import fr.uga.miage.m1.shapes.ShapeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ShapeFactoryTest {

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
        assertEquals(shapeFactory.createCircle(1, 2).getShapeName(), "circle");
        assertEquals(shapeFactory.createSquare(1, 2).getShapeName(), "square");
        assertEquals(shapeFactory.createTriangle(1, 2).getShapeName(), "triangle");
    }

}
