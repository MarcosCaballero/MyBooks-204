
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.ConsultasLibro;
import model.Libro;
import view.ListaLibros;


public class OperacionesController implements ActionListener{
    
    protected ListaLibros vistaLibros = new ListaLibros();
    protected Libro libro = new Libro();
    protected ConsultasLibro consultas = new ConsultasLibro();

    public OperacionesController(ListaLibros vistaLibros, Libro libro, ConsultasLibro consultas) {
        this.vistaLibros = vistaLibros;
        this.libro = libro;
        this.consultas = consultas;
        this.vistaLibros.btnGuardar.addActionListener(this);
        this.vistaLibros.btnModificar.addActionListener(this);
        this.vistaLibros.btnEliminar.addActionListener(this);
              
    }
    
    public void iniciarc(){
        vistaLibros.setVisible(true);
        vistaLibros.setLocationRelativeTo(null);
        vistaLibros.setTitle("Listado de libros");
    }
    
    public void agregar() throws SQLException{
        String titulo = vistaLibros.txttitulo.getText();
        String genero = vistaLibros.txtgenero.getText();
        String autor = vistaLibros.txtAutor.getText();
        if("".equals(titulo) ||"".equals(genero) || "".equals(autor) ){
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos");
        }
        libro.setTitulo(titulo);
        libro.setGenero(genero);
        libro.setAutor(autor);
        
       boolean respuesta = consultas.agregarLibro(libro);
       if(respuesta == true){
           JOptionPane.showMessageDialog(null,"Usuario agregado con éxito");
           limpiarCampos();
           consultas.traerLibros(vistaLibros.tableLibros);
       }else{
           JOptionPane.showMessageDialog(null,"Error al agregar usuario");
       }
        
    }
    
    public void eventoTabla(){
        vistaLibros.tableLibros.addMouseListener(new MouseAdapter(){
           public void mousePressed(MouseEvent Mouse_event){
               try {
                   consultas.cargarTxt(vistaLibros.tableLibros, vistaLibros.txttitulo, vistaLibros.txtgenero,vistaLibros.txtAutor);
               } catch (SQLException ex) {
                    System.err.println(ex);
               }
           }
        });
    }
    
    public void eliminar() throws SQLException{
        boolean respuesta = consultas.eliminarLibro(libro,vistaLibros.txttitulo);
        if(respuesta == true){
            JOptionPane.showMessageDialog(null,"Libro eliminado correctamente");
            limpiarCampos();
            consultas.traerLibros(vistaLibros.tableLibros);
        } else {
            JOptionPane.showMessageDialog(null,"No se pudo eliminar");
        }
        
    }
    
    public void editar() throws SQLException{
        boolean rta = consultas.modificarLibro(vistaLibros.tableLibros, vistaLibros.txttitulo, vistaLibros.txtgenero, vistaLibros.txtAutor);
        if(rta == true){
            JOptionPane.showMessageDialog(null,"Se editó con éxito");
            limpiarCampos();
            consultas.traerLibros(vistaLibros.tableLibros);
        }else{
            JOptionPane.showMessageDialog(null,"Error al editar");
        }
    }
    
    public void limpiarCampos(){
        vistaLibros.txttitulo.setText("");
        vistaLibros.txtgenero.setText("");
        vistaLibros.txtAutor.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == vistaLibros.btnGuardar){
            try {
                agregar();
            } catch (SQLException ex) {
                 System.err.println(e);
            }
            
        }
        if(e.getSource() == vistaLibros.btnModificar){
            try {
                editar();
            } catch (SQLException ex) {
                System.err.println(e);            }
        }
        if(e.getSource() == vistaLibros.btnEliminar){
            try {
                eliminar();
            } catch (SQLException ex) {
                System.err.println(e);
            }
        }
    }
    
    
    
}
