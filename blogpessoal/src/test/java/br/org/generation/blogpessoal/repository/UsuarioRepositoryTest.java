package br.org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.org.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	public void start() {
		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", " "));
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", " "));
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278", " "));
        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", " "));
	}
	@Test
	@DisplayName("Retorna um usuário.")
	public void deveRetornarUmUsuario() {
		Optional<Usuario> usuario=usuarioRepository.findByUsuario("joao@email.com.br");
		
		/*Confirmar o que se busca na linha 34; seja a informação verdadeira ou falsa.*/
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}
	@Test
	@DisplayName("Retorna três usuários.")
	public void deveRetornarTresUsuarios() {
		List<Usuario> listaUsuarios=usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		
		/*Verificar se a lista é composta por três atributos.*/
		assertEquals(3, listaUsuarios.size());
		
		/*Checar se o primeiro usuário foi o João da Silva.*/
		assertTrue(listaUsuarios.get(0).getNome().equals("João da Silva"));
	}
	
	/*Apaga os dados da tabela no fim do teste, pro banco não ficar preso.*/
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
}
