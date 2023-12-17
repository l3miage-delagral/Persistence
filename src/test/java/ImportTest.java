import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import fr.uga.miage.m1.shapereader.Import;
import fr.uga.miage.m1.shapes.SimpleShape;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

class ImportTest {

    private Import importShape;

    @BeforeEach
    void setUp() {
        importShape = new Import();
    }

    @Test
    void testImportXMLWithoutFile() {
        List<SimpleShape> shapes = new ArrayList<>();
        try {
            shapes = importShape.importXML();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(0, shapes.size());
    }

    @Test
    void testImportXMLWithValidFile() throws ParserConfigurationException, SAXException, IOException {
        List<SimpleShape> shapes = importShape.importXML();
        assertNotNull(shapes);
        assertEquals(3, shapes.size());
    }
}
