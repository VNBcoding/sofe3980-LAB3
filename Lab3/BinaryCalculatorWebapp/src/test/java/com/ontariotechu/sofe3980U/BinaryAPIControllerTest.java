package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;

    // Test case: Simple addition via /add endpoint.
    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "111").param("operand2", "1010"))
            .andExpect(status().isOk())
            .andExpect(content().string("10001"));
    }

    // Test case: JSON addition via /add_json endpoint.
    @Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "111").param("operand2", "1010"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    // Test case: Adding with zero operand.
    @Test
    public void addwithzero() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "111").param("operand2", "0"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    // Test case: Addition with carryover.
    @Test
    public void carryover() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "111").param("operand2", "111"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1110))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    // Test case: Addition with operands of different lengths.
    @Test
    public void differentlengths() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "111").param("operand2", "10100"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(10100))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(11011))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }

    // Test case: OR operation with operands of different lengths.
    @Test
    public void differentlengthsor() throws Exception {
        this.mvc.perform(get("/or_json").param("operand1", "111").param("operand2", "10100"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(10100))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("or"));
    }

    // Test case: OR operation with zero operand.
    @Test
    public void or0s() throws Exception {
        this.mvc.perform(get("/or_json").param("operand1", "11110").param("operand2", "00000"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(11110))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(00000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(11110))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("or"));
    }

     // Test case: OR operation on specific operands.
     @Test
     public void or() throws Exception {
         this.mvc.perform(get("/or_json").param("operand1", "110010").param("operand2", "100100"))
             .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(110010))
             .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(100100))
             .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(110110))
             .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("or"));
     }
 

    // Test case: Multiplication of binary numbers.
    @Test
    public void mult() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "1001").param("operand2", "1011"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1001))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1011))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1100011))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
    }

    // Test case: Multiplication with all 1's operands.
    @Test
    public void mult1s() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "111").param("operand2", "111"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(110001))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
    }

    // Test case: Multiplication with one operand being zero and operands of different lengths.
    @Test
    public void mult0difflength() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "111111").param("operand2", "0000"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(000000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
    }

   
    // Test case: AND operation on specific operands.
    @Test
    public void and() throws Exception {
        this.mvc.perform(get("/and_json").param("operand1", "11010").param("operand2", "11001"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(11010))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(11001))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(11000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("and"));
    }

    // Test case: AND operation with operands of different lengths.
    @Test
    public void and0difflength() throws Exception {
        this.mvc.perform(get("/and_json").param("operand1", "11111").param("operand2", "0000"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(11111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(000000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("and"));
    }

    // Test case: AND operation with all 1's operands.
    @Test
    public void and1s() throws Exception {
        this.mvc.perform(get("/and_json").param("operand1", "11111").param("operand2", "11111"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(11111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(11111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(11111))
            .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("and"));
    }
}
