package com.test.offer.controller;

import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;
import java.nio.file.Files;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.test.offer.repository.OfferRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Before
	public void init(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Autowired
	private OfferController offerController;

	@Test
	public void controllerExists() {
		assertNotNull(new OfferController());
	}
	
	@Test
	public void controllerLoads(){
		assertNotNull(offerController);
	}

	@Test
	public void nullFieldsOffer() throws Exception{
		mockMvc.perform(
	            MockMvcRequestBuilders.post("/offers")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new String("{}"))
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()").value(5));
	}
	
	@Test
	public void nullFieldsProduct() throws Exception{
		mockMvc.perform(
	            MockMvcRequestBuilders.post("/offers")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content((new String(Files.readAllBytes(new ClassPathResource("offerWithoutProduct.json").getFile().toPath()), Charset.forName("UTF8"))))
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.errors.length()").value(1));
	}
	
	@Test
	public void successfulSave() throws Exception{
		mockMvc.perform(
	            MockMvcRequestBuilders.post("/offers")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content((new String(Files.readAllBytes(new ClassPathResource("offerWithProduct.json").getFile().toPath()), Charset.forName("UTF8"))))
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(IsNull.notNullValue()));
	}
	
	@Test
	public void getWithoutSave() throws Exception{
		mockMvc.perform(
	            MockMvcRequestBuilders.get("/offers")
	                .contentType(MediaType.APPLICATION_JSON)
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(0));
	}
	
	@Test
	public void successfulSaveAndGet() throws Exception{
		mockMvc.perform(
	            MockMvcRequestBuilders.post("/offers")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content((new String(Files.readAllBytes(new ClassPathResource("offerWithProduct.json").getFile().toPath()), Charset.forName("UTF8"))))
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(IsNull.notNullValue()));
		
		mockMvc.perform(
	            MockMvcRequestBuilders.get("/offers")
	                .contentType(MediaType.APPLICATION_JSON)
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Offer 1"));
	}
	
	@Test
	public void saveAndGetByOrder() throws Exception {
		mockMvc.perform(
	            MockMvcRequestBuilders.post("/offers")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content((new String(Files.readAllBytes(new ClassPathResource("offerWithProduct.json").getFile().toPath()), Charset.forName("UTF8"))))
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(IsNull.notNullValue()));
		
		mockMvc.perform(
	            MockMvcRequestBuilders.post("/offers")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content((new String(Files.readAllBytes(new ClassPathResource("offerWithProduct2.json").getFile().toPath()), Charset.forName("UTF8"))))
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(IsNull.notNullValue()));
		
		mockMvc.perform(
	            MockMvcRequestBuilders.get("/offers")
	            .param("sort", "id,desc")
	            .param("size", "1")
	                .contentType(MediaType.APPLICATION_JSON)
	        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1))
	        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Offer 2"));
	}
	
	@After
	public void deleteAll(){
		offerRepository.deleteAll();
	}
}
