package fr.uvhc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Créé par Florian le 14/02/2015.
 */
public class Etat {

    private static int nbEtats = 0;
    private int id;
    private boolean initial;
    private boolean terminal;
    private HashMap<Character, EnsEtats> transitions;

    Etat() {
        transitions = new HashMap<Character, EnsEtats>();
    }

    /**
     * Constructeur à 1 paramètre
     * Cas d'un état quelconque (ni initial, ni terminal)
     *
     * @param nId Numéro de l'état à créer
     */
    Etat(int nId) {
        nbEtats++;
        transitions = new HashMap<Character, EnsEtats>();
        id = nId;
        terminal = false;
        initial = false;
    }

    /**
     * Constructeur à 3 paramètres
     * Cas d'un état particulier (initial et/ou terminal)
     *
     * @param init Booléen indiquant si l'état est initial
     * @param term Booléen indiquant si l'état est terminal
     * @param nId  Identifiant de l'état
     */
    Etat(boolean init, boolean term, int nId) {
        nbEtats++;
        transitions = new HashMap<Character, EnsEtats>();
        initial = init;
        terminal = term;
        id = nId;
    }

    // Accesseurs et mutateurs
    public int getNbEtats() {
        return nbEtats;
    }

    public void setId(int nId) {
        id = nId;
    }

    public int getId() {
        return id;
    }

    public void setInitial() {
        initial = true;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setTerminal() {
        terminal = true;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public HashMap<Character, EnsEtats> getTransitions() {
        return transitions;
    }


    // Autres méthodes

    /**
     * Récupère tous les successeurs de la transition par c
     *
     * @param c Lettre de la transition
     * @return L'ensemble des états successeurs
     */
    public EnsEtats successeurs(char c) {
        if (transitions.containsKey(c)) {
            return transitions.get(c);
        }
        // Aucune transition n'a pour étiquette c, on renvoie un ensemble vide
        return new EnsEtats();
    }

    /**
     * Récupère l'ensemble des états accessibles à partir de l'état courant
     *
     * @return Un ensemble d'états successeurs
     */
    public EnsEtats successeurs() {
        if (!transitions.isEmpty()) {
            EnsEtats ens = new EnsEtats();
            for (EnsEtats etats : transitions.values()) {
                ens.addAll(etats);
            }
            return ens;
        }
        // On n'a aucune transition partant de notre état, on renvoie donc un ensemble vide
        return new EnsEtats();
    }

    /**
     * Ajoute une transition vers l'état e d'étiquette c
     *
     * @param c L'étiquette de la transition
     * @param e L'état d'arrivée de la transition
     */
    public void ajouterTransition(char c, Etat e) {
        if (transitions.containsKey(c)) {
            // On "enrichi" l'ensemble d'états associé à la lettre c de l'état e
            EnsEtats ens = transitions.get(c);
            ens.add(e);
        } else {
            // L'ensemble d'états associé à la lettre c n'existant pas, on doit l'instancier avant d'y ajouter le nouvel état
            EnsEtats ens = new EnsEtats();
            ens.add(e);
            transitions.put(c, ens);
        }
    }

    /**
     * Ajouter une epsilon-transition vers e
     *
     * @param e Etat d'arrivée de la transition
     */
    public void ajouterTransition(Etat e) {
        char epsilon = ' ';
        ajouterTransition(epsilon, e);
    }

    /**
     * Retourne l'ensemble des lettres associées aux transitions, sans doublon (grâce à l'utilisation d'un Set)
     *
     * @return Ensemble de lettres
     */
    public Set<Character> alphabet() {
        return transitions.keySet();
    }

    /**
     * Vérifie si les transitions partant de cet état ne partagent pas deux fois la même étiquette
     * @return Un booléen
     */
    public boolean estDeterminisant() {
        for ( Character c : alphabet() ) {
            EnsEtats ens = transitions.get(c);
            if ( ens.size() >= 2 )
                return false;
        }
        return true;
    }

    /**
     * Méthode qui renvoie vrai si deux états sont égaux par leur numéro d'identification (id), faux sinon
     *
     * @param e Etat dont on vérifie l'égalité avec l'état courant
     * @return Un booléen
     */
    public boolean equals(Etat e) {
        return ((id == e.getId()) || (hashCode() == e.hashCode()));
    }


    // Surcharges

    @Override
    public boolean equals(Object o) {
        return o instanceof Etat && this.equals((Etat) o);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        String res = "" + id;

        if (isInitial() && !isTerminal())
            res = "=>" + res;
        if (!isInitial() && isTerminal())
            res += "=>";
        if (isInitial() && isTerminal())
            res = "=>" + res + "=>";

        return res;
    }

    /**
     * Une façon d'afficher l'état
     * @return Une chaîne de caractères
     */
    public String afficherTout() {
        String res = "+Etat " + id + "\n";

        res += "Initial : " + ((isInitial()) ? "Oui" : "Non") + "\n";
        res += "Terminal : " + ((isTerminal()) ? "Oui" : "Non") + "\n";
        res += "Alphabet associé : " + alphabet() + "\n";
        res += "Successeurs : " + successeurs() + "\n";

        return res;
    }
}
