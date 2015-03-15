package fr.uvhc;

import java.util.HashSet;

/**
 * Created by Florian on 14/02/2015.
 */
// On "renomme" un HashSet d'Etat en EnsEtats afin de rendre le code plus clair
public class EnsEtats extends HashSet<Etat> {
    /**
     * Ajoute un état à l'ensemble
     *
     * @param e Etat à ajouter
     */
    public void ajouterEtat(Etat e) {
        this.add(e);
    }

    /**
     * Retourne l'état correspondant à un id donné
     *
     * @param id id dont on cherche à récupérer l'état
     * @return Un Etat
     */
    protected Etat recupererEtat(int id) {
        for (Etat e : this) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    /**
     * Retourne tous les successeurs accessibles par l'étiquette c
     *
     * @param c Lettre choisie
     * @return Un ensemble d'états
     */
    public EnsEtats successeurs(char c) {
        EnsEtats a = new EnsEtats();
        for (Etat etat : this) {
            EnsEtats tmp = etat.successeurs(c);
            a.addAll(tmp);
        }
        return a;
    }

    /**
     * Permet de récupérer l'ensemble des états successeurs (donc atteignable) pour chaque lettre de l'alphabet
     *
     * @return Un ensemble d'états. Ceux atteignables dans cet ensemble.
     */
    public EnsEtats successeurs() {
        EnsEtats ensEtatsAtteignables = new EnsEtats();
        // Pour chaque état de l'ensemble d'états, on récupère tous ses successeurs via etatsAtteignablesParEtat
        // On ajoute ensuite le contenu de cet ensemble à l'ensemble des états atteignables
        for (Etat e : this) {
            EnsEtats etatsAtteignablesParEtat = e.successeurs();
            ensEtatsAtteignables.addAll(etatsAtteignablesParEtat);
        }
        return ensEtatsAtteignables;
    }

    /**
     * Permet de vérifier si l'ensemble contient au moins un état terminal
     *
     * @return Vrai/Faux selon le résultat
     */
    public boolean contientTerminal() {
        for (Etat etat : this) {
            if (etat.isTerminal()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de vérifier si l'ensemble contient au moins un état initial
     *
     * @return Vrai/Faux selon le résultat
     */
    public boolean contientInitial() {
        for (Etat etat : this) {
            if (etat.isInitial()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "";

        for (Etat e : this) {
            res += e.getId() + " ";
        }

        return res;
    }
}