package com.dreamteam.bankingapi.controllers;

import com.dreamteam.bankingapi.TestUtils;
import com.dreamteam.bankingapi.models.Customer;
import com.dreamteam.bankingapi.response.DataResponse;
import com.dreamteam.bankingapi.response.Response;
import com.dreamteam.bankingapi.response.exceptions.NotFound;
import com.dreamteam.bankingapi.services.CustomerService;
import com.dreamteam.bankingapi.services.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {





    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    CustomerController customerController;

    @Mock
    Customer customer;


    ObjectMapper objectMapper = new ObjectMapper();

   @Autowired
   private WebApplicationContext context;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ryan");
        customer.setLastName("Batchelor");

    }
   /*
    @Test
   public void addCustomerTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(customerController.addCustomer(any(Customer.class))).thenReturn();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ryan");
        customer.setLastName("Batchelor");
        ResponseEntity<Response> responseEntity = customerController.addCustomer(customer);

        Assert.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        Assert.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/customers");
*/

    }






