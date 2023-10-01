package Estructura.Message;
import Estructura.Proxies.Proxy;

/*Un Mensaje tiene el atributo asignado al mensaje en cuestión y otro asociado a la referencia. Esta
 * referencia a un Actor no es más que un remitente(una referenicia al Actor que envía el mensaje).
 */
public class Message {

    protected Proxy reference;

    public void setReference(Proxy reference) {
        this.reference = reference;
    }

    public Proxy getReference() {
        return reference;
    }

    protected String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Message(Proxy act, String message){
        this.reference=act;
        this.message=message;
    }   
}