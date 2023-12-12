package fr.uga.miage.m1.shapereader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.uga.miage.m1.persistence.Visitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.SimpleShape;

public abstract class Export {

    private final Visitor visitor = new XMLVisitor();

    public void xmlExport(List<SimpleShape> shapesList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<root>\n\t<shapes>\n");

        for (SimpleShape shape : shapesList) {
            shape.accept(visitor);
            sb.append(((XMLVisitor) visitor).getRepresentation());
        }
        sb.append("\n\t</shapes>\n</root>");

        // Appeler la fenêtre d'enregistrement
        exportWindow("XML", sb.toString());

    }

    /**
     * Window to save a file
     *
     * @param typeFile: type of file to save (JSON or XML)
     * @param content: The content of file
     */
    private void exportWindow(String typeFile, String content) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le fichier JSON");

        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers " + typeFile.toUpperCase() + "(*." + typeFile.toLowerCase() + ")", typeFile.toLowerCase());
            fileChooser.setFileFilter(filter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Le type de fichier n'est pas valide");
        }

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();

            if (!selectedFile.endsWith("." + typeFile.toLowerCase())) {
                selectedFile += "." + typeFile.toLowerCase(); // Extension est .json ou .xml
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Erreur lors de l'enregistrement du fichier");
            }
        }
    }
}
