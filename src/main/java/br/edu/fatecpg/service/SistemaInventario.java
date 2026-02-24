package br.edu.fatecpg.service;

import br.edu.fatecpg.model.ItemPedido;
import br.edu.fatecpg.model.Pedido;
import br.edu.fatecpg.model.Produto;
import br.edu.fatecpg.model.StatusPedido;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SistemaInventario {






    private Map<Integer, Produto> produtos = new HashMap<>();
    private Map<Integer, Pedido> pedidos = new HashMap<>();

    // Adiciona ou atualiza a quantidade de um produto
    public void adicionarProduto(Produto novoProduto) {
        if (produtos.containsKey(novoProduto.getId())) {
            Produto existente = produtos.get(novoProduto.getId());
            existente.setQuantidade(existente.getQuantidade() + novoProduto.getQuantidade());
            System.out.println("Produto já existia. Quantidade atualizada.");
        } else {
            produtos.put(novoProduto.getId(), novoProduto);
            System.out.println("Produto adicionado com sucesso.");
        }
        salvarDados();
    }

    // Lista todos os produtos
    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
            return;
        }
        produtos.values().forEach(System.out::println);
    }

    // Busca produto usando Optional (Requisito do desafio)
    public Optional<Produto> buscarProduto(int id) {
        return Optional.ofNullable(produtos.get(id));
    }

    // Validação de E-mail com Regex (Requisito do desafio)
    private boolean isEmailValido(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    // Criação de Pedido com validação de estoque e e-mail
    public void criarPedido(Pedido pedido) {
        // 1. Validar e-mail
        if (!isEmailValido(pedido.getEmail())) {
            System.out.println("Erro: E-mail inválido!");
            return;
        }

        // 2. Validar estoque de todos os itens antes de processar
        for (ItemPedido item : pedido.getItens()) {
            Optional<Produto> prodEstoque = buscarProduto(item.getProduto().getId());

            if (prodEstoque.isEmpty()) {
                System.out.println("Erro: Produto " + item.getProduto().getNome() + " não encontrado.");
                return;
            }

            if (prodEstoque.get().getQuantidade() < item.getQuantidade()) {
                System.out.println("Erro: Estoque insuficiente para " + item.getProduto().getNome());
                return;
            }
        }

        // 3. Subtrair no estoque.
        for (ItemPedido item : pedido.getItens()) {
            Produto prod = produtos.get(item.getProduto().getId());
            prod.setQuantidade(prod.getQuantidade() - item.getQuantidade());
        }

        // 4. Salvar o pedido no Map
        pedidos.put(pedido.getIdPedido(), pedido);
        System.out.println("Pedido #" + pedido.getIdPedido() + " criado com sucesso para: " + pedido.getNomeCliente());

        salvarDados();
        System.out.println("Pedido salvo e estoque atualizado no arquivo!");
    }
    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    private final String CAMINHO_PRODUTOS = "produtos.json";
    private final String CAMINHO_PEDIDOS = "pedidos.json";

    public void salvarDados() {
        try {
            // Salva os valores do Map de produtos
            mapper.writeValue(new File(CAMINHO_PRODUTOS), produtos.values());
            // Salva a lista de pedidos (ou valores do seu Map de pedidos)
            mapper.writeValue(new File(CAMINHO_PEDIDOS), pedidos.values());
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
    public void carregarProdutos() {
        File arquivo = new File(CAMINHO_PRODUTOS);
        if (!arquivo.exists()) return;

        try {
            // Lê o arquivo inteiro como uma árvore de nós
            JsonNode rootNode = mapper.readTree(arquivo);

            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    // Converte cada nó individualmente para a classe Produto
                    Produto p = mapper.treeToValue(node, Produto.class);
                    produtos.put(p.getId(), p);
                }
            }
            System.out.println("Produtos carregados com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
        }
    }
}