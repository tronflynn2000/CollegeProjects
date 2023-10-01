package Datos;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class AStar {

    //private static int matrix[][] = {{1,0,-1,1,3,2,3,4,3,1},{2,1,-1,2,4,2,2,4,2,2},{5,3,-1,2,3,2,-1,3,3,3},{3,3,1,3,4,3,-1,1,2,2},{2,2,2,3,6,4,-1,1,2 ,1},{-1,-1,-1,-1,3,3,-1,0,2,-1},{-1,-1,-1,-1,2,4,-1,2,2,-1},{2,3,4,3,1,3,-1,3,2,-1},{3,5,6,5,2,3,-1,5,3,-1},{5,6,7,6,4,4,-1,6,4,5}};

    public static void main(String args[]){
        String filePath = "Datos/Matriz2.csv";
        int[][] matrix = new int[10][10];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int[] valuesnum = new int[10]; 
                for (int col = 0; col < values.length; col++) {
                    valuesnum[col] = Integer.parseInt(values[col]);
                    matrix[row][col] = valuesnum[col];
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Para cambiar el inicio y el destino del algoritmo podemos cambiar los valores de los nodos.
        NodeStar inicial = new NodeStar(0, 0, matrix[0][0]);
        NodeStar fin = new NodeStar(9,9, matrix[9][9]);
        long start = System.currentTimeMillis();
        String a= aStar(matrix,inicial,fin,10,10);
        long end = System.currentTimeMillis();
        //Camino solución.
        System.out.println(a);
        //Tiempo en ejecutar el algoritmo.
        System.out.println((end - start)+" ms");
    }
    public static String aStar(int[][] matriz, NodeStar inicial, NodeStar fin, int maxx, int maxy){
        //La lista de pendientes deberá ser una lista de prioridades, mientras que la 
    //estructura de tratados será una tabla de hashing.

    List<NodeStar> pends = new LinkedList<>();
    Map<String,NodeStar> tract = new HashMap<>();
    NodeStar actual;
    String solucio="No s'ha trobat el cami";
    pends.add(inicial);
    boolean trobat = false;
    int numiteracions=0;

    while(!trobat && !pends.isEmpty()){
        numiteracions++;
        actual=pends.get(0);
        pends.remove(0);
        if(actual.getX()==fin.getX() && actual.getY()==fin.getY()){
            trobat=true;
            solucio = actual.getCamino().toString();
        }
        else{

            if((actual.getX()+1)<maxx && matriz[actual.getX()+1][actual.getY()]!=-1){
                NodeStar derecha = new NodeStar(actual.getX()+1, actual.getY(), matriz[actual.getX()+1][actual.getY()]);
                derecha.setPadre(actual);
                Set<NodeStar> cam = new LinkedHashSet<>();
                cam.addAll(actual.getCamino());
                cam.add(actual);
                derecha.setCamino(cam);
                derecha.setHeuristic(heuristicaC(derecha, fin));
                derecha.setCost(calcularCost(derecha, actual));
                if(pends.contains(derecha) && pends.get(pends.indexOf(derecha)).getCost()>derecha.getCost()){
                    pends.get(pends.indexOf(derecha)).setCamino(derecha.getCamino());
                    pends.get(pends.indexOf(derecha)).setCost(derecha.getCost());
                    pends.get(pends.indexOf(derecha)).setPadre(derecha.getPadre());
                    pends.get(pends.indexOf(derecha)).setCostTotal(derecha.getCost()+derecha.getHeuristic());

                }

                if(tract.containsKey(derecha.toString()) && tract.get(derecha.toString()).getCost()>derecha.getCost()){
                    tract.get(derecha.toString()).setCamino(derecha.getCamino());
                    tract.get(derecha.toString()).setCost(derecha.getCost());
                    tract.get(derecha.toString()).setPadre(derecha.getPadre());
                    tract.get(derecha.toString()).setCostTotal(derecha.getCost()+derecha.getHeuristic());

                }

                if(!tract.containsKey(derecha.toString()) && !pends.contains(derecha)){
                    pends.add(derecha);
                    Collections.sort(pends);
                }
                
            }
            if(actual.getX()-1>=0 && matriz[actual.getX()-1][actual.getY()]!=-1){
                NodeStar izquierda = new NodeStar(actual.getX()-1, actual.getY(), matriz[actual.getX()-1][actual.getY()]);
                izquierda.setPadre(actual);
                Set<NodeStar> cam = new LinkedHashSet<>();
                cam.addAll(actual.getCamino());
                cam.add(actual);
                izquierda.setCamino(cam);
                izquierda.setHeuristic(heuristicaC(izquierda, fin));
                izquierda.setCost(calcularCost(izquierda, actual));
                if(pends.contains(izquierda) && pends.get(pends.indexOf(izquierda)).getCost()>izquierda.getCost()){
                    pends.get(pends.indexOf(izquierda)).setCamino(izquierda.getCamino());
                    pends.get(pends.indexOf(izquierda)).setCost(izquierda.getCost());
                    pends.get(pends.indexOf(izquierda)).setPadre(izquierda.getPadre());
                    pends.get(pends.indexOf(izquierda)).setCostTotal(izquierda.getCost()+izquierda.getHeuristic());

                }

                if(tract.containsKey(izquierda.toString()) && tract.get(izquierda.toString()).getCost()>izquierda.getCost()){
                    tract.get(izquierda.toString()).setCamino(izquierda.getCamino());
                    tract.get(izquierda.toString()).setCost(izquierda.getCost());
                    tract.get(izquierda.toString()).setPadre(izquierda.getPadre());
                    tract.get(izquierda.toString()).setCostTotal(izquierda.getCost()+izquierda.getHeuristic());

                }

                if(!tract.containsKey(izquierda.toString()) && !pends.contains(izquierda)){
                    pends.add(izquierda);
                    Collections.sort(pends);
                }           
                }

            if(actual.getY()+1<maxy && matriz[actual.getX()][actual.getY()+1]!=-1){
                NodeStar arriba = new NodeStar(actual.getX(), actual.getY()+1, matriz[actual.getX()][actual.getY()+1]);
                arriba.setPadre(actual);
                Set<NodeStar> cam = new LinkedHashSet<>();
                cam.addAll(actual.getCamino());
                cam.add(actual);
                arriba.setCamino(cam);
                arriba.setHeuristic(heuristicaC(arriba, fin));
                arriba.setCost(calcularCost(actual,arriba));
                if(pends.contains(arriba) && pends.get(pends.indexOf(arriba)).getCost()>arriba.getCost()){
                    pends.get(pends.indexOf(arriba)).setCamino(arriba.getCamino());
                    pends.get(pends.indexOf(arriba)).setCost(arriba.getCost());
                    pends.get(pends.indexOf(arriba)).setPadre(arriba.getPadre());
                    pends.get(pends.indexOf(arriba)).setCostTotal(arriba.getCost()+arriba.getHeuristic());

                }

                if(tract.containsKey(arriba.toString()) && tract.get(arriba.toString()).getCost()>arriba.getCost()){
                    tract.get(arriba.toString()).setCamino(arriba.getCamino());
                    tract.get(arriba.toString()).setCost(arriba.getCost());
                    tract.get(arriba.toString()).setPadre(arriba.getPadre());
                    tract.get(arriba.toString()).setCostTotal(arriba.getCost()+arriba.getHeuristic());

                }

                if(!tract.containsKey(arriba.toString()) && !pends.contains(arriba)){
                    pends.add(arriba);
                    Collections.sort(pends);
                }
                

            }
            if(actual.getY()-1>=0  && matriz[actual.getX()][actual.getY()-1]!=-1){
                NodeStar abajo = new NodeStar(actual.getX(), actual.getY()-1, matriz[actual.getX()][actual.getY()-1]);
                abajo.setPadre(actual);
                Set<NodeStar> cam = new LinkedHashSet<>();
                cam.addAll(actual.getCamino());
                cam.add(actual);
                abajo.setCamino(cam);
                abajo.setHeuristic(heuristicaC(abajo, fin));
                abajo.setCost(calcularCost(actual,abajo));
                if(pends.contains(abajo) && pends.get(pends.indexOf(abajo)).getCost()>abajo.getCost()){
                    pends.get(pends.indexOf(abajo)).setCamino(abajo.getCamino());
                    pends.get(pends.indexOf(abajo)).setCost(abajo.getCost());
                    pends.get(pends.indexOf(abajo)).setPadre(abajo.getPadre());
                    pends.get(pends.indexOf(abajo)).setCostTotal(abajo.getCost()+abajo.getHeuristic());

                }

                if(tract.containsKey(abajo.toString()) && tract.get(abajo.toString()).getCost()>abajo.getCost()){
                    tract.get(abajo.toString()).setCamino(abajo.getCamino());
                    tract.get(abajo.toString()).setCost(abajo.getCost());
                    tract.get(abajo.toString()).setPadre(abajo.getPadre());
                    tract.get(abajo.toString()).setCostTotal(abajo.getCost()+abajo.getHeuristic());

                }

                if(!tract.containsKey(abajo.toString()) && !pends.contains(abajo)){
                    pends.add(abajo);
                    Collections.sort(pends);
                }

                
    }

    tract.put(actual.toString(), actual);            
    }
    
}
System.out.println("Num. iteracions: "+numiteracions);
return(solucio);
}
/*Esta heurística toma en cuenta la distancia de Manhattan. */
public static int heuristicaA(NodeStar a, NodeStar b){
    int ret=Math.abs(a.getX()-b.getX()) + Math.abs(a.getY()-b.getY());
    return(ret);
}
/*Esta heuristica calcula el valor mirando la diferencia entre las alturas(sin valor absoluto). */
public static int heuristicaB(NodeStar a, NodeStar b){
    int ret = a.getValor()-b.getValor();
    return(ret);
}

/*Esta heuristica calcula el valor mirando la distancia euclidiana entre el punto al que podemos acceder y el punto destino. */
public static int heuristicaC(NodeStar a, NodeStar b){
    int ret = (int) Math.sqrt(Math.pow(b.getX()-a.getX(), 2)+Math.pow(b.getY()-a.getY(), 2));
    return(ret);
}

/*Esta función se dedica al cálculo del coste de moverse de un nodo 'a' a un nodo 'b' */
public static float calcularCost(NodeStar a, NodeStar b){
    float cost=0;
    if(a.getValor()-b.getValor()<0){
        cost = 1/2;
    }
    else{
        cost = 1+(a.getValor()-b.getValor());
    }
    return(cost);
}
}



