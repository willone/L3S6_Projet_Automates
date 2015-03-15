package fr.uvhc;

import java.util.Scanner;


public class Exptoaut extends Automate {
    private String expression;
    private String alphabet;

    /**
     * Constructeur vide
     */
    Exptoaut() {
        super();
        expression = new String();
    }

    public void nbetatsexp(String exp, String alpha) {
        int etat = 1;
        int i = 1;
        int vrai = 0;
        int j;
        //System.out.println("exp[1} : "+expression.charAt(0));
        while (i <= expression.length()) {
            for (j = 1; j <= alphabet.length(); j++) {
                if (expression.charAt(i - 1) == alphabet.charAt(j - 1)) {
                    vrai = 1;
                    //printf("[%d] vrai = %d\n",j,vrai);
                    //system("pause");
                }
            }
            if (vrai != 0) {
                //System.out.println("etat : la ");
                etat += 1;
                vrai = 0;
                i++;
            } else {
                if (expression.charAt(i - 1) == '[' || expression.charAt(i - 1) == ']' || expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == ')' || expression.charAt(i - 1) == '+' || expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '.') {
                    i++;
                } else {
                    System.out.println("\n### erreur d'expression !!!### \n");
                    break;
                }
            }
        }
        Automate b = new Automate();
        for (i = 1; i <= etat; i++) {
            ajouterEtat(new Etat(i));
        }
        b.genererAutomate(etat);
        System.out.println(b.recupererEtat(etat));
        System.out.println("etat : " + etat);
        System.out.println("expression : " + expression.length());
        System.out.println("alphabet : " + alphabet.length());
        creerAutomate(b, expression, alphabet);
    }

    public void Exptoaut(String exp, String alpha) {
        expression = exp;
        alphabet = alpha;
        nbetatsexp(expression, alphabet);
    }

    int appartient(char a, String alpha) {
        int vrai = 0;
        int i;
        for (i = 1; i <= alpha.length(); i++) {
            if (a == alpha.charAt(i - 1)) {
                vrai = 1;
                return vrai;
            }
        }
        return vrai;
    }


    public void creerAutomate(Automate b, String expression, String alphabet) {
        int etat = 1;
        int i = 1;
        int k = 0;
        int key = 0;
        int val = 1;
        int debut = 0;
        int fin = 0;
        int debut1 = 0;
        Character c = null;
        //Etat f = null;
        Etat e = b.recupererEtat(i);
        e.setInitial();
        while (i <= expression.length()) {
            if (appartient(expression.charAt(i - 1), alphabet) == 1) {
                key = etat;
                int l = i;
                while (appartient(expression.charAt(l - 1), alphabet) == 1) {
                    etat += 1;
                    c = new Character(expression.charAt(l - 1));
                    b.ajouterTransition(recupererEtat(etat - key), c, recupererEtat(etat));
                    l++;
                    if (l > expression.length()) {
                        Etat f = b.recupererEtat(etat);
                        f.setTerminal();
                        break;
                    }
                    System.out.println("l : " + l);
                }
                i = l;
                //System.out.println("etat : ici ");
            } else if ((expression.charAt(i - 1) == '+') && (appartient(expression.charAt(i), alphabet) == 1)) {
                b.ajouterTransition(recupererEtat(etat), recupererEtat(key));
                Etat f = b.recupererEtat(i);
                f.setTerminal();
                i++;
            } else if ((expression.charAt(i - 1) == '*') && (appartient(expression.charAt(i - 2), alphabet) == 1)) {
                c = new Character(expression.charAt(i - 2));
                b.ajouterTransition(recupererEtat(etat), c, recupererEtat(etat));
                i++;
            } else if ((expression.charAt(i - 1) == '(') && (appartient(expression.charAt(i), alphabet) == 1)) {
                debut = etat;
                i++;
            } else if ((expression.charAt(i - 1) == '(') && (expression.charAt(i) == '(')) {
                debut1 = etat;
                i++;
            } else if (expression.charAt(i - 1) == ')') {
                i++;
                fin = etat;
                Etat f = b.recupererEtat(etat - 1);
                f.setTerminal();
                if (expression.charAt(i - 1) == '*') {
                    b.ajouterTransition(recupererEtat(fin), recupererEtat(debut));
                    b.ajouterTransition(recupererEtat(debut), recupererEtat(fin));
                    debut = debut1;
                    i++;
                } else if (expression.charAt(i - 1) == '+') {
                    b.ajouterTransition(recupererEtat(etat), recupererEtat(debut));
                    debut = debut1;
                    i++;
                }
            }
        }
        System.out.println(b);
    }

    @Override
    public void creer() {
        Scanner tc = new Scanner(System.in);
        String expre;
        String alpha;
        System.out.println("\n### Alphabet utilisé ###");
        alpha = tc.nextLine();
        System.out.println("\n### Expression ###");
        expre = tc.nextLine();
        Exptoaut(expre, alpha);
    }
}