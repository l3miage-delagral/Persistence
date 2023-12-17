import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.ShapeFactory;
import fr.uga.miage.m1.shapes.SimpleShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class CubeTest {

    private final int X = 1;
    private final int Y = 2;

    private final int MX = X -25;
    private final int MY = Y -25;

    // Test JSON
    // test des fonctions Circle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Cube JSON")
    void testCubeJSON() {

        JSonVisitor json = new JSonVisitor();;
        ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.CUBE, X, Y, 5).accept(json);
        String expectedRes = "\t{\n\t\t\"type\": \"cube\",\n\t\t\"x\": " + MX + ",\n\t\t\"y\": " + MY + ",\n\t\t\"z\": 5\n\t}";

        Assertions.assertEquals(expectedRes, json.getRepresentation());
    }

    // Test XML
    // test des fonctions Circle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Cube XML")
    void testCubeXML() {

        XMLVisitor xml = new XMLVisitor();;
        SimpleShape cube = ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.CUBE, X, Y, 5);
        cube.accept(xml);

        String expectedRes = "\t\t<shape>\n\t\t\t<type>" + cube.getShapeName() + "</type>\n\t\t\t<x>" + MX + "</x>\n\t\t\t<y>" + MY + "</y>\n\t\t\t<z>" + cube.getZ() + "</z>\n\t\t</shape>\n";

        Assertions.assertEquals(expectedRes, xml.getRepresentation());
    }

}


