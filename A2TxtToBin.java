/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import crearDocumentos.Bin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gmartinezd02
 */
public class A2TxtToBin {
     private File archivo;
    private String nombreFichero;
    private FileReader r;
    private ObjectOutputStream w;
    private String[]datos;
    
    
    public A2TxtToBin(){
        datos =new String[3];
        nombreFichero="datos.txt";
        archivo=new File(nombreFichero);
        leer();
        escribir();
        
    
}
        
    public static void main(String[] args) {
        A2TxtToBin b=new A2TxtToBin();
    }

    private void leer() {
        int cpntador=0;
        r=null;
       StringBuilder sb =new StringBuilder();
        try {
            r=new FileReader(archivo);
            int aux=r.read();
            while(aux!=-1){
                if(aux=='\n'){
                    datos[cpntador]=sb.toString();
                    sb.delete(0, sb.length());
                    System.out.println(datos[cpntador]);
                    cpntador++;
                    
                }else{
                sb.append((char)aux);
                }
                aux=r.read();
                
            }
            r.close();
        }catch(NullPointerException n){
            try {
                r.close();
            } catch (IOException ex) {
                Logger.getLogger(Bin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Bin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void escribir() {
       
        try {
            w=new ObjectOutputStream(new FileOutputStream(new File("datos.dat")));
            w.writeObject(datos[0]);
            w.writeObject(datos[1]);
            w.writeObject(datos[2]);
            w.close();
        } catch (IOException ex) {
            Logger.getLogger(Bin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
