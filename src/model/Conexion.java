package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    
    protected String db = "mydb";
    protected String user = "root";
    protected String password = "root";
    protected String url = "jdbc:mysql://localhost:3306/"+ db;
    protected Connection con = null;
    
    
    public Connection getConexion(){
  
        try{
            Class.forName("com.mysql.jdbc.Driver"); //controlador para realizar la conexion 
            con = DriverManager.getConnection(this.url,this.user, this.password);
            
        }catch(SQLException e){
            System.err.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
       
    }
    
}
