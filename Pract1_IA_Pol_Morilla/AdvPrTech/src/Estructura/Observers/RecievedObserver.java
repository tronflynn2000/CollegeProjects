package Estructura.Observers;

public class RecievedObserver extends ActorListener {
    
    public RecievedObserver(String name){
        super(name);
    }

    public void notify(String note){
        if(note.contains("recieved")){
            this.num++;
        }
    }    
}