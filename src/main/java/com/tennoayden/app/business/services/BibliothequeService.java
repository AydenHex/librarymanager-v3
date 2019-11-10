package com.tennoayden.app.business.services;


import com.tennoayden.app.business.models.Bibliotheque;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class BibliothequeService
{
    // static variable single_instance of type State
    private static BibliothequeService single_instance = null;

    public Bibliotheque bibliotheque;

    // private constructor restricted to this class itself
    private BibliothequeService()
    {
        bibliotheque = new Bibliotheque();

    }

    // static method to create instance of Singleton class
    public static BibliothequeService getInstance()
    {
        if (single_instance == null)
            single_instance = new BibliothequeService();

        return single_instance;
    }

    public static void loadLivre(String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        //reset bibliotheque
        BibliothequeService.getInstance().bibliotheque.getLivre().clear();

        //We had written this file in marshalling example
        Bibliotheque emps = (Bibliotheque) jaxbUnmarshaller.unmarshal(new File(path));

        for(Bibliotheque.Livre emp : emps.getLivre())
        {
            BibliothequeService.getInstance().bibliotheque.getLivre().add(emp);
        }

    }
}