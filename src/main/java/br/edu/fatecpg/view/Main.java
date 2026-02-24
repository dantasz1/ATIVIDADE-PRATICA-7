package br.edu.fatecpg.view;

import br.edu.fatecpg.model.*;
import br.edu.fatecpg.service.SistemaInventario;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Ao instanciar, o construtor do SistemaInventario já carrega os JSONs
        SistemaInventario sistema = new SistemaInventario();
        int opcao;

        do {
            System.out.println("\n========= MENU DO SISTEMA =========");
            System.out.println("1. Adicionar/Atualizar Produto");
            System.out.println("2. Listar Produtos em Estoque");
            System.out.println("3. Criar Novo Pedido");
            System.out.println("0. Sair e Salvar");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Cadastro de Produto ---");
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Preço: ");
                    double preco = scanner.nextDouble();
                    System.out.print("Quantidade inicial: ");
                    int qtd = scanner.nextInt();

                    sistema.adicionarProduto(new Produto(id, nome, preco, qtd));
                    break;

                case 2:
                    System.out.println("\n--- Inventário Atual ---");
                    sistema.listarProdutos();
                    break;

                case 3:
                    System.out.println("\n--- Novo Pedido ---");
                    System.out.print("Nome do Cliente: ");
                    String cliente = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String email = scanner.nextLine();

                    System.out.print("ID do Produto desejado: ");
                    int idBusca = scanner.nextInt();
                    System.out.print("Quantidade: ");
                    int qtdPedido = scanner.nextInt();

                    // O 'buscarProduto' retorna um Optional, por isso usamos o ifPresent
                    sistema.buscarProduto(idBusca).ifPresentOrElse(prod -> {
                        List<ItemPedido> itens = new ArrayList<>();
                        itens.add(new ItemPedido(prod, qtdPedido));

                        // Criamos o pedido com ID 1 (você pode automatizar esse ID depois)
                        Pedido pedido = new Pedido(1, itens, StatusPedido.NOVO, email, cliente);
                        sistema.criarPedido(pedido);
                    }, () -> System.out.println("ERRO: Produto não existe no catálogo!"));
                    break;

                case 0:
                    System.out.println("Encerrando o sistema. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}