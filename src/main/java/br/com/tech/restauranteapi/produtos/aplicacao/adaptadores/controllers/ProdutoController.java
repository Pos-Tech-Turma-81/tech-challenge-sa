package br.com.tech.restauranteapi.produtos.aplicacao.adaptadores.controllers;


import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.produtos.dominio.portas.interfaces.ProdutoServicePort;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
public class ProdutoController {


    private final ProdutoServicePort produtoService;

    @Operation(summary = "Cadastrar novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoDto> salvar(@RequestBody @Valid ProdutoDto produto) {
        ProdutoDto produtoResponse = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> salvar(
            @PathVariable("id") Integer id,
            @RequestBody ProdutoDto produto) {
        produto.setId(id);
        ProdutoDto produtoResponse = produtoService.alterar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
    }


    @GetMapping
    public ResponseEntity<Page<ProdutoDto>> buscar(
            @RequestParam(name = "nome_categoria", required = true) String nomeCategoria,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ProdutoDto> produtosResponse =
                produtoService.buscarPorCategoria(CategoriaEnum.obterPorNome(nomeCategoria), pageable);
        return ResponseEntity.ok(produtosResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Integer id) {
        produtoService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
