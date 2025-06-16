package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.gateway.domain.Produto;
import br.com.tech.restauranteapi.usecase.ProdutoUsecase;
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


    private final ProdutoUsecase produtoService;

    @Operation(summary = "Cadastrar novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoDto> salvar(@RequestBody @Valid ProdutoDto produtoDto) {
        Produto produto = Produto.builderProduto(produtoDto);
        Produto produtoResponse = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse.toProdutoDto());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> salvar(
            @PathVariable("id") Integer id,
            @RequestBody ProdutoDto produtoDto) {
        produtoDto.setId(id);
        Produto produto = Produto.builderProduto(produtoDto);
        Produto produtoResponse = produtoService.alterar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse.toProdutoDto());
    }


    @GetMapping
    public ResponseEntity<Page<ProdutoDto>> buscar(
            @RequestParam(name = "nome_categoria", required = true) String nomeCategoria,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Produto> produtosResponse =
                produtoService.buscarPorCategoria(CategoriaEnum.obterPorNome(nomeCategoria), pageable);
        return ResponseEntity.ok(produtosResponse.map(Produto::toProdutoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Integer id) {
        produtoService.remover(id);
        return ResponseEntity.noContent().build();
    }

}