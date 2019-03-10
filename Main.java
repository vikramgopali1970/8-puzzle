package AI_Hw;



import java.util.Scanner;

public class Main {

    public static void showIntroMessage(){
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Welcome to 8 - puzzle solver");
        System.out.println("Press 1 to configure/re-configure the 8 - puzzle");
        System.out.println("Press 2 for BFS search on 8 - puzzle");
        System.out.println("Press 3 for Iterative deepening on 8 - puzzle");
        System.out.println("Press 4 A* heuristic with number of misplaced tiles on 8 - puzzle");
        System.out.println("Press 5 A* heuristic with manhattan distance for number of misplaced tiles on 8 - puzzle");
        System.out.println("Press -1 to exit the 8 - puzzle");
    }

    public static void main(String[] args){
        char[] puz = new char[9];
        int b_index = -1;
        Scanner sc = new Scanner(System.in);
        SearchMethods sm = new SearchMethods();
        boolean infinite = true;
        while(infinite){
            showIntroMessage();
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Please enter the configuration of the puzzle from left to right and from top to bottom.");
                    System.out.println("input * to represent the blank space.");
                    for(int i=0;i<puz.length;i++){
                        puz[i] = sc.next().charAt(0);
                        if(puz[i] == '*'){
                            b_index = i;
                        }
                    }
                    if(b_index == -1){
                        System.out.println("Wrong configuration without blank space");
                    }else{
                        System.out.println(new  Node(puz,b_index,null));
                    }
                    break;
                case 2:
                    System.out.println("Performing the BFS search : ");
                    sm.bfsSolution(new Node(puz,b_index,null));
                    break;
                case 3:
                    System.out.println("Performing the Iterative Deepening search : ");
                    sm.idsAlgorithm(new Node(puz,b_index,null));
                    break;
                case 4:
                    System.out.println("Performing the A* Heuristic search with Number of Miss placed Tiles : ");
                    sm.aStar1(new Node(puz,b_index,null));
                    break;
                case 5:
                    System.out.println("Performing the A* Heuristic search with Manhattan Distance of Number of Miss placed Tiles : ");
                    sm.aStar2(new Node(puz,b_index,null));
                    break;
                case -1:
                    infinite = false;
                    break;
                default:
                    System.out.println("Please choose any of the above options");
                    break;
            }
        }
    }
}
