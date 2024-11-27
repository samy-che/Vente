package controller;

import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListener implements ActionListener {
    public MenuListener() {}


    public void actionPerformed(ActionEvent e) {
        if (((JButton)e.getSource()).getText().equals("ACCES CLIENT")) {
            GestionClients gestionClients = new GestionClients(magasin);
            // A IMPLEMENT ATTENTION
            // affichage DE LA FENETRE DE CONNEXION
            // connection a la BDD
            gestionClients.setVisible(true);
        }
        if (((JButton)e.getSource()).getText().equals("Gestion Vendeurs")) {
            GestionVendeurs gestionVendeurs = new GestionVendeurs(magasin);
            // A IMPLEMENT ATTENTION
            // affichage DE LA FENETRE DE CONNEXION
            // connection a la BDD
            gestionVendeurs.setVisible(true);
        }
    }
}
