package com.tennoayden.app.gui.controllers;

import business.services.BibliothequeService;
import com.tennoayden.app.gui.models.TableModel;
import com.tennoayden.app.gui.views.HomeView;

public class HomeController {
    HomeView view;
    TableModel model;

    public HomeController(String titre) {
        this.model = new TableModel(BibliothequeService.getInstance().bibliotheque);
        this.view = new HomeView(titre, model);
    }

    public void initHome() {
        this.reloadTable();
    }

    public void reloadTable() {
        this.model.fireTableDataChanged();
        this.view.repaint();
    }

}
