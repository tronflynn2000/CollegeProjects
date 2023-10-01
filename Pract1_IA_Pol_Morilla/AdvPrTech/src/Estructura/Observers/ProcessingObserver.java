package Estructura.Observers;

public class ProcessingObserver extends ActorListener {
    public ProcessingObserver(String name){
        super(name);
        
    }
    public void notify(String note){
        if(note.contains("Processing of")){
            this.num++;
        }
    }
    
}
