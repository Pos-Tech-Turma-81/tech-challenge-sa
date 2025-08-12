package br.com.tech.restauranteapi.api;

import br.com.tech.restauranteapi.controller.ProdutoController;
import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoApi {

    private final ProdutoController controller;

    @Operation(summary = "Cadastrar novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoDto> salvar(@RequestBody ProdutoDto produtoDto) {
        ProdutoDto dto = controller.salvar(produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> salvar(
            @PathVariable("id") Integer id,
            @RequestBody ProdutoDto produtoDto) {
        ProdutoDto dto = controller.salvar(id, produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDto>> buscar(
            @RequestParam(name = "nome_categoria") String nomeCategoria,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ProdutoDto> page = controller.buscar(nomeCategoria, pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Integer id) {
        controller.remover(id);
        return ResponseEntity.noContent().build();
    }
}