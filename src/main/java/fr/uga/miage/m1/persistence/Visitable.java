package fr.uga.miage.m1.persistence;

/**
 * If you accept to be visited, implement this Interface!
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public interface Visitable {

    /**
     * Accept a visitor.
     *
     * @param visitor
     * @return
     */
    Object accept(Visitor visitor);
}
