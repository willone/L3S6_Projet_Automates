package fr.uvhc;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Créé par Florian le 14/02/2015.
 */
public class Automate extends EnsEtats implements Cloneable {
    protected EnsEtats initiaux;
    protected EnsEtats finaux;

    /**
     * Constructeur vide
     */
    Automate() {
        super();
        initiaux = new EnsEtats();
        finaux = new EnsEtats();
    }

    /**
     * Constructeur à 1 paramètre
     *
     * @param nbEtats Nombre d'états de l'automate
     */
    Automate(int nbEtats) {
        for (int i = 1; i <= nbEtats; i++) {
            add(new Etat(i));
        }
    }

    /**
     * Permet de cloner l'automate
     *
     * @return Un objet clone de l'automate
     */
    public Object Clone() {
        Object o = null;

        try {
            o = super.clone();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return o;
    }

    /**
     * Fonction de génération d'un automate -- utile pour Exptoaut.java
     *
     * @param nbEtats
     */
    public void genererAutomate(int nbEtats) {
        for (int i = 1; i <= nbEtats; i++) {
            add(new Etat(i));
        }
    }

    /**
     * Retourne le nombre d'états de l'automate
     *
     * @return Un entier
     */
    public int nbEtats() {
        return this.size();
    }

    /**
     * Retourne le nombre d'états initiaux de l'automate
     *
     * @return Un entier
     */
    public int nbEtatsInitiaux() {
        return initiaux.size();
    }

    /**
     * Retourne le nombre d'états finaux de l'automate
     *
     * @return
     */
    public int nbEtatsFinaux() {
        return finaux.size();
    }

    /**
     * Permet de créer un automate
     */
    public void creer() {
        Scanner sc = new Scanner(System.in);
        int nbEtats;

        System.out.println("### Création de l'automate : les états seront numérotés de 1 à X, où X est le nombre d'états ###");
        System.out.println("### Nombre d'états de l'automate ###");
        nbEtats = sc.nextInt();

        // Ajout des nbEtats
        for (int i = 1; i <= nbEtats; i++) {
            ajouterEtat(new Etat(i));
        }

        // Ajout des états initiaux
        creerEtatsInitiaux(sc);

        // Ajout des états finaux
        creerEtatsTerminaux(sc);

        // Ajout des transitions
        creerTransitions(sc);
    }

    /**
     * Permet de créer des états initiaux [forcément dépendant de creer()]
     *
     * @param sc Un Scanner récupéré à partir de la fonction creer()
     */
    private void creerEtatsInitiaux(Scanner sc) {
        int nbEtatsInitiaux;
        int etatInitial;

        System.out.println("### Nombre d'états initiaux : ###");
        nbEtatsInitiaux = sc.nextInt();

        System.out.println("### Indiquer le numéro des états initiaux : ###");
        while (nbEtatsInitiaux > 0) {
            etatInitial = sc.nextInt();
            for (Etat e : this) {
                if (e.getId() == etatInitial) {
                    e.setInitial();
                    ajouterEtatInitial(e);
                }
            }
            nbEtatsInitiaux--;
        }
    }

    /**
     * Permet de créer des états terminaux [forcément dépendant de creer()]
     *
     * @param sc Un Scanner récupéré à partir de la fonction creer()
     */
    private void creerEtatsTerminaux(Scanner sc) {
        int etatTerminal = 0;
        int nbEtatsTerminaux = 0;
        System.out.println("### Nombre d'états terminaux : ###");
        nbEtatsTerminaux = sc.nextInt();
        System.out.println("### Indiquer le numéro des états terminaux : ###");
        while (nbEtatsTerminaux > 0) {
            etatTerminal = sc.nextInt();
            for (Etat e : this) {
                if (e.getId() == etatTerminal) {
                    e.setTerminal();
                    ajouterEtatFinal(e);
                }
            }
            nbEtatsTerminaux--;
        }
    }

    /**
     * Permet de créer les transitions dans la méthode creer()
     *
     * @param sc Un scanner récupéré via creer()
     */
    private void creerTransitions(Scanner sc) {
        int nbTransitions;
        int etatDepart;
        int etatArrivee;
        char c;
        String transition;

        System.out.println("### Nombre de transitions ###");
        nbTransitions = sc.nextInt();
        sc.nextLine(); // purge le scanner (récupère le retour à la ligne)


        while (nbTransitions > 0) {
            System.out.println("Il vous reste " + nbTransitions + " transition/s à préciser");
            do {
                System.out.println("Syntaxe : <Etat Symbole Etat> (ex. : 1 a 2, a = § si epsilon-transition)");
                transition = sc.nextLine();
            } while (transition.length() != 5);

            etatDepart = Character.getNumericValue(transition.charAt(0));
            etatArrivee = Character.getNumericValue(transition.charAt(4));
            c = transition.charAt(2);

            ajouterTransition(recupererEtat(etatDepart), c, recupererEtat(etatArrivee));

            nbTransitions--;
        }
    }

    /**
     * Ajoute un état initial à l'ensemble initiaux de l'automate
     *
     * @param e Etat initial à ajouter
     */
    public void ajouterEtatInitial(Etat e) {
        initiaux.ajouterEtat(e);
    }

    /**
     * Ajoute un état final à l'ensemble finaux de l'automate
     *
     * @param e Etat final à ajouter
     */
    public void ajouterEtatFinal(Etat e) {
        finaux.ajouterEtat(e);
    }

    /**
     * Récupère l'alphabet associé à l'automate
     *
     * @return L'alphabet lié à l'automate
     */
    public HashSet<Character> alphabet() {
        HashSet<Character> al = new HashSet<Character>();
        for (Etat e : this) {
            HashSet<Character> tmp = new HashSet<Character>();
            tmp.addAll(e.alphabet());
            al.addAll(tmp);
        }
        return al;
    }

    /**
     * Ajoute une transition de e1 vers e2 d'étiquette c
     *
     * @param e1 Etat de départ
     * @param c  Etiquette de la transition
     * @param e2 Etat d'arrivée
     */
    public void ajouterTransition(Etat e1, Character c, Etat e2) {
        for (Etat e : this) {
            if (e.equals(e1)) {
                e.ajouterTransition(c, e2);
            }
        }
    }

    /**
     * Ajoute une epsilon-transition entre e1 et e2
     *
     * @param e1 Etat de départ
     * @param e2 Etat d'arrivée
     */
    public void ajouterTransition(Etat e1, Etat e2) {
        ajouterTransition(e1, ' ', e2);
    }

    /**
     * Supprime une transition de l'automate
     *
     * @param e1 Etat de départ
     * @param c  Etiquette
     * @param e2 Etat d'arrivée
     * @return Un booléen : vrai si la suppression a eu lieu, faux sinon
     */
    public boolean supprimerTransition(Etat e1, char c, Etat e2) {
        for (Etat e : this) {
            if (e.equals(e1)) {
                return e.supprimerTransition(c, e2);
            }
        }
        return false;
    }

    /**
     * Vérifie si un automate est déterministe
     *
     * @return Un booléen
     */
    public boolean estDeterministe() {
        if (nbEtatsInitiaux() != 1) {
            return false;
        } else {
            for (Etat e : this) {
                if (!e.estDeterminisant()) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Vérifie si un automate est synchrone
     *
     * @return Un booléen
     */
    public boolean estSynchrone() {
        for (Etat e : this) {
            if (e.getTransitions().containsKey('§')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (!isEmpty()) {
            String res = "> Etats : " + size() + "\n";
            res += "> Initiaux : " + (initiaux.isEmpty() ? "Aucun" : initiaux + "\n");
            res += "> Terminaux : " + (finaux.isEmpty() ? "Aucun" : finaux + "\n");
            res += "> Déterministe : " + (this.estDeterministe() ? "Oui\n" : "Non\n");
            res += "> Synchrone : " + (this.estSynchrone() ? "Oui\n" : "Non\n");
            res += "> Alphabet : " + this.alphabet() + "\n";
            res += "> Transitions : \n";
            for (Etat e : this) {
                res += e;
            }
            return res;
        } else {
            return ">>> Erreur : Automate vide <<<";
        }
    }
}
