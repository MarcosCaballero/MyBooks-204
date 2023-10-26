//Acá manejo las consultas a la base de datos
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ConsultasLibro extends Conexion{
    
    //método para buscar un libro por su titulo
    public void buscarLibro(JTable table, String condicion) throws SQLException{
     
        try{
           
            DefaultTableModel modelo = new DefaultTableModel(); 
            table.setModel(modelo);
            
            PreparedStatement ps = null;
            ResultSet rs = null; 
        
            Connection con = getConexion();
        
        
            String sql = "SELECT idLibro, titulo, genero, autor FROM Libro where titulo like '%"+ condicion +"%'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(); //trae todos los datos de la consulta
        
            ResultSetMetaData rsMd = rs.getMetaData(); 
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("idLibro");
            modelo.addColumn("Titulo");
            modelo.addColumn("Genero");
            modelo.addColumn("Autor");
            
            
            //Recorro los datos-resultados de la consulta            
            while(rs.next()){
                //el modelo de la tabla requiere objetos
                Object[] filas = new Object[cantidadColumnas];
                //uso un for para pasar los datos al objeto
                for(int i=0;i<cantidadColumnas; i++){
                    filas[i] = rs.getObject(i+1); 
                }
                modelo.addRow(filas);   
            }    
            
        }catch(SQLException e){
            System.err.println(e.toString());
            
        }finally{
            con.close();
        }
    }
    
    //Método para insertar registros en la base de datos
    public boolean agregarLibro(Libro lib){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "INSERT INTO libro(titulo, genero, autor) VALUES (?,?,?)";
        
        try{
            ps = con.prepareStatement(sql); //acá le paso la consulta sql creada
            //en esta parte se interactúa con el modelo libro
            ps.setString(1, lib.getTitulo());
            ps.setString(2, lib.getGenero());
            ps.setString(3, lib.getAutor());
            //ejecuto
            ps.execute();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        
    }
    
    //Método para modificar libros
    public boolean modificarLibro(Libro lib){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "UPDATE libro SET titulo=?, genero=?, autor=? WHERE id=?";
        
        try{
            ps = con.prepareStatement(sql); //acá le paso la consulta sql creada
            //en esta parte se interactúa con el modelo libro
            ps.setString(1, lib.getTitulo());
            ps.setString(2, lib.getGenero());
            ps.setString(3, lib.getAutor());
            ps.setInt(4,lib.getId());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        
    }
    
    //método para eliminar un libro    
    public boolean eliminarLibro(Libro lib){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM libro WHERE id=?";
        
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, lib.getId());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    
    //método para traer todos los libros
    public void traerLibros(JTable table) throws SQLException{
        
        try{
           
            DefaultTableModel modelo = new DefaultTableModel(); 
            table.setModel(modelo);
            
            PreparedStatement ps = null;
            ResultSet rs = null; 
        
            Connection con = getConexion();
        
        
            String sql = "SELECT idLibro, titulo, genero, autor FROM Libro;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(); //trae todos los datos de la consulta
        
            //creo un resultset para la tabla
            ResultSetMetaData rsMd = rs.getMetaData(); 
            //guardo en una variable la cantidad de datos que devuelve la consulta
            int cantidadColumnas = rsMd.getColumnCount();
            
            //encabezados de la tabla
            modelo.addColumn("idLibro");
            modelo.addColumn("Titulo");
            modelo.addColumn("Genero");
             modelo.addColumn("Autor");
            
            
            //Recorro los datos-resultados de la consulta            
            while(rs.next()){
                //el modelo de la tabla requiere objetos
                Object[] filas = new Object[cantidadColumnas];
                //uso un for para pasar los datos al objeto
                for(int i=0;i<cantidadColumnas; i++){
                    filas[i] = rs.getObject(i+1); 
                }
                modelo.addRow(filas);   
            }    
            
        }catch(SQLException e){
            System.err.println(e.toString());
            
        }finally{
            con.close();
        }
        
    }
    
    //método para cargar datos de la BD en el combobox
    public void cargarComboBox(JComboBox cb) throws SQLException{
        try{
            PreparedStatement ps = null;
            ResultSet rs = null; 
        
            Connection con = getConexion();
            
            String sql = "SELECT nombreGenero FROM Genero";
            
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(); //para que me devuleva los resultados de la consulta
            
            while(rs.next()){
                cb.addItem(rs.getString("nombreGenero"));
            }
            
            rs.close();
            
        }catch(SQLException e){
            System.err.println(e.toString());
        }finally{
            con.close(); 
        }
    }
    
    
}
