package Datos;

import java.util.LinkedHashSet;
import java.util.Set;

public class Node implements Comparable<Node>{
    
    private Set<Node> camino;
    private int x;
    private int y;
    private int heuristic;
    private int valor;
    private Node padre;

    
    /*Este es el constructor del Nodo en caso de no precisar de un coste. */
    public Node(int x, int y, int valor){
        this.x=x;
        this.y=y;
        this.valor = valor;
        this.heuristic=0;
        this.padre=null;
        this.camino=new LinkedHashSet<>();
        
    }

    public Node getPadre() {
        return padre;
    }


    public void setPadre(Node padre) {
        this.padre = padre;
    }
    
    public Set<Node> getCamino() {
        return camino;
    }

    public void setCamino(Set<Node> camino) {
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
    public int compareTo(Node o) {
        if(this.heuristic<o.heuristic){
            return -1;
        }   
        else if(this.heuristic>o.heuristic){
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