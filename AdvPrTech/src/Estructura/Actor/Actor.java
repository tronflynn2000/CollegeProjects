package Estructura.Actor;

/*En esta interfaz deberemos buscar las acciones compartidas entre los actores y proporcionar una interfaz,
 *de momento es una interfaz, pero si fuera necesario la cambiaremos a una clase abstracta.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import Estructura.Message.*;
import Estructura.Observers.ActorListener;
import Estructura.Observers.CreationObserver;
import Estructura.Observers.FinalizationObserver;
import Estructura.Observers.IncorrectObserver;
import Estructura.Observers.ProcessingObserver;
import Estructura.Observers.RecievedObserver;
import Estructura.Proxies.ClientProxy;
import Estructura.Proxies.Proxy;


/*Este clase conforma el Actor, que es la unidad básica para el paso de mensajes y el procesamiento de los mismos. */
public abstract class Actor implements Runnable {
    protected Queue<Message> list;
    private String name;
    protected Proxy proxy;
    Thread thr;
    private List<ActorListener> observers; //Hace falta saber si esto es estático o para cada instancia.
    private List<Message> mesList; //Lista de mesnajes enviados por el actor.
    private List<Message> recList;
    private List<String> events;
    

    public Queue<Message> getList() {
        return list;
    }

    public void setThr(Thread thr) {
        this.thr = thr;
    }

    public Thread getThr() {
        return thr;
    }

    public List<String> getEvents() {
        return events;
    }
    public List<Message> getRecList() {
        return recList;
    }
    public List<Message> getMesList() {
        return mesList;
    }
    public List<ActorListener> getObservers() {
        return observers;
    }
    public Proxy getProxy() {
        return proxy;
    }
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Actor(){
        list = new ArrayDeque<Message>();
        proxy = ActorContext.getActorContext().getProxies().get(name);
        mesList=new ArrayList<>();
        recList = new ArrayList<>();
        observers = new ArrayList<>();
        events = new ArrayList<>();
        observers.add(new CreationObserver(this.name));
        observers.add(new FinalizationObserver(this.name));
        observers.add(new IncorrectObserver(this.name));
        observers.add(new RecievedObserver(this.name));
        observers.add(new ProcessingObserver(this.name));
        observers.forEach((ActorListener e)->e.notify("Creation of"+name));
        events.add("Creation of"+name);
        thr=null;
    }
    
    /*El método send() sirve para poner un mensaje en la cola de mensajes del actor. */
    public void send(String nombre, Message m){
        Proxy prox =  ActorContext.getActorContext().lookUp(nombre);
        m.setReference(ActorContext.getActorContext().lookUp(this.name));
        prox.send(m);
        //Te habías olvidado de añadir este mensaje a mensajes enviados.
        this.mesList.add(m);
    }

    public void send(Proxy prox, Message m){
        m.setReference(ActorContext.getActorContext().lookUp(name));
        prox.send(m);
        this.mesList.add(m);

    }

    public void recieve(Message m){
        this.list.add(m);
        recList.add(m);
        observers.forEach((ActorListener a)->a.notify(this.name+ "recieved "+m.getMessage()));
        events.add(this.name+ "received "+m.getMessage());
        if(m.getReference() instanceof ClientProxy) processMessage();
    }

    public  void processMessage(){
        observers.forEach((ActorListener e)->e.notify("Processing of"+name));

    }

    @Override
    public void run(){
        while(!(list.poll() instanceof QuitMessage)){
            if(list.isEmpty()){}
            else{
            this.processMessage();
            }
           
            
        }

        observers.forEach((ActorListener a)->a.notify("Finalization of"+this.name));
        events.add("Finalization of"+this.name);       
    }
}