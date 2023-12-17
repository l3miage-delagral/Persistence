import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.ShapeFactory;
import fr.uga.miage.m1.shapes.SimpleShape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SquareTest {

    private final int X = 1;
    private final int Y = 2;

    private final int MX = X -25;

    private final int MY = Y -25;

    // test des fonctions Square(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Square JSON")
    void testSquareJSON() {

        JSonVisitor json = new JSonVisitor();;
        ShapeFactory.getInstance().createSquare(X, Y).accept(json);
        String excpectedRes = "\t{\n\t\t\"type\": \"square\",\n\t\t\"x\": " + MX + ",\n\t\t\"y\": " + MY + "\n\t}";

        assertEquals(excpectedRes, json.getRepresentation());
    }

    // test des fonctions Square(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Square XML")
    void testSquareXML() {

        XMLVisitor xml = new XMLVisitor();;
        ShapeFactory.getInstance().createSquare(X, Y).accept(xml);
        String excpectedRes = "\t\t<shape>\n\t\t\t<type>square</type>\n\t\t\t<x>" + MX + "</x>\n\t\t\t<y>" + MY + "</y>\n\t\t</shape>\n";

        assertEquals(excpectedRes, xml.getRepresentation());
    }

    @Mock
    private Graphics2D graphics;

    // test de la fonction draw()
    @Test
    @DisplayName("Test Square draw")
    void testSquareDraw() {
        graphics = mock(Graphics2D.class);
        ShapeFactory.getInstance().createSquare(X, Y).draw(graphics);

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
    @DisplayName("Test move a square")
    void testMoveSquare() {
        SimpleShape square = ShapeFactory.getInstance().createSquare(X, Y);
        square.move(10, 10);
        assertEquals(-15, square.getX());
        assertEquals(-15, square.getY());
    }

    @Test
    @DisplayName("Test contains a square")
    void testContainsSquare() {
        SimpleShape square = ShapeFactory.getInstance().createSquare(X, Y);
        assertTrue(square.contains(0, 0));
        assertFalse(square.contains(100, 100));
    }

}
