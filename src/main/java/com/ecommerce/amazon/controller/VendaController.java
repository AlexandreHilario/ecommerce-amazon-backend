@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping("/{id}")
    public Venda buscarPorId(@PathVariable Long id) {
        return vendaService.buscarPorId(id);
    }

    @GetMapping("/historico/{usuarioId}")
    public List<Venda> buscarHistorico(
            @PathVariable Long usuarioId) {

        return vendaService.buscarHistorico(usuarioId);
    }

    @PatchMapping("/{id}/status")
    public Venda atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusVenda status) {

        return vendaService.atualizarStatus(id, status);
    }

    @PostMapping("/finalizar/{usuarioId}")
    public Venda finalizarCompra(
            @PathVariable Long usuarioId) {

        return vendaService.finalizarCompra(usuarioId);
    }
}