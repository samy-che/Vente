package controller;

import Model.DatabaseConnection;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListener implements ActionListener {
    private Menu menu;

    public MenuListener(Menu menu) {
        this.menu =menu;
    }


    public void actionPerformed(ActionEvent e) {
        if (((JButton)e.getSource()).getText().equals("ACCES CLIENT")) {
            ClientLogin clientLogin = new ClientLogin(menu, DatabaseConnection.getConnection());
            // A IMPLEMENT ATTENTION
            // affichage DE LA FENETRE DE CONNEXION
            // connection a la BDD
            clientLogin.setVisible(true);
        }
//        if (((JButton)e.getSource()).getText().equals("Gestion Vendeurs")) {
//            GestionVendeurs gestionVendeurs = new GestionVendeurs(magasin);
//            // A IMPLEMENT ATTENTION
//            // affichage DE LA FENETRE DE CONNEXION
//            // connection a la BDD
//            gestionVendeurs.setVisible(true);
//        }
    }
}
