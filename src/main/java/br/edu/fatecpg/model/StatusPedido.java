package br.edu.fatecpg.model;

import static java.lang.System.out;

public enum StatusPedido {
 //Enum sendo criado pra impedir valores invalidos.
    NOVO("Novo"),
    PROCESSANDO("Processando"),
    ENVIADO("Enviado"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

}
