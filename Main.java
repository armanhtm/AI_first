package com.company;

import java.util.ArrayList;
import java.util.Scanner;

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

        graph.aStarMain(targetsB,targetsP,true);
        //set plates
        for (String plateLocation : targetsP){
            removeButter(plateLocation,matrix);
            addButter(plateLocation,matrix);
        }

        graph.findRobotPath();

        //this arraylist hold first location of each path
        ArrayList<String> firstLocations = new ArrayList<>();
        for (ArrayList<String> array: graph.pathsRobot)
            firstLocations.add(array.get(0));


        ArrayList<String> finalPath = new ArrayList<>();
        int size = firstLocations.size();

        //set butter places
        for (int i=0 ; i<row ; i++){
            for (int j=0 ; j<col ; j++)
                if(matrix[i][j].contains("b"))
                    if(!targetsB.contains(i+","+j))
                        removeButter(i+","+j,matrix);

        }
        for (String butterLocation : targetsB){
            removeButter(butterLocation,matrix);
            addButter(butterLocation,matrix);}

        for (String plateLocation : targetsP){
            removeButter(plateLocation,matrix);
            addButter(plateLocation,matrix);
        }

        for(int i=0 ; i<size ; i++) {
            Graph newGraph = new Graph(matrix, row, col);
            ArrayList<String> srcArray = new ArrayList<>();
            srcArray.add(src);
            if(newGraph.aStarMain(srcArray, firstLocations, false)==0){
                System.out.println("no way: robot to butter : code:r-->b");
                finalPath.clear();
                break;
            }
            //add path from robot to butter locations
            for (String location : newGraph.inverse(newGraph.pathsButter.get(0)))
                finalPath.add(location);

            for (ArrayList<String> path : graph.pathsRobot)
                if (path.contains(finalPath.get(finalPath.size() - 1))) {
                    firstLocations.remove(path.get(0));
                    for (String locationPath : path)
                        finalPath.add(locationPath);
                }
            src=finalPath.get(finalPath.size()-1);
        }

        //remove fake butter
        for(int i=0 ; i<row ; i++)
            for (int j=0 ; j<col ; j++)
                if(matrix[i][j].contains("b") && !targetsB.contains(i+","+j))
                    removeButter(i+","+j,matrix);

        for (String location : finalPath)
            System.out.print("  "+location);

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
}
