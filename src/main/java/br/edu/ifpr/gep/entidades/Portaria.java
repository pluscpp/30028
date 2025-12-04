/* 
 * ERIC DE CARVALHO OLIVEIRA - 20241LON0030028
 */
package br.edu.ifpr.gep.entidades;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Portaria {

  // Para resolver problemas relacionadoao nome 'data'do JSON do MongoDB

  @BsonProperty("idPort")
  private long numero;

  @BsonProperty("data")
  private String data;

  @BsonProperty("nome")
  private String nomePortaria;

  public Portaria() {}

  public Portaria(long numero, String data, String nomePortaria) {
    this.numero = numero;
    this.data = data;
    this.nomePortaria = nomePortaria;
  }

  public long getNumero() {
    return numero;
  }

  public String getData() {
    return data;
  }

  public String getNomePortaria() {
    return nomePortaria;
  }

  public void setNumero(long numero) {
    this.numero = numero;
  }

  public void setData(String data) {
    this.data = data;
  }

  public void setNomePortaria(String nomePortaria) {
    this.nomePortaria = nomePortaria;
  }
}


