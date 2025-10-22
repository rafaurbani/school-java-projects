public class Main {
	public static void main(String[] args) {
		UndirectedGraph grafo = new UndirectedGraph(6);
		
		grafo.addEdge(0, 4);
		grafo.addEdge(0, 5);
		grafo.addEdge(4, 5);
		grafo.addEdge(2, 5);
		grafo.addEdge(1, 2);
		grafo.addEdge(2, 3);
		grafo.addEdge(1, 3);
		
		DepthFirstSearch dfs = new DepthFirstSearch();
		var dfsFinal = dfs.dfs(grafo);
		
		for (int i = 0; i < 6; i++) {
			System.out.print(dfsFinal[i].v() + "= ");
			System.out.println(dfsFinal[i].d() + " " + dfsFinal[i].f());
		}
	}
}
