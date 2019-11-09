package business.services;


import business.models.Bibliotheque;
import business.models.ObjectFactory;

public class BibliothequeService
{
    // static variable single_instance of type State
    private static BibliothequeService single_instance = null;

    public Bibliotheque bibliotheque;

    // private constructor restricted to this class itself
    private BibliothequeService()
    {
        bibliotheque = new Bibliotheque();
        bibliotheque.getLivre().add(new ObjectFactory().createBibliothequeLivre());

    }

    // static method to create instance of Singleton class
    public static BibliothequeService getInstance()
    {
        if (single_instance == null)
            single_instance = new BibliothequeService();

        return single_instance;
    }
}