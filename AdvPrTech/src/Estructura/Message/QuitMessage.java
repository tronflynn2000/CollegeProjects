package Estructura.Message;
import Estructura.Proxies.Proxy;

public class QuitMessage extends Message{
    public QuitMessage(Proxy act){
        super(act,"End of execution");
    }
    
}
