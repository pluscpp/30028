/* 
 * ERIC DE CARVALHO OLIVEIRA - 20241LON0030028
 */
package br.edu.ifpr.gep.menu;

import br.edu.ifpr.gep.base.Base;
import br.edu.ifpr.gep.entidades.Portaria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

public class Menu {

  private final Scanner sc = new Scanner(System.in);
  private final Base base = new Base();

  private final DateTimeFormatter fmt =
    DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

  public void start() {
    while (true) {
      System.out.println("\n--- MENU ---");
      System.out.println("1 - Cadastrar Portaria");
      System.out.println("2 - Listar Portarias");
      System.out.println("3 - Editar Portaria");
      System.out.println("4 - Cadastrar Usuário");
      System.out.println("5 - Listar Usuários");
      System.out.println("6 - Editar Usuário");
      System.out.println("7 - Sair");
      System.out.print("> ");

      String op = sc.nextLine().trim();

      switch (op) {
        case "1" -> cadastrarPortaria();
        case "2" -> listarPortarias();
        case "3" -> editarPortaria();
        case "4" -> cadastrarUsuario();
        case "5" -> listarUsuarios();
        case "6" -> editarUsuario();
        case "7" -> { return; }
          default -> System.out.println("Opção inválida.");
      }
    }
  }

  private void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private void cadastrarPortaria() {
    clear();
    System.out.println(":::: CADASTRAR PORTARIA ::::");

    System.out.print("Informe o nº da Portaria: ");
    String numStr = sc.nextLine().trim();

    if (!numStr.matches("\\d+")) {
      System.out.println("Número inválido.");
      return;
    }

    long numero = Long.parseLong(numStr);

    System.out.print("Informe a data (dd/MM/yyyy): ");
    String data = sc.nextLine().trim();

    try {
      LocalDate.parse(data, fmt);
    } catch (Exception e) {
      System.out.println("Data inválida.");
      return;
    }

    System.out.print("Informe o nome da Portaria - EMISSOR: ");
    String nome = sc.nextLine().trim();
    if (nome.isBlank()) {
      System.out.println("Nome inválido.");
      return;
    }

    if(base.verificarNumPort(numero)){
      System.out.println("Número de Portaria já existe.");
      return;
    }

    Portaria p = new Portaria(numero, data, nome);
    base.inserirPortaria(p);
  }

  private void listarPortarias() {
    clear();
    System.out.println(":::: LISTA DE PORTARIAS ::::");

    List<Portaria> lista = base.listarPortarias();

    if (lista.isEmpty()) {
      System.out.println("Nenhuma portaria cadastrada.");
      return;
    }

    for (Portaria p : lista) {
      System.out.println("Número : " + p.getNumero());
      System.out.println("Data   : " + p.getData());
      System.out.println("Nome   : " + p.getNomePortaria());
      System.out.println("-----------------------------------");
    }
  }

  private void editarPortaria() {
    clear();
    System.out.println(":::: EDITAR PORTARIA ::::");

    System.out.print("Informe o nº da Portaria que deseja editar: ");
    String numStr = sc.nextLine().trim();

    if (!numStr.matches("\\d+")) {
      System.out.println("Número inválido.");
      return;
    }

    long numero = Long.parseLong(numStr);

    Portaria p = base.buscarPortariaPorNumero(numero);
    if (p == null) {
      System.out.println("Portaria não encontrada.");
      return;
    }

    System.out.println("Antigo nome: " + p.getNomePortaria());
    System.out.print("Novo nome: ");
    String novo = sc.nextLine().trim();

    if (novo.isBlank()) {
      System.out.println("Nome inválido.");
      return;
    }

    base.atualizarNomePortaria(numero, novo);
  }

  private void cadastrarUsuario() {
    clear();
    System.out.println(":::: CADASTRAR USUÁRIO ::::");

    System.out.print("Informe o nº da Portaria do Usuário: ");
    String numStr = sc.nextLine().trim();

    if (!numStr.matches("\\d+")) {
      System.out.println("Número inválido.");
      return;
    }

    long numero = Long.parseLong(numStr);

    Portaria p = base.buscarPortariaPorNumero(numero);

    if (p == null) {
      System.out.println("Portaria não encontrada.");
      return;
    }

    System.out.print("Nome do usuário: ");
    String nome = sc.nextLine().trim();

    if (nome.isBlank()) {
      System.out.println("Nome inválido.");
      return;
    }

    base.inserirUsuario(numero, nome);
  }

  private void listarUsuarios() {
    clear();
    System.out.println(":::: LISTAR USUÁRIOS ::::");

    System.out.print("Informe parte do nome: ");
    String filtro = sc.nextLine().trim();

    List<Document> lista = base.listarUsuariosPorFiltro(filtro);

    if (lista.isEmpty()) {
      System.out.println("Nenhum usuário encontrado.");
      return;
    }

    for (Document d : lista) {
      System.out.println("Usuário : " + d.getString("nomeUsuario"));
      System.out.println("ID Port : " + d.getLong("idPort"));
      System.out.println("Portaria: " + d.getString("nomePortaria"));
      System.out.println("-----------------------------------");
    }
  }

  private void editarUsuario() {
    clear();
    System.out.println(":::: EDITAR USUÁRIO ::::");

    System.out.print("Informe o nome do usuário: ");
    String nome = sc.nextLine().trim();

    if (nome.isBlank()) {
      System.out.println("Nome inválido.");
      return;
    }

    List<Document> encontrados = base.listarUsuariosPorFiltro(nome);

    if (encontrados.isEmpty()) {
      System.out.println("Usuário não encontrado.");
      return;
    }

    Document u = encontrados.get(0);

    System.out.println("Antigo nome: " + u.getString("nomeUsuario"));
    System.out.print("Novo nome: ");
    String novo = sc.nextLine().trim();

    if (novo.isBlank()) {
      System.out.println("Nome inválido.");
      return;
    }

    base.atualizarNomeUsuario(u.getString("nomeUsuario"), novo);
  }
}
