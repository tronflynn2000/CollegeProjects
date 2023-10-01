package Estructura.Message;
import Estructura.Proxies.ActorProxy;

public class AddInsultMessage extends Message {

    public AddInsultMessage(ActorProxy act, String message){
        super(act, message);
    }
    
}
