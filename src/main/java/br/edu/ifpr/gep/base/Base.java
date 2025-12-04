/* 
 * ERIC DE CARVALHO OLIVEIRA - 20241LON0030028
 */
package br.edu.ifpr.gep.base;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import br.edu.ifpr.gep.entidades.Portaria;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Base {

  private final MongoClient mongo;
  private final MongoDatabase db;
  private final MongoCollection<Document> colPortarias;
  private final MongoCollection<Document> colUsuarios;

  public Base() {

    MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");

    this.mongo = new MongoClient(uri);
    this.db = mongo.getDatabase("ifpr_portarias");

    this.colPortarias = db.getCollection("portarias");
    this.colUsuarios = db.getCollection("usuarios");
  }

  public void inserirPortaria(Portaria p) {

    Document doc = new Document("idPort", p.getNumero())
      .append("data", p.getData())
      .append("nome", p.getNomePortaria());

    colPortarias.insertOne(doc);
    System.out.println("Portaria cadastrada.");
  }

  public List<Portaria> listarPortarias() {
    List<Portaria> lista = new ArrayList<>();

    try (MongoCursor<Document> cur = colPortarias.find().iterator()) {
      while (cur.hasNext()) {
        Document doc = cur.next();

        Number num = (Number) doc.get("idPort");
        long idPort = (num != null ? num.longValue() : 0L);

        String data = doc.getString("data");
        String nome = doc.getString("nome");

        lista.add(new Portaria(idPort, data, nome));
      }
    }

    return lista;
  }

  public Portaria buscarPortariaPorNumero(long numero) {
    Document filtro = new Document("idPort", numero);
    Document doc = colPortarias.find(filtro).first();

    if (doc == null)
      return null;

    Number num = (Number) doc.get("idPort");
    long idPort = (num != null ? num.longValue() : 0L);

    return new Portaria(idPort, doc.getString("data"), doc.getString("nome"));
  }

  public void atualizarNomePortaria(long numero, String novoNome) {
    Document filtro = new Document("idPort", numero);
    Document update = new Document("$set", new Document("nome", novoNome));

    if (colPortarias.updateOne(filtro, update).getMatchedCount() > 0)
      System.out.println("Nome atualizado.");
    else
      System.out.println("Portaria não encontrada.");
  }

  public void inserirUsuario(long idPort, String nome) {
    Document doc = new Document("idPort", idPort)
      .append("nome", nome);

    colUsuarios.insertOne(doc);
    System.out.println("Usuário cadastrado.");
  }

  public boolean usuarioExiste(String nome) {
    Document filtro = new Document("nome", nome);
    return colUsuarios.find(filtro).first() != null;
  }

  public void atualizarNomeUsuario(String nomeAntigo, String nomeNovo) {
    Document filtro = new Document("nome", nomeAntigo);
    Document update = new Document("$set", new Document("nome", nomeNovo));

    if (colUsuarios.updateOne(filtro, update).getMatchedCount() > 0)
      System.out.println("Usuário atualizado.");
    else
      System.out.println("Usuário não encontrado.");
  }


  public List<Document> listarUsuariosPorFiltro(String filtro) {
    List<Document> lista = new ArrayList<>();
    if (filtro == null || filtro.isBlank())
      return lista;

    Pattern p = Pattern.compile(filtro, Pattern.CASE_INSENSITIVE);

    try (MongoCursor<Document> cur = colUsuarios.find(new Document("nome", p)).iterator()) {

      while (cur.hasNext()) {
        Document doc = cur.next();

        Number num = (Number) doc.get("idPort");
        long idPort = (num != null ? num.longValue() : 0L);

        Document portaria = colPortarias.find(new Document("idPort", idPort)).first();
        String nomePort = (portaria != null ? portaria.getString("nome") : "(Sem Portaria)");

        Document full = new Document("nomeUsuario", doc.getString("nome"))
          .append("idPort", idPort)
          .append("nomePortaria", nomePort);

        lista.add(full);
      }
    }

    return lista;
  }

  public boolean verificarNumPort(long num) {
    MongoCollection<Document> col = db.getCollection("portarias");

    Document filtro = new Document("idPort", num);

    Document existe = col.find(filtro).first();

    return existe != null;
  }
}

