package Aplicacio;

import Estructura.Actor.ActorContext;
import Estructura.Actor.PingPongActor;
import Estructura.Message.Message;
import Estructura.Proxies.ActorProxy;
import Exceptions.CreationException;

/*Este actor lo que hace es probar dos actores que se envían mensajes entre si con el mismo mecanismo que un Ping Pong. */
public class PingPong {
    public static void main(String args[]) throws CreationException{
        ActorProxy ping=ActorContext.getActorContext().spawnActor("Ping", new PingPongActor());
        ActorProxy pong=ActorContext.getActorContext().spawnActor("Pong", new PingPongActor());
        PingPongActor p = (PingPongActor)ping.getActorReal();
        p.setOther(pong);
        p=(PingPongActor)pong.getActorReal();
        p.setOther(ping);
        ping.getActorReal().send(pong, new Message(ping, "Buenas!"));
        
    }
    
}
