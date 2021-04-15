package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        for (String b : targetsB){
            graph.IDDFS(targetsP,maxDepth,true,b);

        }












    }
}
