package AI_Hw;


public class Main {

    public static void main(String[] args){
        Node ss = new Node(new char[]{'*','1','3','4','2','5','7','8','6'},0,null);
//        Node ss = new Node(new char[]{'1','2','*','4','5','3','7','8','6'},2,null);
        SearchMethods sm = new SearchMethods();
        sm.bfsSolution(ss);
        System.out.println("**************************");
        Node res = sm.idsAlgorithm(ss);
        System.out.println("**************************");
        res = sm.aStar1(ss);
        System.out.println("**************************");
        res = sm.aStar2(ss);
    }
}
