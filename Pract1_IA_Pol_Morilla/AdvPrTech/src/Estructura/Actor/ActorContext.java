package Estructura.Actor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import Estructura.Proxies.ActorProxy;
import Estructura.Proxies.ClientProxy;
import Estructura.Proxies.Proxy;
import Exceptions.CreationException;

public class ActorContext extends Thread {
    private static ActorContext context = new ActorContext();
    private Map<String,Actor> map;
    private Map<String,Proxy> proxies;    
   

    public Map<String, Proxy> getProxies() {
        return proxies;
    }

    public Map<String, Actor> getMap() {
        return map;
    }

    private ActorContext(){
        map = new HashMap<String,Actor>(); //Deberemos permitir tan solo a Actores, quizás con una interfaz de Actores.
        proxies = new HashMap<String,Proxy>();
    }
    
    public static ActorContext getActorContext(){
        return(context);
    }

    
    /*spawnActor debe devolver una referencia al actor creado, esta puede ser un proxy. Además, debe poner
     * a funcionar con un thread propio al Actor en cuestión.
    */
    public ActorProxy spawnActor(String name , Actor ac) throws CreationException{

        ActorProxy act = null;
        if(!map.containsKey(name)){
        ac.setName(name);
        act = new ActorProxy(name, ac);
        map.put(name, ac); //Primero ponemos el Actor con su ID. al mapa.
        proxies.put(name, act);
        Thread t1 = new Thread(ac);
        ac.setThr(t1);
        //Aqui en un futuro deberemos asegurar que podemos parar el thread con el QuitMessage.
        t1.start();
        
        }
        else if(map.containsKey(name)){
            throw new CreationException();
        }
        return act;
    }  

    /*Este método nos va a permitir crear un ClientProxy para meterlo en el mapa de Proxies.
     * A diferencia del anterior spawnActor, este no tiene un thread asociado, ya que la 
     * comunicación la hacemos desde el Main y solo se procesan mensajes cuando se pide por
     * la pantalla.
    */

    public ClientProxy c_spawnActor(String name , Actor ac) throws CreationException{

        ClientProxy act = null;
        if(!map.containsKey(name)){
        ac.setName(name);
        act = new ClientProxy(name, ac);
        
        map.put(name, ac); //Primero ponemos el Actor con su ID. al mapa.
        proxies.put(name, act);
        
        }
        else if(map.containsKey(name)){
            throw new CreationException();
        }
        return act;
    }
    
    /*Con este método buscamos un valor dentro del Mapa del ActorContext. */
    public Proxy lookUp(String name){
        return(proxies.get(name));
    }

    /*Con el siguiente método lo que haremos será devolver una lista de String con todos los
     * nombres de los actores almacenados. Dado que cada llave solo debe ser única, este
     * conjunto de nombres de Actores no puede tener repeticiones, por lo cual se devoverá en 
     * forma de conjunto.
     */

    public Set<String> getNames(){
        return(map.keySet());
    }
}