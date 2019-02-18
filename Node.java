package AI_Hw;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Node {
    public char[] puz;
    private int b_index;
    public Node parent;
    public int f_score;

    public Node(char[] puz,int b_index,Node parent){
        this.parent = parent;
        this.puz = puz;
        this.b_index = b_index;
        this.f_score = 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<this.puz.length;i++){
            sb.append(this.puz[i]+" ");
            if(i%3 == 2){
                sb.append(System.getProperty("line.separator"));
            }
        }
        return sb.toString();
    }

    private char[] swap(char[] arr, int pos1, int pos2){
        char temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
        return arr;
    }

    public ArrayList<Node> getChildrenNodes(){
        ArrayList<Node> children = new ArrayList<Node>();
        //move up
        int up = this.b_index-3;
        if(up >= 0){
            char[] arr = this.puz.clone();
            children.add(new Node(this.swap(arr,this.b_index,up),up,this));
        }

        // move down
        int down = this.b_index+3;
        if(down < this.puz.length){
            char[] arr = this.puz.clone();
            children.add(new Node(this.swap(arr,this.b_index,down),down,this));
        }

        // move right
        if(this.b_index%3 < 2){
            int right = this.b_index+1;
            char[] arr = this.puz.clone();
            children.add(new Node(this.swap(arr,this.b_index,right),right,this));
        }

        // move left
        if(this.b_index%3 > 0){
            int left = this.b_index-1;
            char[] arr = this.puz.clone();
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

    public void printSolutionPath(Node n){
        Stack<Node> s = new Stack<Node>();
        while(n != null){
//            System.out.println(n);
            s.push(n);
            n = n.parent;
        }
        int steps = 0;
        while(!s.isEmpty()){
            System.out.println(s.pop());
            steps++;
        }
        System.out.println("Number of moves = "+steps);
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
                dist += ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j / 3));
            }
        }
        return dist;
    }

}
