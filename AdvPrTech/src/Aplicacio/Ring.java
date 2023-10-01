package Aplicacio;

import Exceptions.CreationException;

import Estructura.Actor.*;
import Estructura.Message.Message;
import Estructura.Proxies.ActorProxy;
import Estructura.Proxies.Proxy;

import java.util.UUID;
import java.util.LinkedList;
import java.util.List;

/*Esta clase es una prueba que usa un RingActor para crear un anillo con diferentes actores que forman un círculo. */
public class Ring {
    public static void main(String[] args) throws CreationException{
        List<RingActor> pepito = new LinkedList<>();
        
        for(int cont=0;cont<100;cont++){
            //Nos crea una String random de 32 bits.
            String e = UUID.randomUUID().toString();
            ActorProxy a=ActorContext.getActorContext().spawnActor(e, new RingActor(pepito));
            ActorContext.getActorContext().getMap().get(e).setProxy(a);
            pepito.add((RingActor)a.getActorReal());
        }
        pepito.forEach((RingActor r)->{
            //Con esto conseguimos que cada uno apunte al Proxy siguiente.
            int num = pepito.indexOf(r);
            if(num==99){
                r.setNextElement(pepito.get(0).getProxy());
            }
            else{
            r.setNextElement(pepito.get(num+1).getProxy());
            }
        });
        Proxy hola =ActorContext.getActorContext().spawnActor("Hola", new ActorHelloWorld());
            hola.getActorReal().send(ActorContext.getActorContext().getProxies().get(pepito.get(0).getName()), new Message(hola, "Holaa"));
            hola.getActorReal().send(ActorContext.getActorContext().getProxies().get(pepito.get(0).getName()), new Message(hola, "Holaa"));
    }
    
}
