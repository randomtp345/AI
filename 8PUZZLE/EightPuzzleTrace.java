import java.util.*;

class EightPuzzleTrace {

    static class Node implements Comparable<Node> {
        int[][] state;
        int g, h;
        Node parent;
        String move;   // direction of blank move

        Node(int[][] state, int g, Node parent, String move) {
            this.state = state;
            this.g = g;
            this.parent = parent;
            this.move = move;
            this.h = heuristic(state);
        }

        int f() {
            return g + h;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.f(), o.f());
        }
    }

    static final int[][] GOAL = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
    };

    static int heuristic(int[][] state) {
        int dist = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int val = state[i][j];
                if (val != 0) {
                    for (int x = 0; x < 3; x++)
                        for (int y = 0; y < 3; y++)
                            if (GOAL[x][y] == val)
                                dist += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        return dist;
    }

    static boolean isGoal(int[][] state) {
        return Arrays.deepEquals(state, GOAL);
    }

    static List<Node> getChildren(Node node) {
        List<Node> children = new ArrayList<>();
        int x = 0, y = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (node.state[i][j] == 0) {
                    x = i;
                    y = j;
                }

        if (x > 0)  // UP
            children.add(new Node(swap(node.state, x, y, x - 1, y),
                    node.g + 1, node, "UP"));

        if (x < 2)  // DOWN
            children.add(new Node(swap(node.state, x, y, x + 1, y),
                    node.g + 1, node, "DOWN"));

        if (y > 0)  // LEFT
            children.add(new Node(swap(node.state, x, y, x, y - 1),
                    node.g + 1, node, "LEFT"));

        if (y < 2)  // RIGHT
            children.add(new Node(swap(node.state, x, y, x, y + 1),
                    node.g + 1, node, "RIGHT"));

        return children;
    }

    static int[][] swap(int[][] state, int x1, int y1, int x2, int y2) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(state[i], 0, newState[i], 0, 3);

        newState[x1][y1] = newState[x2][y2];
        newState[x2][y2] = 0;
        return newState;
    }

    static void printState(Node n) {
        for (int[] row : n.state) {
            for (int v : row)
                System.out.print(v + " ");
            System.out.println();
        }
        System.out.println("Move: " + n.move);
        System.out.println("g = " + n.g + "  h = " + n.h + "  f = " + n.f());
        System.out.println();
    }

    public static void main(String[] args) {

        int[][] start = {
                {2, 8, 3},
                {1, 6, 4},
                {7, 0, 5}
        };

        PriorityQueue<Node> open = new PriorityQueue<>();
        Set<String> closed = new HashSet<>();

        Node root = new Node(start, 0, null, "START");
        open.add(root);

        System.out.println("INITIAL STATE");
        printState(root);

        while (!open.isEmpty()) {

            Node current = open.poll();

            if (isGoal(current.state)) {
                System.out.println("FINAL STATE REACHED");
                printState(current);
                return;
            }

            closed.add(Arrays.deepToString(current.state));

            System.out.println("=======================================");
            System.out.println("EXPANDING NODE (Selected for expansion)");
            printState(current);

            List<Node> children = getChildren(current);

            Node bestChild = null;

            for (Node child : children) {
                String key = Arrays.deepToString(child.state);
                if (!closed.contains(key)) {
                    System.out.println("Child Generated:");
                    printState(child);
                    open.add(child);

                    if (bestChild == null || child.f() < bestChild.f())
                        bestChild = child;
                }
            }

            if (bestChild != null) {
                System.out.println(">>> SELECTED NEXT (Lowest f): Move = "
                        + bestChild.move + " | f = " + bestChild.f());
            }
        }
    }
}
