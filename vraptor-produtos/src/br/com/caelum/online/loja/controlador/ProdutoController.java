package br.com.caelum.online.loja.controlador;

import java.util.List;

import br.com.caelum.online.loja.dominio.Produto;
import br.com.caelum.online.loja.repositorio.RepositorioDeProdutos;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;

@Resource
public class ProdutoController {

	private final RepositorioDeProdutos produtoDao;
	private Validator validator;
	private Result result;
	
	public ProdutoController(RepositorioDeProdutos produtos, Validator validator, Result result) {
		this.produtoDao = produtos;
		this.validator = validator;
		this.result = result;
	}
	
	public List<Produto> lista() {
		return produtoDao.pegaTodos();
	}
	
	@Path ("/produto/{id}")
	public Produto exibe(Long id) {
		return produtoDao.pegaPorId(id);
	}
	
	public void formulario() {
		
	}
	
	@Post
	public void adiciona(Produto produto) {
		if(produto.getPreco()<3 && produto.getPreco()>100) {
			validator.add(new ValidationMessage("Preço deve ser maior que 3 e menor que 100 reais.", "preco"));
		} else {
			this.produtoDao.salva(produto);
		}
			
		validator.onErrorUsePageOf(ProdutoController.class).formulario();
		
//		validator.checking(new Validations(){{
//            that(produto.getPreco() > 0,"Produto deve ser maior que 0", "preco");
//            that(produto.getDescricao().length() > 0,"Descrição é obrigatória", "descricao");
//            that(produto.getNome().length() >= 3 && produto.getNome().length() <= 100,"Nome deve ter entre 3 e 100 caracteres", "nome");
//        }});
		
//		validator.checking(new Validations(){{
//            // o preço deve ser maior que 0
//            that(produto.getPreco() > 0,"erro", "produto.preco.invalido");
//        }});
// 
//        // aqui apontamos para o validador o ação que ele deve redirecionar caso alguma validação falhe!
//        // no caso, estamos mandando ele ir para a ação "formulario" no ProdutoController
//        validator.onErrorUsePageOf(ProdutoController.class).formulario();   
// 
//        produtoDao.salva(produto);
//        result.redirectTo(ProdutoController.class).lista();
	}
	
	public void remove(Produto produto) {
		produtoDao.remove(produto);
		result.nothing();
	}
}
