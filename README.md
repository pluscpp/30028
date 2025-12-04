# ERIC DE CARVALHO OLIVEIRA - 20241LON0030028
## Documentação para rodar o programa

Importe as tabelas para o MongoDB:
```bash
mongoimport --uri "mongodb://localhost:27017" --db ifpr_portarias --collection portarias --jsonArray --drop --file seed_portarias.json
mongoimport --uri "mongodb://localhost:27017" --db ifpr_portarias --collection usuarios --jsonArray --drop --file seed_usuarios.json
```

## Depois importe no Eclipse ou rode no console.

### No console:
```bash
mvn clean compile
mvn exec:java
```

---

## Importar e rodar no Eclipse
1. Importar o projeto Maven
  + Vá em `File > Import > Maven > Existing Maven Projects`.
  + Navegue até a pasta do projeto (onde está o `pom.xml`) e clique em `Finish`(ou duplo clique em **Existing Maven Projects**).

2. Atualizar dependências
  + Clique com o botão direito no projeto > `Maven > Update Project` (ou `Alt+F5`).
  + Marque `Force Update of Snapshots/Releases` e clique em `OK`.

3. Configurar execução
  + Clique com o botão direito no projeto > `Run As > Maven build…`
  + Na janela, em `Goals` coloque:
  ```
  clean compile exec:java
  ```
  + Clique em `Run`.

---
