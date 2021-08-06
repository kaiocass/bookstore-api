package com.kaio.bookstore.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.kaio.bookstore.resources.CategoriaResource;
import com.kaio.bookstore.service.exceptions.ObjectNotFoundException;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
@Sql({"/schema.sql", "/data.sql"})
public class CategoriaResourceTest {

	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired private CategoriaResource categoriaResource;
	@Autowired private PageableHandlerMethodArgumentResolver pageable;
	@Autowired private MappingJackson2HttpMessageConverter jacksonMessage;
	private MockMvc restCategoriaMockMvc;
	
	
	@PostConstruct
	public void setup() {
		this.restCategoriaMockMvc = MockMvcBuilders.standaloneSetup(categoriaResource)
				.setCustomArgumentResolvers(pageable)
				.setMessageConverters(jacksonMessage).build();
	}
	
	@Test
	public void deveExibirStatusOkAoConsultarTodasCategorias() throws Exception {
		this.restCategoriaMockMvc.perform(
				get("/categorias")
				.contentType("application/json"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveExibirStatusOkAoConsultarCategoriasPorId() throws Exception {
		MvcResult mvcResult = this.restCategoriaMockMvc.perform(
				get("/categorias/1")
				.contentType("application/json"))
		.andExpect(status().isOk()).andReturn();
		
		String serviceResult = mvcResult.getResponse().getContentAsString();
		
		Categoria retorno = mapper.readValue(serviceResult, Categoria.class);
		
		Categoria expected = Categoria.builder()
				.id(1)
				.nome("Informatica")
				.descricao("Livros de TI").build();
		
		String jsonRetorno = mapper.writeValueAsString(retorno);
		String jsonEsperado = mapper.writeValueAsString(expected);
		
		assertEquals(jsonRetorno, jsonEsperado);
	}
	
	@Test
	public void deveExibirStatusNotFoundAoConsultarCategoriasPorId() throws Exception {
		this.restCategoriaMockMvc.perform(
				get("/categorias/5")
				.contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException))
				.andExpect(result -> assertEquals(
						"Objeto nao encontrado! Id: 5 e Tipo: com.kaio.bookstore.domain.Categoria", 
						result.getResolvedException().getMessage()));
	}
	
	@Test
	public void deveExibirStatusCreatedAoSalvarNovaCategoria() throws Exception {
		Categoria novaCategoria = Categoria.builder()
				.nome("Fantasia")
				.descricao("Livros de Fantasia").build();
		
		this.restCategoriaMockMvc.perform(
				post("/categorias")
				.contentType("application/json")
				.content(mapper.writeValueAsString(novaCategoria)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void deveExibirStatusOkAoEditarCategoria() throws Exception {
		Categoria novaCategoria = Categoria.builder()
				.nome("Fantasia")
				.descricao("Livros de Fantasia").build();
		
		this.restCategoriaMockMvc.perform(
				put("/categorias/1")
				.contentType("application/json")
				.content(mapper.writeValueAsString(novaCategoria)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveExibirStatusOkAoDeletarCategoria() throws Exception {
		this.restCategoriaMockMvc.perform(
				delete("/categorias/4")
				.contentType("application/json"))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void deveExibirStatusBadRequestAoDeletarCategoria() throws Exception {
		this.restCategoriaMockMvc.perform(
				delete("/categorias/1")
				.contentType("application/json"))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof com.kaio.bookstore.service.exceptions.DataIntegrityViolationException))
		.andExpect(result -> assertEquals(
				"Categoria nao pode ser deletada! Possui livros associados", 
				result.getResolvedException().getMessage()));
	}
}
