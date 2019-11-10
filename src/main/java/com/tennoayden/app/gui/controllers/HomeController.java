package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.Filtre;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.business.services.ConfigService;
import com.tennoayden.app.gui.models.TableModel;
import com.tennoayden.app.gui.views.FormView;
import com.tennoayden.app.gui.views.HomeView;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.*;

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
                        HomeController.this.chooseXML();
                    } catch (JAXBException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            view.getEditionAjouterLivre().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    HomeController.this.ajouterLivre();
                }
            });
            view.getTable().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    HomeController.this.modifierLivre(mouseEvent);
                }
                @Override
                public void mousePressed(MouseEvent mouseEvent) {}
                @Override
                public void mouseReleased(MouseEvent mouseEvent) {}
                @Override
                public void mouseEntered(MouseEvent mouseEvent) {}
                @Override
                public void mouseExited(MouseEvent mouseEvent) {}
            });
            view.getDeleteItem().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    supprimerLivre();
                }
            });
    }

    public void reloadTable() {
        this.model.fireTableDataChanged();
        this.view.repaint();
    }

    //Listeners
    public void chooseXML() throws JAXBException {
        Filtre filtre = new Filtre(
                new String[]{".xml"},
                "Les fichiers XML (.xml)"
        );

        JFileChooser choix = new JFileChooser();
        choix.addChoosableFileFilter(filtre);
        if(choix.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            BibliothequeService.loadLivre(choix.getSelectedFile().getAbsolutePath());
        }
        this.reloadTable();

    }
    public void ajouterLivre() {
        new FormController("Test", this);
    }
    public void modifierLivre(MouseEvent mouseEvent) {
        JTable table =(JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
            Bibliotheque.Livre livre = BibliothequeService.getInstance().bibliotheque.getLivre().get(table.getSelectedRow());
            new FormController("Modifier livre", this, livre);
        }
    }
    public void supprimerLivre() {
        int n = JOptionPane.showConfirmDialog(
                null,
                "Voulez-vous vraiment supprimer la ligne séléctionnée ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            BibliothequeService.getInstance().bibliotheque.getLivre().
                    remove(view.getTable().getSelectedRow());
            reloadTable();
            ConfigService.getInstance().modification = true;
        }
    }

}

