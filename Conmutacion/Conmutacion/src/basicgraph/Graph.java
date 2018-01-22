package basicgraph;

import static basicgraph.Graph.Type.DIRECTED;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;


/** Clase que implementa grafos (dirigidos y no dirigidos).
 * The graph may have self-loops, parallel edges. 
 * Vertices son etiquetados por enteros 0 .. n-1
 * and may also have String labels.
 * Los arcos del grafo son objetos que pueden contener pesos e
 * información adicional además del vertice destino.
 * 
 */

public abstract class Graph{
    private int numVertices;
    private int numEdges;
    private final Graph.Type type;
    
    private final Map<Vertex,List<Edge>> adjList;
    
    private final Map<Vertex,Integer> vertexIndices;
    private final Map<Integer, Vertex> indicesVertices;
	
    /**
     * Crea un nuevo grafo
     * @param type tipo de grafo
     */
    public Graph(Type type) {
        this.adjList = new HashMap<>();
        this.vertexIndices = new HashMap<>();
        this.indicesVertices = new HashMap<>();
        this.type = type;
    }
	
    /**
     * Obtiene el numero de vertices
     * @return Numero de vetices en el grafo.
     */
    public int getNumVertices() {
        return numVertices;
    }
    
    /**
     * Obtiene el numero de arcos
     * @return Numero de arcos del grafo.
     */	
    public int getNumEdges() {
        return numEdges;
    }
    
    
	
    /**
     * Agrega un nuevo vertice al grafo
     * @param v nuevo vertice 
     */
    public void addVertex(Vertex v) {
        List<Edge> neighbors = new ArrayList<>();
	
        this.adjList.put(v,  neighbors);
        
	this.vertexIndices.put(v, numVertices);
        this.indicesVertices.put(numVertices, v);
        this.numVertices ++;
    }
    
    /***
     * Agrega un nuevo arco entre 2 vertices. Si el grafo es no dirigido, genera el arco de
     * destino a origen.
     * @param source Vertice origen.
     * @param destination Vertice destino.
     * @param weight peso asignado al grafo.
     * @param info información adicional del arco.
     */
    public void addEdge(Vertex source, Vertex destination, Object weight, Object info) {
        numEdges ++;
        
        if ( adjList.get(source) != null && adjList.get(destination) != null) {
            adjList.get(source).add(new Edge(source, destination, weight, info));
            
            if(this.type == Graph.Type.UNDIRECTED)
                adjList.get(destination).add(new Edge(destination, source, weight, info));
	}
	else {
            throw new NullPointerException("Uno o ambos vértices no pertenecen al grafo.");
	}
    }
    
    /**
     * Agrega un nuevo arco al grafo entre los vertices dados.
     * @param source Vertice origen
     * @param destination Vertice destino
     */
    public void addEdge(Vertex source, Vertex destination) {
        addEdge(source, destination, null, null);
    }
    
    /***
     * Agrega un nuevo arco al grafo entre los vertices dados.
     * @param source Vertice origen.
     * @param destination Vertice destino.
     * @param weight Peso asignado al arco.
     */
    public void addEdge(Vertex source, Vertex destination, int weight) {
        addEdge(source, destination, weight, null);
    }
    
    /***
     * Agrega un nuevo arco al grafo entre los vertices dados.
     * @param source Vertice origen.
     * @param destination Vertice destino.
     * @param weight Peso asignado al arco.
     */
    public void addEdge(Vertex source, Vertex destination, float weight) {
        addEdge(source, destination, weight, null);
    }
    
