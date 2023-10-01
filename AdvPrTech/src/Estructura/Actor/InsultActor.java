package Estructura.Actor;
import Estructura.Message.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*Este actor proporciona diferentes métodos que permiten añadir insultos a una lista, obtener un insulto aleatorio
 * o también ver todos los insultos que tiene el actor en su lista de insultos.
 */
public class InsultActor extends Actor {
    private List<String> insults;
    public List<String> getInsults() {
      return insults;
    }

    public void setInsults(List<String> insults) {
      this.insults = insults;
    }

    private Random random;

    public InsultActor(){
        insults = new ArrayList<>();
        insults.add("Tonto");
        insults.add("Burro");
        insults.add("Imbecil");
        insults.add("Mameluco");
        insults.add("Membrillo");
        random = new Random();
    }

    @Override
    public void processMessage() {
        Message m = list.peek();
        if(m instanceof GetInsultMessage){
            getInsult();
        }
        else if(m instanceof AddInsultMessage){
            addInsult(m.getMessage());
        }
        else if(m instanceof GetAllInsultsMessage){
            getAllInsults();   
        }
        
    }

    public String getRandomInsult() {
        if (insults.isEmpty()) {
          return "No insults available";
        }
        int index = random.nextInt(insults.size());
        return insults.get(index);
      }

      public void getInsult(){
        Message m = list.poll();
        Message mes = new Message(this.proxy, getRandomInsult());
        send(m.getReference(),mes);
      }

      public void addInsult(String insult){
        insults.add(insult);
        list.remove();
      }

      public void getAllInsults(){
        Message m = list.poll();
        Message mes = new Message(this.proxy, insults.toString());
        send(m.getReference(), mes); 
      }
      
}