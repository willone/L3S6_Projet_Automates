package fr.uvhc;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
		for (Object o : tab)
		{
			Etat e = (Etat)o;
			System.out.println("Alphabet lié à " + e + " : " + e.alphabet());
			System.out.println("Etats successeurs de " + e + " : " + e.successeurs());
			System.out.println("---");
		}
		System.out.println("\n\n-> Test d'affichage via la méthode afficherTout()");
		for (Object o : tab)
		{
			Etat e = (Etat)o;
			System.out.println(e.afficherTout());
		}
		*/
        /* Jeu de tests 2 à supprimer
        Automate automate = new Automate();
		automate.add(new Etat(true, false, 0));
		automate.add(new Etat(1));
		automate.add(new Etat(false, true, 2));
		System.out.println(automate);
		*/

		/* Jeu de tests 3 ([Florian] : je l'ai modifiée pour transformer le if..else en switch) */
        System.out.println("[MENU AUTOMATE]\n");
        System.out.println("1 - Expression vers automate");
        System.out.println("2 - Automate");
        System.out.println("3 - Tests");
        Scanner sc1 = new Scanner(System.in);
        int choix;

        do {
            System.out.println("### Faites votre choix ? ###");
            choix = sc1.nextInt();
        } while ((choix < 1) || (choix > 3));

        switch (choix) {
            case 1:
                Exptoaut exp = new Exptoaut();
                exp.creer();
                break;
            case 2:
                Automate a = new Automate();
                a.creer();
                System.out.println(a);
                break;
            case 3:
                System.out.println("Jeu de tests : affichage");
                Etat e1 = new Etat(true, false, 1);
                Etat e2 = new Etat(false, false, 2);
                Etat e3 = new Etat(false, true, 3);
                e1.ajouterTransition('a', e2);
                e1.ajouterTransition('b', e3);
                e2.ajouterTransition('a', e3);
                e2.ajouterTransition('b', e1);
                e3.ajouterTransition('a', e1);
                e3.ajouterTransition('§', e2);

                Automate a1 = new Automate();
                a1.ajouterEtat(e1);
                a1.ajouterEtat(e2);
                a1.ajouterEtat(e3);
                a1.ajouterEtatInitial(e1);
                a1.ajouterEtatFinal(e3);

                System.out.println(a1);

                System.out.println("--- Test automates synchrones ---");
                AutomateSynchrone as = new AutomateSynchrone(a1.nbEtats());
                as.copier(a1);
                System.out.println("Automate copié : \n" + as);
                break;
            default:
                System.out.println(">>> Erreur : Choix hors-limites <<<");
                break;
        }
        sc1.close();
    }
}
