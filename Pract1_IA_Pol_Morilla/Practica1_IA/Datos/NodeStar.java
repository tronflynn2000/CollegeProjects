package Datos;
import java.util.LinkedHashSet;
import java.util.Set;

public class NodeStar  implements Comparable<NodeStar> {
    private Set<NodeStar> camino;
    private int x;
    private int y;
    private int heuristic;
    private int valor;
    private NodeStar padre;
    private float cost;
    private float costTotal;

    
    public float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(float costTotal) {
        this.costTotal = costTotal;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    /*Este es el constructor del Nodo en caso de no precisar de un coste. */
    public NodeStar(int x, int y, int valor){
        this.x=x;
        this.y=y;
        this.valor = valor;
        this.heuristic=0;
        this.padre=null;
        this.camino=new LinkedHashSet<>();
        
    }

    public NodeStar getPadre() {
        return padre;
    }


    public void setPadre(NodeStar padre) {
        this.padre = padre;
    }
    
    public Set<NodeStar> getCamino() {
        return camino;
    }

    public void setCamino(Set<NodeStar> camino) {
        this.camino = camino;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heur) {
        this.heuristic = heur;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public int compareTo(NodeStar o) {
        if(this.heuristic+this.valor<o.heuristic+o.valor){
            return -1;
        }   
        else if(this.heuristic+this.valor>o.heuristic+o.valor){
            return 1;
        }
        else{
            return 0;
        }
    }

    @Override
    public boolean equals(Object a){
        boolean igual=false;
        if(a.toString().equals(this.toString())){
            igual=true;
        }
        return(igual);
    }
    
    public String toString(){
        return("("+x+","+y+")");    
    }
    
}
