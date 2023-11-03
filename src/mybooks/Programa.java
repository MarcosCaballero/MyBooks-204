
package mybooks;

import controller.LibroController;
import model.ConsultasLibro;
import model.Libro;
import view.VentanaPrincipal;


public class Programa {

   
    public static void main(String[] args) {
        
        Libro modelo = new Libro();
        VentanaPrincipal view = new VentanaPrincipal();
        ConsultasLibro consulta = new ConsultasLibro();
        LibroController controlador = new LibroController(view,modelo,consulta);
        controlador.iniciar();
       
    }
    
}
