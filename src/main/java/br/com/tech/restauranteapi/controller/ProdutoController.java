package br.com.tech.restauranteapi.controller;

import br.com.tech.restauranteapi.controller.dtos.ProdutoDto;
import br.com.tech.restauranteapi.domain.Produto;
import br.com.tech.restauranteapi.presenter.ProdutoPresenter;
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


    private final ProdutoUsecase usecase;

    @Operation(summary = "Cadastrar novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoDto> salvar(@RequestBody @Valid ProdutoDto produtoDto) {
        Produto produto = ProdutoPresenter.fromToDomain(produtoDto);
        Produto produtoResponse = usecase.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoPresenter.toDto(produtoResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> salvar(
            @PathVariable("id") Integer id,
            @RequestBody ProdutoDto produtoDto) {
        produtoDto.setId(id);
        Produto produto = ProdutoPresenter.fromToDomain(produtoDto);
        Produto produtoResponse = usecase.alterar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoPresenter.toDto(produtoResponse));
    }


    @GetMapping
    public ResponseEntity<Page<ProdutoDto>> buscar(
            @RequestParam(name = "nome_categoria", required = true) String nomeCategoria,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Produto> produtosResponse =
                usecase.buscarPorCategoria(CategoriaEnum.obterPorNome(nomeCategoria), pageable);
        return ResponseEntity.ok(produtosResponse.map(ProdutoPresenter::toDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Integer id) {
        usecase.remover(id);
        return ResponseEntity.noContent().build();
    }

}