MOCKITO NOTES:

What does it do? Why do we use it?

We use Mockito to mock specific methods in unit testing to make sure
they run beforehand. It is a framework typically used in conjunction with j unit. There are two ways of 
mocking: there is the mocking annotation and a static method in a dummy unit test- ex. "mock", or "stubbing"(see TestUtils)
Mockito allows us to test individual parts of the software as we build, " to isolate a segment of code (unit) and verifies 
its correctness. A unit is referred to as an individual function or procedure (program). 
The developers usually perform it during testing."
 
 
*NO NEED TO INJECT A DEPENDENCY because springboot already carries the mockito framework
in the springboot starter test. If the springboot starter test dependency isn't there, THEN use the 
mockito dependency.


*

 We created CustomerTesting in the test folder to TEST our work.

1. First, we use the @RunWith(SpringRunner.class) to run the test methods found from these classes,
 and reports the test results.
 
2. Second, we use the @MockBean annotation to initialize where we are getting the methods from.

3. Third, we use the @Test annotation to tell spring we will be doing some testing.

4. Lastly, we are making the methods we want to test from the repo and service classes.
