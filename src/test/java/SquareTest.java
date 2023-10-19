import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SquareTest {

    // test des fonctions Square(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Square JSON")
    void testSquareJSON() {
        Square s = new Square(1, 2);
        JSonVisitor json = new JSonVisitor();;
        s.accept(json);
        String excpectedRes = "\t{\n\t\t\"type\": \"square\",\n\t\t\"x\": " + s.getX() + ",\n\t\t\"y\": " + s.getY() + "\n\t}";

        assertEquals(excpectedRes, json.getRepresentation());
    }

    // test des fonctions Square(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Square XML")
    void testSquareXML() {
        Square s = new Square(1, 2);
        XMLVisitor xml = new XMLVisitor();;
        s.accept(xml);
        String excpectedRes = "\t\t<shape>\n\t\t\t<type>square</type>\n\t\t\t<x>" + s.getX() + "</x>\n\t\t\t<y>" + s.getY() + "</y>\n\t\t</shape>";

        assertEquals(excpectedRes, xml.getRepresentation());
    }

    @Mock
    private Graphics2D graphics;

    // test de la fonction draw()
    @Test
    @DisplayName("Test Square draw")
    void testSquareDraw() {
        graphics = mock(Graphics2D.class);
        Square c = new Square(1, 2);
        c.draw(graphics);

        // verifier que graphics a bien executé setRenderingHint
        verify(graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // pareil pour fill
        verify(graphics).fill(new Rectangle2D.Double(c.getX(), c.getY(), 50, 50));
        // pareil pour setColor
        verify(graphics).setColor(Color.black);
        // pareil pour draw
        verify(graphics).draw(new Rectangle2D.Double(c.getX(), c.getY(), 50, 50));
    }
}
