package Estructura.Observers;

public class CreationObserver extends ActorListener {

    public CreationObserver(String name){
        super(name);
        
    }
    public void notify(String note){
        if(note.contains("Creation of")){
            this.num++;
        }
    }
    
}
