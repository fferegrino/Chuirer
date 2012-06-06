/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.utilitarios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Clase encargada de realizar la impresión a archivos XML, usa la librería JDOM
 * @author Antonio
 */
public class Impresion {

    /**
     * Imprime a un XML
     *
     * @param Documento El documento que deseamos que se imprima
     * @param Archivo El archivo al que queremos escribir
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void imprimeXML(Document Documento, File Archivo)
            throws FileNotFoundException, IOException {
        Format format = Format.getPrettyFormat();
        XMLOutputter xmloutputter = new XMLOutputter(format);
        FileOutputStream file_os = new FileOutputStream(Archivo);
        xmloutputter.output(Documento, file_os);
        file_os.close();

    }

    /**
     * Imprime a un XML
     * @param Documento El documento que deseamos imprimir
     * @param RutaArchivo La ruta del archivo que deseamos imprimir
     * @throws IOException 
     */
    public static void imprimeXML(Document Documento, String RutaArchivo) throws IOException {
        imprimeXML(Documento, new File(RutaArchivo));
    }
    
    public static void imprimeImg(){
        
    }
}
