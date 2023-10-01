package Estructura.Service;

/*Para pasar como parámetro esta clase, no puede ser una interface, por lo cual hemos hecho estos métodos
 * que realmente no tendrán utilidad, ya que en realidad no se usará así
 */
public class InsultService implements Service {
    public InsultService(){}

    public String getInsult(){
        return(null);
    }

    public String getAllInsult(){
        return(null);
    }

    public void addInsult(String insult){}
}