package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
            if(!matrix[row-1][col].contains("x") && !matrix[row-1][col].contains("b"))
                if(special){
                    if(row+1<matrixRow &&
                            (!matrix[row+1][col].contains("x") && !matrix[row+1][col].contains("b")))
                        output.add((row-1)+","+col);}
                else
                    output.add((row-1)+","+col);



        //down of src
        if(row+1 < matrixRow )
            if( !matrix[row+1][col].contains("x") && !matrix[row+1][col].contains("b"))
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

    /**
     * this method find robot path
     * @return
     */
    public boolean findRobotPath(){
        //set plate

        int pathsNumber = pathsButter.size();
        for(int i=0 ; i<pathsNumber ; i++) {
            ArrayList<String> path = new ArrayList<>();
            for (int j = pathsButter.get(i).size()-1 ; j > 0; j--) {
                String first = pathsButter.get(i).get(j);
                String second = pathsButter.get(i).get(j - 1);

                if(!pathsButter.get(i).contains(findDirection(first,second)))
                    if(j==pathsButter.get(i).size()-1)
                        path.add(findDirection(first, second));

                    else{
                        addButter(first);
                        ArrayList<String> targets = new ArrayList<>();
                        targets.add(findDirection(first,second));
                        Graph graph = new Graph(matrix,matrixRow,matrixCol);
                        ArrayList<String> aStarArray = new ArrayList<>();
                        aStarArray.add(pathsButter.get(i).get(j+1));
                        //if(graph.IDDFS(targets,Math.min(matrixRow,matrixCol),false,pathsButter.get(i).get(j+1)))
                        if(graph.aStarMain(aStarArray,targets,false) != 0)
                        {
                            for (String location : inverse(graph.pathsButter.get(0)))
                                path.add(location);
                            removeButter(first);}
                        else {
                            addButter(findDirection(first,second));
                            removeButter(first);
                            return false;}
                    }

                path.add(first);



            }
            pathsRobot.add(path);

        }
        return true;
    }

    /**
     * this method remove butter from location
     * @param first
     */
    private void removeButter(String first) {
        int firstRow = Integer.parseInt(first.split(",")[0]);
        int firstCol = Integer.parseInt(first.split(",")[1]);

        matrix[firstRow][firstCol] = matrix[firstRow][firstCol].replace("b","");
    }

    /**
     * this method get location and add a butter to it
     * @param first
     */
    private void addButter(String first) {
        int firstRow = Integer.parseInt(first.split(",")[0]);
        int firstCol = Integer.parseInt(first.split(",")[1]);

        matrix[firstRow][firstCol] = matrix[firstRow][firstCol]+"b";
    }


    /**
     *
     * @param first
     * @param second
     * @return
     */
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
        for (int i=input.size()-1 ; i>=0 ; i--)
            inverse.add(input.get(i));
        return inverse;
    }


    public Node Min ;
    /**
     * A* serch method
     * @return
     */
    public boolean aStar(String src,String goal,boolean special){
        Min = null;
        removeButter(src);
        //1.  Initialize the open list
        //2.  Initialize the closed list
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closeList = new ArrayList<>();

        //put the starting node on the open list
        Node srcNode = new Node(src,content(src,matrix));
        openList.add(srcNode);

        //while open list is not empty
        while (!openList.isEmpty()){
            //find the node with the least f on the open list, call it "min"
            Node min = listMinFind(openList);
            //pop min off the open list
            openList.remove(min);
            //generate min's successors and set their parents to min
            ArrayList<Node> successors = new ArrayList<>();
            for (String neighbor : neighbors(min.location,special)){
                Node node = new Node(neighbor,content(neighbor,matrix));
                node.parent = min;
                successors.add(node);
            }
            //goal test and add path
            if(min.location.equals(goal)){
                Min = min;
                addButter(goal);
                return true;}

            for (Node neighbor : successors){
                //set costs
                neighbor.g += min.g;
                neighbor.calculateHeuristic(goal);
                neighbor.cost = neighbor.g+neighbor.heuristic;
                //if a node with the same position as successor is in the OPEN list which has a
                //lower cost than successor, skip this successor
                boolean lessCostNode = false;

                for (Node node : openList)
                    if (node.location.equals(neighbor.location) && node.cost<=neighbor.cost)
                        lessCostNode = true;
                //if a node with the same position as successor  is in the CLOSED list which has
                // a lower f than successor, skip this successor
                for (Node node : closeList)
                    if (node.location.equals(neighbor.location) && node.cost<=neighbor.cost)
                        lessCostNode = true;

                if(lessCostNode)
                    continue;

                //otherwise, add  the node to the open list
                openList.add(neighbor);

            }
            //push min to closed list
            closeList.add(min);
        }
        addButter(src);
        return false;

    }

    /**
     * this method get goal node and find path to it and add to pathButter
     * @param min
     */
    private void findPathAstar(Node min) {
        Node node = min;
        pathTemp = new ArrayList<>();
        while (node.parent!=null) {
            pathTemp.add(node.location);
            node = node.parent;
        }
        pathTemp.add(node.location);
        pathsButter.add(pathTemp);

    }

    /**
     * this method find the minimum cost node in the list
     * @param list
     */
    private Node listMinFind(ArrayList<Node> list) {
        Node min ;
        min = list.get(0);
        for (Node node : list)
            if(min.cost > node.cost)
                min = node;

        return min;

    }

    /**
     * content of graph in location
     * @param location
     * @param matrix
     * @return
     */
    private String content(String location, String[][] matrix) {
        int row = Integer.parseInt(location.split(",")[0]);
        int col = Integer.parseInt(location.split(",")[1]);
        return matrix[row][col];

    }


    /**
     *
     * @param targetsB
     * @param targetsP
     */
    public int aStarMain(ArrayList<String> targetsB,ArrayList<String> targetsP,boolean special){
        int output = 0;
        //this hashMap hold pathes from butter to plate
        HashMap<String,ArrayList<Node>> bToP = new HashMap<>();
        //find path and put to hashMap
        for (String butter : targetsB) {
            ArrayList<Node> nodes = new ArrayList<>();
            for (String plate : targetsP) {
                setPlateLocation(targetsP,plate);
                aStar(butter, plate,special);
                if(Min!=null){
                    nodes.add(Min);
                    output+=1;}

            }
            bToP.put(butter,nodes);
        }
        //find the butter that have less path and add main path of that to pathButter
        while (!bToP.isEmpty()){

            String butterLess = bToP.keySet().iterator().next();
            for (String butterKey : bToP.keySet()){
                if (bToP.get(butterKey).size() <= bToP.get(butterLess).size())
                    butterLess = butterKey;}

            //find minimum node of butterLess key
            if(bToP.get(butterLess).size()==0){
                bToP.remove(butterLess);
                System.out.println(butterLess+" : not found any path/ code=b-->p");
                continue;}

            Node minCostNode = bToP.get(butterLess).get(0);
            for (Node node : bToP.get(butterLess))
                if(node.cost<=minCostNode.cost)
                    minCostNode=node;
            //find path of min cost node
            findPathAstar(minCostNode);
            //remove from hashMap
            bToP.remove(butterLess);
            removeFindedPlat(minCostNode,bToP);

        }
        return output;

    }

    /**
     * this method remove nodes that finded in
     * @param minCostNode
     * @param bToP
     */
    private void removeFindedPlat(Node minCostNode, HashMap<String, ArrayList<Node>> bToP) {
        for (String butter : bToP.keySet()){
            Iterator<Node> it = bToP.get(butter).iterator();
            while (it.hasNext())
                if(it.next().location.equals(minCostNode.location))
                    it.remove();
        }
    }

    /**
     * this method add butter to each plate location and remove butter from target location
     * @param targetsP
     * @param plate
     */
    private void setPlateLocation(ArrayList<String> targetsP, String plate) {
        for (String p : targetsP)
            if(p.equals(plate))
                removeButter(plate);
            else
                addButter(p);
    }

    /**
     * this method calculate  manhatan
     * @param goal
     */
    public int calculateManhatan(String src,String goal){
        int goalRow = Integer.parseInt(goal.split(",")[0]);
        int goalCol = Integer.parseInt(goal.split(",")[1]);

        int srcRow = Integer.parseInt(src.split(",")[0]);
        int srcCol = Integer.parseInt(src.split(",")[1]);

        return Math.abs(goalCol-srcCol)+Math.abs(goalRow-srcRow);

    }
}