    /***
     * Agrega un nuevo arco entre 2 vertices. Si el grafo es no dirigido, genera el arco de
     * destino a origen.
     * @param v Vertice origen.
     * @param w Vertice destino.
     * @param weight peso asignado al grafo.
     * @param info información adicional del arco.
     */
    public void addEdge(int v, int w, Object weight, Object info) {
        Vertex source, destination;
        
        if (v >= 0 && v < numVertices && w >= 0 && w < numVertices) {
            source = indicesVertices.get(v);
            destination = indicesVertices.get(w);
            addEdge(source, destination, weight, info);
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }
    
    /**
     * Obtiene todos los vecinos (a los que se llega) de un vertice dato.
     * @param vertex Vertice del cual se desea conoconer los vecinos.
     * @return Listado de todos los vertices que son adyacentes a v.
     */
    public List<Vertex> getNeighbors(Vertex vertex){
        List<Edge> arcos;
        List<Vertex> vecinos = new ArrayList<>();
        Vertex v;
        arcos = this.adjList.get(vertex);
        for(int i = 0; i < arcos.size(); i++){
            v = arcos.get(i).getDestination();
            vecinos.add(v);
        }
        return vecinos;
    }
    
    /**
     * Obtiene todos los vecinos (a los que se llega) de un vertice dato.
     * @param v Vertice del cual se desea conoconer los vecinos.
     * @return Listado de todos los vertices que son adyacentes a v.
     */
    public List<Vertex> getNeighbors(int v){
        Vertex vertex;
        vertex = indicesVertices.get(v);
        return getNeighbors(vertex);
    }
	
    /**
     * Obtiene los vertices que tienen arcos hacia un vertice dado.
     * @param v Vertice para el cual se desea conocer vertices que tienen arcos hacia él.
     * @return Listado de todos los vertices que son adyacentes a v.
     * 	via incoming edges to v. 
     */
    public List<Integer> getInNeighbors(Vertex v){
        List<Vertex> vecinos = getNeighbors(v);
        List<Integer> vecinosInteger = new ArrayList<>();
        Vertex vertex;
        for(int i = 0; i < vecinos.size() ; i++ ){
            vertex = vecinos.get(i);
            vecinosInteger.add(this.vertexIndices.get(vertex));
        }
        return vecinosInteger;
    }
    
    /***
     * Recorrido en profundidad.
     * @param v Vertice a partir del cual se realiza el recorrido.
     * @return Listado de todos los vertices visitados en el recorrido.
     */
    public List<Vertex> depthFirstSearch(Vertex v){
        List<Vertex> visitados = new ArrayList<>();
        Stack<Vertex> pilaV = new Stack<>();
        pilaV.push(v);
        while(!pilaV.isEmpty()){
            Vertex vertex = pilaV.pop();
            if(!visitados.contains(vertex)){
                visitados.add(vertex);
                vertex.setVisited(true);
                List<Vertex> vecinos = getNeighbors(vertex);
                pilaV.addAll(vecinos);
            }
        }
        return visitados;
        // setUnvisited();
    }
    
    /***
     * Recorrido en achura.
     * @param v Vertice a partir del cual se realiza el recorrido.
     * @return Listado de todos los vertices visitados en el recorrido.
     */
    public List<Vertex> breadthFirstSearch(Vertex v){
        List<Vertex> visitados = new ArrayList<>();
        Queue<Vertex> colaV = new LinkedList<>();
        colaV.add(v);
        while(!colaV.isEmpty()){
            Vertex vertex = colaV.poll();
            if(!visitados.contains(vertex)){
                visitados.add(vertex);
                vertex.setVisited(true);
                List<Vertex> vecinos = getNeighbors(vertex);
                colaV.addAll(vecinos);
            }
        }
        return visitados;
        // setUnvisited();        
    }
    
    /***
     * Establece todos los vertices del grafo como no visitados.
     * Por ejemplo, luego de recorrer un grafo es necesario dejar los vertices
     * como no visitados.
     */
    public void setUnvisited(){
        for(int i = 0 ; i < this.numVertices ; i++){
            if (this.getVertex(i) != null){
                this.getVertex(i).setVisited(false);
            }
        }
    }
    
    /***
     * Obtiene las componentes conexas de un grafo.
     * @return Componentes conexas del grafo.
     */
    public Map<Integer, List<Vertex>> getComponents(){
        Map<Integer, List<Vertex>> componentesConexos = new HashMap<>();
        if(this.type.equals(DIRECTED)){
            componentesConexos = getComponentsDirectedGraph();
        }
        else{
            componentesConexos = getComponentsUndirectedGraph();
        }
        return componentesConexos;
    }
    
    /***
     * Obtiene las componentes conexas de un grafo dirigido.
     * @return Componentes conexas del grafo.
     */
    private Map<Integer, List<Vertex>> getComponentsUndirectedGraph(){
        //private final Map<Vertex,Integer> vertexIndices;
        Map<Integer, List<Vertex>> componentesConexos = new HashMap<>();
        Queue<Vertex> vertices = new LinkedList<>();
        int j = 0;
        for(int i = 0 ; i < this.numVertices ; i++){
            if (this.getVertex(i) != null){
                vertices.add(this.getVertex(i));
            }
        }
        while(!vertices.isEmpty()){
            List<Vertex> comp = depthFirstSearch(vertices.peek());
            vertices.removeAll(comp);
            componentesConexos.put(j, comp);
            j++;
        }
        return componentesConexos;
    }
    
    /***
     * Obtiene las componentes conexas de un grafo dirigido.
     * @return Componentes conexas del grafo.
     */
    private Map<Integer, List<Vertex>> getComponentsDirectedGraph(){
        Map<Integer, List<Vertex>> componentesConexos = new HashMap<>();
        Queue<Vertex> vertices = new LinkedList<>();
        int j = 0;
        for(int i = 0 ; i < this.numVertices ; i++){
            if (this.getVertex(i) != null){
                vertices.add(this.getVertex(i));
            }
        }
        while(!vertices.isEmpty()){
            List<Vertex> comp = depthFirstSearch(vertices.peek());
            vertices.removeAll(comp);
            componentesConexos.put(j, comp);
            j++;
        }
        return componentesConexos;
    }
	
    /** 
     * The degree sequence of a graph is a sorted (organized in numerical order 
     * from largest to smallest, possibly with repetitions) list of the degrees 
     * of the vertices in the graph.
     * 
     * @return The degree sequence of this graph.
     */
    public List<Vertex> degreeSequence() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }
    
