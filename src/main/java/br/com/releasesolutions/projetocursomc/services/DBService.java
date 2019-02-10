package br.com.releasesolutions.projetocursomc.services;

import br.com.releasesolutions.projetocursomc.domain.*;
import br.com.releasesolutions.projetocursomc.domain.enums.EstadoPagamento;
import br.com.releasesolutions.projetocursomc.domain.enums.Perfil;
import br.com.releasesolutions.projetocursomc.domain.enums.TipoCliente;
import br.com.releasesolutions.projetocursomc.repositories.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    private CategoriaRepository categoriaRepository;
    private ProdutoRepository produtoRepository;
    private EstadoRepository estadoRepository;
    private CidadeRepository cidadeRepository;
    private ClienteRepository clienteRepository;
    private EnderecoRepository enderecoRepository;
    private PedidoRepository pedidoRepository;
    private PagamentoRepository pagamentoRepository;
    private ItemPedidoRepository itemPedidoRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public DBService(
            CategoriaRepository categoriaRepository,
            ProdutoRepository produtoRepository,
            EstadoRepository estadoRepository,
            CidadeRepository cidadeRepository,
            ClienteRepository clienteRepository,
            EnderecoRepository enderecoRepository,
            PedidoRepository pedidoRepository,
            PagamentoRepository pagamentoRepository,
            ItemPedidoRepository itemPedidoRepository,
            BCryptPasswordEncoder passwordEncoder
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
        this.passwordEncoder = passwordEncoder;
    }

    public void instantiateTestDatabase() throws Exception {

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
        Produto produto12 = new Produto(null, "Produto 12", 2000.00);
        Produto produto13 = new Produto(null, "Produto 13", 800.00);
        Produto produto14 = new Produto(null, "Produto 14", 80.00);
        Produto produto15 = new Produto(null, "Produto 15", 300.00);
        Produto produto16 = new Produto(null, "Produto 16", 50.00);
        Produto produto17 = new Produto(null, "Produto 17", 200.00);
        Produto produto18 = new Produto(null, "Produto 18", 1200.00);
        Produto produto19 = new Produto(null, "Produto 19", 800.00);
        Produto produto20 = new Produto(null, "Produto 20", 100.00);
        Produto produto21 = new Produto(null, "Produto 21", 180.00);
        Produto produto22 = new Produto(null, "Produto 22", 2000.00);
        Produto produto23 = new Produto(null, "Produto 23", 800.00);
        Produto produto24 = new Produto(null, "Produto 24", 80.00);
        Produto produto25 = new Produto(null, "Produto 25", 300.00);
        Produto produto26 = new Produto(null, "Produto 26", 50.00);
        Produto produto27 = new Produto(null, "Produto 27", 200.00);
        Produto produto28 = new Produto(null, "Produto 28", 1200.00);
        Produto produto29 = new Produto(null, "Produto 29", 800.00);
        Produto produto30 = new Produto(null, "Produto 30", 100.00);
        Produto produto31 = new Produto(null, "Produto 31", 180.00);
        Produto produto32 = new Produto(null, "Produto 32", 2000.00);
        Produto produto33 = new Produto(null, "Produto 33", 800.00);
        Produto produto34 = new Produto(null, "Produto 34", 80.00);
        Produto produto35 = new Produto(null, "Produto 35", 300.00);
        Produto produto36 = new Produto(null, "Produto 36", 50.00);
        Produto produto37 = new Produto(null, "Produto 37", 200.00);
        Produto produto38 = new Produto(null, "Produto 38", 1200.00);
        Produto produto39 = new Produto(null, "Produto 39", 800.00);
        Produto produto40 = new Produto(null, "Produto 40", 100.00);
        Produto produto41 = new Produto(null, "Produto 41", 180.00);
        Produto produto42 = new Produto(null, "Produto 42", 2000.00);
        Produto produto43 = new Produto(null, "Produto 43", 800.00);
        Produto produto44 = new Produto(null, "Produto 44", 80.00);
        Produto produto45 = new Produto(null, "Produto 45", 300.00);
        Produto produto46 = new Produto(null, "Produto 46", 50.00);
        Produto produto47 = new Produto(null, "Produto 47", 200.00);
        Produto produto48 = new Produto(null, "Produto 48", 1200.00);
        Produto produto49 = new Produto(null, "Produto 49", 800.00);
        Produto produto50 = new Produto(null, "Produto 50", 100.00);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));

        categoria1.getProdutos().addAll(Arrays.asList(produto12, produto13, produto14, produto15, produto16, produto17, produto18, produto19, produto20, produto21,
                produto22, produto23, produto24, produto25, produto26, produto27, produto28, produto29, produto30, produto31, produto32, produto33, produto34, produto35,
                produto36, produto37, produto38, produto39, produto40, produto31, produto32, produto33, produto34, produto35, produto36, produto37, produto38, produto39,
                produto40, produto41, produto42, produto43, produto44, produto45, produto46, produto47, produto48, produto49, produto50));


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

        produto12.getCategorias().add(categoria1);
        produto13.getCategorias().add(categoria1);
        produto14.getCategorias().add(categoria1);
        produto15.getCategorias().add(categoria1);
        produto16.getCategorias().add(categoria1);
        produto17.getCategorias().add(categoria1);
        produto18.getCategorias().add(categoria1);
        produto19.getCategorias().add(categoria1);
        produto20.getCategorias().add(categoria1);
        produto21.getCategorias().add(categoria1);
        produto22.getCategorias().add(categoria1);
        produto23.getCategorias().add(categoria1);
        produto24.getCategorias().add(categoria1);
        produto25.getCategorias().add(categoria1);
        produto26.getCategorias().add(categoria1);
        produto27.getCategorias().add(categoria1);
        produto28.getCategorias().add(categoria1);
        produto29.getCategorias().add(categoria1);
        produto30.getCategorias().add(categoria1);
        produto31.getCategorias().add(categoria1);
        produto32.getCategorias().add(categoria1);
        produto33.getCategorias().add(categoria1);
        produto34.getCategorias().add(categoria1);
        produto35.getCategorias().add(categoria1);
        produto36.getCategorias().add(categoria1);
        produto37.getCategorias().add(categoria1);
        produto38.getCategorias().add(categoria1);
        produto39.getCategorias().add(categoria1);
        produto40.getCategorias().add(categoria1);
        produto41.getCategorias().add(categoria1);
        produto42.getCategorias().add(categoria1);
        produto43.getCategorias().add(categoria1);
        produto44.getCategorias().add(categoria1);
        produto45.getCategorias().add(categoria1);
        produto46.getCategorias().add(categoria1);
        produto47.getCategorias().add(categoria1);
        produto48.getCategorias().add(categoria1);
        produto49.getCategorias().add(categoria1);
        produto50.getCategorias().add(categoria1);

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8, produto9, produto10, produto11));

        produtoRepository.saveAll(Arrays.asList(produto12, produto13, produto14, produto15, produto16, produto17, produto18, produto19, produto20,
                produto21, produto22, produto23, produto24, produto25, produto26, produto27, produto28, produto29, produto30,
                produto31, produto32, produto33, produto34, produto35, produto36, produto37, produto38, produto39, produto40,
                produto41, produto42, produto43, produto44, produto45, produto46, produto47, produto48, produto49, produto50));

        Estado estado1 = new Estado(null, "São Paulo");
        Estado estado2 = new Estado(null, "Rio de Janeiro");

        Cidade cidade1 = new Cidade(null, "São José dos Campos", estado1);
        Cidade cidade2 = new Cidade(null, "São Paulo", estado1);
        Cidade cidade3 = new Cidade(null, "Rio de Janeiro", estado2);
        Cidade cidade4 = new Cidade(null, "Campos do Jordão", estado1);

        estado1.getCidades().addAll(Arrays.asList(cidade1, cidade2, cidade4));
        estado2.getCidades().add(cidade3);

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3, cidade4));

        Cliente cliente1 = new Cliente(null, "Cliente 1", "robsoncartes@gmail.com", "123456789", TipoCliente.PESSOA_FISICA, passwordEncoder.encode("aluno"));
        Cliente cliente2 = new Cliente(null, "Admin", "robsoncartes@outlook.com", "123456789", TipoCliente.PESSOA_FISICA, passwordEncoder.encode("aluno"));

        cliente1.getTelefones().addAll(Arrays.asList("(12) 3966-3685", "(12) 99114-9818"));
        cliente2.getTelefones().addAll(Arrays.asList("(12) 3966-3685", "(12) 99114-9818"));

        Endereco endereco1 = new Endereco(null, "Rua Felisbina", "383", "casa", "Jardim Imperial", "12234-070", cliente1, cidade1);
        Endereco endereco2 = new Endereco(null, "Rua Estados Unidos", "2001", "apto 11", "Jardim Paulista", "05212-060", cliente1, cidade2);
        Endereco endereco3 = new Endereco(null, "Rua Rio", "40", "171", "Mil graus", "05212-060", cliente2, cidade3);
        Endereco endereco4 = new Endereco(null, "Rua Comendador José Schaffer", "1675", "casa", "Vila Inglesa", "12460-000", cliente2, cidade4);
        //Endereco endereco4 = new Endereco(null, "Rua Rio", "40", "casa", "Leblon", "12345-000", cliente2, cidade3);

        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
        cliente2.getEnderecos().addAll(Arrays.asList(endereco3, endereco4));
        cliente2.addPerfil(Perfil.ADMIN);

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3, endereco4));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido pedido1 = new Pedido(null, sdf.parse("13/01/2019 16:35"), cliente1, endereco1);
        Pedido pedido2 = new Pedido(null, sdf.parse("14/01/2019 14:35"), cliente1, endereco2);

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