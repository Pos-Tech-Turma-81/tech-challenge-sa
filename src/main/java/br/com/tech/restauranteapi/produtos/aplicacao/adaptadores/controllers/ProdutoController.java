package br.com.tech.restauranteapi.produtos.aplicacao.adaptadores.controllers;


import br.com.tech.restauranteapi.produtos.dominio.dtos.ProdutoDto;
import br.com.tech.restauranteapi.produtos.dominio.portas.interfaces.ProdutoServicePort;
import br.com.tech.restauranteapi.utils.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {


    private final ProdutoServicePort produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDto> salvar(@RequestBody ProdutoDto produto) {
        ProdutoDto produtoResponse = produtoService.salvar(produto);
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
