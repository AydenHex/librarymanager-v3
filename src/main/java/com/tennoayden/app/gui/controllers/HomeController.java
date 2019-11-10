package com.tennoayden.app.gui.controllers;

import business.models.Filtre;
import business.services.BibliothequeService;
import com.tennoayden.app.gui.models.TableModel;
import com.tennoayden.app.gui.views.HomeView;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.jar.JarException;

public class HomeController {
    HomeView view;
    TableModel model;

    public HomeController(String titre) {
        this.model = new TableModel(BibliothequeService.getInstance().bibliotheque);
        this.view = new HomeView(titre, model);
        this.initView();
        this.initController();
    }

    public void initView() {
        this.reloadTable();
    }

    public void initController() {
            view.getFichierOuvrirMenu().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        HomeController.this.chooseFile();
                    } catch (JAXBException ex) {
                        ex.printStackTrace();
                    }
                }
            });
    }

    public void reloadTable() {
        this.model.fireTableDataChanged();
        this.view.repaint();
    }

    //Listeners
    public void chooseFile() throws JAXBException {
        Filtre filtre = new Filtre(
                new String[]{".xml"},
                "Les fichiers XML (.xml)"
        );

        JFileChooser choix = new JFileChooser();
        choix.addChoosableFileFilter(filtre);
        if(choix.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            BibliothequeService.loadLivre(choix.getSelectedFile().getAbsolutePath());

        }
        System.out.println(BibliothequeService.getInstance().bibliotheque.getLivre());
        this.reloadTable();

    }

}

