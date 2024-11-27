
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.Catalogue;
import Model.Client;
import Model.Personne;
import view.Menu;


public class Main {


    public static void main(String[] args) throws SQLException {
        Catalogue catalogue = new Catalogue();
        Menu menu = new Menu();
        Personne p1 = new Personne("nom1", "pre1", "rue k", "09000000", "jsdn@jdk.com");
        Client c1 = new Client(p1);
    }
    
}