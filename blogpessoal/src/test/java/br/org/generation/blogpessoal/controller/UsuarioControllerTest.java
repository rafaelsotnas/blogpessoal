package br.org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.org.generation.blogpessoal.model.Usuario;
import br.org.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	/*Injeção de dependência pra realizar testes, como no Postman, no STS.*/
	/*Pra consumir APIs de terceiros, basta retirar o "test" de testRestTemplate.*/
	
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar usuário.")
	public void deveCriarUmUsuario() {
		
		/*Inserção de dados Http: criação da requisição.*/
		HttpEntity<Usuario> requisicao=new HttpEntity<Usuario>(new Usuario(0L, "Paulo Antunes", 
				"paulo_antunes@email.com.br", "13465278", " "));
		
		/*Passa endereço, verbo, requisição criada e o que se quer receber.*/
		ResponseEntity<Usuario> resposta=testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		/*Confirmação se a resposta foi a esperada.*/
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		
		/*Checar se o que foi gravado no banco de dados tá correto.*/
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
	}
	@Test
	@Order(2)
	@DisplayName("Não deve permitir a duplicação do usuário.")
	public void naoDeveDuplicarUsuario() {
		
		/*Gravando um usuário no banco de dados, que será duplicado no corpo da requisição.*/
		usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Maria da Silva", "maria_silva@email.com.br", "13465278", " "));
		
		/*Inserção de dados Http: criação da requisição.*/
		HttpEntity<Usuario> requisicao=new HttpEntity<Usuario>(new Usuario(0L, 
				"Maria da Silva", "maria_silva@email.com.br", "13465278", " "));
		
		/*Passa endereço, verbo, requisição criada e o que se quer receber.*/
		ResponseEntity<Usuario> resposta=testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		/*Confirmação se a resposta foi a esperada.*/
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	@Test
	@Order(3)
	@DisplayName("Atualizar usuário.")
	public void deveAtualizarUmUsuario() {
		
		/*Gravando um usuário no banco de dados, que será alterado no corpo da requisição.*/
		java.util.Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", " "));
		
		/*Atualização dos dados.*/
		Usuario usuarioUpdate=new Usuario(usuarioCreate.get().getId(), 
				"Roberto Clemente", "roberto.clemente@email.com", "robertoclemente21", " ");
		
		/*Requisição.*/
		HttpEntity<Usuario> requisicao=new HttpEntity<Usuario>(usuarioUpdate);
		
		/*Autenticação.*/
		ResponseEntity<Usuario>resposta=testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);
		
		/*Confirmação se a resposta foi a esperada.*/
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
		/*Checar se o que foi gravado no banco de dados tá correto.*/
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
	}
	@Test
	@Order(4)
	@DisplayName("Listar todos os usuários.")
	public void deveListarTodosOsUsuario() {
		
		/*Cadastro de usuários da lista.*/
		usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", " "));		
			usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", " "));
		
		/*Buscar lista de usuários.*/
		ResponseEntity<String>resposta=testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		
		/*Confirmação se a resposta foi a esperada.*/
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	@Test
	@Order(5)
	@DisplayName("Identificar usuários pelo Id.")
	public void deveIdentificarUsuariosPeloId() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", " "));		
			usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", " "));	
			
		ResponseEntity<String>resposta=testRestTemplate
					.withBasicAuth("root", "root")
					.exchange("/usuarios/1", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());		
	}
}
/*@Disabled: desabilita um método específico na hora do teste.*/
