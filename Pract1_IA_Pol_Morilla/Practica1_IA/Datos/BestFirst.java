package Datos;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BestFirst{
    

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
        Node inicial = new Node(0, 0, matrix[0][0]);
        Node fin = new Node(9,9, matrix[9][9]);
        long start = System.currentTimeMillis();
        String a= bestFirst(matrix,inicial,fin,10,10);
        long end = System.currentTimeMillis();
        //Camino de la solución.
        System.out.println(a);
        //Tiempo en ejecutar el algoritmo.
        System.out.println((end - start)+" ms");
        }

        public static String bestFirst(int[][] matriz, Node inicial, Node fin, int maxx, int maxy){
        
        List<Node> pends = new LinkedList<>();
        Map<String,Node> tract = new HashMap<>();
        Node actual;
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
                    Node derecha = new Node(actual.getX()+1, actual.getY(), matriz[actual.getX()+1][actual.getY()]);
                    derecha.setPadre(actual);
                    Set<Node> cam = new LinkedHashSet<>();
                    cam.addAll(actual.getCamino());
                    cam.add(actual);
                    derecha.setCamino(cam);
                    derecha.setHeuristic(heuristicaC(derecha, fin));
                    if(!tract.containsKey(derecha.toString()) && !pends.contains(derecha)){
                        pends.add(derecha);
                        Collections.sort(pends);
                    }
                    
                }
                if(actual.getX()-1>=0 && matriz[actual.getX()-1][actual.getY()]!=-1){
                    Node izquierda = new Node(actual.getX()-1, actual.getY(), matriz[actual.getX()-1][actual.getY()]);
                    izquierda.setPadre(actual);
                    Set<Node> cam = new LinkedHashSet<>();
                    cam.addAll(actual.getCamino());
                    cam.add(actual);
                    izquierda.setCamino(cam);
                    izquierda.setHeuristic(heuristicaC(izquierda, fin));
                    if(!tract.containsKey(izquierda.toString()) && !pends.contains(izquierda)){
                        pends.add(izquierda);
                        Collections.sort(pends);
                    }

                }
                if(actual.getY()+1<maxy && matriz[actual.getX()][actual.getY()+1]!=-1){
                    Node arriba = new Node(actual.getX(), actual.getY()+1, matriz[actual.getX()][actual.getY()+1]);
                    arriba.setPadre(actual);
                    Set<Node> cam = new LinkedHashSet<>();
                    cam.addAll(actual.getCamino());
                    cam.add(actual);
                    arriba.setCamino(cam);
                    arriba.setHeuristic(heuristicaC(arriba, fin));
                    if(!tract.containsKey(arriba.toString()) && !pends.contains(arriba)){
                        pends.add(arriba);
                        Collections.sort(pends);

                    }

                }
                if(actual.getY()-1>=0  && matriz[actual.getX()][actual.getY()-1]!=-1){
                    Node abajo = new Node(actual.getX(), actual.getY()-1, matriz[actual.getX()][actual.getY()-1]);
                    abajo.setPadre(actual);
                    Set<Node> cam = new LinkedHashSet<>();
                    cam.addAll(actual.getCamino());
                    cam.add(actual);
                    abajo.setCamino(cam);
                    abajo.setHeuristic(heuristicaC(abajo, fin));
                    if(!tract.containsKey(abajo.toString()) && !pends.contains(abajo)){
                        pends.add(abajo);
                        Collections.sort(pends);
                    }
                }
        }

        tract.put(actual.toString(), actual);            
        }
        System.out.println("Num. iteracions: "+numiteracions);
        return(solucio);
    }

    /*Esta heurística toma en cuenta la distancia de Manhattan. */
    public static int heuristicaA(Node a, Node b){
    int ret=Math.abs(a.getX()-b.getX()) + Math.abs(a.getY()-b.getY());
    return(ret);
}

/*Esta heuristica calcula el valor mirando la diferencia entre las alturas(sin valor absoluto). */
public static int heuristicaB(Node a, Node b){
    int ret = a.getValor()-b.getValor();
    return(ret);
}

/*Esta heuristica calcula el valor mirando la distancia euclidiana entre el punto al que podemos acceder y el punto destino. */
public static int heuristicaC(Node a, Node b){
    int ret = (int) Math.sqrt(Math.pow(b.getX()-a.getX(), 2)+Math.pow(b.getY()-a.getY(), 2));
    return(ret);
}
}
