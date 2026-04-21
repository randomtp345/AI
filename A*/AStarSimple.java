import java.util.*;

class Edge {
    int to, cost;

    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

public class AStarSimple {

    public static List<Integer> aStar(List<List<Edge>> graph, int[] h, int start, int goal) {

        int n = graph.size();
        int[] g = new int[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(g, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        // PriorityQueue storing {node, f(n)}
        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        g[start] = 0;
        pq.add(new int[]{start, h[start]});

        while (!pq.isEmpty()) {

            int[] curr = pq.poll();
            int node = curr[0];

            if (visited[node]) continue;
            visited[node] = true;

            System.out.println("Visiting: " + node +
                    " | g=" + g[node] +
                    " h=" + h[node] +
                    " f=" + (g[node] + h[node]));

            if (node == goal) break;

            for (Edge e : graph.get(node)) {

                int newCost = g[node] + e.cost;

                if (!visited[e.to] && newCost < g[e.to]) {
                    g[e.to] = newCost;
                    parent[e.to] = node;

                    pq.add(new int[]{e.to, newCost + h[e.to]});
                }
            }
        }

        // Build path
        List<Integer> path = new ArrayList<>();
        for (int v = goal; v != -1; v = parent[v]) {
            path.add(v);
        }
        Collections.reverse(path);

        return path;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        System.out.println("Enter edges (from to cost):");
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            graph.get(u).add(new Edge(v, cost));
        }

        int[] h = new int[n];
        System.out.println("Enter heuristic values:");
        for (int i = 0; i < n; i++) {
            h[i] = sc.nextInt();
        }

        System.out.print("Enter start node: ");
        int start = sc.nextInt();

        System.out.print("Enter goal node: ");
        int goal = sc.nextInt();

        List<Integer> path = aStar(graph, h, start, goal);

        System.out.println("\nShortest Path: " + path);

        sc.close();
    }
}
