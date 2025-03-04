package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }
	
	    @Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }
	@Test
	    public void postParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
    }
	//Test case Add operation with different length of 2 binary numbers
	@Test
		public void difflength() throws Exception{
			this.mvc.perform(post("/").param("operand1","111" ).param("operator","+" ).param("operand2","01110"))
				.andExpect(status().isOk())
				.andExpect(view().name("result"))
				.andExpect(model().attribute("result", "10101"))
				.andExpect(model().attribute("operand1", "111"))
            	.andExpect(model().attribute("operand2", "01110"));
    }
	// Test case Add operation with carry 
	@Test
	public void carry() throws Exception {
        this.mvc.perform(post("/").param("operand1","1111").param("operator","+").param("operand2","1111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "11110"))
			.andExpect(model().attribute("operand1", "1111"))
            .andExpect(model().attribute("operand2", "1111"));
		}
	
	//Test case Add operation with 0
	@Test
	public void zero() throws Exception {
	this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","000"))//.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("result"))
		.andExpect(model().attribute("result", "111"))
		.andExpect(model().attribute("operand1", "111"))
		.andExpect(model().attribute("operand2", "000"));
	}
	//Test case Or operation
	@Test
	public void postParameteror() throws Exception {
	this.mvc.perform(post("/").param("operand1","1010").param("operator","|").param("operand2","1001"))//.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("result"))
		.andExpect(model().attribute("result", "1011"))
		.andExpect(model().attribute("operand1", "1010"))
		.andExpect(model().attribute("operand2", "1001"));
}
	//Test case for bitwise OR operation with 0
	@Test
	public void postParameterorzero() throws Exception {
	this.mvc.perform(post("/").param("operand1","1010").param("operator","|").param("operand2","0000"))//.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("result"))
		.andExpect(model().attribute("result", "1010"))
		.andExpect(model().attribute("operand1", "1010"))
		.andExpect(model().attribute("operand2", "0000"));
	}

	// Test case for bitwise OR operation when both operands are all 1s
    @Test
    public void postParameterorall1() throws Exception {
        this.mvc.perform(post("/").param("operand1","1111").param("operator","|").param("operand2","1111"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1111"))
            .andExpect(model().attribute("operand1", "1111"))
            .andExpect(model().attribute("operand2", "1111"));
    }

    // Test case for bitwise OR operation with operands of different lengths
    @Test
    public void ordifflength() throws Exception {
        this.mvc.perform(post("/").param("operand1","111100").param("operator","|").param("operand2","1111"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "111111"))
            .andExpect(model().attribute("operand1", "111100"))
            .andExpect(model().attribute("operand2", "1111"));
    }

    // Test case for bitwise AND operation when both operands are all 1s
    @Test
    public void postParameterandall1() throws Exception {
        this.mvc.perform(post("/").param("operand1","111111").param("operator","&").param("operand2","111111"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "111111"))
            .andExpect(model().attribute("operand1", "111111"))
            .andExpect(model().attribute("operand2", "111111"));
    }

    // Test case for bitwise AND operation when one operand is all 0s
    @Test
    public void postParameterandzero() throws Exception {
        this.mvc.perform(post("/").param("operand1","10101011").param("operator","&").param("operand2","00000000"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "0"))
            .andExpect(model().attribute("operand1", "10101011"))
            .andExpect(model().attribute("operand2", "00000000"));
    }

	// Test case for bitwise AND operation with two different binary numbers
	 @Test
	 public void postParameterand() throws Exception {
		 this.mvc.perform(post("/").param("operand1","10110").param("operator","&").param("operand2","11010"))
			 .andExpect(status().isOk())
			 .andExpect(view().name("result"))
			 .andExpect(model().attribute("result", "10010"))
			 .andExpect(model().attribute("operand1", "10110"))
			 .andExpect(model().attribute("operand2", "11010"));
	 }

    // Test case for multiplication operation when one operand is all 0s
    @Test
    public void postParametermultzero() throws Exception {
        this.mvc.perform(post("/").param("operand1","110111").param("operator","*").param("operand2","000000"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "0"))
            .andExpect(model().attribute("operand1", "110111"))
            .andExpect(model().attribute("operand2", "000000"));
    }

    // Test case for multiplication operation with operands of different lengths
    @Test
    public void postParametermultdifflength() throws Exception {
        this.mvc.perform(post("/").param("operand1","1111").param("operator","*").param("operand2","011"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "101101"))
            .andExpect(model().attribute("operand1", "1111"))
            .andExpect(model().attribute("operand2", "011"));
    }

    // Test case for multiplication operation when both operands are all 1s
    @Test
    public void postParametermult1s() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","*").param("operand2","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "110001"))
            .andExpect(model().attribute("operand1", "111"))
            .andExpect(model().attribute("operand2", "111"));
    }

   

    // Test case for multiplication operation with two non-trivial binary operands
    @Test
    public void postParametermult() throws Exception {
        this.mvc.perform(post("/").param("operand1","1110").param("operator","*").param("operand2","1010"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "10001100"))
            .andExpect(model().attribute("operand1", "1110"))
            .andExpect(model().attribute("operand2", "1010"));
    }

}




