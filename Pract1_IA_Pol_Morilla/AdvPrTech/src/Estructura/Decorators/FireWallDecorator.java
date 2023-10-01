package Estructura.Decorators;
import Estructura.Message.*;
import Estructura.Actor.*;

public class FireWallDecorator extends ActorDecorator{

    public FireWallDecorator(Actor actor){
        super(actor);
    }

    @Override
    public void processMessage(){
        Message m = this.list.poll();
        //Originalmente en mirabamos si el actor del decorator es valido, pero lo que nos interesa
        //realmente es mirar si el remitente del mensaje es valido o no.
        if(ActorContext.getActorContext().lookUp(m.getReference().getActorReal().getName())!=null){
            super.processMessage();
        }
        else{

             //No entiendo por qué no se puede acceder al atributo list.
            //Enviando este QuitMessage, aseguramos que este no pueda seguir enviando peticiones.
            //el problema es que si este actor no está registrado en el ActorContext no tiene por
            //qué tener un Proxy asociado. Lo mejor para evitar código extra erróneo es esperar a la
            //respuesta del profesor.
            actor.send(m.getReference(), new QuitMessage(actor.getProxy()));
        }   
    }

    
}
