
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    public void agregar(){
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
       }else{
           JOptionPane.showMessageDialog(null,"Error al agregar usuario");
       }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == vistaLibros.btnGuardar){
           agregar();
            
        }
        if(e.getSource() == vistaLibros.btnModificar){
           
        }
        if(e.getSource() == vistaLibros.btnEliminar){
            
        }
    }
    
    
    
}
