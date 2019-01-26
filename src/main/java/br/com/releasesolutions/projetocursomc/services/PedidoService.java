package br.com.releasesolutions.projetocursomc.services;

// Nota: O Spring Boot na versão 2.X.X é compatível apenas com as versões do JAVA 8 em diante.

import br.com.releasesolutions.projetocursomc.domain.ItemPedido;
import br.com.releasesolutions.projetocursomc.domain.PagamentoComBoleto;
import br.com.releasesolutions.projetocursomc.domain.Pedido;
import br.com.releasesolutions.projetocursomc.domain.enums.EstadoPagamento;
import br.com.releasesolutions.projetocursomc.repositories.ItemPedidoRepository;
import br.com.releasesolutions.projetocursomc.repositories.PagamentoRepository;
import br.com.releasesolutions.projetocursomc.repositories.PedidoRepository;
import br.com.releasesolutions.projetocursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final BoletoService boletoService;
    private final PagamentoRepository pagamentoRepository;
    private final ProdutoService produtoService;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ClienteService clienteService;
    private final EmailService emailService;

    public PedidoService(
            PedidoRepository pedidoRepository,
            BoletoService boletoService,
            PagamentoRepository pagamentoRepository,
            ProdutoService produtoService,
            ItemPedidoRepository itemPedidoRepository,
            ClienteService clienteService,
            EmailService emailService
    ) {
        this.pedidoRepository = pedidoRepository;
        this.boletoService = boletoService;
        this.pagamentoRepository = pagamentoRepository;
        this.produtoService = produtoService;
        this.itemPedidoRepository = itemPedidoRepository;
        this.clienteService = clienteService;
        this.emailService = emailService;
    }

    public Pedido buscarPedidoPorId(Integer id) {

        Pedido pedido = pedidoRepository.findById(id).orElse(null);

        if (pedido == null)
            throw new ObjectNotFoundException("Pedido não encontrado. Id: " + id + ", Tipo: " + Pedido.class.getName());

        return pedido;
    }

    @Transactional
    public Pedido inserirPedido(Pedido pedido){

        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.buscarClientePorId(pedido.getCliente().getId()));
        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if (pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
        }

        pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for (ItemPedido itemPedido : pedido.getItensPedidos()){
            itemPedido.setDesconto(0.0);
            itemPedido.setProduto(produtoService.buscarProdutoPorId(itemPedido.getProduto().getId()));
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            itemPedido.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItensPedidos());
        emailService.sendOrderConfirmationEmail(pedido);

        return pedido;
    }
}
