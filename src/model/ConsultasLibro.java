//Acá manejo las consultas a la base de datos
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JTextField;
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
    public boolean modificarLibro(JTable table,JTextField txt1,JTextField txt2,JTextField txt3){
        
        int fila = table.getSelectedRow();
        String id = table.getValueAt(fila,0).toString();
        
        
        
        String sql = "UPDATE Libro SET titulo=?, genero=?, autor=? WHERE idLibro=?";
        
        try{
            PreparedStatement ps = null;
            Connection con = getConexion();
            
            ps = con.prepareStatement(sql); 
          
            ps.setString(1, txt1.getText()); 
            ps.setString(2, txt2.getText());
            ps.setString(3, txt3.getText());
            ps.setString(4, id);
            
            ps.execute();
            
            table.setValueAt(txt1.getText(), fila, 1);
            table.setValueAt(txt2.getText(), fila, 2);
            table.setValueAt(txt3.getText(), fila, 3);
            
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
    public boolean eliminarLibro(Libro lib,JTextField txt1){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "DELETE FROM libro WHERE titulo=?";
        
        try{
            
            ps = con.prepareStatement(sql);
            ps.setString(1,txt1.getText() );
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
    
 
    
    //método para pasar los datos del JTable a los JTextField
    public void cargarTxt(JTable table,JTextField txt1,JTextField txt2,JTextField txt3) throws SQLException{
        
        try{
            PreparedStatement ps = null;
            ResultSet rs = null; 
        
            Connection con = getConexion();
            
            int fila = table.getSelectedRow();
            String id = table.getValueAt(fila,0).toString();
            
            String sql = "SELECT titulo, genero, autor FROM Libro WHERE idLibro=?";
            
            ps = con.prepareStatement(sql);
            ps.setString(1,id);
            rs = ps.executeQuery();
                        
            while(rs.next()){
                txt1.setText(rs.getString("titulo"));
                txt2.setText(rs.getString("genero"));
                txt3.setText(rs.getString("autor"));
            }
            
        }catch(SQLException e){
            System.err.println(e.toString());
        }finally{
            con.close();
        }
        
        
    }
    
    //método para comprobar si hay datos en la BD
    public boolean comprobarDatos(String condicion){
        try{
           
            PreparedStatement ps = null;
            ResultSet rs = null;
            
            Connection con = getConexion();
            
            String sql = "SELECT idLibro, titulo, genero, autor FROM Libro where titulo like '%"+ condicion +"%'";
            
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            return rs.next();
            
        }catch(SQLException e){
            return false;
            
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.print("Ha ocurrido un error: " + ex.getMessage());
            }
        }
    }
    
    
}
