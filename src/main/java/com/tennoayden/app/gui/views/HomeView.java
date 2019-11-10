package com.tennoayden.app.gui.views;

import com.tennoayden.app.gui.models.TableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class HomeView{
    //Home
    private JFrame frame;

    //MenuBar
    private JMenuBar menubar;
    private JMenu fichier;
    private JMenu edition;
    private JMenu apropos;
    private JMenuItem ouvrir;
    private JMenuItem exporter;
    private JMenuItem fermer;
    private JMenuItem ajouterLivre;
    private JMenuItem sauvegarder;
    private JMenuItem sauvegarderSous;
    private JMenuItem informations;

    //Table
    private JPanel panTable;
    private JTable jTable;
    private JScrollPane jScrollPane;

    public HomeView(String titre, TableModel modelTable){
        // Set the frame
        frame = new JFrame(titre);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Set MenuBar UI
        menubar = new JMenuBar();

        fichier = new JMenu("Fichier");
        edition = new JMenu("Edition");
        apropos = new JMenu("A propos");

        ouvrir = new JMenuItem("Ouvrir");
        exporter = new JMenuItem("Exporter");
        fermer = new JMenuItem("Fermer");

        ajouterLivre = new JMenuItem("Ajouter un livre");
        sauvegarder = new JMenuItem("Sauvegarer...");
        sauvegarderSous = new JMenuItem("Sauvegarder sous...");


        informations = new JMenuItem("Informations");

        fichier.add(ouvrir);
        fichier.add(exporter);
        fichier.add(fermer);
        edition.add(ajouterLivre);
        edition.add(sauvegarder);
        edition.add(sauvegarderSous);
        apropos.add(informations);

        menubar.add(fichier);
        menubar.add(edition);
        menubar.add(apropos);

        frame.setJMenuBar(menubar);

        //set Table
        panTable = new JPanel();
        jTable = new JTable(modelTable);
        jScrollPane = new JScrollPane(jTable);
        panTable.setBorder((BorderFactory.createTitledBorder("Tableau des livres")));
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panTable.add(jScrollPane, BorderLayout.CENTER);
        panTable.setVisible(true);

        frame.add(panTable);


        frame.pack();

    }
    public void repaint() {
        jTable.clearSelection();
        jTable.repaint();
    }

    public JMenuItem getFichierOuvrirMenu() {
        return this.ouvrir;
    }

}
