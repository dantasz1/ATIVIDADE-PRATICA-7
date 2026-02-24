# 📦 Sistema de Gerenciamento de Inventário e Pedidos

Este projeto é um sistema de console robusto desenvolvido em Java para gerenciar produtos e pedidos de clientes. O foco principal foi a aplicação de padrões de projeto (Model-Service-View), persistência em arquivos JSON e o uso de recursos modernos do Java 8+.

## 🧠 Reflexão sobre o Desenvolvimento

O maior desafio deste projeto não foi apenas a sintaxe, mas a construção da **Camada de Serviço (Service Layer)**. Centralizar a inteligência do sistema — como a lógica de estoque, validação de e-mails e a automação de processos — exigiu uma decomposição cuidadosa do problema em subtarefas menores e fragmentadas. 

Voltar a temas da faculdade permitiu aperfeiçoar a lógica por trás de cada método. O processo de entender como o sistema deve se comportar antes de codificar foi essencial para consolidar o aprendizado sobre regras de negócio e manipulação de dados.

---

## 🛠️ Tecnologias e Conceitos Aplicados

### 1. Persistência de Dados (Jackson Databind)
* **ObjectMapper**: Utilizado para converter objetos Java em JSON (Serialização) e vice-versa (Desserialização).
* **JsonNode**: Aplicado no carregamento de dados para percorrer a estrutura do arquivo como uma árvore de nós. Isso permitiu uma manipulação granular e flexível dos dados antes de mapeá-los para os objetos da aplicação.

### 2. Robustez e Segurança
* **Optional (Java 8+)**: Implementado nas buscas por ID para evitar o `NullPointerException`. Em vez de retornar nulo, o sistema retorna um "container" que força o tratamento seguro da ausência de um valor.
* **Regex (Pattern & Matcher)**: Implementação de validação rigorosa de e-mails de clientes utilizando expressões regulares antes de permitir a criação de pedidos.

### 3. Organização e Estrutura
* **Arquitetura MSV (Model-Service-View)**: 
    * **Model**: Classes que representam os dados (`Produto`, `Pedido`, `ItemPedido`).
    * **Service**: A inteligência do sistema onde residem as regras de negócio (`SistemaInventario`).
    * **View**: Interface de interação com o usuário via console (`Main`).
* **Enums**: Uso de estados tipados para o status dos pedidos (NOVO, PROCESSANDO, ENVIADO, etc.), garantindo que o sistema aceite apenas valores válidos e pré-definidos.

---

## ⚙️ Funcionalidades Principais

* **Gestão de Estoque**: Adição de novos produtos com atualização automática de quantidade caso o ID já exista no sistema.
* **Controle de Pedidos**: Validação de e-mail e conferência de estoque em tempo real. O sistema impede a conclusão do pedido caso a quantidade solicitada seja superior à disponível em estoque.
* **Persistência Automática**: Carregamento automático de `produtos.json` e `pedidos.json` ao iniciar e salvamento instantâneo após qualquer alteração.
* **Busca Segura**: Localização de produtos no catálogo utilizando `Optional` para garantir estabilidade.

---

## 🚀 Como Executar o Projeto

1. Certifique-se de ter o **JDK 17+** e o **Maven** instalados.
2. Adicione a dependência do Jackson ao seu arquivo `pom.xml`:
   ```xml
   <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
       <version>2.15.2</version>
   </dependency>
