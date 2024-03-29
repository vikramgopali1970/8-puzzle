package AI_Hw;

import java.util.*;

public class SearchMethods {

    public static final int DEPTH_LIMIT = 15;
//    public final Node goal = new Node(new int[]{1,2,3,4,5,6,7,8,0},8,null);
    Node goal;
    int enque;

    public SearchMethods(Node goal){
        this.goal = goal;
    }

    public Stack<Node> bfsSolution(Node startState){
        HashSet<Node> set = new HashSet<Node>();
        Queue<Node> q = new LinkedList<Node>();
        q.offer(startState);
        Node c_Node = q.peek();
        Node res = null;
        set.add(c_Node);
        int lvl = 0;
        int len = q.size();
        while (!(Arrays.equals(c_Node.puz,goal.puz))) {
            c_Node = q.poll();
            len--;
            ArrayList<Node> children = c_Node.getChildrenNodes();
            for (Node child : children) {
                this.enque++;
                if (set.contains(child)) {
                    continue;
                } else {
//                    if(Arrays.equals(c_Node.puz,goal.puz)){
//                        res = c_Node;
//                        break;
//                    }
                    set.add(child);
                    q.offer(child);
                }
            }
            if(len == 0){
                len = q.size();
                lvl++;
            }
        }
        if(Arrays.equals(c_Node.puz,goal.puz)){
//            System.out.println("Number of states enqueued : "+this.enque+" "+c_Node);
            return c_Node.printSolutionPath(c_Node,null);

        }else{
//            System.out.println("Maximum Depth of 10 reached without finding the goal");
//            System.out.println(-1);
            return null;
        }
    }

    private Node dfs(Node root, int limit){
        if(Arrays.equals(root.puz,goal.puz)){
            return root;
        }else if(limit<0){
            return null;
        }else{
            ArrayList<Node> children = root.getChildrenNodes();
            for(Node child : children){
                Node res = this.dfs(child,limit-1);
                if(res != null){
                    return res;
                }
            }
            return null;
        }
    }

    public Stack<Node> idsAlgorithm(Node startState){
        int limit = 0;
        while(limit <= DEPTH_LIMIT){
//            System.out.println("for depth "+limit);
            Node res = this.dfs(startState,limit);
            if(res != null){
                return res.printSolutionPath(res,null);
            }
            limit++;
        }
//        System.out.println("-1");
        return null;
    }


    public Stack<Node> aStar1(Node startState,UIComponent ui){
        PriorityQueue<Node> q = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.f_score,o2.f_score);
            }
        });
        HashSet<Node> set = new HashSet<Node>();
        Node temp = startState;
        int g = 0;
        temp.f_score =  g + temp.getMisplacedTiles(goal);
        q.offer(temp);
        g++;
        while(!Arrays.equals(temp.puz,goal.puz) && !q.isEmpty()){
            temp = q.poll();
            set.add(temp);
            ArrayList<Node> children = temp.getChildrenNodes();
            for(Node child : children){
                this.enque++;
                if(set.contains(child)){
                    continue;
                }
                child.f_score = g+child.getMisplacedTiles(goal);
                q.add(child);
            }
            g++;
        }
        return temp.printSolutionPath(temp,ui);
    }


    public Stack<Node> aStar2(Node startState){
        PriorityQueue<Node> q = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.f_score,o2.f_score);
            }
        });
        HashSet<Node> set = new HashSet<Node>();
        Node temp = startState;
        int g = 0;
        temp.f_score =  g + temp.getManhattenDistance(goal);
        q.offer(temp);
        g++;

        while(!Arrays.equals(temp.puz,goal.puz) && !q.isEmpty()){
            temp = q.poll();
            set.add(temp);
            ArrayList<Node> children = temp.getChildrenNodes();
            for(Node child : children){
                this.enque++;
                if(set.contains(child)){
                    continue;
                }
                child.f_score = g+child.getManhattenDistance(goal);
                q.add(child);
            }
            g++;
        }
        return temp.printSolutionPath(temp,null);
    }


    public Stack<Node> aStar3(Node startState){
        PriorityQueue<Node> q = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.f_score,o2.f_score);
            }
        });
        HashSet<Node> set = new HashSet<Node>();
        Node temp = startState;
        int g = 0;
        temp.f_score =  temp.NilsonsSequenceScore(goal);
        q.offer(temp);
        g++;

        while(!Arrays.equals(temp.puz,goal.puz) && !q.isEmpty()){
            temp = q.poll();
            set.add(temp);
            ArrayList<Node> children = temp.getChildrenNodes();
            for(Node child : children){
                this.enque++;
                if(set.contains(child)){
                    continue;
                }
                child.f_score = child.NilsonsSequenceScore(goal);
                q.add(child);
            }
            g++;
        }
        return temp.printSolutionPath(temp,null);
    }




}
