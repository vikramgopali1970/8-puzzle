package AI_Hw;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Node {
    public int[] puz;
    private int b_index;
    public Node parent;
    public int f_score;
    UIComponent ui;

    public Node(int[] puz,int b_index,Node parent){
        this.ui = new UIComponent();
        this.parent = parent;
        this.puz = puz;
        this.b_index = b_index;
        this.f_score = 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<this.puz.length;i++){
            sb.append(this.puz[i]);
            if(i%3 == 2){
                sb.append(System.getProperty("line.separator"));
            }
        }
        return sb.toString();
    }

    private int[] swap(int[] arr, int pos1, int pos2){
        int temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
        return arr;
    }

    public ArrayList<Node> getChildrenNodes(){
        ArrayList<Node> children = new ArrayList<Node>();
        //move up
        int up = this.b_index-3;
        if(up >= 0){
            int[] arr = this.puz.clone();
            children.add(new Node(this.swap(arr,this.b_index,up),up,this));
        }

        // move down
        int down = this.b_index+3;
        if(down < this.puz.length){
            int[] arr = this.puz.clone();
            children.add(new Node(this.swap(arr,this.b_index,down),down,this));
        }

        // move right
        if(this.b_index%3 < 2){
            int right = this.b_index+1;
            int[] arr = this.puz.clone();
            children.add(new Node(this.swap(arr,this.b_index,right),right,this));
        }

        // move left
        if(this.b_index%3 > 0){
            int left = this.b_index-1;
            int[] arr = this.puz.clone();
            children.add(new Node(this.swap(arr,this.b_index,left),left,this));
        }

        return children;
    }

    public boolean equals(Object o){
        Node obj = (Node)o;
        if(Arrays.equals(obj.puz,this.puz) && (this.b_index == obj.b_index)){
            return true;
        }else{
            return false;
        }
    }

    public int hashCode(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<this.puz.length;i++){
            sb.append(this.puz[i]);
        }
        sb.append(this.b_index);
        return sb.toString().hashCode();
    }

    public Stack<Node> printSolutionPath(Node n,UIComponent ui){
        Stack<Node> s = new Stack<Node>();
        while(n != null){
            s.push(n);
            n = n.parent;
        }
        System.out.println("number of steps ae "+s.size());
        return s;
    }

    public int getMisplacedTiles(Node goal){
        int count=0;
        for(int i=0;i<goal.puz.length;i++){
            if(this.puz[i] != goal.puz[i]){
                count++;
            }
        }
        return count;
    }

    public int getManhattenDistance(Node goal){
        int dist = 0;
        for(int i=0;i<this.puz.length;i++){
            for(int j=0;j<goal.puz.length;j++){
                if(this.puz[i] == goal.puz[j]){
                    int d = ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 - j / 3));
                    dist+=d;
                }
            }
        }
        return dist;
    }


    public int NilsonsSequenceScore(Node goal){
        int score = 0;
        int prev = -1;
        int[] temp = new int[this.puz.length];
        temp[0] = this.puz[0];
        temp[1] = this.puz[1];
        temp[2] = this.puz[2];
        temp[5] = this.puz[3];
        temp[8] = this.puz[4];
        temp[7] = this.puz[5];
        temp[6] = this.puz[6];
        temp[3] = this.puz[7];
        temp[4] = this.puz[8];

        int[] goal_puz = new int[]{1,2,3,8,0,4,7,6,5};
        int[] goal_puz1 = new int[]{1,2,3,4,5,6,7,8,0};
        for(int i=0;i<this.puz.length;i++){
            for(int j=0;j<goal_puz.length;j++){
                if(this.puz[i] == goal_puz[j]){
                    int d = ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 - j / 3));
                    score+=d;
                }

            }
            if(i == 8){
                score += 3*1;
            }else if(temp[i+1] != goal_puz1[i+1]){
                score += 3*2;
            }
        }
        return score;
    }

}
