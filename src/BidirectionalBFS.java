import java.util.ArrayList;
import java.util.HashMap;
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
        String[][] tempMatrix = new String[Row][Col];

        String Robot = new String();
        ArrayList<String> Butters = new ArrayList<>();
        ArrayList<String> Plates = new ArrayList<>();
        //getting input
        for (int i = 0; i < Row; i++)
            for (int j = 0; j < Col; j++) {
                matrix[i][j] = scanner.next();
                tempMatrix[i][j] = matrix[i][j];
                if (matrix[i][j].contains("r"))
                    Robot = i + "," + j;
                if (matrix[i][j].contains("b"))
                    Butters.add(i + "," + j);
                if (matrix[i][j].contains("p"))
                    Plates.add(i + "," + j);
            }
        int MaxDepth = Math.min(1000, Math.max(Row, Col));
        ArrayList<ArrayList<String>> temp = FindButterToPlate(Butters,Plates,tempMatrix,Row,Col);
        for(ArrayList<String> path : temp) {
            terminalShow(path,matrix,Row,Col);
            System.out.println();
            System.out.println();
        }
    }

    public static ArrayList<String> FindNeighborsForward(String location, String[][] matrix, int Row, int Col, boolean special) {
        int row = Integer.parseInt(location.split(",")[0]);
        int col = Integer.parseInt(location.split(",")[1]);
        ArrayList<String> neighbors = new ArrayList<>();
        System.out.println(location + "$$");

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
        for(String s : neighbors)
            System.out.println(s + "$");
        return neighbors;
    }
    public static ArrayList<String> FindNeighborsBackWard(String location, String[][] matrix, int Row, int Col, boolean special) {
        int row = Integer.parseInt(location.split(",")[0]);
        int col = Integer.parseInt(location.split(",")[1]);
        ArrayList<String> neighbors = new ArrayList<>();
        System.out.println(location + "**");


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

        for(String s : neighbors)
            System.out.println(s + "*");
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
        addButter(location,matrix);
        System.out.println("^^^^" + matrix[1][2] + "^^^^");
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
        removeButter(location,matrix);
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
    public static int calculateManhatan(String src,String goal){
        int goalRow = Integer.parseInt(goal.split(",")[0]);
        int goalCol = Integer.parseInt(goal.split(",")[1]);

        int srcRow = Integer.parseInt(src.split(",")[0]);
        int srcCol = Integer.parseInt(src.split(",")[1]);

        return Math.abs(goalCol-srcCol)+Math.abs(goalRow-srcRow);

    }
    private static void terminalShow(ArrayList<String> finalPath, String[][] matrix,int row ,int col) {
        for (String location : finalPath){
            for(int i = 0 ; i < row ; i++){
                for (int j = 0 ; j < col ; j++)
                    if(i == Integer.parseInt(location.split(",")[0]) && j == Integer.parseInt(location.split(",")[1]))
                        System.out.printf("\u001B[33m%5s\u001B[0m",matrix[i][j]);
                    else
                        System.out.printf("%5s",matrix[i][j]);
                System.out.println("");}
            System.out.println("\n\n\n");

        }

        for (int i = 0 ; i < row ; i++) {
            for (int j = 0; j < col; j++) {
                String location = i + "," + j;
                if(finalPath.contains(location))
                    System.out.printf("\u001B[33m%5s\u001B[0m",matrix[i][j]);
                else
                    System.out.printf("%5s",matrix[i][j]);

            }
            System.out.println();
        }
    }

    public static String findDirection(String first, String second) {

        String nexLocation ;

        int firstRow = Integer.parseInt(first.split(",")[0]);
        int firstCol = Integer.parseInt(first.split(",")[1]);

        int secRow = Integer.parseInt(second.split(",")[0]);
        int secCol = Integer.parseInt(second.split(",")[1]);

        if(firstRow == secRow && secCol > firstCol){
            nexLocation = firstRow + "," + (firstCol - 1);
            return nexLocation;}

        if(firstRow == secRow && secCol < firstCol){
            nexLocation = firstRow + "," + (firstCol + 1);
            return nexLocation;}

        if(firstCol == secCol && firstRow > secRow){
            nexLocation = (firstRow + 1) + "," + firstCol;
            return nexLocation; }

        else
            return (firstRow - 1) + "," + firstCol;
    }
    public static ArrayList<ArrayList<String>> FindButterToPlate(ArrayList<String> butters, ArrayList<String> plates, String[][] matrix, int row, int col){
        HashMap<String,ArrayList<ArrayList<String>>> paths = new HashMap<>();
        ArrayList<ArrayList<String>> finalPaths = new ArrayList<>();

        for(int i = 0; i < butters.size() ; i++){
             paths.put(butters.get(i),new ArrayList<>());
             finalPaths.add(new ArrayList<>());
        }

        for(int i = 0 ;i < butters.size(); i++){
            for(int j = 0 ; j < plates.size(); j++) {
                ArrayList<String> temp = BIBFS(butters.get(i), plates.get(j), matrix, row, col, true);
                if(temp != null) {
                    paths.get(butters.get(i)).add(temp);
                }
            }
        }
        boolean [] butterIndexes = new boolean[butters.size()];
        boolean [] platesIndexes = new boolean[plates.size()];
        for(int i = 0 ; i < butterIndexes.length; i++) {
            butterIndexes[i] = false;
            platesIndexes[i] = false;
        }

        for(int i = 0; i < butters.size() ; i ++){
            int min = plates.size() + 1;
            int minIndex = -1;
            for(int j = 0 ; j < butters.size() ; j++){
                if(paths.get(butters.get(j)).size() < min && paths.get(butters.get(j)).size() != 0 && !butterIndexes[j]){
                    min = paths.get(butters.get(j)).size();
                    minIndex = j;
                }
            }

            int minPath = row * col + 1;
            int minPathIndex = -1;
            for(int k = 0 ; k < paths.get(butters.get(minIndex)).size() ; k++){
                if(paths.get(butters.get(minIndex)).get(k).size() < minPath && !platesIndexes[checkPlate(plates,paths.get(butters.get(minIndex)).get(k))]){
                    minPath = paths.get(butters.get(minIndex)).get(k).size();
                    minPathIndex = k;
                }
            }
            setPlate(butters,minIndex,plates,checkPlate(plates,paths.get(butters.get(minIndex)).get(minPathIndex)),butterIndexes,platesIndexes,matrix);
            finalPaths.add(paths.get(butters.get(minIndex)).get(minPathIndex));
        }
        return finalPaths;
    }
    public static int checkPlate(ArrayList<String> plates,ArrayList<String> path) {
        for (int i = 0; i < plates.size(); i++)
            if (plates.get(i).equals(path.get(path.size() - 1)))
                    return i;
        return -1;
    }
    public static void setPlate(ArrayList<String> butters,int butterIndex,ArrayList<String> plates,int plateIndex,boolean[] buttersIndexes,boolean[] plateIndexes,String[][] matrix){
        buttersIndexes[butterIndex] = true;
        String butter = butters.get(butterIndex);
        int butterRow = Integer.parseInt(butter.split(",")[0]);
        int butterCol = Integer.parseInt(butter.split(",")[1]);
        matrix[butterRow][butterCol] = matrix[butterRow][butterCol].replace("b","");

        plateIndexes[plateIndex] = true;
        String plate = plates.get(plateIndex);
        int plateRow = Integer.parseInt(plate.split(",")[0]);
        int plateCol = Integer.parseInt(plate.split(",")[1]);
        matrix[plateRow][plateCol] = matrix[plateRow][plateCol] + "b";
    }
    public static void removeButter(String first,String[][] matrix) {
        int firstRow = Integer.parseInt(first.split(",")[0]);
        int firstCol = Integer.parseInt(first.split(",")[1]);

        matrix[firstRow][firstCol] = matrix[firstRow][firstCol].replace("B","b");
    }

    /**
     * this method get location and add a butter to it
     * @param first
     */
    public static void addButter(String first,String[][] matrix) {
        int firstRow = Integer.parseInt(first.split(",")[0]);
        int firstCol = Integer.parseInt(first.split(",")[1]);

        matrix[firstRow][firstCol] = matrix[firstRow][firstCol].replace("b","B");
    }
}
