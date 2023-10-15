package fr.uga.miage.m1;

import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.Circle;
import fr.uga.miage.m1.shapes.SimpleShape;
import fr.uga.miage.m1.shapes.Square;
import fr.uga.miage.m1.shapes.Triangle;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener {

    private static final Logger LOGGER = Logger.getLogger(JDrawingFrame.class.getName());

    private enum Shapes {

        SQUARE, TRIANGLE, CIRCLE
    }

    private static final long serialVersionUID = 1L;

    private final JToolBar toolbar;

    private Shapes selected;

    private final JPanel panel;

    private final JLabel label;

    private transient ActionListener reusableActionListener = new ShapeActionListener();

    private final ArrayList<SimpleShape> listShapes = new ArrayList<>();

    // Editor to manage commands
    //private final transient Editor editor;

    /**
     * Tracks buttons to manage the background.
     */
    private final EnumMap<Shapes, JButton> buttons = new EnumMap<>(Shapes.class);

    /**
     * Default constructor that populates the main window.
     *
     */
    public JDrawingFrame(String frameName) {
        super(frameName);
        // Instantiates components
        toolbar = new JToolBar("Toolbar");
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setMinimumSize(new Dimension(400, 400));
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        panel.setFocusable(true);

        // Instantiates components to manage commands
        // editor = new Editor();

        label = new JLabel(" ", SwingConstants.CENTER);
        JButton buttonJSON = new JButton("Export JSON", null);
        JButton buttonXML = new JButton("Export XML", null);

        // Fills the panel
        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        // Add shapes in the menu
        addShape(Shapes.SQUARE, new ImageIcon("images/square.png"));
        addShape(Shapes.TRIANGLE, new ImageIcon("images/triangle.png"));
        addShape(Shapes.CIRCLE, new ImageIcon("images/circle.png"));
        addButton(buttonJSON, null, "JSON");
        addButton(buttonXML, null, "XML");
        setPreferredSize(new Dimension(400, 400));
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     *
     * @param name The name of the injected <tt>SimpleShape</tt>.
     * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
     */
    private void addShape(Shapes name, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        buttons.put(name, button);
        button.setActionCommand(name.toString());
        button.addActionListener(reusableActionListener);
        if (selected == null) {
            button.doClick();
        }
        toolbar.add(button);
        toolbar.validate();
        repaint();
    }

    private void addButton(JButton button, ImageIcon icon, String typeFile) {
        button.setBorderPainted(false);
        button.setIcon(icon);
        toolbar.add(button);
        toolbar.validate();
        repaint();
        button.setActionCommand("Export button " + typeFile.toUpperCase());
        reusableActionListener = new ShapeActionListener();
        button.addActionListener(reusableActionListener);

    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     *
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        if (panel.contains(evt.getX(), evt.getY())) {
            Graphics2D g2 = (Graphics2D) panel.getGraphics();
            switch (selected) {
                case CIRCLE:
                    Circle c = new Circle(evt.getX(), evt.getY());
                    c.draw(g2);
                    listShapes.add(c);
                    break;
                case TRIANGLE:
                    Triangle t = new Triangle(evt.getX(), evt.getY());
                    t.draw(g2);
                    listShapes.add(t);
                    break;
                case SQUARE:
                    new Square(evt.getX(), evt.getY());
                    Square s = new Square(evt.getX(), evt.getY());
                    s.draw(g2);
                    listShapes.add(s);
                    break;
                default:
                    LOGGER.warning("No shape selected");
            }
            LOGGER.info("Shape added");
        }
    }


    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     *
     * @param evt The associated mouse event.
     */
    public void mouseEntered(MouseEvent evt) { // default implementation ignored
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     *
     * @param evt The associated mouse event.
     */
    public void mouseExited(MouseEvent evt) {
        label.setText(" ");
        label.repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     *
     * @param evt The associated mouse event.
     */
    public void mousePressed(MouseEvent evt) { // default implementation ignored
    }


    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     */
    public void mouseReleased(MouseEvent e) { // default implementation ignored
    }

    public void componentResized(MouseEvent e) { // default implementation ignored
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     *
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) { // default implementation ignored
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt>
     * interface.
     *
     * @param evt The associated mouse event.
     */
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        label.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    public List<SimpleShape> getListShapes() {
        return listShapes;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        for (SimpleShape shape : listShapes) {
            shape.draw(g2);
        }
    }
    public void undo() {
        LOGGER.info("Undo action (Ctrl Z)");
        if (!listShapes.isEmpty()) {
            listShapes.remove(listShapes.size() - 1);
            this.paintComponents(this.getGraphics());
        }
    }
    /**
     * Simple action listener for shape tool bar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     */
    private class ShapeActionListener extends Component implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            boolean exportRequested = false; // Indicateur pour vérifier si une action d'exportation a été demandée
            // Itère sur tous les boutons
            for (Map.Entry<Shapes, JButton> entry : buttons.entrySet()) {
                Shapes shape = entry.getKey();
                JButton btn = entry.getValue();
                if (evt.getActionCommand().equals(shape.toString())) {
                    btn.setBorderPainted(true);
                    selected = shape;
                } else if (evt.getActionCommand().equals("Export button JSON")) {
                    exportRequested = true;
                } else if (evt.getActionCommand().equals("Export button XML")) {
                    exportRequested = true;
                } else {
                    btn.setBorderPainted(false);
                }
                btn.repaint();
            }

            // Une seule déclaration continue à la fin, si une action d'exportation a été demandée
            if (exportRequested) {
                if (evt.getActionCommand().equals("Export button JSON")) {
                    exportJSON();
                } else if (evt.getActionCommand().equals("Export button XML")) {
                    exportXML();
                }
            }
        }


        /**
         * Creation of JSON string for JSON export
         */
        private void exportJSON() {
            StringBuilder res = new StringBuilder();

            res.append("{\n\t\"shapes\" : [\n");

            for (SimpleShape shape : listShapes) {
                if (shape instanceof Circle) {
                    if (listShapes.indexOf(shape) != 0) {
                        res.append(",\n");
                    }
                    JSonVisitor circleVisit = new JSonVisitor();
                    ((Circle) shape).accept(circleVisit);
                    res.append(circleVisit.getRepresentation());
                } else if (shape instanceof Triangle) {
                    if (listShapes.indexOf(shape) != 0) {
                        res.append(",\n");
                    }
                    JSonVisitor triangleVisit = new JSonVisitor();
                    ((Triangle) shape).accept(triangleVisit);
                    res.append(triangleVisit.getRepresentation());
                } else if (shape instanceof Square) {
                    if (listShapes.indexOf(shape) != 0) {
                        res.append(",\n");
                    }
                    JSonVisitor squareVisit = new JSonVisitor();
                    ((Square) shape).accept(squareVisit);
                    res.append(squareVisit.getRepresentation());
                }
            }

            res.append("\n\t]\n}");

            String resultString = res.toString();

            LOGGER.info("*********************** Export JSON ***********************");
            LOGGER.info(resultString);

            // Call up the recording window
            exportWindow("JSON", resultString);
        }



        /**
         * Creation of XML string for XML export
         */
        private void exportXML() {
            StringBuilder res = new StringBuilder();

            res.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n\t<shapes>\n");

            for (SimpleShape shape : listShapes) {
                if (shape instanceof Circle) {
                    XMLVisitor circleVisit = new XMLVisitor();
                    ((Circle) shape).accept(circleVisit);
                    res.append(circleVisit.getRepresentation());
                } else if (shape instanceof Triangle) {
                    if (listShapes.indexOf(shape) != 0) {
                        res.append("\n");
                    }
                    XMLVisitor triangleVisit = new XMLVisitor();
                    ((Triangle) shape).accept(triangleVisit);
                    res.append(triangleVisit.getRepresentation());
                } else if (shape instanceof Square) {
                    if (listShapes.indexOf(shape) != 0) {
                        res.append("\n");
                    }
                    XMLVisitor squareVisit = new XMLVisitor();
                    ((Square) shape).accept(squareVisit);
                    res.append(squareVisit.getRepresentation());
                }
            }

            res.append("\n\t</shapes>\n</root>");

            // Convertir le StringBuilder en une chaîne
            String resultString = res.toString();

            LOGGER.info("*********************** Export XML ***********************");
            LOGGER.info(resultString);

            // Appeler la fenêtre d'enregistrement
            exportWindow("XML", resultString);
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
                // Filter for type of file
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers " + typeFile.toUpperCase() + "(*." + typeFile.toLowerCase() + ")", typeFile.toLowerCase());
                fileChooser.setFileFilter(filter);
            } catch (Exception e) {
                LOGGER.warning("Erreur lors de la création du filtre pour les fichiers " + typeFile.toUpperCase() + " : " + e.getMessage());
            }

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();

                if (!selectedFile.endsWith("." + typeFile.toLowerCase())) {
                    selectedFile += "." + typeFile.toLowerCase(); // Assurer que l'extension est .json ou .xml
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                    writer.write(content); // Écrire la chaîne dans le fichier sélectionné
                    writer.flush();
                    LOGGER.fine("Le fichier a été enregistré avec succès");
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.warning("Erreur lors de l'enregistrement du fichier " + typeFile.toUpperCase() + " : " + e.getMessage());
                }
            }

        }

    }

}
