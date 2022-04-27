/******************************************************************************************************************

  Nombre:   XXX
  Fecha:    XX/XX/2019
  Modulo:   Programación.
  UD:       UD7 ArrayList.
  Tarea:    Tarea Evaluación. Realiza un programa en Java.
  
  Descripción del programa:   Definición de la clase Tweet
                              Gestionará el usuario, la fecha y el contenido de un tweet
                                
*******************************************************************************************************************/

import java.util.*;           // Librerías para manejar objetos de la clase Date
import java.text.*;           // Librerías para manejar objetos de la clase SimpleDateFormat

// Definición de la clase Tweet
public class Tweet {

   // Atributos
    private String usuario;
    private Date fecha;
    private String tweet; 
    
   /* 
      Constructor: crea la Un Tweet.
      Parámetros: 
         String usuario: nombre del usuario que ha escrito el tweet
         Date fecha: fecha del tweet
         String tweet: contenido del tweet
      Se define el formato y se convierte usando el método parse.
      Se debe gestionar la excepción ParseException
   */
   public Tweet(String usuario, Date fecha, String tweet) {
      this.usuario = usuario;
      this.tweet = tweet;
      this.fecha = new Date(fecha.getTime());
   }   
   
   // Constructor copia
   // Para usar el otro constructor mediante this(), habría que convertir la fecha Date a String
   public Tweet(Tweet otroTweet) {
      this(otroTweet.usuario, otroTweet.fecha, otroTweet.tweet);
   }
   
   // toString
   // Es necesario utilizar la clase SimpleDateFormat para convertir la fecha Date a un String
   // En este caso el formato será dd de MMMM d' yyyy (HH:mm:ss)
   // Se usará el método format
   public String toString() {
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy (HH:mm:ss)");
      String fechaString = formatoFecha.format(fecha);
      return String.format("%-13s\t%s\t%s", usuario, fechaString, tweet);
   }
   
   // Métodos get y set
   
   // Devuelve el usuario del Tweet
   public String getUsuario() {
      return usuario;
   }
   
   // Devuelve la fecha del Tweet
   public Date getFecha() {
      return new Date(fecha.getTime());
   }
   
   // Devuelve el contenido del Tweet
   public String getTweet() {
      return tweet;
   }
     
   // Compara el atributo fecha con la fecha del Tweet otro 
   // Devuelve true si es más reciente y false en caso contrario
   // Utilizamos el método compareTo
   public boolean esMasReciente(Tweet otro) {
      Date otraFecha = otro.getFecha();
      int compararFechas = fecha.compareTo(otraFecha);
      return compararFechas > 0;
   }
}