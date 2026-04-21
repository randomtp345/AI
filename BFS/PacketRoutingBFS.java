import java.util.*;

public class PacketRoutingBFS {

    static void addEdge(List<List<Integer>> graph, int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    static void bfs(List<List<Integer>> graph, int source, int dest, int n) {

        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        int[] distance = new int[n];

        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        distance[source] = 0;

        System.out.println("Starting BFS traversal...\n");
        System.out.println("Initial Queue: " + queue + "\n");

        while (!queue.isEmpty()) {

            System.out.println("Current Queue: " + queue);

            int current = queue.poll();
            System.out.println("Dequeued (Visiting): " + current);

            if (current == dest) {
                System.out.println("Destination " + dest + " reached!");
                System.out.println("Shortest distance: " + distance[current] + " edges");
                break;
            }

            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                    parent[neighbor] = current;

                    // 🔥 distance update (important)
                    distance[neighbor] = distance[current] + 1;

                    // 🔥 live queue update
                    System.out.println("Enqueued: " + neighbor + " → Queue: " + queue);
                    System.out.println("Distance of " + neighbor + " = " + distance[neighbor]);
                }
            }

            System.out.println(); // spacing
        }

        // If destination not reached
        if (!visited[dest]) {
            System.out.println("No path found!");
            return;
        }

        // 🔥 Build shortest path using parent[]
        List<Integer> path = new ArrayList<>();
        for (int i = dest; i != -1; i = parent[i]) {
            path.add(i);
        }

        Collections.reverse(path);

        System.out.println("\nShortest Path: " + path);
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

        bfs(graph, source, destination, n);

        sc.close();
    }
}
