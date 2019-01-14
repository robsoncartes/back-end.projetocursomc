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

    public MainApplication(
            CategoriaRepository categoriaRepository,
            ProdutoRepository produtoRepository,
            EstadoRepository estadoRepository,
            CidadeRepository cidadeRepository,
            ClienteRepository clienteRepository,
            EnderecoRepository enderecoRepository,
            PedidoRepository pedidoRepository,
            PagamentoRepository pagamentoRepository
    ) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.estadoRepository = estadoRepository;
        this.cidadeRepository = cidadeRepository;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Categoria categoria1 = new Categoria(null, "Informática");
        Categoria categoria2 = new Categoria(null, "Escritório");

        Produto produto1 = new Produto(null, "Computador", 5000.00);
        Produto produto2 = new Produto(null, "Impressora", 1600.00);
        Produto produto3 = new Produto(null, "Mouse", 140.00);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().add(produto2);

        produto1.getCategorias().add(categoria1);
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
        produto3.getCategorias().add(categoria1);

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));

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
    }
}
