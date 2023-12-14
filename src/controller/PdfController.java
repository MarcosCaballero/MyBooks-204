
package controller;

import com.itextpdf.layout.element.Image;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;


public class PdfController {
    
    //método para generar el pdf
    public static void crearPDF(JTextField txt1, JTextField txt2, JTextField txt3,JTextField txt4){
        
        PdfWriter pdfWriter = null;
        //variables con los valores tomados de los txt
        String titulo = txt1.getText();
        String genero = txt2.getText();
        String autor = txt3.getText();
        String rutaImagen = txt4.getText();

        try {
            
            File file = new File("./src/pdf/" + titulo + ".pdf"); //crea un archivo con el titulo del libro y le concatena la extension .pdf

            pdfWriter = new PdfWriter(file);
            
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            
            Document documento = new Document(pdfDocument);
                    
            //contenido del pdf
            Paragraph texto1 = new Paragraph(titulo);
            Paragraph texto2 =new Paragraph("Autor: "+ autor);
            Paragraph texto3 =new Paragraph("Género: "+ genero);
            //imagen-portada del libro
            
            ImageData data = ImageDataFactory.create(rutaImagen);
            Image imagen = new Image(data); 
            imagen.setWidth(150);
            imagen.setHeight(200);
            //falta poner autor y genero
            
            //añado contenido al pdf
            documento.add(texto1);
            documento.add(imagen);
            documento.add(texto2);
            documento.add(texto3);
            
            
            documento.close();
            pdfWriter.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacionesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OperacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
