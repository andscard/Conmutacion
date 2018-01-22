/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgraph;

/**
 *
 * @author Jorge Rodríguez jirodrig@espol.edu.ec
 */
public class Edge {
    private Object weight;	
    private Object extraInfo; 
    private Vertex source;
    private Vertex destination;

    /**
     * Crea un arco para el grafo.
     * @param source
     * @param destination
     * @param weight
     * @param extraInfo
     */
    public Edge(Vertex source, Vertex destination, Object weight, Object extraInfo) {
        this.weight = weight;
        this.extraInfo = extraInfo;
        //this.source = source;
        this.destination = destination;
    }

    public Edge(Vertex source, Vertex destination) {
        this(source, destination, null, null);
    }

    public Edge(Vertex source, Vertex destination, Object weight) {
        this(source, destination, weight, null);
    }

    public Edge(Vertex source, Vertex destination, int weight) {
        this(source, destination, weight, null);
    }

    public Edge(Vertex source, Vertex destination, float weight) {
        this(source, destination, weight, null);
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public Object getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Object extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }



}