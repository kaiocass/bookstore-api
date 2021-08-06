package com.kaio.bookstore.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaio.bookstore.domain.Categoria;
import com.kaio.bookstore.domain.Livro;
import com.kaio.bookstore.resources.LivroResource;
import com.kaio.bookstore.service.exceptions.ObjectNotFoundException;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
public class LivroResourceTest {

	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired private LivroResource livroResource;
	@Autowired private PageableHandlerMethodArgumentResolver pageable;
	@Autowired private MappingJackson2HttpMessageConverter jacksonMessage;
	private MockMvc restLivroMockMvc;
	
	@PostConstruct
	public void setup() {
		this.restLivroMockMvc = MockMvcBuilders.standaloneSetup(livroResource)
				.setCustomArgumentResolvers(pageable)
				.setMessageConverters(jacksonMessage).build();
	}
	
	@Test
	public void deveExibirStatusOkAoConsultarTodosLivrosPorCategoria() throws Exception {
		this.restLivroMockMvc.perform(
				get("/livros?categoria=1")
				.contentType("application/json"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveExibirStatusOkAoConsultarTodosLivrosPorId() throws Exception {
		MvcResult mvcResult = this.restLivroMockMvc.perform(
				get("/livros/1")
				.contentType("application/json"))
		.andExpect(status().isOk()).andReturn();
		
		String serviceResult = mvcResult.getResponse().getContentAsString();
		
		Livro retorno = mapper.readValue(serviceResult, Livro.class);
		
		Livro expected = Livro.builder()
				.id(1)
				.titulo("Clean code")
				.nomeAutor("Robert C. Martin")
				.texto("Lorem ipsum")
				.categoria(Categoria.builder()
						.id(1).build()).build();
		
		String jsonRetorno = mapper.writeValueAsString(retorno);
		String jsonEsperado = mapper.writeValueAsString(expected);
		
		assertEquals(jsonRetorno, jsonEsperado);
	}
	
	@Test
	public void deveExibirStatusNotFoundAoConsultarLivrosPorId() throws Exception {
		this.restLivroMockMvc.perform(
				get("/livros/6")
				.contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException))
				.andExpect(result -> assertEquals(
						"Objeto nao encontrado! Id: 6 e Tipo: com.kaio.bookstore.domain.Livro", 
						result.getResolvedException().getMessage()));
	}
	
	@Test
	public void deveExibirStatusCreatedAoSalvarNovoLivro() throws Exception {
		Livro novoLivro = Livro.builder()
				.titulo("Base de Conhecimento em Teste de Software")
				.nomeAutor("Aderson Bastos")
				.texto("Lorem ipsum").build();
		
		this.restLivroMockMvc.perform(
				post("/livros?categoria=1")
				.contentType("application/json")
				.content(mapper.writeValueAsString(novoLivro)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void deveExibirStatusOkAoEditarLivro() throws Exception {
		Livro novoLivro = Livro.builder()
				.titulo("Base de Conhecimento em Teste de Software")
				.nomeAutor("Aderson Bastos")
				.texto("Lorem ipsum").build();
		
		this.restLivroMockMvc.perform(
				put("/livros/1")
				.contentType("application/json")
				.content(mapper.writeValueAsString(novoLivro)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveExibirStatusOkAoEditarTituloLivro() throws Exception {
		Livro novoLivro = Livro.builder()
				.titulo("Base de Conhecimento em Teste de Software")
				.nomeAutor("Erich Gamma")
				.texto("Lorem ipsum").build();
		
		MvcResult mvcResult = this.restLivroMockMvc.perform(
				patch("/livros/4")
				.contentType("application/json")
				.content(mapper.writeValueAsString(novoLivro)))
		.andExpect(status().isOk()).andReturn();
		
		String serviceResult = mvcResult.getResponse().getContentAsString();
		
		Livro retorno = mapper.readValue(serviceResult, Livro.class);
		
		Livro expected = Livro.builder()
				.id(4)
				.titulo("Base de Conhecimento em Teste de Software")
				.nomeAutor("Erich Gamma")
				.texto("Lorem ipsum")
				.categoria(Categoria.builder()
						.id(1).build()).build();
		
		String jsonRetorno = mapper.writeValueAsString(retorno);
		String jsonEsperado = mapper.writeValueAsString(expected);
		
		assertEquals(jsonRetorno, jsonEsperado);
	}
	
	@Test
	public void deveExibirStatusOkAoDeletarLivro() throws Exception {
		this.restLivroMockMvc.perform(
				delete("/livros/4")
				.contentType("application/json"))
		.andExpect(status().isNoContent());
	}
}
