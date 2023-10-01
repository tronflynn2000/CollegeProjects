package Estructura.Proxies;
import Estructura.Message.*;
import Estructura.Actor.*;

/*El ActorProxy es un Proxy que permite que un Actor se comunique con otro Actor. Es el elemento intermedio
en la comunicación entre actores.
 */
public class ActorProxy implements Proxy{

    protected String associatedKey;
    protected Actor actorReal;

    public Actor getActorReal() {
        return actorReal;
    }

    public ActorProxy(String associatedString ,Actor actorReal){
        this.actorReal=actorReal;
        this.associatedKey=associatedString;
    
    }
    /*Con este método lo que logramos es enviar un mensaje al Actor asociado al ActorProxy. 
    /* 
    */
    public void send(Message m){
        actorReal.recieve(m);
    }
}