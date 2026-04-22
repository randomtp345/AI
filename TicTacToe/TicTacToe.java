import java.util.*;

public class TicTacToe {
    static char[][] b = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            print();

            // User move
            int r, c;
            do {
                System.out.print("Enter row & col (0-2): ");
                r = sc.nextInt(); c = sc.nextInt();
            } while (b[r][c] != ' ');
            b[r][c] = 'X';

            if (win('X')) { print(); System.out.println("You win!"); break; }
            if (full()) { print(); System.out.println("Draw!"); break; }

            // AI move
            ai();

            if (win('O')) { print(); System.out.println("AI wins!"); break; }
            if (full()) { print(); System.out.println("Draw!"); break; }
        }
    }

    static void ai() {
        if (move('O') || move('X')) return;
        for (int i=0;i<3;i++)
            for (int j=0;j<3;j++)
                if (b[i][j]==' ') { b[i][j]='O'; return; }
    }

    static boolean move(char p) {
        for (int i=0;i<3;i++)
            for (int j=0;j<3;j++)
                if (b[i][j]==' ') {
                    b[i][j]=p;
                    if (win(p)) { if (p=='X') b[i][j]='O'; return true; }
                    b[i][j]=' ';
                }
        return false;
    }

    static boolean win(char p) {
        for (int i=0;i<3;i++)
            if ((b[i][0]==p&&b[i][1]==p&&b[i][2]==p) ||
                (b[0][i]==p&&b[1][i]==p&&b[2][i]==p))
                return true;
        return (b[0][0]==p&&b[1][1]==p&&b[2][2]==p) ||
               (b[0][2]==p&&b[1][1]==p&&b[2][0]==p);
    }

    static boolean full() {
        for (char[] r : b)
            for (char c : r)
                if (c==' ') return false;
        return true;
    }

    static void print() {
        System.out.println("---------");
        for (char[] r : b) {
            System.out.print("| ");
            for (char c : r) System.out.print(c+" ");
            System.out.println("|");
        }
        System.out.println("---------");
    }
}
