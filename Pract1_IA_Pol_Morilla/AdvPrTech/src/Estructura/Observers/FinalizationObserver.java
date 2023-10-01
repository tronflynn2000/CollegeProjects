package Estructura.Observers;

public class FinalizationObserver extends ActorListener {
    
    public FinalizationObserver(String name){
        super(name);
        this.num=0;
    }
    public void notify(String note){
        if(note.contains("Finalization of")){
            this.num++;
        }
    }
    
}
