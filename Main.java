package com.company;

import java.util.*;

public class Main {


    public static void main(String[] args) {




        //scanner for get input
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        //table matrix
        String[][] matrix = new String[row][col];

        ArrayList<String> targetsB = new ArrayList<>();
        ArrayList<String> targetsP = new ArrayList<>();
        String src = new String();
        //getting input
        for(int i=0 ; i<row ; i++)
            for(int j=0 ; j<col ; j++){
                matrix[i][j] = scanner.next();
                if(matrix[i][j].contains("r"))
                    src = i+","+j;
                if(matrix[i][j].contains("b"))
                    targetsB.add(i+","+j);
                if(matrix[i][j].contains("p"))
                    targetsP.add(i+","+j);}



        Graph graph = new Graph(matrix,row,col);
        int maxDepth = Math.min(1000,Math.max(row,col));


        for (String b : targetsB){
            graph.IDDFS(targetsP,maxDepth,true,b);
        }

        if(!graph.findRobotPath()){
            System.out.println("sorry");
            return;
        }

        //this arraylist hold first location of each path
        ArrayList<String> firstLocations = new ArrayList<>();
        for (ArrayList<String> array: graph.pathsRobot)
            firstLocations.add(array.get(0));


        ArrayList<String> finalPath = new ArrayList<>();
        int size = firstLocations.size();

        for (int i=0 ; i<row ; i++)
            for (int j=0 ; j<col ; j++)
                matrix[i][j] = matrix[i][j].replace("B","b");

        for(int i=0 ; i<size ; i++){
            Graph newGraph = new Graph(matrix,row,col);
            newGraph.IDDFS(firstLocations,maxDepth,false,src);
            //add path from robot to butter locations
                for (String location: newGraph.inverse(newGraph.pathsButter.get(0)))
                    finalPath.add(location);

                //add path from butter to plate
                for (ArrayList<String> path : graph.pathsRobot)
                    if(path.contains(newGraph.srcF)){
                        path.set(1,path.get(1).replace("b","B")) ;
                        for (String location1 : path )
                            finalPath.add(location1);}

                src = finalPath.get(finalPath.size()-1);

        }




        for (String location : finalPath)
            System.out.print("  "+location);

        if(finalPath.size()==0)
            System.out.println("can’t pass the butter");

        //standard output
        for (int i=0 ; i<finalPath.size()-1 ; i++)
            System.out.print(FindDirection(finalPath.get(i),finalPath.get(i+1))+" ");
        System.out.println("\n");


        System.out.println("\n");

        terminalShow(finalPath,matrix,row,col);


    }

    private static void terminalShow(ArrayList<String> finalPath, String[][] matrix,int row ,int col) {
        for (String location : finalPath){
            for(int i=0 ; i<row ; i++){
                for (int j=0 ; j<col ; j++)
                    if(i==Integer.parseInt(location.split(",")[0]) && j==Integer.parseInt(location.split(",")[1]))
                        System.out.printf("\u001B[33m%5s\u001B[0m",matrix[i][j]);
                    else
                        System.out.printf("%5s",matrix[i][j]);
                System.out.println("");}
            System.out.println("\n\n\n");

        }

        for (int i=0 ; i<row ; i++) {
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

    /**
     * this method remove butter from location
     * @param first
     */
    private static void removeButter(String first,String[][] matrix) {
        int firstRow = Integer.parseInt(first.split(",")[0]);
        int firstCol = Integer.parseInt(first.split(",")[1]);

        matrix[firstRow][firstCol] = matrix[firstRow][firstCol].replace("b","");
    }

    /**
     * this method get location and add a butter to it
     * @param first
     * @param matrix
     */
    private static void addButter(String first, String[][] matrix) {
        int firstRow = Integer.parseInt(first.split(",")[0]);
        int firstCol = Integer.parseInt(first.split(",")[1]);

        matrix[firstRow][firstCol] = matrix[firstRow][firstCol]+"b";
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

            return "U";
        }
        if(FirstCol==SecondCol && FirstRow==SecondRow)
            return "";

        else
            return "D";

    }


}
