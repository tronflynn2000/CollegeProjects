package Estructura.Message;
import Estructura.Proxies.Proxy;

public class GetInsultMessage extends Message {

    public GetInsultMessage(Proxy act) {
        super(act, "Get a random insult.");
    }
    
}
