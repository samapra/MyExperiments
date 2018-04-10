import cucumber.api.java.en.Given;

public class HelloWorldStepDef {

    @Given("^I print Hello world string$")
    public void IprintHelloWorldString(){
//        Assert.assertTrue(false, "it is true");
        System.out.println("Hello world String = " + "Hello world String");
    }

}
