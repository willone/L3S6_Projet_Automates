package fr.uvhc;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Créé par Florian le 15/03/2015.
 */
public class AutomateSynchrone extends Automate {

    AutomateSynchrone(int nbEtats) {
        super(nbEtats);
    }

    /**
     * Synchroniser un automate a donné en entrée [méthode à terminer]
     * @param a Automate asynchrone (ou pas, mais inutile sinon)
     */
    public void synchroniser(Automate a) {
        int nbEtats = a.size();
        AutomateSynchrone eqSync = new AutomateSynchrone(nbEtats);

        // On duplique l'automate de base et numérote les états de 1 à nbEtats où nbEtats est le nombre d'états
        for (Etat e : eqSync) {
            Iterator it = a.iterator();
            if (it.hasNext()) {
                Etat ea = (Etat) it.next();
                if (ea.isInitial()) {
                    e.setInitial();
                    eqSync.ajouterEtatInitial(e);
                }
                if (ea.isTerminal()) {
                    e.setTerminal();
                    eqSync.ajouterEtatFinal(e);
                }
                for (HashMap.Entry<Character, EnsEtats> entree : ea.getTransitions().entrySet()) {
                    Iterator<Etat> ite = entree.getValue().iterator();
                    e.ajouterTransition(entree.getKey(), (Etat) it.next());
                }
            }
        }

        // On détermine la valeur de k (id automate) la plus élevée telle qu'il existe <q epsilon k>, q != k
        // On récupère les états q1 tels que <q1 epsilon k> (k état != q1) => EnsEtats ensQ1
        // On récupère les états q2 tels que <k etiquette q2> (etiquette != epsilon) => EnsEtats ensQ2
        // On supprime la transition <q1 epsilon k>
        // On ajoute les transitions <q1 etiquette q2> à partir des ensembles ensQ1 et ensQ2
        // On réitère l'algorithme jusqu'à ce qu'il n'y ait plus d'epsilon-transition
        // L'automate est synchronisé
    }
}
