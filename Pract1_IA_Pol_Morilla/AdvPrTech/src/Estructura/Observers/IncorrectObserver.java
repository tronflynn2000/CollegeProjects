package Estructura.Observers;

public class IncorrectObserver extends ActorListener {
    
    public IncorrectObserver(String name){
        super(name);
        
    }
    public void notify(String note){
        if(note.contains("Incorrect finalization of")){
            this.num++;
        }
    }
    
}
