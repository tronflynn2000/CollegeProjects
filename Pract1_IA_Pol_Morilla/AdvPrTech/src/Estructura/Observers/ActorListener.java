package Estructura.Observers;

/*Esta clase es la clase padre que conforma el resto de Observadores con roles específicos. */
public abstract class ActorListener {
    private String name;
    protected int num;
    
    
    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public ActorListener(String name){
        this.name = name;
        this.num=0;
    }

    public abstract void notify(String note);
    
}
