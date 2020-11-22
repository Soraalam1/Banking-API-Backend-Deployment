package com.dreamteam.bankingapi.services;

import com.dreamteam.bankingapi.TestUtils;
import com.dreamteam.bankingapi.models.Customer;
import com.dreamteam.bankingapi.repositories.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean; //MockBean: it mocks the object and all its methods with do nothing and their result value will be null
import org.springframework.boot.test.mock.mockito.SpyBean; //SpyBean: tests SPECIFIC object and their classes. Object will behave like an @autowired object and all its methods will actually works
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class) //A test runner instantiates our test classes, runs the test methods found from these classes, and reports the test results
public class CustomerServiceTest {

  @SpyBean //Behaves like an autowire
  private CustomerService customerService;

  @MockBean //running it with the @ BEAN
  private CustomerRepository customerRepository;

  @MockBean
  private AccountService accountService;

  @Mock
  private Customer customer;

  @Test
  public void getAllCustomersTest(){
      List<Customer> expectedCustomers = TestUtils.getTestCustomers(); //I am getting a list of customers. (referring to the dummy test [TestUtils])
      when(customerRepository.findAll()).thenReturn(expectedCustomers); //When you use the findAll() method from the repo, then return the expected customer

      List<Customer> actualCustomers = customerService.getAllCustomers(); //Getting all the customers

      verify(customerRepository, times(1)).findAll(); //We are verifying that the findAll() method is invoked 1 time
      Assert.assertEquals(actualCustomers.size(),expectedCustomers.size()); //We want to match the size of the actual and expected customer

  }
    @Test(expected = RuntimeException.class) //Using a runtime exception that is supposed to give us @code null
    public void getCustomerFailsTest(){
        List<Customer> expectedCustomers = TestUtils.getTestCustomers(); //getting all the customers
        when(customerRepository.findAll()).thenThrow(RuntimeException.class); //when you invoke the findAll method, we throw an exception

        List<Customer> actualCustomers = customerService.getAllCustomers(); //getting all the customers from the list

        Assert.assertEquals(expectedCustomers,actualCustomers); //compare the two and see if they pass/match
    }

    @Test
    public void getCustomerByAccountId() {
      Customer expectedCustomer = TestUtils.getTestCustomers().get(0); //Made an expected customer that we made from the testUtils

      //Stubbing
      when(accountService.getAccountById(anyLong())).thenReturn(TestUtils.getAccount()); //When we get the customer accountID inserting any long, then return the account from the testUtils
      when(customerRepository.findById(anyLong())).thenReturn(Optional.of(expectedCustomer)); //When we find the ID with any long, then return an optional

      Customer actualCustomer = customerService.getCustomerByAccountId(expectedCustomer.getId()).orElse(null); //Get the customer by the accountID or else return NULL

      Assert.assertEquals(expectedCustomer,actualCustomer); //comparing the two
    }


    @Test (expected = RuntimeException.class)
    public void getCustomerByAccountIdFailTest() {
    //Customer mocked = mock(Customer.class);

        Customer expectedCustomer = TestUtils.getTestCustomers().get(0); //Getting the customer from index 0 since it is a list

        //Stubbing
        when(accountService.getAccountById(anyLong())).thenReturn(TestUtils.getAccount()); //Getting an accountID with any long, then returning it
        when(customerRepository.findById(anyLong())).thenThrow(new RuntimeException("Test")); //finding the ID with any long, and throwing a runtime exception message

        Customer actualCustomer = customerService.getCustomerByAccountId(expectedCustomer.getId()).orElse(null); //Getting the customer by their account ID and 

        Assert.assertEquals(expectedCustomer.getFirstName(),actualCustomer.getFirstName()); //making sure it matches so it can pass
    }
    @Test
    public void addCustomerTest(){
        Customer expectedCustomers = TestUtils.getTestCustomer();

        when(customerRepository.save(customer)).thenReturn(expectedCustomers);

        Customer actualNewCustomer = customerService.addCustomer(customer);

        Assert.assertEquals(actualNewCustomer,expectedCustomers);

    }
    @Test
    public void updateCustomerByIdTest(){
      Customer expectedUpdate = TestUtils.getTestCustomer();
      when(customerRepository.save(customer)).thenReturn(expectedUpdate);
      Customer actualUpdateByCustomerId = customerService.updateCustomerById(1L,customer);

      Assert.assertEquals(actualUpdateByCustomerId,expectedUpdate);
    }

    @Test
    public void getCustomerById(){

        Customer expectedCustomer = TestUtils.getTestCustomers().get(0);

        //Stubbing
        // Stub object is generally used for state verification
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(expectedCustomer));

        Customer actualCustomer = customerService.getCustomerById(expectedCustomer.getId()).orElse(null);

        verify(customerRepository, times(1)).findById(anyLong());

        Assert.assertEquals(expectedCustomer, actualCustomer);

        //Mock objects are typically used for behavior verification
    }

    //Good for edge cases
    @Test(expected = RuntimeException.class)
    public void getCustomerByIdFailTest(){

        Customer expectedCustomer = TestUtils.getTestCustomers().get(0);

        when(customerRepository.findById(anyLong())).thenThrow(new RuntimeException("Test"));

        Customer actualCustomer = customerService.getCustomerById(expectedCustomer.getId()).orElse(null);

        Assert.assertEquals(actualCustomer,expectedCustomer);

    }

    //when we create a connection to the database, some issues related to configurations occur.
    // It requires mocking for creating mock components to provide unit testing.

    //If we want to test a component that depends on the other component, but it is under development.
    // It generally uses when working in a team and parts are divided between several team-mates.
    // In this case, mocking plays an essential role in the testing of that component.
    // Without mocking, we need to wait for the completion of the required elements for testing

}
