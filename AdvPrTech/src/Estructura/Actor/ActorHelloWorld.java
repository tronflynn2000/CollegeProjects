package Estructura.Actor;
import Estructura.Message.*;

public class ActorHelloWorld extends Actor {

    public ActorHelloWorld(){
        super();
    }

    @Override
    public void processMessage() {
        super.processMessage();
        Message m = this.list.poll();
        System.out.println(m.getMessage());
        
        
    }
    
    
}
