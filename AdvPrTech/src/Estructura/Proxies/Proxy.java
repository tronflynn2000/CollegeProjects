package Estructura.Proxies;
import Estructura.Actor.Actor;
import Estructura.Message.*;

/*El Proxt es una interfaz comuna que tanto el ActorProxy como el ClientProxy deben implementar. */
public interface Proxy {
    public void send(Message m);

    default public Message recieve(){
        return(null);
    }

    public Actor getActorReal();

}
