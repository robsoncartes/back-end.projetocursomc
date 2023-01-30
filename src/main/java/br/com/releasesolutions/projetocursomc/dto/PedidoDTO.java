package br.com.releasesolutions.projetocursomc.dto;

import br.com.releasesolutions.projetocursomc.domain.Cliente;
import br.com.releasesolutions.projetocursomc.domain.Endereco;
import br.com.releasesolutions.projetocursomc.domain.ItemPedido;
import br.com.releasesolutions.projetocursomc.domain.Pagamento;
import br.com.releasesolutions.projetocursomc.domain.Pedido;
import java.util.HashSet;
import java.util.Set;


public class PedidoDTO {

    private Integer id;
    private Cliente cliente;
    private Endereco enderecoDeEntrega;
    private Pagamento pagamento;
    private Set<ItemPedido> itensPedido = new HashSet<>();

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = pedido.getCliente();
        this.enderecoDeEntrega = pedido.getEnderecoDeEntrega();
        this.pagamento = pedido.getPagamento();
        this.itensPedido = pedido.getItensPedidos();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Set<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(Set<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }
}
