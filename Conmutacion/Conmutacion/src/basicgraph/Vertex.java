
package basicgraph;

/**
 * Representa un vertice que puede ser agregado a un grafo. *
 * @author Jorge Rodríguez jirodrig@espol.edu.ec
 * @param <T> TDA que será contenido dentro del vertice. El contenido del vertice 
 * debe implementar la interface Comprable.
 */
public class Vertex<T> {
    private Comparable<T> content;
    private boolean visited;

    public Vertex(Comparable<T> content, boolean visited) {
        this.content = content;
        this.visited = visited;
    }
    
    public Vertex(Comparable<T> content) {
        this(content, false);
    }

    public Comparable<T> getContent() {
        return content;
    }

    public void setContent(Comparable<T> content) {
        this.content = content;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return "Vertex{" + "content=" + content + ", visited=" + visited + '}';
    }
    
    
}
