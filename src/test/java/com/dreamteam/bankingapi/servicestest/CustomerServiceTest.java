package com.dreamteam.bankingapi.servicestest;
//import com.dreamteam.bankingapi.models.Customer;
//import com.dreamteam.bankingapi.repositories.CustomerRepository;
//import com.dreamteam.bankingapi.services.AccountService;
//import com.dreamteam.bankingapi.services.CustomerService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
//public class CustomerServiceTest {
//
//    @SpyBean
//    private CustomerService customerService;
//
//    @MockBean
//    private CustomerRepository customerRepository;
//
//    @MockBean
//    private AccountService accountService;
//
//    @Mock
//    private Customer customer;
//
//    @Test
//    public void getAllCustomersTest(){
//        List<Customer> expectedCustomers = TestUtils.getTestCustomers();
//        when(customerRepository.findAll()).thenReturn(expectedCustomers);
//
//        List<Customer> actualCustomers = customerService.getAllCustomers();
//
//        Assert.assertEquals(expectedCustomers.size(), actualCustomers.size());
//
//    }
//}
