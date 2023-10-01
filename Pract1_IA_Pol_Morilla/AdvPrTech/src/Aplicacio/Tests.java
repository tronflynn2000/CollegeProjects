package Aplicacio;
import Estructura.Message.*;
import Estructura.Proxies.ActorProxy;
import Estructura.Proxies.ClientProxy;
import Estructura.Proxies.DynamicProxy;
import Estructura.Service.Service;
import Estructura.Service.InsultService;
import Estructura.Actor.*;
import Exceptions.CreationException;
import org.junit.Assert;
import org.junit.Test;

public class Tests {
    @Test
       public void testCreation() throws CreationException{
        ActorProxy p = ActorContext.getActorContext().spawnActor("Pepe", new ActorHelloWorld());
        ActorProxy po = ActorContext.getActorContext().spawnActor("Helloo", new ActorHelloWorld());
        Assert.assertEquals("Pepe", ActorContext.getActorContext().lookUp("Pepe").getActorReal().getName());
        Assert.assertEquals("Helloo", ActorContext.getActorContext().lookUp("Helloo").getActorReal().getName()); 
       }

    @Test
    public void testClientProxy() throws CreationException{
        ClientProxy po = ActorContext.getActorContext().c_spawnActor("Pe", new InsultActor());
        po.send(new GetAllInsultsMessage(null));
        Assert.assertEquals("[Tonto, Burro, Imbecil, Mameluco, Membrillo]", po.recieve().getMessage());
        po.send(new AddInsultMessage(null, "Burrego"));
        po.send(new GetAllInsultsMessage(null));
        Assert.assertEquals("[Tonto, Burro, Imbecil, Mameluco, Membrillo, Burrego]", po.recieve().getMessage());
    }   

    @Test
    public void testDynamicProxy() throws CreationException{
        ClientProxy din = ActorContext.getActorContext().c_spawnActor("Din", new InsultActor());
        Service isn = (Service)DynamicProxy.intercept(new InsultService(), din);
        Assert.assertEquals("[Tonto, Burro, Imbecil, Mameluco, Membrillo]", isn.getAllInsult());
        isn.addInsult("Estupido");
        Assert.assertEquals("[Tonto, Burro, Imbecil, Mameluco, Membrillo, Estupido]", isn.getAllInsult());
        }

    @Test
    public void testRecievedListener() throws CreationException{
        ActorProxy test = ActorContext.getActorContext().spawnActor("qwewe", new ActorHelloWorld());
        ActorProxy test2 = ActorContext.getActorContext().spawnActor("wqoe",new ActorHelloWorld());
        test.getActorReal().send(test2, new Message(test,"Buenos"));
        System.out.println("");
        Assert.assertEquals(1, test2.getActorReal().getObservers().get(3).getNum());
    }
}
