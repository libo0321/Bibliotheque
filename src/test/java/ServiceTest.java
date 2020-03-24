import com.intellij.librarymanager.exception.ServiceException;
import com.intellij.librarymanager.model.Abonnement;
import com.intellij.librarymanager.model.Emprunt;
import com.intellij.librarymanager.model.Livre;
import com.intellij.librarymanager.model.Membre;
import com.intellij.librarymanager.service.EmpruntService;
import com.intellij.librarymanager.service.LivreService;
import com.intellij.librarymanager.service.MembreService;
import com.intellij.librarymanager.service.impl.EmpruntServiceImpl;
import com.intellij.librarymanager.service.impl.LivreServiceImpl;
import com.intellij.librarymanager.service.impl.MembreServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceTest {
    public static void main(String[] args) {
        /*When you execute the file, you shold execute FillDatebase firstly.*/
        /**test for EmpruntService**/
        try {
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            List<Emprunt>emprunts = new ArrayList<>();
            empruntService.getList();
            int number = empruntService.count();
            System.out.println(number);
            MembreService membreService = MembreServiceImpl.getInstance();
            empruntService.isEmpruntPossible(membreService.getById(2));
            empruntService.getListCurrent();
            empruntService.getListCurrentByLivre(2);
            empruntService.getListCurrentByMembre(5);
            empruntService.isLivreDispo(9);
            empruntService.getById(2);
            empruntService.returnBook(2);
            empruntService.create(2,9, LocalDate.of(2020,03,22));
            empruntService.getList();
        }catch (ServiceException e1){
            System.out.println(e1.getMessage());
        }

        /**test for MembreService**/
        try{
            MembreService membreService = MembreServiceImpl.getInstance();
            membreService.getList();
            membreService.getListMembreEmpruntPossible();
            membreService.getById(12);
            int number = membreService.count();
            System.out.println(number);
            membreService.delete(11);
            membreService.create("aaa","bbb","ccc","ddd","123456");
            //.create(null,"bbb","ccc","ddd","123456");
            Membre membre = new Membre(7,"bbb","bbb","ccc","ddd","123456", Abonnement.VIP);
            membreService.update(membre);
            membreService.getList();
        }catch (ServiceException e1){
            System.out.println(e1.getMessage());
        }

        /**test for LivreService**/
        try{
            LivreService livreService = LivreServiceImpl.getInstance();
            List<Livre>livres = livreService.getList();
            livreService.getById(1);
            System.out.println(livreService.count());
            int ID = livreService.create(null,"HYF","666");
            System.out.println("the ID of livre created:"+ID);
            livreService.getById(1);
            livreService.create("123","HYF","456");
            Livre livre = new Livre(1,"222","HYF","333");
            livreService.update(livre);
            livreService.getList();
            livreService.delete(1);
            livreService.getList();
            int number = livreService.count();
            System.out.println(number);
            livreService.getListDispo();
        }catch (ServiceException e1){
            System.out.println(e1.getMessage());
        }



    }
}
