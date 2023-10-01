package Estructura.Actor;

import Estructura.Message.Message;
import Estructura.Proxies.ActorProxy;

/*Este actor al procesar el mensaje automáticamente responde a su actor "Pong" asociado. */
public class PingPongActor extends Actor {
    private ActorProxy other;

    public PingPongActor(){
        super();
        this.other=null;
    }

    public void setOther(ActorProxy oth){
        this.other=oth;
    }

    @Override
    public void processMessage() {
        Message m = this.list.poll();
        System.out.println(m.getMessage()+" "+this.getName());
        m.setReference(this.proxy);
        this.send(other, m);
    }
    
}
