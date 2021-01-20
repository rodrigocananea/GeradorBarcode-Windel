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

public class Database {
    
    private String url;
    private String userName;
    private String password;
    private Connection connection;

    public Database(String url, String userName, String password) throws SQLException {
        this.url = url;
        this.userName = userName;
        this.password = password;
        setConnection();
    }

    public Database() {
        InfoUtil config = new InfoUtil();
        this.url = "jdbc:firebirdsql:native:"
            + config.prop().getString("host.name", "localhost") + "/" + config.prop().getString("host.port", "3050")
            + ":" + config.prop().getString("host.database")
            + config.prop().getString("host.params", "?cl_ctype=WIN1252");
        this.userName = config.prop().getString("host.user", "SYSDBA");
        this.password = config.prop().getString("host.password", "masterkey");
        setConnection();    
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void setConnection() {
        try {
            System.out.println("########## FIREBIRD 3.0 CONNECTION ##########");
            System.out.println("# URL:...... " + url);
            System.out.println("# USER:..... " + userName);
            System.out.println("# PASSWORD:. " + password);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao se econtar no banco de dados, motivo:\n" 
                    + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }

}
