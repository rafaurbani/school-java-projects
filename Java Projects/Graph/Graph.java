import java.util.ArrayList;
import java.util.List;

public abstract class Graph {
	protected final int V;
	protected int E;
	protected List<Integer>[] adj;

	public Graph(int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices must be non-negative");
		this.V = V;
		clear();
	}

	public boolean isEmpty() {
		return V == 0;
	}

	public void clear() {
		E = 0;
		adj = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new ArrayList<Integer>();
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		E++;
	}

	public Iterable<Integer> adj(int v) {
		validateVertex(v);
		return adj[v]; 
	}

	protected void validateVertex(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < V; i++) {
			sb.append("[" + i + "] => " + adj(i));
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
