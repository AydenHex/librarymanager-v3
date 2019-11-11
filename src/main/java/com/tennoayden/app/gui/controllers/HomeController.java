package com.tennoayden.app.gui.controllers;

import com.tennoayden.app.business.models.Bibliotheque;
import com.tennoayden.app.business.models.Filtre;
import com.tennoayden.app.business.others.WordGenerator;
import com.tennoayden.app.business.services.BibliothequeService;
import com.tennoayden.app.business.services.ConfigService;
import com.tennoayden.app.gui.models.TableModel;
import com.tennoayden.app.gui.views.FormView;
import com.tennoayden.app.gui.views.HomeView;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

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
                        chooseXML();
                    } catch (JAXBException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            view.getEditionAjouterLivre().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        ajouterLivre();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            view.getTable().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    try {
                        HomeController.this.modifierLivre(mouseEvent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            view.getSauvegarderSous().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        sauvegarderSous();
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                }
            });
            view.getSauvegarder().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        sauvegarder();
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                }
            });
            view.getExporter().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        exporter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
            BibliothequeService.getInstance().loadLivre(choix.getSelectedFile().getAbsolutePath());
            ConfigService.getInstance().path = choix.getSelectedFile().getAbsolutePath();
        }
        this.reloadTable();

    }
    public void ajouterLivre() throws IOException {
        new FormController("Test", this);
    }
    public void modifierLivre(MouseEvent mouseEvent) throws IOException {
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

    public void sauvegarderSous() throws JAXBException {
        JFileChooser choix = new JFileChooser();
        if(choix.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
           BibliothequeService.getInstance().sauvegarderLivre(choix.getSelectedFile().getAbsolutePath());
            JOptionPane.showConfirmDialog(null,
                    "Vos modification ont bien été sauvegarger");
        }

    }
    public void sauvegarder() throws JAXBException {
        if (ConfigService.getInstance().path.isEmpty()) {
            sauvegarderSous();
        } else {
            BibliothequeService.getInstance().sauvegarderLivre(ConfigService.getInstance().path);
        }
        JOptionPane.showConfirmDialog(null,
                "Vos modification ont bien été sauvegarger");
    }
    public void exporter() throws Exception {
        JFileChooser choix = new JFileChooser();
        if(choix.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
            new WordGenerator(choix.getSelectedFile().getAbsolutePath());
        }
    }


}

