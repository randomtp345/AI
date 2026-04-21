import java.util.*;

public class PacketRoutingDFS {

    static void addEdge(List<List<Integer>> graph, int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    static boolean dfs(List<List<Integer>> graph, int current, int dest,
                       boolean[] visited, List<Integer> path) {

        System.out.println("Visiting node: " + current);
        path.add(current);

        if (current == dest) {
            System.out.println("Destination " + dest + " reached!");
            return true;
        }

        visited[current] = true;

        // Force DFS to take one direction first (helps show backtracking)
        List<Integer> neighbors = graph.get(current);
        Collections.sort(neighbors, Collections.reverseOrder());

        for (int neighbor : neighbors) {
            if (!visited[neighbor]) {
                System.out.println("Moving from " + current + " -> " + neighbor);

                if (dfs(graph, neighbor, dest, visited, path)) {
                    return true;
                }
            }
        }

        // Dead-end clearly visible
        System.out.println("Dead end at node: " + current);
        System.out.println("Backtracking from node: " + current);

        path.remove(path.size() - 1); // remove last node from path
        return false;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        System.out.println("Enter edges (u v):");
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            addEdge(graph, u, v);
        }

        System.out.print("Enter source node: ");
        int source = sc.nextInt();

        System.out.print("Enter destination node: ");
        int destination = sc.nextInt();

        boolean[] visited = new boolean[n];
        List<Integer> path = new ArrayList<>();

        System.out.println("\nStarting packet routing using DFS...\n");

        boolean found = dfs(graph, source, destination, visited, path);

        if (found) {
            System.out.println("\nFinal Path: " + path);
        } else {
            System.out.println("No path found!");
        }

        sc.close();
    }
}
