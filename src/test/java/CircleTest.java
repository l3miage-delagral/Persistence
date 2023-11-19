import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.ShapeFactory;
import fr.uga.miage.m1.shapes.SimpleShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CircleTest {

    private final int X = 1;
    private final int Y = 2;

    private final int MX = X -25;
    private final int MY = Y -25;

    // Test JSON
    // test des fonctions Circle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Circle JSON")
    void testCircleJSON() {

        JSonVisitor json = new JSonVisitor();;
        ShapeFactory.getInstance().createCircle(X, Y).accept(json);
        String excpectedRes = "\t{\n\t\t\"type\": \"circle\",\n\t\t\"x\": " + MX + ",\n\t\t\"y\": " + MY + "\n\t}";

        assertEquals(excpectedRes, json.getRepresentation());
    }

    // Test XML
    // test des fonctions Circle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Circle XML")
    void testCircleXML() {

        XMLVisitor xml = new XMLVisitor();;
        ShapeFactory.getInstance().createCircle(X, Y).accept(xml);

        String excpectedRes = "\t\t<shape>\n\t\t\t<type>circle</type>\n\t\t\t<x>" + MX + "</x>\n\t\t\t<y>" + MY + "</y>\n\t\t</shape>";

        assertEquals(excpectedRes, xml.getRepresentation());
    }

    // test de draw

    @Mock
    private Graphics2D graphics;

    @Test
    @DisplayName("Test Circle draw")
    void testCircleDraw() {
        graphics = mock(Graphics2D.class);
        ShapeFactory.getInstance().createCircle(X, Y).draw(graphics);

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
    @DisplayName("Test move a Circle")
    void testMoveCircle() {
        SimpleShape circle = ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.CIRCLE,X, Y, 0);
        circle.move(10, 10);
        Assertions.assertEquals(-15, circle.getX());
        Assertions.assertEquals(-15, circle.getY());
    }

    @Test
    @DisplayName("Test contains a Circle")
    void testContainsCircle() {
        SimpleShape circle = ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.CIRCLE,X, Y, 0);
        Assertions.assertTrue(circle.contains(0, 0));
        Assertions.assertFalse(circle.contains(100, 100));
    }

}
