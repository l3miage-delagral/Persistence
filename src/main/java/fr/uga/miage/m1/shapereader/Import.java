package fr.uga.miage.m1.shapereader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;

import fr.uga.miage.m1.shapes.Group;
import fr.uga.miage.m1.shapes.ShapeFactory;
import fr.uga.miage.m1.shapes.SimpleShape;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class to import a file
 */

public class Import {
    
    public List<SimpleShape> importXML() throws ParserConfigurationException, SAXException, IOException {
        // Appeler la fenêtre d'ouverture
        File file = importWindow("XML");

        List<SimpleShape> shapesList = new ArrayList<>();

        if (file != null) {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmlDocument = db.parse(file.getPath());
            
            NodeList shapes = xmlDocument.getDocumentElement().getElementsByTagName("shapes");

            for (int i = 0; i < shapes.getLength(); i++) {
                NodeList shapeElements = shapes.item(i).getChildNodes();

                for (int j = 0; j < shapeElements.getLength(); j++) {
                    Node shapeElement = shapeElements.item(j);

                    if (shapeElement.getNodeType() == Node.ELEMENT_NODE) {
                        if (shapeElement.getNodeName().equals("shape")) {
                            shapesList.add(createSimpleShape(shapeElement));
                        } else if (shapeElement.getNodeName().equals("group")) {
                            shapesList.add(createGroup(shapeElement));
                        }
                    }
                }
            }

        }

        return shapesList;
    }
    
    /**
     * Create a shape from a node
     * @param shapeElement
     * @return
     */
    private SimpleShape createSimpleShape(Node shapeElement) {

        String type = shapeElement.getFirstChild().getNextSibling().getTextContent();
        int x = Integer.parseInt(shapeElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getTextContent());
        int y = Integer.parseInt(shapeElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent());
        int z = 0;
        if (shapeElement.getChildNodes().item(7) != null) {
            z = Integer.parseInt(shapeElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent());
        }
    
        return ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.valueOf(type.toUpperCase()), x, y, z);
    }

    /**
     * Create a group of shapes from a node
     * @param shapeElement
     * @return
     */
    private SimpleShape createGroup(Node shapeElement) {

        List<SimpleShape> shapes = new ArrayList<>();
    
        NodeList shapeElements = shapeElement.getChildNodes();
        for (int j = 0; j < shapeElements.getLength(); j++) {
            Node shapeElem = shapeElements.item(j);
    
            if (shapeElem.getNodeType() == Node.ELEMENT_NODE) {
                shapes.add(createSimpleShape(shapeElem));
            }
        }
    
        return ShapeFactory.getInstance().createGroup(shapes);
    }

    /**
     * Window to open a file
     *
     * @param typeFile: type of file to open (JSON or XML)
     * @return File: The file to open
     */
    public File importWindow(String typeFile) {
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Ouvrir le fichier " + typeFile.toUpperCase());
    
            try {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers " + typeFile.toUpperCase() + "(*." + typeFile.toLowerCase() + ")", typeFile.toLowerCase());
                fileChooser.setFileFilter(filter);
            } catch (Exception e) {
                throw new IllegalArgumentException("Le type de fichier n'est pas valide");
            }
    
            int userSelection = fileChooser.showOpenDialog(null);
    
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile();
            }
    
            return null;
    }
}
