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
            
            NodeList shapes = xmlDocument.getDocumentElement().getElementsByTagName("shapes").item(0).getChildNodes();
            
            for (int i = 0; i < shapes.getLength(); i++) {
                
                Node shapeElement = shapes.item(i);
                if 
                Node shapeElement = shapes.item(i);
                String type = shapeElement.getChildNodes().item(1).getTextContent();

                int x = Integer.parseInt(shapeElement.getChildNodes().item(3).getTextContent());
                int y = Integer.parseInt(shapeElement.getChildNodes().item(5).getTextContent());
                int z = 0;
                if (shapeElement.getChildNodes().item(7) != null) {
                    z = Integer.parseInt(shapeElement.getChildNodes().item(7).getTextContent());
                }
                    
                shapesList.add(ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.valueOf(type.toUpperCase()), x, y, z));
            }

            for (int i = 0; i < groups.getLength(); i++) {
                NodeList groupShapes = groups.item(i).getChildNodes();
                    List<SimpleShape> groupList = new ArrayList<>();
                for (int j = 0; j < groupShapes.getLength(); j++) {
                    Node groupShapeElement = groupShapes.item(j);
                    String groupType = groupShapeElement.getChildNodes().item(1).getTextContent();
                    int x = Integer.parseInt(groupShapeElement.getChildNodes().item(3).getTextContent());
                    int y = Integer.parseInt(groupShapeElement.getChildNodes().item(5).getTextContent());
                    int z = 0;

                    if (groupShapeElement.getChildNodes().item(7) != null) {
                        z = Integer.parseInt(groupShapeElement.getChildNodes().item(7).getTextContent());
                    }
                    groupList.add(ShapeFactory.getInstance().createSimpleShape(ShapeFactory.Shapes.valueOf(groupType.toUpperCase()), x, y, z));
                }
                shapesList.add(ShapeFactory.getInstance().createGroup(groupList));
            }

        }

        return shapesList;
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
