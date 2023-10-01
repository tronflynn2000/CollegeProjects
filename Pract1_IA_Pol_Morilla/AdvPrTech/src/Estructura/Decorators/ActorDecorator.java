package Estructura.Decorators;
import Estructura.Actor.*;
import Estructura.Message.Message;
import Estructura.Proxies.Proxy;

public abstract class ActorDecorator extends Actor {
    protected Actor actor;

    public ActorDecorator(Actor decoratedActor){
        this.actor = decoratedActor;
    }
    
//    @Override
    public void processMessage(){
        actor.processMessage();
    }

    @Override
    public void send(Proxy prox, Message m){
        actor.send(prox, m);
    }

    @Override
    public void recieve(Message m){
        actor.recieve(m);
    }    
}
