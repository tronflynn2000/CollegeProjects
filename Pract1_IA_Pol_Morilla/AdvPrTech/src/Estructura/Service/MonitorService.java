package Estructura.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Estructura.Message.*;
import Estructura.Observers.ActorListener;
import Estructura.Actor.*;


/*Esta clase es la que nos permitirá coger los datos de monitoreo de cada actor en particular. Tenemos una
 * lista para los diferentes ActorListener y otra para 
*/
public class MonitorService {
    private List<List<ActorListener>> lista;

    private enum Events{
        CREATED, 
        STOPPED, 
        ERROR
    }
    
    public MonitorService(){
        this.lista = new ArrayList<>();
    }

    public void monitorActor(String name){
        this.lista.add(ActorContext.getActorContext().getMap().get(name).getObservers());
    }

    /*Para evitar elementos repetidos hemos usado esta lambda. */
    public void monitorAllActors(){
        
        List<Actor> actorList = ActorContext.getActorContext().getMap().values().stream().toList();
        actorList.forEach((Actor a)->{
            if(!this.lista.contains(a.getObservers())){
                lista.add(a.getObservers());
            }
        });
    }

    public Map<String, List<String>> getTrafic(){
        Map<String, List<String>> map = new HashMap<>();
        List<String> low = new ArrayList<>();
        List<String> medium = new ArrayList<>();
        List<String> high = new ArrayList<>();

        lista.forEach((List<ActorListener> ac)->{
            String name = ac.get(3).getName();
            if(ac.get(3).getNum()<5){
                low.add(name);        
            }
            else if(ac.get(3).getNum()>5 && ac.get(3).getNum()<15){
                medium.add(name);
            }
            else if(ac.get(3).getNum()>15){
                high.add(name);
            }
        });

        map.put("LOW", low);
        map.put("MEDIUM", medium);
        map.put("HIGH", high);
        return(map);
    }

    public int getNumberofMessages(String name){
        Integer n=0;
        this.lista.forEach((List<ActorListener> act)->{
            ActorListener listener = act.get(3);
            if(listener.getName().equals(name)){
                n.equals(listener.getNum());
            }
        });
        return(n);
    }

    /*Este método lo que hace es proporcionar al usuario con el HashMap que tiene por clave el actor
     * y por valor la lista de mesnsajes enviados por el actor.
     */
    public Map<Actor,List<Message>> getSentMessages(){

        Map<Actor, List<Message>> sentMes = new HashMap<>();
        this.lista.forEach((List<ActorListener> li)->{
            String a =li.get(0).getName();
            Actor actor=ActorContext.getActorContext().getMap().get(a);
            sentMes.put(actor, actor.getMesList());
        });
        return(sentMes);
    }

    public Map<Actor, List<Message>> getRecievedMessages(){
        Map<Actor, List<Message>> recMes = new HashMap<>();
        this.lista.forEach((List<ActorListener> li)->{
            String a=li.get(0).getName();
            Actor actor=ActorContext.getActorContext().getMap().get(a);
            recMes.put(actor, actor.getRecList());
        });
        return(recMes);
    }

    public Map<Events, List<String>>  getEvents(){
        Map<Events, List<String>> events = new HashMap<>();
        List<String> creation = new ArrayList<>();
                List<String> stop = new ArrayList<>();
                List<String> error=new ArrayList<>();
        this.lista.forEach((List<ActorListener> a)->{
            a.forEach((ActorListener listener)->{
                
                if(listener.getName().contains("Creation of")){
                    creation.add(listener.getName());
                }
                else if(listener.getName().contains("Finalization of")){
                    stop.add(listener.getName());
                }
                else if(listener.getName().contains("Incorrect finalization of")){
                    error.add(listener.getName());
                }
            });
        });
        events.put(Events.CREATED, creation);
        events.put(Events.STOPPED, stop);
        events.put(Events.ERROR, error);
        return(events);
    }
}
