/******************************************************************************************************************

  Nombre:   XXX
  Fecha:    XX/XX/2019
  Modulo:   Programación.
  UD:       UD7 ArrayList.
  Tarea:    Tarea Evaluación. Realiza un programa en Java.
  
  Descripción del programa: Lee una serie de tweets del fichero tweet.txt y los guarda en un objeto TweetBD
      Muestra por consola el número total de tweets que contiene y el tweet más reciente.
      Además, proporcionará un menú que permitirá trabajar con esos tweets hasta que se elija salir ("0").
      Las opciones serás:
         1. Añadir un tweet
         2. Borrar tweets
         3. Mostrar tweets
         4. Tweets de un tema
         5. Tweets anteriores
                                
*******************************************************************************************************************/
import java.util.*;
import java.io.*;
import java.text.*;

public class TweetMain {

   // Constantes
   public static final String FIN = "0";
   public static final String ADD = "1";
   public static final String BORRAR = "2";
   public static final String MOSTRAR = "3";
   public static final String TEMA = "4";
   public static final String ANTERIORES = "5";
   public static final String[] opcionesMenu = {"Añadir un tweet", "Borrar tweets", "Mostrar tweets", "Tweets de un tema", "Tweets anteriores"};
   
   // Método principal
   // Es un resumen del programa
   public static void main(String[] args) {
      
      presentacion();
      
      // Abre el fichero y crea un objeto TweetBD
      // Utiliza los métodos de esa clase para mostrar cuántos tweets se han leído
      Scanner ficheroEntrada = null;
      try {
         ficheroEntrada = new Scanner(new File("tweets.txt"));
         TweetBD unTweetBD = new TweetBD("Tweets del fichero", ficheroEntrada);
         
         // Muestra el número de tweets total y el tweet más reciente 
         int size = unTweetBD.getNumeroTweets();
         System.out.println("Se han leído " + size + " tweets");
         System.out.println("El tweet más reciente es:\n" + unTweetBD.TweetMasReciente());
              
         String opcion = elegirOpcion();
         while (!opcion.equals(FIN)) {
            switch (opcion) {
               case ADD:
                  add(unTweetBD);
               break;
               case BORRAR:
                  borrar(unTweetBD);
               break;
               case MOSTRAR:
                  System.out.println(unTweetBD.toString());
               break;
               case ANTERIORES:
                  anteriores(unTweetBD);
               break;
               case TEMA:
                  tema(unTweetBD);
               break;
               default:
                  System.out.println("Opción incorrecta");
               break;
            }
            opcion = elegirOpcion();
         }
      } catch (FileNotFoundException excepcion) {
         System.out.println("Se ha producido el error al abrir el fichero");
      } catch (Exception excepcion) {
         System.out.println("Se ha producido el error: " + excepcion.getMessage());
      }
      
      // Cierra los ficheros
      if (ficheroEntrada != null) {
         ficheroEntrada.close();
      }

      System.out.println("Fin del programa"); 
   }
   
   /*
      Presentación
   */
   public static void presentacion() {
      System.out.println("Este programa permite trabajar con una lista de tweets");
      System.out.println("Utiliza las clase Tweet y TweetBD");
      System.out.println("Lee tweets del fichero tweets.txt");
      System.out.println("Mediante un menú permite elegir diferentes opciones");
      System.out.println("Cuando se elige finalizar, muestra \"Fin del programa\"");
      System.out.println();
   }
   
   /*
      Elegir opcion:  muestra el menú, lee la opción elegida y la devuelve
      Parámetros: 
         Scanner consola para leer datos por teclado
      return: un int con la opción elegida
   */
   public static String elegirOpcion() {
      System.out.println("\n******* MENU *******");
      for (int i = 0; i < opcionesMenu.length; i++) {
         System.out.println((i + 1) + ". " + opcionesMenu[i]);
      }
      System.out.print("Elige tu opción (0 para acabar): ");
      String opcion = Teclado.leerTeclado.nextLine();
      return opcion;
   }

   /*
      Pide el usuario, la fecha y el contenido de un Tweet y lo añade al TweetBD indicado
      Pide una nueva fecha hasta que cumpla con el patrón indicado
      Se usará el método addTweet de la clase TweetBD
      Parámetros: 
         Scanner consola para leer datos por teclado
         TweetBD unTweetBD: Objeto donde hay que almacenar el tweet
      No se devuelve nada
   */  
   public static void add(TweetBD unTweetBD) {
      System.out.println("Vas a añadir un nuevo tweet.");
      
      String usuario = Teclado.leeUsuario("Usuario");
      
      String fecha = Teclado.leeFecha();

      String mensaje = Teclado.leeTexto("Mensaje");
      
      unTweetBD.addTweet(usuario, fecha, mensaje);
      System.out.println("El tweet se ha añadido");
   }

   /*
      Pide un usuario y borra todos sus tweet del TweetBD indicado
      Preguntará si realmente se quieren borrar y mostrará cuántos se han borrado
      Se usará el método borrarTweets de la clase TweetBD
      Parámetros: 
         Scanner consola para leer datos por teclado
         TweetBD unTweetBD: Objeto donde hay que borrar los tweets
      No se devuelve nada
   */   
   public static void borrar(TweetBD unTweetBD) {

      String usuario = Teclado.leeTexto("Elige un usuario");
      
      String mensaje = "Borrar todos los tweets del usuario " + usuario + " (S/N)";
      String respuesta = Teclado.leeTexto(mensaje);
      
      if (respuesta.equals("S")) {
         int total = unTweetBD.borrarTweets(usuario);
         System.out.print("Se han borrado " + total + " mensajes");
      }
   }

   /*
      Pide el número de un tweet y muestra todos los tweet anteriores (más antiguos) en el TweetBD indicado
      Se usarán los métodos getTweet, tweetsAnteriores y toString de la clase TweetBD
      Parámetros: 
         Scanner consola para leer datos por teclado
         TweetBD unTweetBD: Objeto donde hay que borrar los tweets
      No se devuelve nada
   */     
   public static void anteriores(TweetBD unTweetBD) {
   
      System.out.print("Elige el número de un tweet: ");
      int num = Teclado.leeInt();
      
      Tweet unTweet = unTweetBD.getTweet(num - 1);             // El índice es igual al número menos 1
      
      TweetBD resultados = unTweetBD.tweetsAnteriores(unTweet);
      System.out.println(resultados.toString());
   }
   
   /*
      Pide un texto y muestra todos los tweet que contienen ese texto en el TweetBD indicado,
      incluso dentro de una palabra
      Se usarán los métodos buscarTweets y toString (de manera implicita) de la clase TweetBD
      Parámetros: 
         Scanner consola para leer datos por teclado
         TweetBD unTweetBD: Objeto donde hay que borrar los tweets
      No se devuelve nada
   */    
   public static void tema(TweetBD unTweetBD) {

      String palabra = Teclado.leeTexto("Elige la palabra a buscar");
      
      TweetBD resultados = unTweetBD.buscarTweets(palabra);
      System.out.println(resultados);
   }
}