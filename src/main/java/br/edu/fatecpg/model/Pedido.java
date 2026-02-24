package br.edu.fatecpg.model;

import java.util.List;
import java.util.ArrayList;

public class Pedido {

    private int idPedido;
    private String nomeCliente;
    private String email;
    private StatusPedido status;
    private List<ItemPedido> itens;


    public Pedido(int idPedido, List<ItemPedido> itens, StatusPedido status, String email, String nomeCliente) {
        this.idPedido = idPedido;
        this.itens = itens;
        this.status = status;
        this.email = email;
        this.nomeCliente = nomeCliente;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}


