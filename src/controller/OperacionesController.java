  
package controller;


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        this.vistaLibros.btnSeleccionar.addActionListener(this);
        this.vistaLibros.btnLimpiar.addActionListener(this);
              
    }
    
    public void iniciarc(){
        vistaLibros.setVisible(true);
        vistaLibros.setLocationRelativeTo(null);
        vistaLibros.setTitle("Listado de libros");
    }
    
    public void agregar(JTextField txt) throws SQLException, FileNotFoundException{
        String titulo = vistaLibros.txttitulo.getText();
        String genero = vistaLibros.txtgenero.getText();
        String autor = vistaLibros.txtAutor.getText();
        if("".equals(titulo) ||"".equals(genero) || "".equals(autor) ){
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos");
        }
        libro.setTitulo(titulo);
        libro.setGenero(genero);
        libro.setAutor(autor);
        
       boolean respuesta = consultas.agregarLibro(libro,txt);
       if(respuesta == true){
           JOptionPane.showMessageDialog(null,"Libro agregado con éxito");
           limpiarCampos();
           consultas.traerLibros(vistaLibros.tableLibros);
       }else{
           JOptionPane.showMessageDialog(null,"Error al agregar Libro");
       }
        
    }
    
    //método para cargar los txt al seleccionar una fila de la tabla
    public void eventoTabla(){
        vistaLibros.tableLibros.addMouseListener(new MouseAdapter(){
           public void mousePressed(MouseEvent Mouse_event){
               try {
                   consultas.cargarTxt(vistaLibros.tableLibros, vistaLibros.txttitulo, vistaLibros.txtgenero,vistaLibros.txtAutor, vistaLibros.txtImagen, vistaLibros.lblFoto);
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
    
    public void editar(JTextField txt,JTable table,JTextField txt1,JTextField txt2,JTextField txt3) throws SQLException, FileNotFoundException{
        boolean rta = consultas.modificarLibro(txt,table,txt1,txt2,txt3);
        if(rta == true){
            JOptionPane.showMessageDialog(null,"Se editó con éxito");
            limpiarCampos();
            consultas.traerLibros(vistaLibros.tableLibros);
        }else{
            JOptionPane.showMessageDialog(null,"Error al editar");
        }
    }
    
    //método para seleccionar foto y visualizar la foto elegida en el formulario
    private void seleccionarImagen(){
      
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de archivos JPG, PNG", "jpg","png"); //Aplica filtro para los formatos de imagen aceptados
        JFileChooser archivo = new JFileChooser();
        archivo.addChoosableFileFilter(filtro);//añade el filtro al selector de archivos
        archivo.setDialogTitle("Seleccionar foto"); 
        File ruta = new File("C:\\Users\\rocio\\OneDrive\\Documentos"); //ruta en la que abre el selector de archivos
        archivo.setCurrentDirectory(ruta); //establece la ruta inicial del selector
             
       
        int ventana = archivo.showOpenDialog(null);//ventana es de tipo int porque "showOpendialog devuelve un int
        //comprobación para ver si se ha seleccionado algún archivo
  
        if(ventana == JFileChooser.APPROVE_OPTION){ //APPROVE_OPTION indica si se seleccionó el botón para abrir
            File file = archivo.getSelectedFile(); // obtengo el archivo seleccionado
            vistaLibros.txtImagen.setText(String.valueOf(file));
            Image foto = Toolkit.getDefaultToolkit().getImage(vistaLibros.txtImagen.getText()); //tomo el texto de la ruta del txt
            foto = foto.getScaledInstance(130, 160, Image.SCALE_DEFAULT);
            vistaLibros.lblFoto.setIcon(new ImageIcon(foto));
            
        }
             
    }
    
    //método para limpiar los campos del formulario
    public void limpiarCampos(){
        vistaLibros.txttitulo.setText("");
        vistaLibros.txtgenero.setText("");
        vistaLibros.txtAutor.setText("");
        vistaLibros.txtImagen.setText("");
        vistaLibros.lblFoto.setIcon(null);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == vistaLibros.btnGuardar){
            try {
                agregar(vistaLibros.txtImagen);
            } catch (SQLException ex) {
                 System.err.println(e);
            } catch (FileNotFoundException ex) { //Excepción para el archivo
                Logger.getLogger(OperacionesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(e.getSource() == vistaLibros.btnModificar){
            try {
                editar(vistaLibros.txtImagen,vistaLibros.tableLibros, vistaLibros.txttitulo, vistaLibros.txtgenero, vistaLibros.txtAutor);
            } catch (SQLException ex) {
                System.err.println(e);            
            } catch (FileNotFoundException ex) { //Excepción para el archivo
                Logger.getLogger(OperacionesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getSource() == vistaLibros.btnEliminar){
            try {
                eliminar();
            } catch (SQLException ex) {
                System.err.println(e);
            }
        }
        
        if(e.getSource() == vistaLibros.btnSeleccionar){
           seleccionarImagen();
        }
        
        if(e.getSource() == vistaLibros.btnLimpiar){
            limpiarCampos();
        }
    }
    
    
    
}
