package Estructura.Decorators;
import Estructura.Actor.*;
import Estructura.Message.*;
import Estructura.Proxies.Proxy;


public class EncryptionDecorator extends ActorDecorator {

    //El algoritmo de César lo que hará será aplicar una rotación de n valores, donde n es el entero 
    //definido como atributo de la clase EncryptionDecorator.  
    private int n;
    public EncryptionDecorator(Actor decoratedActor, int num){
        super(decoratedActor);
        this.n = num;
    }


    public void send(String nombre, Message m){
        m.setMessage(encrypt(m.getMessage()));
        super.send(nombre, m);
    }

    public void send(Proxy p, Message m){
        m.setMessage(encrypt(m.getMessage()));
        super.send(p, m);
    }

    /*Cuando el Actor recibe el mensaje este lo va a recibir ya desencriptado tras pasar por su proxy.
     * Creemos que tiene sentido ya que de esta forma el mensaje ya ha llegado a su destino(la cola del
     * actor). Podría ser que requirieramos hacer esto pero en el process, pero sería más difícil por como
     * está montado el software.
     */
    public void recieve(Message m){
        m.setMessage(decrypt(m.getMessage()));
        super.recieve(m);
    }
    /*Este algoritmo sirve para encriptar el texto que contiene un mensaje. El algoritmo de criptografía
     * es de los más sencillos y antiguos, el algoritmo de César. Cuando inicializemos el EncryptionDecorator
     * daremos un valor entero que es el valor de rotación-
     */
    public String encrypt(String str){
        int contador=0;
        char men[] = str.toCharArray();
        for(char ca:men){
            int num = (int)ca;
            num = num+this.n;
            ca = (char)num;
            men[contador]=ca;
            contador++;
        }
        String res = String.valueOf(men);
       return(res);
    }
    /*Este algoritmo sirve para desencriptar el texto del mensaje, se basa en restar el valor n a los
    *caracteres dentro del mensaje.
    */
    public String decrypt(String str){
        int contador=0;
        char men[] = str.toCharArray();
        for(char ca:men){
            int num = (int)ca;
            num = num-this.n;
            ca = (char)num;
            men[contador]=ca;
            contador++;
        }
        String res = String.valueOf(men);
       return(res);
    }    
}
