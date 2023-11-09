package fr.uga.miage.m1.persistence;

import fr.uga.miage.m1.shapes.SimpleShape;



/**
 * You must define a method for each type of Visitable.
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public interface Visitor {

    public void visit(SimpleShape shape);

}
