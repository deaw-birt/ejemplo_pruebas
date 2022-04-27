/******************************************************************************************************************

  Nombre:   XXX
  Fecha:    XX/XX/2019
  Modulo:   Programación.
  UD:       UD7 ArrayList.
  Tarea:    Tarea Evaluación. Realiza un programa en Java.
  
  Descripción de la clase: 
  Clase para leer datos por teclado. Todos los datos se leen como cadenas de caracteres con el método nextLine.
  Tanto el atributo como los métodos son estáticos. Están definidos a nivel de clase.
  Para usarlos no hay que crear ningún objeto. Como se hace con la clase Math,
  solo hay que escribir el nombre de la clase, un punto y el nombre del atributo o el método.
  Por ejemplo: Teclado.leeInt() o Teclado.leerTeclado.nextInt();
                                
*******************************************************************************************************************/

import java.util.Scanner;
import java.util.regex.*;     // Para poder usar patrones

public class Teclado {

   public static final Scanner leerTeclado = new Scanner(System.in);
   
   public static String leeTexto(String mensaje) {
      System.out.print(mensaje + ": ");
      String texto = leerTeclado.nextLine();
      return texto;
   }
   // Comprueba que el número introducido es realmente un entero antes de leerlo.
   // Si no lo es, lo descarta y pide otro.
   // return: Número entero válido.
   public static int leeInt() {
      do {
         try {
            String textoInt = leerTeclado.nextLine();
            int numInt = Integer.parseInt(textoInt);
            return numInt;
         } catch(Exception ex) {
            System.out.print("\nIntroduzca un número entero válido: ");
         }
      } while (true);
    }

   // Comprueba que el número introducido es realmente un double antes de leerlo.
   // Si no lo es, lo descarta y pide otro.
   // return: número decimal válido.
   public static double leeDouble() {
      do {
         try {
            String textoDouble = leerTeclado.nextLine();
            double numDouble = Double.parseDouble(textoDouble);
            return numDouble;
         } catch(Exception ex) {
            System.out.print("\nIntroduzca un número decimal válido: ");
         }
      } while (true);
   }
   
   // Comprueba que el texto introducido se corresponde con una usuario con formato: USER_1a3c7d9f
   // USER_ en mayúsculas, más 8 números hexadecimales
   // Si no lo es, lo descarta y pide otro.
   // return: String con la fecha válida.   
   public static String leeUsuario(String mensaje) {

      /* También podemos controlar el formato del usuario
      Pattern formatoUsuario = Pattern.compile("USER_[0-9abcdef]{8}");
      String usuario = null;
      Matcher comparaFormato = null;
      do {
         System.out.print(mensaje + ": ");
         usuario = leerTeclado.nextLine();
         comparaFormato = formatoUsuario.matcher(usuario);
      } while (!comparaFormato.matches());
      */
      System.out.print(mensaje + ": ");
      String usuario = leerTeclado.nextLine();
      return usuario;
   }

   // Comprueba que el texto introducido se corresponde con una fecha con formato: yyyy-MM-ddTHH:mm:ss
   // Si no lo es, lo descarta y pide otro.
   // return: String con la fecha válida.   
   public static String leeFecha() {
   
      Pattern formatoFecha = Pattern.compile("20[0-9]{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])T[0-9]{2}:\\d{2}:\\d{2}");
      String fecha = null;
      Matcher comparaFormato = null;
      do {
         System.out.print("Introduce una fecha y hora (aaaa-mm-ddThh:mm:ss): ");
         fecha = leerTeclado.nextLine();
         comparaFormato = formatoFecha.matcher(fecha);
      } while (!comparaFormato.matches());
      
      return fecha;
   }
   
}