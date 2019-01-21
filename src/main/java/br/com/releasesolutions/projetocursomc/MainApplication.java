package br.com.releasesolutions.projetocursomc;

import br.com.releasesolutions.projetocursomc.domain.*;
import br.com.releasesolutions.projetocursomc.domain.enums.EstadoPagamento;
import br.com.releasesolutions.projetocursomc.domain.enums.TipoCliente;
import br.com.releasesolutions.projetocursomc.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public MainApplication(
            CategoriaRepository categoriaRepository,
            ProdutoRepository produtoRepository,
            EstadoRepository estadoRepository,
            CidadeRepository cidadeRepository,
            ClienteRepository clienteRepository,
            EnderecoRepository enderecoRepository,
            PedidoRepository pedidoRepository,
            PagamentoRepository pagamentoRepository,
            ItemPedidoRepository itemPedidoRepository
    ) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.estadoRepository = estadoRepository;
        this.cidadeRepository = cidadeRepository;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Categoria categoria1 = new Categoria(null, "Informática");
        Categoria categoria2 = new Categoria(null, "Escritório");
        Categoria categoria3 = new Categoria(null, "Cama Mesa e Banho");
        Categoria categoria4 = new Categoria(null, "Eletrônicos");
        Categoria categoria5 = new Categoria(null, "Jardinagem");
        Categoria categoria6 = new Categoria(null, "Decoração");
        Categoria categoria7 = new Categoria(null, "Perfumaria");

        Produto produto1 = new Produto(null, "Computador", 2000.00);
        Produto produto2 = new Produto(null, "Impressora", 800.00);
        Produto produto3 = new Produto(null, "Mouse", 80.00);
        Produto produto4 = new Produto(null, "Mesa de Escritório", 300.00);
        Produto produto5 = new Produto(null, "Toalha", 50.00);
        Produto produto6 = new Produto(null, "Colcha", 200.00);
        Produto produto7 = new Produto(null, "TV true color", 1200.00);
        Produto produto8 = new Produto(null, "Roçadeira", 800.00);
        Produto produto9 = new Produto(null, "Abajour", 100.00);
        Produto produto10 = new Produto(null, "Pendente", 180.00);
        Produto produto11 = new Produto(null, "Shampoo", 90.00);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
        categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
        categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
        categoria5.getProdutos().add(produto8);
        categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
        categoria7.getProdutos().add(produto11);

        produto1.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
        produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
        produto4.getCategorias().add(categoria2);
        produto5.getCategorias().add(categoria3);
        produto6.getCategorias().add(categoria3);
        produto7.getCategorias().add(categoria4);
        produto8.getCategorias().add(categoria5);
        produto9.getCategorias().add(categoria6);
        produto10.getCategorias().add(categoria6);
        produto11.getCategorias().add(categoria7);

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8, produto9, produto10, produto11));

        Estado estado1 = new Estado(null, "São Paulo");
        Estado estado2 = new Estado(null, "Rio de Janeiro");

        Cidade cidade1 = new Cidade(null, "São José dos Campos", estado1);
        Cidade cidade2 = new Cidade(null, "São Paulo", estado1);
        Cidade cidade3 = new Cidade(null, "Rio de Janeiro", estado2);


        estado1.getCidades().addAll(Arrays.asList(cidade1, cidade2));
        estado2.getCidades().add(cidade3);

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente(null, "Robson Sousa", "robsoncartes@gmail.com", "123456789", TipoCliente.PESSOA_FISICA);

        cliente1.getTelefones().addAll(Arrays.asList("(12) 3966-3685", "(12) 99114-9818"));

        Endereco endereco1 = new Endereco(null, "Rua Felisbina", "383", "casa", "Jardim Imperial", "12234-070", cliente1, cidade1);
        Endereco endereco2 = new Endereco(null, "Rua Estados Unidos", "2001", "apto 11", "Jardim Paulista", "05212-060", cliente1, cidade2);

        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

        clienteRepository.save(cliente1);
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido pedido1 = new Pedido(null, sdf.parse("14/01/2019 16:35"), cliente1, endereco1);
        Pedido pedido2 = new Pedido(null, sdf.parse("13/01/2019 14:35"), cliente1, endereco2);

        Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 10);
        pedido1.setPagamento(pagamento1);

        Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, sdf.parse("13/02/2019 10:00"), null);
        pedido2.setPagamento(pagamento2);

        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

        ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2000.00);
        ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
        ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);

        pedido1.getItensPedidos().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItensPedidos().add(itemPedido3);

        produto1.getItensPedidos().add(itemPedido1);
        produto2.getItensPedidos().add(itemPedido3);
        produto3.getItensPedidos().add(itemPedido2);

        itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }
}
