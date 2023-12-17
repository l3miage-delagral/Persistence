package fr.uga.miage.m1;

import fr.uga.miage.m1.commands.AddShape;
import fr.uga.miage.m1.commands.Command;
import fr.uga.miage.m1.commands.Editor;
import fr.uga.miage.m1.commands.MoveShape;
import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.shapes.*;
import fr.uga.miage.m1.shapereader.Export;
import fr.uga.miage.m1.shapereader.Import;

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

    private transient List<SimpleShape> listShapes = new ArrayList<>();

    private transient Group selectedGroup = null;

    private transient SimpleShape selectedShape = null;

    private final transient Editor editor;

    private boolean isClicked;

    private boolean isDragged = false;

    private int startingDragX;
    private int startingDragY;

    private boolean groupingMode = false;

    private final transient Export export = new Export();
    private final transient Import imp = new Import();

    /**
     * Tracks buttons to manage the background.
     */
    private final EnumMap<ShapeFactory.Shapes, JButton> buttons = new EnumMap<>(ShapeFactory.Shapes.class);

    private final JButton buttonGroup = new JButton("New Group", null);
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
        JButton importXML = new JButton("Import XML", null);

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
        addButtonFile(buttonJSON);
        addButtonFile(buttonXML);
        addButtonFile(importXML);
        addButton(buttonGroup);
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

    private void addButton(JButton button) {
        button.setBorderPainted(false);
        toolbar.add(button);
        toolbar.validate();
        repaint();
        button.setActionCommand("Group Mode");
        reusableActionListener = new ButtonActionListener();
        button.addActionListener(reusableActionListener);
        buttons.put(selected, button);
    }

    private void addButtonFile(JButton button) {
        button.setBorderPainted(false);
        toolbar.add(button);
        toolbar.validate();
        repaint();
    
        // Crée un nouveau gestionnaire d'événements pour les boutons d'exportation
        button.addActionListener(evt -> {
            if (evt.getActionCommand().contains("Export JSON")) {
                exportJSON();
            } else if (evt.getActionCommand().contains("Import XML")) {
                importXML();
            } else if (evt.getActionCommand().contains("Export XML")) {
                exportXML();
            }
        });
    
        buttons.put(selected, button);
    }


    /**
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     *
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {

        for (SimpleShape shape : listShapes) {
            if (shape.contains(evt.getX(), evt.getY()) && groupingMode) {
                isClicked = true;
                shape.selected(true);
                selectedGroup.add(shape);
                shape.draw((Graphics2D) this.panel.getGraphics());
                return;
            }
            isClicked = false;
        }

        if(panel.contains(evt.getX(), evt.getY()) && !groupingMode) {
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
        if (listShapes.isEmpty() || groupingMode) {
            return;
        }

        startingDragX = evt.getX();
        startingDragY = evt.getY();

        for (SimpleShape shape : listShapes) {
            if(shape.contains(evt.getX(), evt.getY())) {
                selectedShape = shape;
                isClicked = false;
            }
        }

    }


    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     *
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {

        if (!groupingMode && selectedShape != null) {
            //selectedShape.move(evt.getX(), evt.getY()); // effet artistique de déplacement
            selectedShape.draw((Graphics2D) this.panel.getGraphics());
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
        if (selectedShape != null && isDragged) {

            int deltaX = e.getX() - startingDragX;
            int deltaY = e.getY() - startingDragY;

            MoveShape moveShape = new MoveShape(this, selectedShape, deltaX, deltaY);
            editor.addCommand(moveShape);
            editor.play();
            isDragged = false;
            selectedShape = null;
        }
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

    public void validerGroup() {
        Group group = new Group(selectedGroup.getListGroup());
        for (SimpleShape shape : group.getListGroup()) {
            removeShape(shape);
        }
        group.validerGroup(Color.BLUE);
        group.draw((Graphics2D) this.panel.getGraphics());
        groupingMode = false;
        selectedGroup = null;
        editor.addCommand(new AddShape(this, group));
        editor.play();
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
        SimpleShape shapeFromList = this.isInList(shape);
        if (listShapes.isEmpty()){
            LOGGER.info("Canvas is empty");
            return;
        } else if (shapeFromList == null) {
            LOGGER.info("Shape already removed");
            return;
        }

        LOGGER.info("Shape removed");
        listShapes.remove(shapeFromList);
        this.paintComponents(this.getGraphics());
    }

    public SimpleShape isInList(SimpleShape shape) {
        for (SimpleShape shapeFromList : listShapes) {
            if (shape.contains(shapeFromList.getX(), shapeFromList.getY()) || shape.getX() == shapeFromList.getX() && shape.getY() == shapeFromList.getY()) {
                return shapeFromList;
            }
        }
        return null;
    }

    /**
         * Creation of JSON string for JSON export
         */
        private void exportJSON() {
            StringBuilder res = new StringBuilder();
            boolean startedGroup = false;
            final String group = "group";

            res.append("{\n\t\"shapes\" : [\n");

            for (SimpleShape shape : listShapes) {
                if (listShapes.indexOf(shape) != listShapes.size() - 1) {
                    res.append(",\n");
                }

                if(shape.getShapeName().contains(group)){
                    startedGroup = true;
                    res.append("\t\t{\n\t\t\t\"group\" : [\n");

                    for (SimpleShape shapeInGroup : ((Group) shape).getListGroup()) {
                        shapeInGroup.accept(new JSonVisitor());
                        JSonVisitor visitor = new JSonVisitor();
                        shapeInGroup.accept(visitor);
                        res.append(visitor.getRepresentation());
                        res.append(",\n");
                    }


                } else if (shape.getShapeName().contains(group) && startedGroup) {
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


        private void importXML() {
            try {
                listShapes = imp.importXML();
                for (SimpleShape shape : listShapes) {
                    if (shape.getShapeName().contains("group")) {
                        shape.validerGroup(Color.BLUE);
                    }
                }
                this.paintComponents(this.getGraphics());
            } catch (Exception e) {
                LOGGER.warning("Erreur lors de l'import du fichier XML : " + e.getMessage());
            }
        }
        /**
         * Creation of XML string for XML export
         */
        private void exportXML() {
            LOGGER.info("Attention j'exporte");
            export.xmlExport(listShapes);
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

    private class ButtonActionListener extends Component implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent evt) {
                
            if (evt.getActionCommand().equals("Group Mode")) {
                if (groupingMode){
                    if (selectedGroup != null) {
                        validerGroup();
                    }
                    buttonGroup.setBackground(Color.WHITE);
                } else {
                    groupingMode = true;
                    buttonGroup.setBackground(Color.BLUE);
                    newGroup();
                }
                buttonGroup.repaint();

            } else if (evt.getActionCommand().contains("JSON")) {
                exportJSON();

            } else if (evt.getActionCommand().contains("XML")) {
                exportXML();
            }
        }

        /**
        * New group in grouping Mode
        */
        private void newGroup() {
            selectedGroup = new Group();
        }

        
    }

    /**
     * Simple action listener for shape tool bar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     */
    private class ShapeActionListener extends Component implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            
            // Itère sur tous les boutons
            for (Map.Entry<ShapeFactory.Shapes, JButton> entry : buttons.entrySet()) {
                ShapeFactory.Shapes shape = entry.getKey();
                JButton btn = entry.getValue();
                if (evt.getActionCommand().equals(shape.toString())) {
                    btn.setBorderPainted(true);
                    selected = shape;
                } else {
                    btn.setBorderPainted(false);
                }
                btn.repaint();
            }
        }
    }

}
