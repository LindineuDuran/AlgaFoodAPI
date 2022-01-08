package com.lduran.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.algafood.api.model.input.FotoProdutoInput;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController
{
//	@Autowired
//	private CadastroProdutoService cadastroProduto;
//
//	@Autowired
//	private CadastroRestauranteService cadastroRestaurante;
//
//	@Autowired
//	private ProdutoModelAssembler assembler;
//
//	@Autowired
//	private ProdutoInputModelDisassembler disassembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput)
	{
		var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

		var arquivoFoto = Path.of("D:\\GitHub\\AlgaFoodAPI\\catalogo", nomeArquivo);

		System.out.println(fotoProdutoInput.getDescricao());
		System.out.println(arquivoFoto);
		System.out.println(fotoProdutoInput.getArquivo().getContentType());

		try
		{
			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
//
//	@GetMapping
//	public ResponseEntity<List<ProdutoModel>> listar(@PathVariable Long restauranteId,
//			@RequestParam(required = false) boolean incluirInativos)
//	{
//		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
//
//		List<Produto> todosProdutos = null;
//		if (incluirInativos)
//		{
//			todosProdutos = cadastroProduto.listar(restaurante);
//		}
//		else
//		{
//			todosProdutos = cadastroProduto.listarAtivos(restaurante);
//		}
//
//		return ResponseEntity.ok(assembler.toCollectionModel(todosProdutos));
//	}
//
//	@GetMapping("/{produtoId}")
//	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId)
//	{
//		return assembler.toModel(cadastroProduto.buscar(restauranteId, produtoId));
//	}
//
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputModel produtoInput)
//	{
//		Restaurante restaurante = cadastroRestaurante.buscar(restauranteId);
//
//		Produto produto = disassembler.toDomainObject(produtoInput);
//		produto.setRestaurante(restaurante);
//
//		produto = cadastroProduto.salvar(produto);
//
//		return assembler.toModel(produto);
//	}
//
}
