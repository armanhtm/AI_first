package com.company;

import java.util.ArrayList;

public class Node {
    //location of node
    public String location ;
    //cost of node
    public int cost;
    //cost of between src to this node
    public int g;
    //cost from this node to goal
    public int heuristic;
    //
    public String content;
    //parent of this node
    public Node parent;

    /**
     * constructor
     * @param location
     * @param content
     */
    public Node(String location,String content){
        this.location = location;
        this.content = content;

        StringBuilder digits = new StringBuilder();
        for (Character ch : content.toCharArray()){
            if(Character.isDigit(ch))
                digits.append(ch);
        }
        g =  Integer.parseInt(String.valueOf(digits));

    }

    /**
     * this method calculate heuristic with manhatan
     * @param goal
     */
    public void calculateHeuristic(String goal){
        int goalRow = Integer.parseInt(goal.split(",")[0]);
        int goalCol = Integer.parseInt(goal.split(",")[1]);

        int nodeRow = Integer.parseInt(location.split(",")[0]);
        int nodeCol = Integer.parseInt(location.split(",")[1]);

        heuristic = Math.abs(goalCol-nodeCol)+Math.abs(goalRow-nodeRow);

    }
}
