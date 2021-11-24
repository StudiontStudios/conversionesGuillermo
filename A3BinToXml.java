/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import crearDocumentos.Xml;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author gmartinezd02
 */
public class A3BinToXml {
     private ObjectInputStream r;
    private String mombreXml;
    private String xmlPadre;
    private Document document;

    public A3BinToXml(String nombreDocumento, String nodoPadre) {
        mombreXml = nombreDocumento;
        xmlPadre = nodoPadre;
        iniciarDocumento();
        leerBin();
        terminarDocumento();
    }

    public void setMombreXml(String mombreXml) {
        this.mombreXml = mombreXml;
    }

    public void setXmlPadre(String xmlPadre) {
        this.xmlPadre = xmlPadre;
    }

    public void crearXml(String numDep, String nombre, String loc) {

        try {
            //el padre sera departamentos
            //departamento envuelve a cada registro suelto
            Element raiz = document.createElement("departamento");
            document.getDocumentElement().appendChild(raiz);
            //empleado es hijo de departamento
            //Element raiz1 =document.createElement("empleado");     
            // raiz.appendChild(raiz1);

            crearElemento("dep", numDep, raiz);
            crearElemento("nombre", nombre, raiz);
            crearElemento("loc", loc, raiz);

            //de esta forma dentro de departamento hay 2 empleados
            /*
            crearElemento("emp","guillermo",raiz1);
            crearElemento("apellido","martinez",raiz1);
            
            raiz1 =document.createElement("empleado");
            raiz.appendChild(raiz1);
            crearElemento("emp","luis",raiz1);
            crearElemento("apellido","perez",raiz1);
             */
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }

    }

    //Inserci�n de los datos del empleado
    public void crearElemento(String nodo, String valor, Element raiz) {
        Element elem = document.createElement(nodo);
        Text text = document.createTextNode(valor); //damos valor
        raiz.appendChild(elem); //pegamos el elemento hijo a la raiz
        elem.appendChild(text); //pegamos el valor		 	
    }

    private void iniciarDocumento() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            document = implementation.createDocument(null, xmlPadre, null);
            document.setXmlVersion("1.0");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void terminarDocumento() {
        try {
            Source source = new DOMSource(document);
            Result result = new StreamResult(new java.io.File(mombreXml));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void leerBin() {
        String datos;
        String[] info;
        try {
            r = new ObjectInputStream(new FileInputStream(new File("datos.dat")));
            while (true) {
                datos = (String) r.readObject();
                info = datos.split("#");
                crearXml(info[0], info[1], info[2]);
            }

        } catch (EOFException e) {
            try {
                r.close();
            } catch (IOException ex) {
                Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

        A3BinToXml a = new A3BinToXml("datos.xml", "Departamentos");

    }
}
