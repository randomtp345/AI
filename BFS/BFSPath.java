import java.util.*;

public class BFSPath {

    public static List<String> bfs(Map<String, List<String>> graph, String start, String goal) {

        Queue<String> Q1 = new LinkedList<>();   // To be processed
        List<String> Q2 = new ArrayList<>();     // Processed
        Map<String, String> parent = new HashMap<>();

        Q1.add(start);
        parent.put(start, null);

        System.out.printf("%-5s%-10s%-30s%s\n",
                "Step", "Removed", "Q1 (To be processed)", "Q2 (Processed)");
        System.out.println("--------------------------------------------------------------------------------");

        int step = 0;

        while (!Q1.isEmpty()) {

            String current = Q1.poll();   // Remove from front
            Q2.add(current);

            System.out.printf("%-5d%-10s%-30s%s\n",
                    step, current, Q1.toString(), Q2.toString());
            step++;

            if (current.equals(goal)) {
                System.out.println("\nGoal node found!");
                break;
            }

            for (String neighbor : graph.get(current)) {
                if (!Q1.contains(neighbor) && !Q2.contains(neighbor)) {
                    Q1.add(neighbor);
                    parent.put(neighbor, current);

                    // Show enqueue operation
                    System.out.println("Enqueued: " + neighbor + " → Q1: " + Q1);
                }
            }
        }

        List<String> path = new ArrayList<>();

        if (Q2.contains(goal)) {
            String node = goal;
            while (node != null) {
                path.add(node);
                node = parent.get(node);
            }
            Collections.reverse(path);
        }

        return path;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Map<String, List<String>> graph = new HashMap<>();

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter node names:");
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String node = sc.nextLine();
            nodes.add(node);
            graph.put(node, new ArrayList<>());
        }

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        System.out.println("Enter edges (u v):");
        for (int i = 0; i < e; i++) {
            String u = sc.next();
            String v = sc.next();

            graph.get(u).add(v);
            graph.get(v).add(u); // undirected graph
        }

        sc.nextLine();

        System.out.print("Enter source node: ");
        String start = sc.nextLine();

        System.out.print("Enter destination node: ");
        String goal = sc.nextLine();

        List<String> path = bfs(graph, start, goal);

        if (path.isEmpty()) {
            System.out.println("\nNo path found!");
        } else {
            System.out.println("\nBFS Path: " + path);
        }

        sc.close();
    }
}
