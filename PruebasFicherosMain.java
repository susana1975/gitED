/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ficheros;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

// Clase con métodos que escriben y leen de ficheros utilizando
// los distintos métodos disponibles en Java
public class PruebasFicherosMain {

    // Método main
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        // Aquí llamaremos al método que queramos probar
       /*m01EscribeFicheroBinario();
       m02LeerFicheroBinario();
       m03copiarImagen("ficheros", "universo.jpg");
       m04EscribeTiposBasicos();
       m05LeerTiposBasicos();
       m06EscribeBasicosBuffer();
       m06LeeBasicosBuffer();
       m07EscribeTexto1();
       m08EscribeTexto2();
       m09EscribeTexto3();
       m10LeerTextoBuffer();*/
       m11EscribeObjeto();
       m12CargarObjeto();
    }
    public static void m01EscribeFicheroBinario() {
        // Instanciamos el objeto de tipo File 
        File f = new File("ficheros", "datos.bin"); //carpeta y nombre_fichero

        // Si el fichero no existe lo creamos, pero antes
        // comprobamos si existe el directorio donde tiene
        // que estar y si no existe también lo creamos
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile(); //lanza IOException
            } catch (IOException ioex) {
                System.out.println("Error creando el fichero.");
            }
        }
        // Declaramos un Stream de salida para escribir en un File
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(f); //lanza FileNotFoundException
            System.out.println("Escribiendo en el fichero...");
            // Escribimos 100 bytes en el fichero, el valor del byte será 
            // el número 33233 + el contador del bucle
            for (int i = 0; i < 100; i++) {
                fos.write(33233 + i);  //lanza IOException
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ioex) {
            System.out.println("Error escribiendo en el fichero");
        } finally {
            try {
                fos.close(); //lanza IOException
                System.out.println("Fichero cerrado.");

            } catch (IOException ioex) {
                System.out.println("Error cerrando el fichero");
            }
        }
    }

    public static void m02LeerFicheroBinario() {
        // Instanciamos el objeto File
        File f = new File("ficheros", "datos.bin"); // otra opcion ->  //File f2 = new File("ficheros/datos.bin");
       
        // Array de bytes para guardar lo que se lea del fichero
        byte[] datos = null;

        // Stream de entrada que conectaremos al fichero
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(f);  //FileNotFoundException

            System.out.println("Leyendo el fichero...");

            // Recuperamos el tamaño del fichero
            int size = (int) f.length();
            // Instanciamos el array de bytes con el tamaño del fichero
            datos = new byte[size];

            // Leemos los datos (bytes) del fichero y se guardan en el array
            fis.read(datos); //IOExceptiocn

            // Mostramos los datos
            for (int i = 0; i < datos.length; i++) {
                System.out.print(datos[i] + " ");
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ioex) {
            System.out.println("Error leyendo el fichero");
        } finally {
            try {
                fis.close();
                System.out.println("Fichero cerrado.");

            } catch (IOException ioex) {
                System.out.println("Error cerrando el fichero");
            }
        }
    }

    /**
     * Método que hace una copia de un fichero binario, añadiendo a su nombre el
     * sufijo "_copia"
     *
     * @param directorio Directorio que contiene el fichero
     * @param fichero Nombre del fichero que queremos copiar
     */
    public static void m03copiarImagen(String directorio, String fichero) {
        // Creamos el objeto File
        File f = new File(directorio, fichero);

        // Construimos el nuevo nombre
        String newName = fichero.substring(0, fichero.lastIndexOf('.'));
        newName = newName + "_copia";
        newName = newName + fichero.substring(fichero.lastIndexOf('.'), fichero.length());

        // Vemos si existe un fichero con ese nombre y sino lo creamos
        File fCopy = new File(directorio, newName);
        if (!fCopy.exists()) {
            try {
                fCopy.createNewFile();
            } catch (IOException ex) {
                System.out.println("Error al crear el fichero de copia");
            }
        }

        // Streams para leer y escribir
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            System.out.println("Haciendo la copia...");

            // Creamos los streams de entrada y de salida
            fis = new FileInputStream(f);
            fos = new FileOutputStream(fCopy);

            // Vamos leyendo de un fichero y escribiendo en el otro, byte a byte
            int temp = fis.read();
            while (temp != -1) {
                fos.write(temp);
                temp = fis.read();
            }

            System.out.println("Ficheros copiados");

        } catch (FileNotFoundException ex) {
            System.out.println("Ficheros no encontrados");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida");
        } finally {
            try {
                fis.close();
                fos.close();
                f.delete();
            } catch (IOException ex) {
                System.out.println("Error cerrando ficheros");
            }
        }
    }

    // Método para escribir tipos básicos de Java directamente en un fichero
    public static void m04EscribeTiposBasicos() {
        // Declaramos e instanciamos el objeto File
        File f = new File("ficheros", "basicos.dat");

        // Si el fichero y el directorio no existen los creamos
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException ioex) {
                System.out.println("Error creando el fichero.");
            }
        }

        // Declaramos el Stream de salida al ficheros
        FileOutputStream fos = null;

        // Stream para escribir directamente los tipos básicos
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);

            // Escribimos directamente un tipo básico
            dos.writeFloat(3.1416F);

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida.");
        } finally {
            try {
                dos.close();
            } catch (IOException ex) {
                System.out.println("Error cerrando fichero.");
            }
        }
    }

    // Lectura de tipos básicos de un fichero
    public static void m05LeerTiposBasicos() {
        File f = new File("ficheros", "basicos.dat");

        // Stream de entrada del fichero
        FileInputStream fis = null;

        // Stream para poder leer tipos básicos
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);

            // Leemos un Float y lo mostramos
            float dato = dis.readFloat();
            System.out.println(dato);

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida.");
        } finally {
            try {
                dis.close();
            } catch (IOException ex) {
                System.out.println("Error cerrando fichero.");
            }
        }
    }

    // Escribir tipos básicos usando un buffer que hace más eficiente
    // la escritura de datos en el fichero
    public static void m06EscribeBasicosBuffer() {
        // Objeto de tipo File para modelar el fichero
        File f = new File("ficheros", "basicos.dat");

        // Si no existe el fichero y/o el directorio lo creamos
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException ioex) {
                System.out.println("Error creando el fichero.");
            }
        }

        // Stream para escribir en el fichero
        FileOutputStream fos = null;
      
        // Stream que implementa el buffer de escritura
        BufferedOutputStream bos = null;
       
        // Stream para escribir tipos básicos
        DataOutputStream dos = null;
       
        try {
            // Cramos los streams uno sobre otro
            fos = new FileOutputStream(f);
            bos = new BufferedOutputStream(fos);
            dos = new DataOutputStream(bos);
            // Escribimos varios datos básicos
            dos.writeFloat(6.66666F);
            dos.writeBoolean(true);
            dos.writeChar('A');
           
          

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida.");
        } finally {
            try {
                dos.close();
            } catch (IOException ex) {
                System.out.println("Error cerrando fichero.");
            }
        }
    }

    
    public static void m06LeeBasicosBuffer() {
        // Objeto de tipo File para modelar el fichero
        File f = new File("ficheros", "basicos.dat");

        // Si no existe el fichero y/o el directorio lo creamos
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException ioex) {
                System.out.println("Error creando el fichero.");
            }
        }

        // Stream para leer en el fichero
        FileInputStream fin = null;
        // Stream que implementa el buffer de lectura
        BufferedInputStream bin = null;
        // Stream para leer tipos básicos
        DataInputStream din = null;
        try {
            // Cramos los streams uno sobre otro
            din = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
            // Escribimos varios datos básicos
          
            System.out.println(din.readFloat());
            System.out.println(din.readBoolean());
            System.out.println(din.readChar());

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida.");
        } finally {
            try {
                din.close();
            } catch (IOException ex) {
                System.out.println("Error cerrando fichero.");
            }
        }
    }

    // Escritura de texto en un fichero con OutputStreamWriter
    public static void m07EscribeTexto1() {
        // Objeto File para acceder al fichero
        File f = new File("ficheros", "datos.txt");

        // Si no existe el fichero y/o directorio lo creamos
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException ioex) {
                System.out.println("Error creando el fichero.");
            }
        }

        // Stream para escribir en el fichero (escribe bytes)
        FileOutputStream fos = null;
        // Steam que recoje caracteres, podemos indicar la codificación
        // y si no especificamos ninguna coge la codificación por defecto
        OutputStreamWriter osw = null;

        try {
            fos = new FileOutputStream(f);
            // Creamos el OutputStreamWriter con la codificación por defecto
            osw = new OutputStreamWriter(fos);

            // Obtenemos la codificación y la mostramos
            System.out.println(osw.getEncoding());

            // Escribimos 10 caracteres desde la posición 0
            osw.write("Con cien cañones por banda", 0, 10);

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida.");
        } finally {
            try {
                osw.close();
            } catch (IOException ex) {
                System.out.println("Error cerrando fichero.");
            }
        }

    }

    // Escritura de texto en fichero usando FileWriter
    public static void m08EscribeTexto2() {
        // Objeto File para el acceso al fichero
        File f = new File("ficheros/datos.txt");

        // Sino existe el fichero y/o directorio lo creamos
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException ioex) {
                System.out.println("Error creando el fichero.");
            }
        }

        // Stream para escribir texto directamente en un File
        FileWriter fw = null;

        try {
            fw = new FileWriter(f);

            System.out.println("Escribiendo fichero...");

            // Escribimos directamente cadenas de caracteres
            fw.write("Con diez cañones por banda\n");
            fw.write("viento en popa a toda vela...");

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida.");
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                System.out.println("Error cerrando fichero.");
            }
        }

    }

    
    // Escribir texto utilizando PrintWriter, que permite
    // escribir usando print y println, como en la consola
    public static void m09EscribeTexto3() {
        // Objeto File para acceder al fichero
        File f = new File("ficheros", "datos.txt");

        // Si no existe el fichero y/o directorio lo creamos
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException ioex) {
                System.out.println("Error creando el fichero.");
            }
        }

        // Stream para escribir en un fichero como si fuera la consola
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(f);

            System.out.println("Escribiendo fichero...");

            // Escribimos usando los mismos métodos que en la consola
            pw.println("Con cien cañones por banda");
            pw.println("viento en popa a toda vela...");

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida.");
        } finally {
            pw.close();
            System.out.println("Fichero cerrado");
        }

    }

    // Leer de un fichero de texto usando un buffer (línea a línea)
    public static void m10LeerTextoBuffer() {
        // Objeto File para acceder al fichero
        File f = new File("ficheros", "datos.txt");

        // Stream que implementa el buffer de lectura
        BufferedReader br = null;
        // Stream que accede directamente al fichero
        FileReader fr = null;

        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);

            // Variable donde almacenamos cada línea de texto del fichero
            String linea;
            
            // Vamos leyendo el fichero línea a línea y escribiéndola 
            linea = br.readLine();
            while (linea != null) {
                System.out.println(linea);
                linea = br.readLine();
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado.");
        } catch (IOException ex) {
            System.out.println("Error de entrada/salida.");
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                System.out.println("Error cerrando fichero.");
            }
        }
    }

    // Escribe un objeto Opositor en un fichero mediante serialización
    public static void m11EscribeObjeto() throws FileNotFoundException, IOException {
        // Creamos el objeto opositor
        Opositor op = new Opositor("Rosa", "Guillamon", "Susana", LocalDate.of(2021, 2, 14));
        ArrayList<Opositor> lista = new ArrayList<>();
        // Creamos el stream para escribir en el fichero
        FileOutputStream fos = new FileOutputStream("ficheros/datosSusana.dat");
        // Creamos el ObjectOutputStream para escribir objetos
        MyObjectOutputStream oos = new MyObjectOutputStream(fos);
        lista.add(op);
        lista.add(op);
        // Escribimos el objeto y cerramos el ObjectOutputStream
        oos.writeObject(lista);
         
        //oos.close();
        
    }
    
    
    // Método para leer un objeto de un fichero
    public static void m12CargarObjeto() throws FileNotFoundException, IOException, ClassNotFoundException {
        // Stream para leer del fichero
        FileInputStream fis = new FileInputStream("ficheros/datosSusana.dat");
        // ObjectInputStream para leer objetos directamente
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        // Leemos el objeto y lo casteamos a una variable de tipo Opositor
        Opositor op = (Opositor) ois.readObject();
          System.out.println(op);
        while(op!=null){
        // Mostramos los datos del opositor
        op = (Opositor) ois.readObject();
         System.out.println(op);
        }
        
        
        // Cerramos el stream
        ois.close();
        
    }
    
    
    
    
}
