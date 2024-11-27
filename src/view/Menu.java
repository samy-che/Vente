package view;


import javax.swing.*;
import java.awt.*;


public class Menu extends JFrame{
    JLabel nom = new JLabel("MAGASIN DAUPHINE");
    JButton client = new JButton("ACCES CLIENT");
    JButton responsable = new JButton("ACCES responsable");


    JPanel gridConteneur = new JPanel();

    public Menu(){
        this.setTitle("Menu MAGASIN DAUPHINE");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(600,600));
        this.setResizable(true);

        gridConteneur.setLayout(new GridLayout(4,1,10,10));
        gridConteneur.setPreferredSize(new Dimension(600,200));
        nom.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(gridConteneur);

        gridConteneur.add(nom);
        gridConteneur.add(client);
        gridConteneur.add(responsable);
        gridConteneur.setBackground(new Color(204,229,255));

        nom.setFont(new Font("Monospaced", Font.BOLD, 18));
        nom.setForeground(Color.blue);
        client.setBackground(new Color(153,204,255));
        client.setForeground(Color.black);
        client.setFont( new Font("Tahoma",Font.BOLD | Font.ITALIC, 18) );
        responsable.setBackground(new Color(153,204,255));
        responsable.setForeground(Color.black);
        responsable.setFont( new Font("Tahoma",Font.BOLD | Font.ITALIC, 18) );

//        MenuListener m_listener = new MenuListener(monMagasin);
//
//        client.addActionListener(m_listener);
//        responsable.addActionListener(m_listener);

        this.setContentPane(gridConteneur);
        this.pack();
        this.setVisible(true);

    }

}
