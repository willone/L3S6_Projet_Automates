package fr.uvhc;

import java.util.HashSet;
import java.util.Set;

/**
 * Créé par Florian le 14/02/2015.
 */
public class Automate extends EnsEtats {

    private EnsEtats initiaux;
    private HashSet<Character> alphabet;
    // les transitions sont disponibles via grâce à la classe mère

    /**
     * Constructeur vide
     */
    Automate() {
        super();
        initiaux = new EnsEtats();
    }

    /**
     * Constructeur à 1 paramètre
     * @param nbEtats Nombre d'états de l'automate
     */
    Automate(int nbEtats){
        for (int i = 0; i < nbEtats; i++) {
            add(new Etat(i));
        }
    }

    /**
     * Récupère l'alphabet associé à l'automate
     * @return L'alphabet lié à l'automate
     */
    public Set<Character> alphabet() {
        Set<Character> al = new HashSet<Character>();
        for(Etat e : this){
            Set<Character> tmp = new HashSet<Character>();
            tmp.addAll(e.alphabet());
            al.addAll(tmp);
        }
        return al;
    }

    /**
     * Ajoute une transition de e1 vers e2 d'étiquette c
     * @param e1 Etat de départ
     * @param c Etiquette de la transition
     * @param e2 Etat d'arrivée
     */
    public void ajouterTransition(Etat e1, Character c, Etat e2) {
        for(Etat e : this)
        {
            if (e.equals(e1))
                e.ajouterTransition(c, e2);
        }
    }

    /**
     * Ajoute une epsilon-transition entre e1 et e2
     * @param e1 Etat de départ
     * @param e2 Etat d'arrivée
     */
    public void ajouterTransition(Etat e1, Etat e2) {
        ajouterTransition(e1, ' ', e2);
    }

    @Override
    public String toString() {
        String res = "";

        for(Etat e : this){
            res += e + " ";
        }

        return res;
    }

    /**
     * Vérifie si un automate est déterministe
     * @return Un booléen
     */
    public boolean estDeterministe() {
        for ( Etat e : this) {
            if (!e.estDeterminisant())
                return false;
        }
        return true;
    }
}
