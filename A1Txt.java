/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import crearDocumentos.Txt;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmartinezd02
 */
public class A1Txt {
    private FileWriter w;
    private File archivo;
    private String nombreFichero;
    public A1Txt(){
        try {
            nombreFichero="datos.txt";
            archivo=new File(nombreFichero);
            
            w=new FileWriter(archivo);
            
            w.write("10#marketing#LaRioja\n");
            w.write("20#contabilidad#arnedo\n");
            w.write("30#finanzas#logroño\n");
            w.close();
        } catch (IOException ex) {
            Logger.getLogger(Txt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static void main(String[] args) {
        A1Txt r =new A1Txt();
    }
}
