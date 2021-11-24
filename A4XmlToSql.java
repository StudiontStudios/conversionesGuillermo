/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author gmartinezd02
 */
public class A4XmlToSql {

    private Document document;
    private String sDep,sNombre,sLoc;
    private Connection conexion;
    public A4XmlToSql() {
        conexion=conexionesBBDD.conexiones.getConexionMySQL("jdbc:mysql://192.168.56.129:3306/ejemplo", "usuario", "curso2122");
      
        leerXml();
    }

    private void leerXml() {
        iniciarDocumento();
        try {

            System.out.printf("Elemento raiz: %s %n", document.getDocumentElement().getNodeName());
            //crea una lista con todos los nodos departamento 
            NodeList departamento = document.getElementsByTagName("departamento");
            System.out.printf("Nodos de departamento a recorrer: %d %n", departamento.getLength());

            //recorrer la lista  
            for (int i = 0; i < departamento.getLength(); i++) {

                //cogemos los datos del nodo i, vamos leyendo nodo departamento uno a uno
                Node dep = departamento.item(i);

                //obtener los elementos del nodo           
                Element elemento = (Element) dep;

//              Recorre todos los nodos del departamento i buscando el atributo id
/*<departamento>
        <dep id="1">10</dep>
        <nombre>marketing</nombre>
        <loc>LaRioja</loc>
    </departamento>*/

                //creauna lista con todos los nodos que tiene dentro un departamento
                NodeList n = dep.getChildNodes();
                
                for (int j = 0; j < n.getLength(); j++) {
                    //va pasando por todos los nodos que tenga, dep,nombre,loc
                    Node aux = n.item(j);
                    //en caso de que sea dep, se crea el elemento y muestra su atributo id
                    if (aux.getNodeName().equals("dep")) {
                        Element e = (Element) aux;
                        System.out.println("atributo de dep: " + e.getAttribute("id"));
                        System.out.println("nodo dep valor: " + aux.getTextContent() + "\n");
                    }

                }

                //el caso de que tenga varios empleados dentro:
                /*<departamento>
                    <dep id="1">10</dep>
                    <nombre>marketing</nombre>
                    <loc>LaRioja</loc>
                    <empleados>
                        <emp>
                            <nombre>pepe</nombre>
                        </emp>
                        <emp>
                            <nombre>luis</nombre>
                        </emp>
                    </empleados>
                </departamento>*/
                
                
                 //dentro del departamento hace una lista de los nodos emp, en este caso hay 2
                 NodeList empleados = elemento.getElementsByTagName("emp");
            System.out.printf("Nodos de empleados a recorrer: %d %n", empleados.getLength());
            
                for (int j = 0; j < empleados.getLength(); j++) {
                    Node aux = empleados.item(j);
                        Element e = (Element) aux;
                        //muestra el nombre de esos empleados
                         System.out.printf(" * nombre = %s %n ", e.getElementsByTagName("nombre").item(0).getTextContent());
                    }

                
                
                //por defecto podemos sacar todos los datos directamente si no hay atributos
                System.out.printf("Dep = %s %n", elemento.getElementsByTagName("dep").item(0).getTextContent());
                System.out.printf(" * nombre = %s %n", elemento.getElementsByTagName("nombre").item(0).getTextContent());
                System.out.printf(" * localizacion = %s %n \n", elemento.getElementsByTagName("loc").item(0).getTextContent());
                sDep=  elemento.getElementsByTagName("dep").item(0).getTextContent();
                sNombre= elemento.getElementsByTagName("nombre").item(0).getTextContent();
                sLoc= elemento.getElementsByTagName("loc").item(0).getTextContent();
                insertarDatos(sDep,sNombre,sLoc);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        A4XmlToSql a = new A4XmlToSql();

    }//fin de main 

    private void iniciarDocumento() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File("datos.xml"));
            document.getDocumentElement().normalize();
        } catch (SAXException ex) {
            Logger.getLogger(A4XmlToSql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(A4XmlToSql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(A4XmlToSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertarDatos(String sDep, String sNombre, String sLoc) {
       conexionesBBDD.conexiones.ejecutarMySQL("insert into pruebasCasa values("+sDep+",'"+sNombre+"','"+sLoc+"');",conexion);
    
    }
    
}//fin de la clase

