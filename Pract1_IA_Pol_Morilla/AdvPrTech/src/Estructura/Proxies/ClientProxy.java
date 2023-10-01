package Estructura.Proxies;

import java.util.ArrayDeque;
import java.util.Queue;
import Estructura.Message.*;
import Estructura.Actor.*;

/*El ClientProxy, a diferencia del ActorProxy permite que un solo usuario haga la comunicación con un Actor. Es
 * una alternativa para permitir una comunicación más familiar para el usuario con un Actor que tiene por Proxy
 * este tipo ClientProxy.
 */
public class ClientProxy implements Proxy {
    private Queue<Message> queue;
    private Actor actorReal;

    public Actor getActorReal() {
        return actorReal;
    }

    public ClientProxy(String associatedString, Actor actorReal){
        
        queue = new ArrayDeque<>();
        this.actorReal = actorReal;
        actorReal.setProxy(this);
    }

    public void send(Message m){

        if(m.getReference()==this){
            queue.add(m);
        }
        else if(m.getReference()==null){
        m.setReference(this);
        actorReal.recieve(m);
        }
    }

    public Message recieve(){
        while(queue.peek()==null){}
        return(queue.poll());
    }   
}