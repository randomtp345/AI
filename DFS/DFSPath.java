import java.util.*;

public class DFSPath {

    public static List<String> dfsPath(Map<String, List<String>> graph, String start, String goal) {
        Stack<String> stack = new Stack<>();
        List<String> processed = new ArrayList<>();
        Map<String, String> parent = new HashMap<>();

        stack.push(start);
        parent.put(start, null);

        int step = 0;

        System.out.printf("%-5s%-8s%-25s%s\n", "Step", "Popped", "Stack (STK)", "Processed");
        System.out.println("-----------------------------------------------------------------");

        while (!stack.isEmpty()) {
            String node = stack.pop();

            if (!processed.contains(node)) {
                processed.add(node);

                System.out.printf("%-5d%-8s%-25s%s\n", step, node, stack.toString(), processed.toString());
                step++;

                if (node.equals(goal)) {
                    System.out.println("\nGoal node found!");
                    break;
                }

                List<String> neighbors = new ArrayList<>(graph.get(node));
                Collections.sort(neighbors, Collections.reverseOrder());

                for (String neighbor : neighbors) {
                    if (!processed.contains(neighbor) && !stack.contains(neighbor)) {
                        stack.push(neighbor);
                        parent.put(neighbor, node);

                        // Show push operation
                        System.out.println("Pushed: " + neighbor + " → Stack: " + stack);
                    }
                }
            }
        }

        List<String> path = new ArrayList<>();

        if (processed.contains(goal)) {
            String curr = goal;
            while (curr != null) {
                path.add(curr);
                curr = parent.get(curr);
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
        sc.nextLine(); // consume newline

        System.out.println("Enter node names:");
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String node = sc.nextLine();
            nodes.add(node);
            graph.put(node, new ArrayList<>());
        }

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter edges (u v):");
        for (int i = 0; i < e; i++) {
            String u = sc.next();
            String v = sc.next();

            graph.get(u).add(v);
            graph.get(v).add(u); // undirected
        }

        sc.nextLine();

        System.out.print("Enter source node: ");
        String start = sc.nextLine();

        System.out.print("Enter destination node: ");
        String goal = sc.nextLine();

        List<String> path = dfsPath(graph, start, goal);

        if (path.isEmpty()) {
            System.out.println("\nNo path found!");
        } else {
            System.out.println("\nDFS Path: " + path);
        }

        sc.close();
    }
}
