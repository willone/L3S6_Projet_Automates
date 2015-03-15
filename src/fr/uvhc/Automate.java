package fr.uvhc;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Créé par Florian le 14/02/2015.
 */
public class Automate extends EnsEtats {
    private EnsEtats initiaux;
    /*modification apportée*/
    private EnsEtats finaux;
    private HashSet<Character> alphabet;
    // les transitions sont disponibles via grâce à la classe mère

    /**
     * Constructeur vide
     */
    Automate() {
        super();
        initiaux = new EnsEtats();
        /*modification apportée*/
        finaux = new EnsEtats();
    }

    /**
     * Constructeur à 1 paramètre
     *
     * @param nbEtats Nombre d'états de l'automate
     */
    public void Automate(int nbEtats) {
        for (int i = 1; i <= nbEtats; i++) {
            add(new Etat(i));
        }
    }

    /**
     * Retourne le nombre d'états initiaux de l'automate
     * @return Un entier
     */
    public int nbEtatsInitiaux() {
        return initiaux.size();
    }

    /**
     * Retourne le nombre d'états finaux de l'automate
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
        int nbEtats = 0;

        System.out.println("### Création de l'automate : les états seront numérotés de 1 à X où X est le nombre d'états ###");
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
        int nbEtatsInitiaux = 0;
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
        int nbTransitions = 0;
        int etatDepart = 0;
        int etatArrivee = 0;
        Character c = null;
        String transition;
        System.out.println("### Nombre de transitions : ###");
        nbTransitions = sc.nextInt();
        sc.nextLine(); // purge le scanner (récupère le retour à la ligne)
        System.out.println("### Note : Ecrire les transitions sous la forme Etat Symbole Etat (ex. : 0 a 1 ou 0 § 1 si epsilon-transition) ###");
        while (nbTransitions > 0) {
            transition = sc.nextLine();
            etatDepart = Character.getNumericValue(transition.charAt(0));
            if ((transition.length() == 3) && (transition.charAt(1) != ' ')) {
                etatArrivee = Character.getNumericValue(transition.charAt(2));
                c = new Character(transition.charAt(1));
                ajouterTransition(recupererEtat(etatDepart), c, recupererEtat(etatArrivee));

				/*modification apportée*/
                //etatArrivee = Character.getNumericValue(transition.charAt(2));
                //ajouterTransition(recupererEtat(etatDepart), recupererEtat(etatArrivee));
            } else {
                etatArrivee = Character.getNumericValue(transition.charAt(2));
                ajouterTransition(recupererEtat(etatDepart), recupererEtat(etatArrivee));

				/*modification apportée*/
                //etatArrivee = Character.getNumericValue(transition.charAt(4));
                //c = new Character(transition.charAt(2));
                //ajouterTransition(recupererEtat(etatDepart), c, recupererEtat(etatArrivee));
            }
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
    public Set<Character> alphabet() {
        Set<Character> al = new HashSet<Character>();
        for (Etat e : this) {
            Set<Character> tmp = new HashSet<Character>();
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
     * Vérifie si un automate est déterministe
     *
     * @return Un booléen
     */
    public boolean estDeterministe() {
        //System.out.println("nombre d etat initial : "+nbEtatsInitiaux());
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
     * @return Un booléen
     */
    public boolean estSynchrone() {
        for (Etat e : this){
            if (e.getTransitions().containsKey('§')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String res = "> Etats : " + size() + "\n";
        res += "> Déterministe : " + (estDeterministe() ? "Oui\n" : "Non\n");
        res += "> Alphabet : " + alphabet() + "\n";
        res += "> Transitions : \n";
        for (Etat e : this) {
			/*modification apportée*/
            res += e;
        }
        return res;
    }
}
