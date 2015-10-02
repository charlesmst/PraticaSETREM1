/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Charles
 */
public class ConnectionUtil {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            String url = "jdbc:postgresql://localhost:5432/AULA";
            String usuario = "postgres";
            String senha = "123";
            try {
                connection = DriverManager.getConnection(url, usuario, senha);
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados");
            }
        }
        return connection;
    }
}
