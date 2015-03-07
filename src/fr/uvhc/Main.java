package fr.uvhc;

public class Main {

    public static void main(String[] args) {

        System.out.println("[TESTS]\n");

        /* Jeu de tests à supprimer
        EnsEtats ens = new EnsEtats();

        Etat e0 = new Etat(true, false, 0);
        Etat e1 = new Etat(false, false, 1);
        Etat e2 = new Etat(false, true, 2);

        e0.ajouterTransition('a', e2);
        e0.ajouterTransition('a', e1);
        e0.ajouterTransition('b', e0);
        e1.ajouterTransition('b', e0);
        e1.ajouterTransition('b', e2);
        e2.ajouterTransition('c', e0);

        ens.add(e0);
        ens.add(e1);
        ens.add(e2);

        Object[] tab = ens.toArray();

        System.out.println("-> Test d'affichage via la surcharge de toString()");
        for (Object o : tab) {
            Etat e = (Etat)o;
            System.out.println("Alphabet lié à " + e + " : " + e.alphabet());
            System.out.println("Etats successeurs de " + e + " : " + e.successeurs());
            System.out.println("---");
        }

        System.out.println("\n\n-> Test d'affichage via la méthode afficherTout()");
        for (Object o : tab) {
            Etat e = (Etat)o;
            System.out.println(e.afficherTout());
        }
        */
        Automate automate = new Automate();
        automate.add(new Etat(true, false, 0));
        automate.add(new Etat(1));
        automate.add(new Etat(false, true, 2));

        System.out.println(automate);
    }
}
