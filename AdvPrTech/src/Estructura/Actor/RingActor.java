package Estructura.Actor;

import java.util.List;

import Estructura.Message.*;
import Estructura.Proxies.Proxy;

/*Este actor permite conformar un anillo de actores que se comunican cada uno con su vecino, formando así una 
 * cadena ciruclar que termina en el actor del anillo que ha enviado el primer mensaje.
 */
public class RingActor extends Actor {
    private Proxy nextElement;
    private List<RingActor> ring;
    private Proxy end;

    public Proxy getEnd() {
        return end;
    }

    public void setEnd(Proxy end) {
        this.end = end;
    }

    public RingActor(List<RingActor> ring){
        super();
        this.nextElement=null;
        this.ring = ring;
        this.end=null;
    }

    @Override
    public void processMessage(){
        Message m = list.poll();
        if(!this.nextElement.equals(this.end)){
            m.setReference(this.proxy);
            this.send(nextElement, m);
            System.out.println("Recibido "+this.getName());
        }
        
    }

    @Override
    public void recieve(Message m){
        super.recieve(m);
        //Así todos los elementos de la lista a la que pertenece sabrán cual es el proxy final.
        if(!this.ring.contains(m.getReference().getActorReal())){
            ring.forEach((RingActor r)->r.setEnd(this.proxy));
        }
    }

    
    public Proxy getNextElement() {
        return nextElement;
    }

    public void setNextElement(Proxy nextElement) {
        this.nextElement = nextElement;
        
    }


}