    /**
     * Get all the vertices that are 2 away from the vertex in question.
     * @param vertex
     * @return A list of the vertices that can be reached in exactly two hops (by 
     * following two edges) from vertex v.
     * XXX: Implement in part 2 of week 1 for each subclass of Graph
     */
    public List<Vertex> getDistance2(Vertex vertex){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** Return a String representation of the graph
     * @return A string representation of the graph
     */
    @Override
    public String toString() {
        String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
        s += "Degree sequence: " + degreeSequence() + ".\n";
	//if (numVertices <= 20) 
        s += adjacencyToString();
	return s;
    }
	
    /**
     * Test whether some vertex in the graph is labeled 
     * with a given index.
     * @param v The indrue if there's a vertex in the graph with this index; false otherwise.
     * @return 
     */
    public boolean hasVertex(Vertex v){
        return adjList.get(v) != null;
    }
    
    public boolean hasVertex(int v){
        return vertexIndices.containsValue(v);
    }
    
    public boolean hasVertex(Object vertexContent){
        for(Vertex v : vertexIndices.keySet()){
            if(v.getContent().equals(vertexContent)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Report index of vertex with given label.
     * (Assume distinct labels for vertices.)
     * @param content
     * @return The integer index of this vertex 
     */
    public Vertex getVertex(Object content) {
        for (Map.Entry<Vertex,List<Edge>> entry : adjList.entrySet()) {
            if (entry.getKey().getContent().equals(content))
                return entry.getKey();
        }
	return null;
    }
    
    public Vertex getVertex(int i){
        return indicesVertices.get(i);
    }
    
    /**
     * Generate string representation of adjacency list
     * @return the String
     */
    public String adjacencyToString() {
        String s = "Adjacency list";
	s += " (size " + getNumVertices() + "+" + getNumEdges() + " integers):";
        
        for (Vertex v : adjList.keySet()) {
            s += String.format("\n\t%s: {\n", v);
            
            for ( Edge edge : adjList.get(v)) {
                s += String.format("%s, ", edge.getDestination());
            }
            s += "}\n";
	}
	return s;
    }
        
    /**
     * Generate string representation of adjacency list
     * @param fileName nombre de archivo generado en formato HT
     * @param title
     * @throws java.io.IOException
     */
    public void saveToHtml(String fileName, String title) throws IOException, URISyntaxException {
        Iterator<Vertex> vertexIterator;
        Iterator<Edge> edgeIterator;
        Vertex vertex;
        Edge edge;
        boolean flag;
        StringBuilder sb = new StringBuilder();
        FileWriter fw; 
        BufferedWriter bw; 
        String s = "";
        int i;
        
        fw = new FileWriter(fileName);
        bw = new BufferedWriter(fw);

        sb.append("<!DOCTYPE html>\n\n<html lang=\"en\">\n    <head>\n        <meta charset=\"utf-8\" />\n");
        sb.append(String.format("                    <title>%s</title>\n", title));
        sb.append("        <script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/vis/4.13.0/vis.min.js\"></script>\n");
        sb.append("<meta charset=\"utf-8\">  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        sb.append("<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
        sb.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
        sb.append("<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>");
            
        sb.append("        <link href=\"vis/vis.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");

        sb.append("    <script type=\"text/javascript\">\n" +
                  "        function draw() {\n" +
                  "            // create some nodes\n");
        sb.append("var nodes = [");

        vertexIterator = adjList.keySet().iterator();
        
        while ( vertexIterator.hasNext() ) {
            vertex = vertexIterator.next();
            i = vertexIndices.get(vertex);
//            sb.append(String.format("{id: %d, \"label\": \"hola\", \"group\": 1}", i));
            sb.append(String.format("{id: %d, shape: 'image',  image: 'image/%s.png', label:\"%s\"}", i,vertex.getContent().toString(), vertex.getContent().toString()));
            sb.append(vertexIterator.hasNext() ? ",\n" : "");
        }
        sb.append("];\n");

        // create some edges
        sb.append("var edges = [\n");
        
        vertexIterator = adjList.keySet().iterator();
        
        while (vertexIterator.hasNext()) {
            flag = false;
            vertex = vertexIterator.next();
            edgeIterator = adjList.get(vertex).iterator();
            i = vertexIndices.get(vertex);
            while ( edgeIterator.hasNext() ) {
                flag = true;
                edge = edgeIterator.next();
                sb.append(String.format("{\"from\": %d, \"to\": %d}",i, vertexIndices.get(edge.getDestination())));
                sb.append(edgeIterator.hasNext() ? ",\n" : "");
            }
            if(flag)
                sb.append(vertexIterator.hasNext() ? ",\n" : "");
        }
        sb.append("];\n");

        sb.append("            // create a network\n" +
                  "            var container = document.getElementById('grafo');\n" +
                  "            var data = {\n" +
                  "                nodes: nodes,\n" +
                  "                edges: edges\n" +
                  "            };\n" +
                  "            var options = {\n" +
                  "                nodes: {\n" +
                  "                    shape: 'dot',\n" +
                  "                    size: 80\n" +
                  "                },\n" +
                  "                physics: {\n" +
                  "                    forceAtlas2Based: {\n" +
                  "                        gravitationalConstant: -100,\n" +
                  "                        centralGravity: 0.0008,\n" +
                  "                        springLength:300,\n" +
                  "                        springConstant: 0.2\n" +
                  "                    },\n" +
                  "                    maxVelocity: 146,\n" +
                  "                    solver: 'forceAtlas2Based',\n" +
                  "                    timestep: 0.35,\n" +
                  "                    stabilization: {iterations: 150}\n" +
                  "                },edges: {arrows:{to:{enable:true}}}\n" +
                  "            };\n" +
                  "            var network = new vis.Network(container, data, options);\n" +
                  "\n" +
                  "        }\n" +
                  "    </script>\n" +
                  "    <script src=\"../../googleAnalytics.js\"></script>");
        fw.write(sb.toString());
        fw.write("    </head>\n    <body onload=\"draw()\" background=\"wall.png\">");
//        fw.write(String.format("<h1>%s</h1>", this.type == Graph.Type.DIRECTED ? "Grafo Dirigido" : "Grafo No Dirigido"));
         fw.write("<div class=\"page-header\">\n" +
                "  <h1>Grafo de la red Mpls de la ciudad Guayaquil y Quito</h1>\n" +
                "</div>");
//        fw.write(String.format("<h2> Vertices: %d Arcos: %d </h2>", getNumVertices(), getNumEdges()));
        fw.write("            <div id=\"grafo\" style=\"position: absolute; top: 80px; bottom: 20px; width: 100%;\"></div>\n    </body>\n</html>");
        fw.close();
        
        Desktop.getDesktop().browse(new URI(fileName));
    }
    
    
    /**
     * Representa el tipo de un grafo: Dirigido o No Dirigido.
     */
    public enum Type {
        /***
         * Grafo no dirigido.
         */
        UNDIRECTED,
        /***
         * Grafo dirigido.
         */
        DIRECTED
    }
}
