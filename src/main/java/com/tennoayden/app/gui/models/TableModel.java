package com.tennoayden.app.gui.models;

import business.models.*;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    private final Bibliotheque bibliotheque;
    private static final long serialVersionUID = 1L;

    private final String[] entetes = {"Titre", "Auteur","Année","Présentation","Status","Rangée","Colonne"};

    public TableModel(Bibliotheque bibliotheque) {
        super();
        this.bibliotheque = new ObjectFactory().createBibliotheque();
    }

    public int getRowCount() {
        return this.bibliotheque.getLivre().size();
    }

    public int getColumnCount() {
        return entetes.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return this.bibliotheque.getLivre().get(rowIndex).getTitre();
            case 1:
                return this.bibliotheque.getLivre().get(rowIndex).getAuteur();
            case 2:
                return this.bibliotheque.getLivre().get(rowIndex).getParution();
            case 3:
                return this.bibliotheque.getLivre().get(rowIndex).getPresentation();
            case 4:
                return this.bibliotheque.getLivre().get(rowIndex).getStatus();
            case 5:
                return this.bibliotheque.getLivre().get(rowIndex).getRangee();
            case 6:
                return this.bibliotheque.getLivre().get(rowIndex).getColonne();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
}