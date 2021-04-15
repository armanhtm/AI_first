package com.company;

import java.util.ArrayList;

public class Graph {
    //find the target boolean value
    private boolean find ;
    //matrix
    private String[][] matrix;
    //row and col of matrix
    private int matrixRow;
    private int matrixCol;

    public String srcF;

    public ArrayList<ArrayList<String>> pathsButter;

    private int index;

    public ArrayList<ArrayList<String>> pathsRobot;

    //constructor
    public Graph(String[][] matrix,int matrixRow , int matrixCol){
        this.matrix = matrix;
        find = false;
        this.matrixRow = matrixRow;
        this.matrixCol = matrixCol;
        pathsButter = new ArrayList<>();
        index = 0 ;
        pathsRobot = new ArrayList<ArrayList<String>>();

    }

    /**
     * this method return neighbors of location
     * @param location
     * @return
     */

    public ArrayList<String> neighbors (String location , boolean special){
        int row = Integer.parseInt(location.split(",")[0]);
        int col = Integer.parseInt(location.split(",")[1]);
        ArrayList<String> output = new ArrayList<>();


        //top of src
        if(row-1 >= 0 )
            if(!matrix[row-1][col].contains("x") && !matrix[row-1][col].contains("b") )
                if(special){
                    if(row+1<matrixRow &&
                            (!matrix[row+1][col].contains("x") && !matrix[row+1][col].contains("b")))
                        output.add((row-1)+","+col);}
                else
                    output.add((row-1)+","+col);



        //down of src
        if(row+1 < matrixRow )
            if( !matrix[row+1][col].contains("x") && !matrix[row+1][col].contains("b") )
                if(special){
                    if(row-1>=0 &&
                            (!matrix[row-1][col].contains("x") && !matrix[row-1][col].contains("b")))
                        output.add((row+1)+","+col);
                }
            else
                output.add((row+1)+","+col);


        //left of src
        if(col-1 >= 0 )
            if( !matrix[row][col-1].contains("x") && !matrix[row][col-1].contains("b"))
                if(special){
                    if(col+1<matrixCol &&
                            (!matrix[row][col+1].contains("x") && !matrix[row][col+1].contains("b")))
                        output.add(row+","+(col-1));
                }

            else
                output.add(row+","+(col-1));


        //right of src
        if(col+1 <matrixCol )
            if( !matrix[row][col+1].contains("x") && !matrix[row][col+1].contains("b"))
                if(special){
                    if(col-1>=0 &&
                            (!matrix[row][col-1].contains("x") && !matrix[row][col-1].contains("b")))
                        output.add(row+","+(col+1));
                }

                else
                    output.add(row+","+(col+1));

        return output;
    }



    ArrayList<String> pathTemp = new ArrayList<>();
    /**
     *  A function to perform a Depth-Limited search
     * 	from given source 'src'
     * @param src first location that we are in
     * @param targets locations that we want to go
     * @param maxDepth maximum depth that  we want to search
     * @return true if finding the target and else false
     */

    public boolean DLS(String src,ArrayList<String> targets , int maxDepth , boolean special){
        //if we find target return true
        for (String target : targets)
            if(src.equals(target)){
                find = true;
                srcF = target;
                targets.remove(target);
                return true;}

        if(maxDepth<0)
            return false;

        for (String nowLocation : neighbors(src,special)){
            if(DLS(nowLocation , targets,maxDepth-1,special))
                if(find){
                    pathTemp.add(nowLocation);
                    return true;
                }
        }
        return false;

    }


    /**
     *
      IDDFS to search if target is reachable from v.
      It uses recursive DLS()
      */
    public boolean IDDFS( ArrayList<String> targets , int maxDepth,boolean special,String src){
        find = false;
        srcF = src;
        int srcRow = Integer.parseInt(src.split(",")[0]);
        int srcCol = Integer.parseInt(src.split(",")[1]);
        matrix[srcRow][srcCol] = matrix[srcRow][srcCol].replace('b','B');
        for (int i=1 ; i<=maxDepth ; i++){
            if(DLS(srcF,targets,i,special)){
                System.out.println("finish target,Max depth:"+i);
                int srcFRow = Integer.parseInt(srcF.split(",")[0]);
                int srcFCol = Integer.parseInt(srcF.split(",")[1]);
                matrix[srcFRow][srcFCol] = matrix[srcFRow][srcFCol].replace('p','b');
                pathTemp.add(src);
                pathsButter.add(pathTemp);
                pathTemp = new ArrayList<>();
                return true;}
        }
        matrix[srcRow][srcCol] = matrix[srcRow][srcCol].replace('B','b');
        return false;
    }


    public void findRobotPath(){
        int pathsNumber= pathsButter.size();
        for(int i=0 ; i<pathsNumber ; i++) {
            ArrayList<String> path = new ArrayList<>();
            for (int j = pathsButter.get(i).size()-1 ; j > 0; j--) {
                String first = pathsButter.get(i).get(j);
                String second = pathsButter.get(i).get(j - 1);

                if(!path.contains(findDirection(first,second)))
                    if(j==pathsButter.get(i).size())
                        path.add(findDirection(first, second));
                    else{
                        
                    }

                path.add(first);

            }
            pathsRobot.add(path);
        }
    }



    private String findDirection(String first, String second) {

        String nexLocation = new String();

        int firstRow = Integer.parseInt(first.split(",")[0]);
        int firstCol = Integer.parseInt(first.split(",")[1]);

        int secRow = Integer.parseInt(second.split(",")[0]);
        int secCol = Integer.parseInt(second.split(",")[1]);

        if(firstRow==secRow && secCol>firstCol){
            nexLocation = firstRow+","+(firstCol-1);
            return nexLocation;}

        if(firstRow==secRow && secCol<firstCol){
            nexLocation = firstRow+","+(firstCol+1);
            return nexLocation;}

        if(firstCol==secCol && firstRow>secRow){
            nexLocation = (firstRow+1)+","+firstCol;
            return nexLocation; }

        else
            return (firstRow-1)+","+firstCol;



    }


    /**
     * this method return inverse of arrayList
     * @param input
     * @return
     */
    public ArrayList<String> inverse (ArrayList<String> input){
        ArrayList<String> inverse = new ArrayList<>();
        for (String s : input)
            inverse.add(s);
        return inverse;
    }


}
