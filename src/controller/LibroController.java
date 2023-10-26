package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ConsultasLibro;
import model.Libro;
import view.ListaLibros;
import view.VentanaPrincipal;


public class LibroController implements ActionListener{ //implementa ActionListener para escuchar eventos
    
    protected VentanaPrincipal vista; //vista ventana principal
    protected Libro modelo; 
    protected ConsultasLibro consulta = new ConsultasLibro();
    
    
    public LibroController(VentanaPrincipal vista, Libro modelo, ConsultasLibro consulta) {
        this.vista = vista;
        this.modelo = modelo;
        this.consulta = consulta;
        this.vista.btnBuscar.addActionListener(this);
        this.vista.btnListado.addActionListener(this);
        
    
    }
    
    //método para que el controlador inicie
    public void iniciar(){
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
    
    //metodo para que el controlador abra la ventana ListaLibros
    public void iniciarListado(){
        ListaLibros lista = new ListaLibros();
        OperacionesController opcontroller = new OperacionesController(lista,modelo,consulta);
        opcontroller.iniciarc();
        
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
        rbusqueda.setVisible(true);
        rbusqueda.setLocationRelativeTo(null);
        rbusqueda.lblTituloLista.setText("Resultados de búsqueda");
        rbusqueda.setTitle("Resultados de la búsqueda");
         
        consulta = new ConsultasLibro();
        try{
            consulta.buscarLibro(rbusqueda.tableLibros,busqueda);
            

        }catch (SQLException ex) {
            Logger.getLogger(ListaLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
    //funcionalidad de los botones
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == vista.btnBuscar){
            String campo = vista.txtBuscarLibro.getText(); //tomo el texto del campo de  búsqueda
            if("".equals(campo)){ //si la variable está vacía muestra msj alerta
                JOptionPane.showMessageDialog(null,"Debe ingresar un texto para la búsqueda");
            }else{
                ejecutarBusqueda(campo);
            }
            limpiarFormVentanaPrincipal();
        }
        if(e.getSource() == vista.btnListado){
            iniciarListado();
        }
       
    }
    
    //método para limpiar el formulario de la ventana principal
    public void limpiarFormVentanaPrincipal(){
        vista.txtBuscarLibro.setText(null);
    }
    
    
}
