import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args){


        

        //welcome message to console:
        System.out.println("hello");

        //creating new GUI and setting properties:
        Screen win = new Screen();

        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(250, 320);
        win.setResizable(false);    // resizing is prevented in order to preserve well organized GUI structure
        win.setVisible(true);
        win.setLocationRelativeTo(null);
        



    }
}
