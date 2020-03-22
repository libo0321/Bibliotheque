import com.intellij.librarymanager.dao.*;
import com.intellij.librarymanager.dao.impl.*;
import com.intellij.librarymanager.model.*;
import com.intellij.librarymanager.exception.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class DaoTest {
    public static void main(String[] args){
        LocalDate ld1 = LocalDate.of(2019, 2, 12);
        LocalDate ld2 = LocalDate.of(2019, 1, 31);
        Emprunt emprunt = new Emprunt(1, 1, 2,ld1,ld2);
        Livre livre = new Livre(3,"1984", "Patrick FICHTER", "978-2817704876");
        Membre membre = new Membre(2,"CRIF", "der", "2 rnot", "kcherimail.com", "02472", Abonnement.PREMIUM);


        LocalDate ld4 = LocalDate.parse("2019-07-30");
        System.out.println(ld4.toString());
        EmpruntDao em = EmpruntDaoImpl.getInstance();
        LivreDao liv = LivreDaoImpl.getInstance();
        MembreDao mem = MembreDaoImpl.getInstance();

        try{
            //test for emprunt
            em.create(4,2,ld1);
            em.create(2,4,ld2);
            em.getById(4);
            em.getListCurrent();
            em.getListCurrentByMembre(4);
            em.getListCurrentByLivre(2);
            System.out.println(em.count());
            em.getList();
            em.update(emprunt);
            em.getList();
            System.out.println(em.count());

            //test for livre
            liv.create("harry potter","JK Rowling", "3ijf");
            liv.getById(5);
            liv.getList();
            System.out.println(liv.count());
            liv.delete(13);
            liv.getById(13);
            liv.getList();
            System.out.println(liv.count());

            //test for membre
            mem.create("harry","Rowling", "3ijf","jdi","23",Abonnement.VIP);
            mem.getById(5);
            mem.getList();
            System.out.println(mem.count());
            mem.delete(3);
            mem.getById(4);
            mem.getList();
            System.out.println(mem.count());

        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }


    }

}