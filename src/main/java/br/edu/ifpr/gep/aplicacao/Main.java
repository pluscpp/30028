/* 
 * ERIC DE CARVALHO OLIVEIRA - 20241LON0030028
*/
package br.edu.ifpr.gep.aplicacao;

import br.edu.ifpr.gep.menu.Menu;

import java.io.InputStream;
import java.util.logging.LogManager;

public class Main {
  public static void main(String[] args) {

    // Evita avisos de Log do Mongo ao iniciar o programa
    try (InputStream in = Main.class.getResourceAsStream("/logging.properties")) {
      if (in != null) {
        LogManager.getLogManager().readConfiguration(in);
      }
    } catch (Exception ignored) {}

    new Menu().start();
  }
}
