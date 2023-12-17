import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.ShapeFactory;
import fr.uga.miage.m1.shapes.SimpleShape;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TriangleTest {

    private final int X = 1;
    private final int Y = 2;

    private final int MX = X -25;
    private final int MY = Y -25;

    // Test JSON
    // test des fonctions Triangle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Triangle JSON")
    void testCircleJSON() {

        JSonVisitor json = new JSonVisitor();;
        ShapeFactory.getInstance().createTriangle(X, Y).accept(json);
        String excpectedRes = "\t{\n\t\t\"type\": \"triangle\",\n\t\t\"x\": " + MX + ",\n\t\t\"y\": " + MY + "\n\t}";

        assertEquals(excpectedRes, json.getRepresentation());
    }

    // Test XML
    // test des fonctions Triangle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Triangle XML")
    void testCircleXML() {

        XMLVisitor xml = new XMLVisitor();;
        SimpleShape triangle = ShapeFactory.getInstance().createTriangle(X, Y);
        triangle.accept(xml);
        String excpectedRes = "\t\t<shape>\n\t\t\t<type>" + triangle.getShapeName() + "</type>\n\t\t\t<x>" + MX + "</x>\n\t\t\t<y>" + MY + "</y>\n\t\t</shape>\n";

        Assertions.assertEquals(excpectedRes, xml.getRepresentation());
    }


    // test de draw()
    @Mock
    private Graphics2D graphics;

    // test de la fonction draw()
    @Test
    @DisplayName("Test triangle draw")
    void testTriangleDraw() {
        graphics = mock(Graphics2D.class);
        ShapeFactory.getInstance().createTriangle(X, Y).draw(graphics);

        // verifier que graphics a bien executé setRenderingHint
        verify(graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // pareil pour fill
        verify(graphics).fill(Mockito.any());
        // pareil pour setColor
        verify(graphics).setColor(Color.black);
        // pareil pour draw
        verify(graphics).draw(Mockito.any());
    }

    @Test
    @DisplayName("Test move a triangle")
    void testMoveTriangle() {
        SimpleShape triangle = ShapeFactory.getInstance().createTriangle(X, Y);
        triangle.move(10, 10);
        Assertions.assertEquals( -15, triangle.getX());
        Assertions.assertEquals(-15, triangle.getY());
    }

    @Test
    @DisplayName("Test contains in triangle")
    void testContainsTriangle() {
        SimpleShape triangle = ShapeFactory.getInstance().createTriangle(X, Y);
        Assertions.assertTrue(triangle.contains(1, 2));
        Assertions.assertFalse(triangle.contains(100, 100));
    }
}
