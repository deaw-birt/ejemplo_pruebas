/******************************************************************************************************************

  Nombre:   XXX
  Fecha:    XX/XX/2019
  Modulo:   Programación.
  UD:       UD7 ArrayList.
  Tarea:    Tarea Evaluación. Realiza un programa en Java.
  
  Descripción del programa:   Definición de la clase TweetBD
                              Gestionará la descripción y todos los tweets almacenados
                                
*******************************************************************************************************************/

import java.util.*;
import java.text.*;
import java.util.regex.*;     // Para poder usar patrones

public class TweetBD {

   // Atributos   
   private String descripcion;
   private ArrayList<Tweet> tweets;
     
   /* Constructor: inicializa los atributos descripción y tweets
      Parámetros: 
         String descripción: Descripción de los tweets que va a almacenar
   */
   public TweetBD(String descripcion) {
      this.descripcion = descripcion;
      this.tweets = new ArrayList<Tweet>();
   }

   /* Constructor: inicializa los atributos descripción y tweets.
      Aprovecha el otro constructor y el método addTweet para evitar redundancias.
      Carga los tweets leídos de un Scanner conectado a un fichero.
      Parámetros: 
         String descripción: Descripción de los tweets que va a almacenar
         Scanner leerFichero: Scanner conectado a un fichero
   */  
   public TweetBD(String descripcion, Scanner leerFichero) {
      this(descripcion);
      while (leerFichero.hasNextLine()) {
         String linea = leerFichero.nextLine();
         Scanner leerLinea = new Scanner(linea);
         while (leerLinea.hasNext()) {
            String usuario = leerLinea.next();
            String fecha = leerLinea.next();
            String tweet = leerLinea.nextLine();
            tweet = tweet.substring(1);
            addTweet(usuario, fecha, tweet);          
         }
      }
   }

  
/* También se podrían haber usado los patrones para comprobar el formato de los datos
   que leemos del fichero
   TweetBD(String descripcion, Scanner leerFichero) throws ParseException {
      this(descripcion);

      String formato = "(^USER_\\w+).+([0-9]{4}-[0-9]{2}-[0-9]{2}T[\\d]{2}:[\\d]{2}:[\\d]{2}) (.+)";
      Pattern patron = Pattern.compile(formato);

      while (leerFichero.hasNextLine()) {
         String linea = leerFichero.nextLine();
         Matcher comparaPatron = patron.matcher(linea);
         if (comparaPatron.find()) {
            String usuario = comparaPatron.group(1);
            String fecha = comparaPatron.group(2);
            String tweet = comparaPatron.group(3);
            this.addTweet(usuario, fecha, tweet);
         }
      }        
   }
*/
   
   /* Método toString
      Formatea un TweetBD de la siguiente manera:
         DESCRIPCION
         1.	usuario          	fecha                         	tweet
         2.	usuario          	fecha                         	tweet
         3.	usuario          	fecha                         	tweet 
      ...
      Utiliza el método toString de la clase Tweet y el bucle for-each
   */
   public String toString() {
      
      String formato = descripcion.toUpperCase();
      
      int cont = 1;
      for (Tweet unTweet : tweets) {
         formato += "\n" + cont + ".\t" + unTweet.toString();
         cont++;
      }
      formato += "\n";
      return formato;
   }
   
   // Añade un Tweet al ArrayList a partir de los datos indicados
   // Parámetros:
   //    String usuario: nombre del usuario que ha escrito el tweet
   //    String fechaHora: fecha del tweet con formato yyyy-MM-ddThh:mm:ss
   //    String tweet: contenido del tweet
   // No devuelve nada 
   public void addTweet(String usuario, String fecha, String tweet) {
   
      SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      // Si alguna fecha no cumple con el formato válido fallará
      // Se evita que ese tweet se añada al ArrayList
      try {
         Date fechaDate = formatoFecha.parse(fecha);
         Tweet nuevoTweet = new Tweet(usuario, fechaDate, tweet);
         tweets.add(nuevoTweet);     
      } catch (ParseException e) {
         System.out.println("El formato de la fecha es incorrecto");
      } 
            
   }

   // Añade una copia de Tweet indicado al ArrayList
   // Parámetros:
   //    Tweet unTweet: Tweet original. Se debe copiar antes de añadirlo
   // No devuelve nada    
   private void addTweet(Tweet unTweet) {
      Tweet copia = new Tweet(unTweet);
      tweets.add(copia);
   }
   
   // Métodos get y set
   // Devuelve el número de tweet del ArrayList tweets
   public int getNumeroTweets() {
      return tweets.size();
   }

   // Busca y devuelve una copia del tweet más reciente de la BBDD.
   // Si no hay ningún tweet devolverá null
   // Return: copia de Tweet más reciente del ArrayList
   public Tweet TweetMasReciente() {
      if (getNumeroTweets() == 0) {
         return null;
      }
      
      Tweet masReciente = tweets.get(0);
      for (int i = 1; i < tweets.size(); i++) {
         Tweet esteTweet = tweets.get(i);
         if (esteTweet.esMasReciente(masReciente)) {
            masReciente = esteTweet;
         }
      }
      return new Tweet(masReciente);      // Devolvemos una copia
   }

   // Borra todos los tweets del usuario indicado
   // Devolverá el número de tweets borrados
   // Parámetros:
   //    String usuario: usuario cuyos tweets hay que borrar
   // Return: número de tweets borrados  
   public int borrarTweets(String usuario) {
      int cont = 0;
      Iterator<Tweet> it = tweets.iterator();   
      while (it.hasNext()) {
         Tweet unTweet = it.next();
         String unUsuario = unTweet.getUsuario();
         if (unUsuario.equals(usuario)) {
            it.remove();
            cont++;
         }
      }
      return cont;  
   }

   // Devuelve el Tweet con el índice indicado
   // Se realizará una copia antes de devolver el objeto para protegerlo de accesos externos
   // Parámetros:
   //       int i: índice del tweet
   // Return: 
   //       Copia del Tweet buscado
   public Tweet getTweet(int i) {
      Tweet buscado = tweets.get(i);
      Tweet copia = new Tweet(buscado);
      return copia;
   }
   
   // Crea y devuelve un TweetBD con todos los tweet que contienen el texto dado
   // La descripción será "Tweets que contienen la palabra + palabra"
   // Parámetros:
   //    String palabra: Palabra a buscar en el texto del tweet. Puede ser parte de una palabra
   // Return: un objeto TweetBD con el resultado obtenido    
   public TweetBD buscarTweets(String palabra) {
      TweetBD nuevaBD = new TweetBD("Tweets que contienen la palabra " + palabra);
      for (Tweet unTweet : tweets) {
         String texto = unTweet.getTweet();
         if (texto.contains(palabra)) {
            nuevaBD.addTweet(unTweet);
         }
      }
         return nuevaBD; 
   }

   // Crea y devuelve un TweetBD con todos los tweet anteriores (más antiguos) al tweet dado
   // La descripción será "Tweets escritos antes de + tweet"
   // Parámetros:
   //    Tweet esteTweet: Tweet cuya fecha hay que mirar para obtener los que son más antiguos
   // Return: un objeto TweetBD con el resultado obtenido  
   public TweetBD tweetsAnteriores(Tweet esteTweet) {
      TweetBD nuevaBD = new TweetBD("Tweets escritos antes de este tweet: " + esteTweet);
           
      for (Tweet unTweet : tweets) {
         if (esteTweet.esMasReciente(unTweet)) {
            nuevaBD.addTweet(unTweet);
         }
      }
      return nuevaBD;    
   }
 
}












