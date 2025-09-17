package dev.java10x.CadastroDeNinjas.Missoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private final MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    // POST -- Mandar uma requisicao para criar as missoes
    // Adicionar missoes (CREATE)
    @PostMapping("/criar")
    public ResponseEntity<String> criarMissao(@RequestBody MissoesDTO missao) {
        MissoesDTO novaMissao = missoesService.criarMissoes(missao);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Missao criada com sucesso: " + novaMissao.getNome() + " (ID): " + novaMissao.getId() + " Com a dificuldade " +  novaMissao.getDificuldade());
    }

    // GET -- Mandar uma requisicao para listar as missoes
    // Mostrar todas as missoes (READ)
    @GetMapping("/listar")
    public ResponseEntity<List<MissoesDTO>> listarMissoes() {
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        return ResponseEntity.ok(missoes);
    }

    // GET -- Manadar uma requisicao para listar por id as missoes
    // Mostrar todas as missoes por id (READ)
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarMissoesPorId(@PathVariable Long id) {
        MissoesDTO missoes = missoesService.listarMissoesPorId(id);

        if (missoes != null) {
            return ResponseEntity.ok("Missão encontrada: " + missoes.getNome());
        } else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão com id: " + id + " não está nos nossos registros");
        }
    }

    // PUT -- Mandar uma requisicao para alterar as missoes
    // Alterar missoes (UPDATE)
    @PutMapping("/alterar/{id}")
    public ResponseEntity<?> alterarMissaoPorId(@PathVariable Long id, @RequestBody MissoesDTO missaoAtualizado) {
        MissoesDTO missoes = missoesService.atualizarMissao(id, missaoAtualizado);

        if (missaoAtualizado != null) {
            return ResponseEntity.ok(missoes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Missão com id: " + id + " não está nos nossos registros");
        }
    }

    // DELETE -- Mandar uma requisicao para deletar as missoes
    // Deletar missoes (DELETE)
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarMissaoPorId(@PathVariable Long id) {
        if (missoesService.listarMissoesPorId(id) != null) {
            missoesService.deletarMissoesPorId(id);
            return ResponseEntity.ok("Missão com ID: " + id + " deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A missão com id " + id + " não encontrada");
        }
    }
}
