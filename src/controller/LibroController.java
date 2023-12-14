package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ConsultasLibro;
import view.ListaLibros;
import view.VentanaPrincipal;


public class LibroController implements ActionListener{ //implementa ActionListener para escuchar eventos
    
    
    //atributos
    protected VentanaPrincipal vista; //vista ventana principal
    protected ConsultasLibro consulta; 
    
    //controlador
    public LibroController() {
        this.vista = new VentanaPrincipal();
        this.consulta = new ConsultasLibro();
        this.vista.btnBuscar.addActionListener(this);// para que detecte el evento
        this.vista.btnListado.addActionListener(this);
        iniciar();
    
    }
    
    //método para que el controlador inicie
    protected void iniciar(){
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
    
    //metodo para que el controlador abra la ventana ListaLibros
    public void iniciarListado(){
        ListaLibros lista = new ListaLibros();
        OperacionesController opcontroller = new OperacionesController(lista);
        opcontroller.iniciarc();
        opcontroller.eventoTabla();
        
        try{
                        
            consulta.traerLibros(lista.tableLibros); 
 
        }catch (SQLException ex) {
            Logger.getLogger(ListaLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    //funcionalidad del boton buscar
    public void ejecutarBusqueda(String busqueda){
        
        ListaLibros rbusqueda = new ListaLibros();
        rbusqueda = new ListaLibros();
                 
        consulta = new ConsultasLibro();
        try{
            boolean respuesta = consulta.comprobarDatos(busqueda);   //buscarLibro(rbusqueda.tableLibros,busqueda);
            if(respuesta){
                
               
                OperacionesController opcontroller = new OperacionesController(rbusqueda);
                opcontroller.iniciarc();
                consulta.buscarLibro(rbusqueda.tableLibros,busqueda);
                opcontroller.eventoTabla();
                rbusqueda.setTitle("Resultados de la búsqueda");
            }else{
               JOptionPane.showMessageDialog(null,"No se encontraron resultados para la búsqueda.\n Por favor, ingrese otro título."); 
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(ListaLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
    //funcionalidad de los botones
    @Override
    public void actionPerformed(ActionEvent e){
        //condicional para los botones
        
        //boton de búsqueda
        if(e.getSource() == vista.btnBuscar){
            String campo = vista.txtBuscarLibro.getText(); //tomo el texto del campo de  búsqueda
            if("".equals(campo)){ //si la variable está vacía muestra msj alerta
                JOptionPane.showMessageDialog(null,"Debe ingresar un texto para la búsqueda");
            }else{
                ejecutarBusqueda(campo);
            }
            limpiarFormVentanaPrincipal();
        }
        
        //botón listado
        if(e.getSource() == vista.btnListado){
            iniciarListado();
        }
       
    }
    
    //método para limpiar el formulario de la ventana principal
    public void limpiarFormVentanaPrincipal(){
        vista.txtBuscarLibro.setText(null);
    }
    
    
}
