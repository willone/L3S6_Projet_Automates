package fr.uvhc;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Florian on 14/02/2015.
 */
public class Automate extends EnsEtats {

    private EnsEtats initiaux;
    private HashSet<Character> alphabet;
    // les transitions sont disponibles via grâce à la classe mère

    Automate() {
        super();
        initiaux = new EnsEtats();
    }

    Automate(int nbEtats){
        for (int i = 0; i < nbEtats; i++) {
            add(new Etat(i));
        }
    }

    public Set<Character> alphabet() {
        Set<Character> al = new HashSet<Character>();
        for(Etat e : this){
            Set<Character> tmp = new HashSet<Character>();
            tmp.addAll(e.alphabet());
            al.addAll(tmp);
        }
        return al;
    }
}
