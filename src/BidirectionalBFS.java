import java.util.ArrayList;
import java.util.Scanner;

class Example {
    public static String Src;
    public static String Dest;
}
public class BidirectionalBFS {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int Row = scanner.nextInt();
        int Col = scanner.nextInt();

        //table matrix
        String[][] matrix = new String[Row][Col];

        String Robot = new String();
        ArrayList<String> Butters = new ArrayList<>();
        ArrayList<String> Plates = new ArrayList<>();
        //getting input
        for (int i = 0; i < Row; i++)
            for (int j = 0; j < Col; j++) {
                matrix[i][j] = scanner.next();
                if (matrix[i][j].contains("r"))
                    Robot = i + "," + j;
                if (matrix[i][j].contains("b"))
                    Butters.add(i + "," + j);
                if (matrix[i][j].contains("p"))
                    Plates.add(i + "," + j);
            }
        int MaxDepth = Math.min(1000, Math.max(Row, Col));
        ArrayList<String> temp = new ArrayList<>();
        Example.Src = "1,1";
        Example.Dest = "4,4";
        temp = BIBFS(Example.Src, Example.Dest, matrix, Row, Col, true);
        for (String string : temp) {
            System.out.println(string);
        }
    }

    public static ArrayList<String> FindNeighborsForward(String location, String[][] matrix, int Row, int Col, boolean special) {
        int row = Integer.parseInt(location.split(",")[0]);
        int col = Integer.parseInt(location.split(",")[1]);
        ArrayList<String> neighbors = new ArrayList<>();


        //top of src
        if (row - 1 >= 0)
            if (!matrix[row - 1][col].contains("x") && !matrix[row - 1][col].contains("b"))
                if (special) {
                    if (row + 1 < Row && (!matrix[row + 1][col].contains("x") && !matrix[row + 1][col].contains("b")))
                        neighbors.add((row - 1) + "," + col);
                } else
                    neighbors.add((row - 1) + "," + col);


        //down of src
        if (row + 1 < Row)
            if (!matrix[row + 1][col].contains("x") && !matrix[row + 1][col].contains("b"))
                if (special) {
                    if (row - 1 >= 0 && (!matrix[row - 1][col].contains("x") && !matrix[row - 1][col].contains("b")))
                        neighbors.add((row + 1) + "," + col);
                } else
                    neighbors.add((row + 1) + "," + col);


        //left of src
        if (col - 1 >= 0)
            if (!matrix[row][col - 1].contains("x") && !matrix[row][col - 1].contains("b"))
                if (special) {
                    if (col + 1 < Col && (!matrix[row][col + 1].contains("x") && !matrix[row][col + 1].contains("b")))
                        neighbors.add(row + "," + (col - 1));
                } else
                    neighbors.add(row + "," + (col - 1));


        //right of src
        if (col + 1 < Col)
            if (!matrix[row][col + 1].contains("x") && !matrix[row][col + 1].contains("b"))
                if (special) {
                    if (col - 1 >= 0 && (!matrix[row][col - 1].contains("x") && !matrix[row][col - 1].contains("b")))
                        neighbors.add(row + "," + (col + 1));
                } else
                    neighbors.add(row + "," + (col + 1));

        return neighbors;
    }
    public static ArrayList<String> FindNeighborsBackWard(String location, String[][] matrix, int Row, int Col, boolean special) {
        int row = Integer.parseInt(location.split(",")[0]);
        int col = Integer.parseInt(location.split(",")[1]);
        ArrayList<String> neighbors = new ArrayList<>();


        //top of src
        if (row - 1 >= 0)
            if (!matrix[row - 1][col].contains("x") && !matrix[row - 1][col].contains("b"))
                if (special) {
                    if (row - 2 >= 0 && (!matrix[row - 2][col].contains("x") && !matrix[row - 2][col].contains("b")))
                        neighbors.add((row - 1) + "," + col);
                } else
                    neighbors.add((row - 1) + "," + col);


        //down of src
        if (row + 1 < Row)
            if (!matrix[row + 1][col].contains("x") && !matrix[row + 1][col].contains("b"))
                if (special) {
                    if (row + 2 < Row && (!matrix[row + 2][col].contains("x") && !matrix[row + 2][col].contains("b")))
                        neighbors.add((row + 1) + "," + col);
                } else
                    neighbors.add((row + 1) + "," + col);


        //left of src
        if (col - 1 >= 0)
            if (!matrix[row][col - 1].contains("x") && !matrix[row][col - 1].contains("b"))
                if (special) {
                    if (col - 2 >= 0 && (!matrix[row][col - 2].contains("x") && !matrix[row][col - 2].contains("b")))
                        neighbors.add(row + "," + (col - 1));
                } else
                    neighbors.add(row + "," + (col - 1));


        //right of src
        if (col + 1 < Col)
            if (!matrix[row][col + 1].contains("x") && !matrix[row][col + 1].contains("b"))
                if (special) {
                    if (col + 2 < Col && (!matrix[row][col + 2].contains("x") && !matrix[row][col + 2].contains("b")))
                        neighbors.add(row + "," + (col + 1));
                } else
                    neighbors.add(row + "," + (col + 1));

        return neighbors;
    }

    public static String FindThirdLocation(String first, String second) {

        int FirstRow = Integer.parseInt(first.split(",")[0]);
        int FirstCol = Integer.parseInt(first.split(",")[1]);

        int SecondRow = Integer.parseInt(second.split(",")[0]);
        int SecondCol = Integer.parseInt(second.split(",")[1]);

        if (FirstRow == SecondRow && SecondCol > FirstCol) {
            return FirstRow + "," + (FirstCol - 1);
        }

        if (FirstRow == SecondRow && SecondCol < FirstCol) {
            return FirstRow + "," + (FirstCol + 1);
        }

        if (FirstCol == SecondCol && FirstRow > SecondRow) {
            return (FirstRow + 1) + "," + FirstCol;
        } else
            return (FirstRow - 1) + "," + FirstCol;

    }

    public static String FindDirection(String first, String second) {

        int FirstRow = Integer.parseInt(first.split(",")[0]);
        int FirstCol = Integer.parseInt(first.split(",")[1]);

        int SecondRow = Integer.parseInt(second.split(",")[0]);
        int SecondCol = Integer.parseInt(second.split(",")[1]);

        if (FirstRow == SecondRow && SecondCol > FirstCol) {

            return "R";
        }

        if (FirstRow == SecondRow && SecondCol < FirstCol) {

            return "L";
        }

        if (FirstCol == SecondCol && FirstRow > SecondRow) {

            return "D";
        } else
            return "U";
    }
    public static int PositionToInt(String location,int Row,int Col){
        int row = Integer.parseInt(location.split(",")[0]);
        int col = Integer.parseInt(location.split(",")[1]);
        int counter = 0;
        for(int i = 0; i < Row; i++)
            for(int j = 0 ; j < Col; j++){
                if(i == row && j == col)
                    return counter;
                counter++;
            }
        return 0;
    }
    public static String PositionToString(int location,int Row,int Col){
        int counter = 0;
        for(int i = 0 ; i < Row; i++)
            for(int j = 0; j < Col; j++){
                if(location == counter)
                    return i + "," + j;
                counter ++;
            }
        return "";
    }

    public static ArrayList<String> BIBFS(String location, String target, String[][] matrix, int Row, int Col, boolean special) {
        ArrayList<String> path = new ArrayList<>();
        Graph G = new Graph(Row, Col, special,matrix);
        int src = PositionToInt(location,Row,Col);
        int dest = PositionToInt(target,Row,Col);
        boolean[] Visited1 = new boolean[Row * Col];
        boolean[] Visited2 = new boolean[Row * Col];
        int[] parent1 = new int[Row * Col];
        int[] parent2 = new int[Row * Col];
        for (int i = 0; i < Row * Col; i++) {
            Visited1[i] = false;
            Visited2[i] = false;
            parent1[i] = -1;
            parent2[i] = -1;
        }
        ArrayList<Integer> queue1 = new ArrayList<>();
        ArrayList<Integer> queue2 = new ArrayList<>();
        queue1.add(src);
        Visited1[src] = true;
        queue2.add(dest);
        Visited2[dest] = true;
        int intersection = -1;
        while (queue1.size() > 0 && queue2.size() > 0 && intersection == -1) {
            G.BFS(queue1, Visited1, parent1,false);
            G.BFS(queue2, Visited2, parent2,true);

            for (int i = 0; i < Row * Col; i++) {
                if (Visited1[i] && Visited2[i]) {  //checking intersection
                    intersection = i;
                    break;
                }
            }
        }

        if (intersection == -1)
            return null;

        ArrayList<String> path1 = new ArrayList<>();
        int j = intersection;
        while (j != -1) {
            path1.add(PositionToString(j,Row,Col));
            j = parent1[j];
        }
        ArrayList<String> path2 = new ArrayList<>();
        j = parent2[intersection];
        while (j != -1) {
            path2.add(PositionToString(j,Row,Col));
            j = parent2[j];
        }
        for (int i = path1.size() - 1 ; i >= 0; i--) {
            path.add(path1.get(i));
        }
        for (String string : path2) {
            path.add(string);
        }
        return path;
    }

    public static int toInt(String string) {
        String first = string.split(",")[0];
        String second = string.split(",")[1];
        String result = first + second;
        return Integer.parseInt(result);
    }

    static class Graph {
        String[][] matrix;
        int row;
        int col;
        boolean special;
        ArrayList<Integer>[] Adj; // adjacency list

        Graph(int row, int col, boolean special,String[][] Matrix) {
            this.row = row;
            this.col = col;
            this.special = special;
            this.matrix = Matrix;
            Adj = new ArrayList[row * col];
            for (int i = 0; i < row * col; i++) {
                Adj[i] = new ArrayList<>();
            }
        }

        void add_edge(int src,boolean back) {
                ArrayList<String> temp;
                if(back){
                    temp = FindNeighborsBackWard(PositionToString(src,row,col), matrix, row, col, special);
                }
                else{
                    temp = FindNeighborsForward(PositionToString(src,row,col), matrix, row, col, special);
                }
                for (String string : temp) {
                    Adj[src].add(PositionToInt(string,row,col));
                }
        }

        void BFS(ArrayList<Integer> queue, boolean[] visited, int[] parent,boolean back) {
            int current = queue.remove(0);
            add_edge(current,back);
            for (int i = 0; i < Adj[current].size(); i++) {
                int x = Adj[current].get(i);
                if (!visited[x]) {
                    queue.add(x);
                    visited[x] = true;
                    parent[x] = current;
                }
            }
        }
    }
}
