/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.utilitarios;

import java.io.File;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Clase encargada de recuperar datos de los XML, explícitamente tipos
 * <code>DocumentoXML</code> de la librería JDOM
 *
 * @author Antonio
 */
public class DocumentoXML {

    /**
     * Recupera un tipo <code>Document</code> a partir de un archivo <b>XML</b>, se puede validar o no el documento
     * @param Archivo El archivo <b>XML</b> del cual queremos recuperar los datos
     * @param Validar Bandera que indica si vamos a validar o no el archivo, <code>true</code> para validar
     * @return El tipo <code>Document</code> asociado a ese archivo XML
     * @throws JDOMException
     * @throws IOException 
     */
    public static Document recuperaDocumento(File Archivo, boolean Validar) throws JDOMException, IOException {
        SAXBuilder sx = new SAXBuilder(Validar);
        Document Documento = sx.build(Archivo);
        return Documento;
    }
    
    /**
     * Recupera un tipo <code>Document</code> a partir de un archivo <b>XML</b>, no valida el documento
     * @param Archivo El archivo <b>XML</b> del cual queremos recuperar los datos
     * @return El tipo <code>Document</code> asociado a ese archivo XML
     * @throws JDOMException
     * @throws IOException  
     */
    public static Document recuperaDocumento(File Archivo) throws JDOMException, IOException{
        return recuperaDocumento(Archivo, false);
    }
    
    /**
     * Recupera un tipo <code>Document</code> a partir de un archivo <b>XML</b>, no valida el documento
     * @param RutaArchivo La ruta del archivo <b>XML</b> del cual queremos recuperar los datos
     * @param Valida Indica si se va a validar el documento <code>true</code> valida, <code>false</code> no.
     * @return El tipo <code>Document</code> asociado a ese archivo XML
     * @throws JDOMException
     * @throws IOException 
     */
    public static Document recuperaDocumento(String RutaArchivo, boolean Valida) throws JDOMException, IOException{
        return recuperaDocumento(new File(RutaArchivo), Valida);
    }
    
    
    /**
     * Recupera un tipo <code>Document</code> a partir de un archivo <b>XML</b>, no valida el documento
     * @param RutaArchivo La ruta del archivo <b>XML</b> del cual queremos recuperar los datos
     * @return El tipo <code>Document</code> asociado a ese archivo XML
     * @throws JDOMException
     * @throws IOException 
     */
    public static Document recuperaDocumento(String RutaArchivo) throws JDOMException, IOException{
        return recuperaDocumento(new File(RutaArchivo), false);
    }
}
