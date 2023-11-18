package fr.uga.miage.m1;

import fr.uga.miage.m1.commands.AddShape;
import fr.uga.miage.m1.commands.Command;
import fr.uga.miage.m1.commands.Editor;
import fr.uga.miage.m1.commands.MoveShape;
import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.*;

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
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener{

    private static final String URL = "src/main/java/fr/uga/miage/m1";
    private static final Logger LOGGER = Logger.getLogger(JDrawingFrame.class.getName());

    private static final long serialVersionUID = 1L;

    private final JToolBar toolbar;

    private ShapeFactory.Shapes selected;

    private final JPanel panel;

    private final JLabel label;

    private transient ActionListener reusableActionListener = new ShapeActionListener();

    private final transient List<SimpleShape> listShapes = new ArrayList<>();

    private final List<SimpleShape> startingShapeList = new ArrayList<>();

    private transient Group selectedGroup = null;

    private final transient Editor editor;

    private boolean isClicked;

    private boolean isDragged = false;

    private int startingDragX;
    private int startingDragY;


    /**
     * Tracks buttons to manage the background.
     */
    private final EnumMap<ShapeFactory.Shapes, JButton> buttons = new EnumMap<>(ShapeFactory.Shapes.class);

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


        label = new JLabel(" ", SwingConstants.CENTER);
        JButton buttonJSON = new JButton("Export JSON", null);
        JButton buttonXML = new JButton("Export XML", null);

        // Fills the panel
        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        // Add shapes in the menu
        addButtonShape(ShapeFactory.Shapes.SQUARE, new ImageIcon(URL + "/resources/images/square.png"));
        addButtonShape(ShapeFactory.Shapes.TRIANGLE, new ImageIcon(URL + "/resources/images/triangle.png"));
        addButtonShape(ShapeFactory.Shapes.CIRCLE, new ImageIcon(URL + "/resources/images/circle.png"));
        addButtonShape(ShapeFactory.Shapes.CUBE, new ImageIcon(URL + "/resources/images/underc.png"));
        addButton(buttonJSON, "JSON");
        addButton(buttonXML,"XML");
        setPreferredSize(new Dimension(400, 400));

        editor = new Editor();

        panel.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
        panel.getRootPane().getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command c = editor.getLastCommand();
                if (c != null) {
                    c.undo();
                } else {
                    LOGGER.info("Can't undo");
                }

            }
        });
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     *
     * @param name The name of the injected <tt>SimpleShape</tt>.
     * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
     */
    private void addButtonShape(ShapeFactory.Shapes name, ImageIcon icon) {
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

    private void addButton(JButton button, String typeFile) {
        button.setBorderPainted(false);
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

        for (SimpleShape shape : listShapes) {
            if (shape.contains(evt.getX(), evt.getY())) {
                isClicked = false;
                return;
            }
        }

        if(panel.contains(evt.getX(), evt.getY())) {
             SimpleShape shape = ShapeFactory.getInstance().createSimpleShape(selected, evt.getX(), evt.getY(), 0);
             if (shape == null) {
                LOGGER.warning("No shape selected");
             } else {
                editor.addCommand(new AddShape(this,shape));
                editor.play();
             }
             isClicked = true;

        }

    }

    public void addShape(SimpleShape shape){
        listShapes.add(shape);
        shape.draw((Graphics2D) this.panel.getGraphics());
        LOGGER.info(shape.getShapeName() + " drawn" );
    }



    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     *
     * @param evt The associated mouse event.
     */
    public void mouseEntered(MouseEvent evt) {
        // not implemented
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
    public void mousePressed(MouseEvent evt) {
        if (listShapes.isEmpty()) {
            return;
        }

        for (SimpleShape shape : listShapes) {
            SimpleShape shapeCopy = ShapeFactory.getInstance().createSimpleShape(shape.getShapeType(), shape.getX() + 25, shape.getY() + 25, 0);

            if (shape.contains(evt.getX(), evt.getY())){

                if (selectedGroup != null){
                    if (!selectedGroup.isInGroup(shape)) {
                        selectedGroup.add(shape);
                    } else if (selectedGroup.isInGroup(shape)) {
                        startingShapeList.clear();
                        for (SimpleShape shapeInGroup : selectedGroup.getListGroup()) {

                            startingShapeList.add(ShapeFactory.getInstance().createSimpleShape(shapeInGroup.getShapeType(), shapeInGroup.getX() + 25, shapeInGroup.getY() + 25, 0));

                        }
                        startingDragX = evt.getX();
                        startingDragY = evt.getY();
                        isClicked = false;
                        return;
                    }
                } else {
                    this.selectedGroup = new Group();
                    selectedGroup.add(shape);
                }
                startingShapeList.add(shapeCopy);
                shape.draw((Graphics2D) panel.getGraphics());

                startingDragX = evt.getX();
                startingDragY = evt.getY();
                isClicked = false;
            }
        }
        LOGGER.info("Shape selected");
    }




    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     *
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {

        if (selectedGroup != null) {
            int compteur = 0;
            for (SimpleShape shape : selectedGroup.getListGroup()) {
                if (startingShapeList.size() <= compteur) {
                    return;
                }
                SimpleShape startShape = startingShapeList.get(compteur);

                int deltaX = evt.getX() - startingDragX + 25;
                int deltaY = evt.getY() - startingDragY + 25;
                shape.selected(true);
                shape.move(startShape.getX() + deltaX, startShape.getY() + deltaY);
                shape.draw((Graphics2D) panel.getGraphics());
                compteur++;
            }
            isDragged = true;
        }


    }


    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     */
    public void mouseReleased(MouseEvent e) {
        if(isClicked) {
            return;
        }
        if (selectedGroup != null && isDragged) {

            int deltaX = e.getX() - startingDragX + 25;
            int deltaY = e.getY() - startingDragY + 25;

            MoveShape moveShape = new MoveShape(this, startingShapeList, selectedGroup,deltaX, deltaY, e.getX(), e.getY());

            editor.addCommand(moveShape);
            editor.play();

            startingShapeList.clear();

            isDragged = false;

        }
    }


    public void componentResized(MouseEvent e) { // default implementation ignored
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
        Graphics2D g2 = (Graphics2D) this.panel.getGraphics();
        for (SimpleShape shape : getListShapes()) {
            shape.draw(g2);
        }
    }

    // This method is called when the user makes Ctrl z
    public void removeShape(SimpleShape shape) {
        if (listShapes.isEmpty()){
            LOGGER.info("Canvas is empty");
            return;
        }
        LOGGER.info("Shape removed");
        listShapes.remove(shape);
        if (selectedGroup != null && selectedGroup.isInGroup(shape)) {
            selectedGroup.remove(shape);
        }
        this.paintComponents(this.getGraphics());
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
            for (Map.Entry<ShapeFactory.Shapes, JButton> entry : buttons.entrySet()) {
                ShapeFactory.Shapes shape = entry.getKey();
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
            boolean startedGroup = false;

            res.append("{\n\t\"shapes\" : [\n");

            for (SimpleShape shape : listShapes) {
                if (listShapes.indexOf(shape) != listShapes.size() - 1) {
                    res.append(",\n");
                }

                if(selectedGroup.isInGroup(shape) && !startedGroup){
                    startedGroup = true;
                    res.append("\t\t{\n\t\t\t\"group\" : [\n");

                    for (SimpleShape shapeInGroup : selectedGroup.getListGroup()) {
                        shapeInGroup.accept(new JSonVisitor());
                        JSonVisitor visitor = new JSonVisitor();
                        shapeInGroup.accept(visitor);
                        res.append(visitor.getRepresentation());
                        res.append(",\n");
                    }

                } else if (selectedGroup.isInGroup(shape) && startedGroup) {
                    startedGroup = false;
                    res.append("\n\t\t\t]\n\t\t}");

                } else {
                    JSonVisitor visitor = new JSonVisitor();
                    shape.accept(visitor);
                    res.append(visitor.getRepresentation());
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
            boolean startedGroup = false;
            res.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n\t<shapes>\n");

            for (SimpleShape shape : listShapes) {

                if(selectedGroup.isInGroup(shape) && !startedGroup){
                    startedGroup = true;
                    res.append("\n\t\t<group>\n");

                    for (SimpleShape shapeInGroup : selectedGroup.getListGroup()) {
                        shapeInGroup.accept(new XMLVisitor());
                        XMLVisitor visitor = new XMLVisitor();
                        shapeInGroup.accept(visitor);
                        res.append(visitor.getRepresentation());
                        res.append("\n");
                    }

                } else if (selectedGroup.isInGroup(shape) && startedGroup) {
                    startedGroup = false;
                    res.append("\n\t\t</group>\n");

                } else {
                    XMLVisitor visitor = new XMLVisitor();
                    shape.accept(visitor);
                    res.append(visitor.getRepresentation());
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
