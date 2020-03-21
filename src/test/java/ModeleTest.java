import com.intellij.librarymanager.model.Abonnement;
import com.intellij.librarymanager.model.Emprunt;
import com.intellij.librarymanager.model.Livre;
import com.intellij.librarymanager.model.Membre;

import java.time.LocalDate;

public class ModeleTest {
    public static void main(String[] args){
        Livre livre = new Livre(1,"Les oiseaux migrateurs", "Patrick FICHTER", "978-2817704876");
        System.out.println(livre);
        Membre membre = new Membre(1,"CHERIF", "Kader", "2 rue Sadi Carnot", "kcherif@email.com", "0243585672", Abonnement.PREMIUM);
        System.out.println(membre);
        System.out.println(membre.getAbonnement());
        LocalDate ld1 = LocalDate.of(2019, 2, 12);
        LocalDate ld2 = LocalDate.of(2019, 2, 19);
        Emprunt emprunt = new Emprunt(1, 2, 1,ld1,ld2);
        System.out.println(emprunt);
    }

}