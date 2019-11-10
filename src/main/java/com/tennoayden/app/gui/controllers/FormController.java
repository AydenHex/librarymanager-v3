package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.ObjectFactory;
import com.tennoayden.app.business.models.StatusType;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.business.services.ConfigService;
import com.tennoayden.app.gui.views.FormView;
import com.tennoayden.app.gui.views.HomeView;
import sun.misc.FormattedFloatingDecimal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormController {
    FormView view;
    Bibliotheque.Livre model;
    ObjectFactory of;
    HomeController hc;

    public FormController(String titre, HomeController homeController) {
        of = new ObjectFactory();
        view = new FormView(homeController.view, titre);
        model = of.createBibliothequeLivre();
        hc = homeController;
        initController();
    }
    public FormController(String titre, HomeController homeController, Bibliotheque.Livre livre) {
        of = new ObjectFactory();
        view = new FormView(homeController.view, titre);
        model = livre;
        hc = homeController;
        initController();
        initView();
    }
    public void initView() {
        this.view.setTitre(model.getTitre());
        this.view.setAuteurPrenom(model.getAuteur().getPrenom());
        this.view.setAuteurNom(model.getAuteur().getNom());
        this.view.setResume(model.getPresentation());
        this.view.setParution(model.getParution());
        this.view.setColonne(model.getColonne());
        this.view.setRangee(model.getRangee());
        this.view.setStatus(model.getStatus());
        this.view.setAQui(model.getAqui());
    }

    public void initController() {
        view.getButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               creerLivre();
            }
        });
        view.getStatusField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleAQui();
            }
        });
    }
    public void toggleAQui() {
        if (view.getStatusField().getSelectedItem() == StatusType.PRETE) {
            view.getAquiField().setEnabled(true);
        } else {
            view.getAquiField().setEnabled(false);
        }
    }

    public Boolean validateForm() {
        if (view.getTitre().isEmpty() ||
            view.getAuteurNom().isEmpty() ||
            view.getAuteurPrenom().isEmpty()) {

            return false;
        }
        if (0 > view.getColonne() && view.getColonne() > 6) {
            return false;
        }
        if (0 > view.getRangee() && view.getRangee() > 8) {
            return false;
        }
        if (-800 > view.getParution() && view.getParution() <= 2030) {
            return false;
        }
        return true;
    }
    public void creerLivre() {
        if (this.validateForm()) {
            Bibliotheque.Livre.Auteur auteur= of.createBibliothequeLivreAuteur();
            auteur.setNom(view.getAuteurNom());
            auteur.setPrenom(view.getAuteurPrenom());

            model.setTitre(view.getTitre());
            model.setAuteur(auteur);
            model.setPresentation(view.getResume());
            model.setColonne(view.getColonne());
            model.setRangee(view.getRangee());
            model.setStatus(view.getStatus());
            model.setAqui(view.getAQui());
            model.setParution(view.getParution());

            if (view.getTitle() == "Ajouter un livre") {
                BibliothequeService.getInstance().bibliotheque.getLivre().add(model);
            }
            view.dispose();
            hc.reloadTable();
            ConfigService.getInstance().modification = true;
        } else {
            JOptionPane.showMessageDialog(null, "Remplissez-tous les champs du formulaire, s'il vous plait.", "Erreur Formulaire", JOptionPane.ERROR_MESSAGE);
        }
    }





}
