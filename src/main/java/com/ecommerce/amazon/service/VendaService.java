@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoProdutoRepository carrinhoProdutoRepository;

    public Venda buscarPorId(Long id) {
        ...
    }

    public List<Venda> buscarHistorico(Long usuarioId) {
        ...
    }

    public Venda atualizarStatus(Long vendaId,
                                 StatusVenda novoStatus) {
        ...
    }

    private void baixarEstoque(
            Produto produto,
            Integer quantidade
    ) {
        ...
    }
}