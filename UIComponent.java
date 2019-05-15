package AI_Hw;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.Stack;

import com.sun.tools.hat.internal.model.JavaLazyReadObject;
import javafx.scene.control.TextInputDialog;
import sun.misc.JavaLangAccess;

public class UIComponent implements Runnable{


    int[] arr;
    int blankRow = 0;
    int blankCol = 0;
    JFrame puzzleWindow;
    JButton[][] buttons;
    private Thread thread;
    Stack<Node> path;
    int b_index;
    int state;

    public UIComponent(){
    }

    public void showAnalysis(int n, int[] nVals,int[] goal, JFrame prev){
        JFrame analysis = new JFrame();
        JPanel main = new JPanel(new GridLayout(7,5,5,5));
        int ind=-1;
        for(int i=0;i<goal.length;i++){
            if(goal[i] == 0){
                ind = i;
            }
            if(nVals[i] == 0){
                this.b_index = i;
            }
        }
        String[] names = new String[]{"BFS","IDS","A* (Misplaced Tiles)","A* (Hamilton Distances)","A* (Nilsson's sequence score)"};
        JPanel[] data = new JPanel[names.length+1];

        // Headers
        data[0] = new JPanel(new GridLayout(1,5,5,5));
        JLabel name = new JLabel("Name");
        JLabel complete = new JLabel("Complete");
        JLabel depthLimit = new JLabel("Steps to Solve");
        JLabel stateEnqued = new JLabel("Run Time");
        JLabel optimal = new JLabel("Optimal");
        data[0].add(name);
        data[0].add(complete);
        data[0].add(depthLimit);
        data[0].add(stateEnqued);
        data[0].add(optimal);
        main.add(data[0]);

        //Run all Algo
        SearchMethods[] sm = new SearchMethods[names.length];

        //BFS

        sm[0] = new SearchMethods(new Node(goal,ind,null));
        long startTime = System.nanoTime();
        Stack<Node> bfs = sm[0].bfsSolution(new Node(nVals,this.b_index,null));
        long endTime = System.nanoTime();
        name = new JLabel(names[0]);
        complete = new JLabel((bfs==null)?"No":"Yes");
        depthLimit = new JLabel(String.valueOf(bfs.size()));
        stateEnqued = new JLabel(String.valueOf((endTime - startTime)));
        optimal = new JLabel("No");
        data[1] = new JPanel(new GridLayout(1,5,5,5));
        data[1].add(name);
        data[1].add(complete);
        data[1].add(depthLimit);
        data[1].add(stateEnqued);
        data[1].add(optimal);
        main.add(data[1]);

        //IDS
        sm[1] = new SearchMethods(new Node(goal,ind,null));
        startTime = System.nanoTime();
        Stack<Node> idfs = sm[1].idsAlgorithm(new Node(nVals,this.b_index,null));
        endTime = System.nanoTime();
        name = new JLabel(names[1]);
        complete = new JLabel((idfs==null)?"No":"Yes");
        depthLimit = new JLabel("15");
        stateEnqued = new JLabel(String.valueOf((endTime - startTime)));
        optimal = new JLabel("No");
        data[2] = new JPanel(new GridLayout(1,5,5,5));
        data[2].add(name);
        data[2].add(complete);
        data[2].add(depthLimit);
        data[2].add(stateEnqued);
        data[2].add(optimal);
        main.add(data[2]);


        //A* with misplaced tiles
        sm[2] = new SearchMethods(new Node(goal,ind,null));
        startTime = System.nanoTime();
        Stack<Node> a1star = sm[2].aStar1(new Node(nVals,this.b_index,null),null);
        endTime = System.nanoTime();
        name = new JLabel(names[2]);
        complete = new JLabel((a1star==null)?"No":"Yes");
        depthLimit = new JLabel(String.valueOf(a1star.size()));
        stateEnqued = new JLabel(String.valueOf(Math.sqrt(endTime - startTime)));
        optimal = new JLabel("Yes");
        data[3] = new JPanel(new GridLayout(1,5,5,5));
        data[3].add(name);
        data[3].add(complete);
        data[3].add(depthLimit);
        data[3].add(stateEnqued);
        data[3].add(optimal);
        main.add(data[3]);

        //A* with Hamliton Distance
        sm[3] = new SearchMethods(new Node(goal,ind,null));
        startTime = System.nanoTime();
        Stack<Node> a2star = sm[3].aStar2(new Node(nVals,this.b_index,null));
        endTime = System.nanoTime();
        name = new JLabel(names[3]);
        complete = new JLabel((a2star==null)?"No":"Yes");
        depthLimit = new JLabel(String.valueOf(a2star.size()));
        stateEnqued = new JLabel(String.valueOf(Math.sqrt(endTime - startTime)));
        optimal = new JLabel("Yes");
        data[4] = new JPanel(new GridLayout(1,5,5,5));
        data[4].add(name);
        data[4].add(complete);
        data[4].add(depthLimit);
        data[4].add(stateEnqued);
        data[4].add(optimal);
        main.add(data[4]);

        sm[4] = new SearchMethods(new Node(goal,ind,null));
        startTime = System.nanoTime();
        Stack<Node> a3star = sm[4].aStar3(new Node(nVals,this.b_index,null));
        endTime = System.nanoTime();
        name = new JLabel(names[4]);
        complete = new JLabel((a3star==null)?"No":"Yes");
        depthLimit = new JLabel(String.valueOf(a3star.size()));
        stateEnqued = new JLabel(String.valueOf(Math.sqrt(endTime - startTime)));
        optimal = new JLabel("Yes");
        data[5] = new JPanel(new GridLayout(1,5,5,5));
        data[5].add(name);
        data[5].add(complete);
        data[5].add(depthLimit);
        data[5].add(stateEnqued);
        data[5].add(optimal);
        main.add(data[5]);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });
        main.add(ok);
        prev.dispose();
        analysis.add(main);
        analysis.setVisible(true);
        analysis.setLocationRelativeTo(null);
        analysis.pack();



    }

    public void getInformation(){
        JFrame window = new JFrame("Number Puzzle");
        window.setLayout(new GridLayout(5,1,30,20));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // get n value
        JPanel inputPanel = new JPanel(new GridLayout(1,2,10,10));
        JLabel inpMsg = new JLabel("Enter the Value of N");
        JTextField inpSize = new JTextField();
        inputPanel.add(inpMsg);
        inputPanel.add(inpSize);


        //set the initial state
        JPanel numSeqPanel = new JPanel(new GridLayout(2,1,10,10));
        JLabel numSeq = new JLabel("<html>Enter the Sequence of the numbers from left to right and top to bottom<html>");
        JTextField inpSeq = new JTextField();
        numSeqPanel.add(numSeq);
        numSeqPanel.add(inpSeq);


        //get the final state
        JPanel goalSeqPanel = new JPanel(new GridLayout(2,1,10,10));
        JLabel goalSeq = new JLabel("<html>Enter the Sequence of the numbers from left to right and top to bottom for the GOAL<html>");
        JTextField goalSeqTxt = new JTextField();
        goalSeqPanel.add(goalSeq);
        goalSeqPanel.add(goalSeqTxt);



        //get which algorithm to run
        JPanel algoSelectpanel = new JPanel();
        ButtonGroup algoSelect = new ButtonGroup();
        String[] algoLabels = new String[]{"BFS","IDS","A* (Misplaced Tiles)","A* (Hamilton Distances)","A* (Nilsson's sequence score)","Solve Manually"};
        JRadioButton[] algos = new JRadioButton[algoLabels.length];
        for(int i=0;i<algos.length;i++){
            algos[i] = new JRadioButton(algoLabels[i]);
            algos[i].setActionCommand(algoLabels[i]);
            algoSelect.add(algos[i]);
            algoSelectpanel.add(algos[i]);
        }


        // Add the Analysis panel
        JPanel solvePanel = new JPanel();
        JButton analyze = new JButton("Analyze All Algorithms");
        JButton set = new JButton("set");
        solvePanel.add(analyze);
        solvePanel.add(set);

        //adding the panels to window JFrame
        window.add(inputPanel);
        window.add(numSeqPanel);
        window.add(goalSeqPanel);
        window.add(algoSelectpanel);
        window.add(solvePanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.pack();

        analyze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String algo = "";
                String n = inpSize.getText();
                String[] seq = inpSeq.getText().trim().split(" ");
                String[] goalStr = goalSeqTxt.getText().trim().split(" ");
                int[] nums = new int[seq.length];
                int[] goal = new int[seq.length];
                for(int i=0;i<seq.length;i++){
                    nums[i] = Integer.parseInt(seq[i]);
                    goal[i] = Integer.parseInt(goalStr[i]);
                }
//                window.dispose();
                showAnalysis(Integer.parseInt(n),nums,goal,window);
            }
        });

        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String algo = "";
                for(int i=0;i<algos.length;i++){
                    if(algos[i].isSelected()){
                        algo = algos[i].getActionCommand();
                    }
                }
                String n = inpSize.getText();
                String[] seq = inpSeq.getText().trim().split(" ");
                String[] goalStr = goalSeqTxt.getText().trim().split(" ");
                int[] nums = new int[seq.length];
                int[] goal = new int[seq.length];
                for(int i=0;i<seq.length;i++){
                    nums[i] = Integer.parseInt(seq[i]);
                    goal[i] = Integer.parseInt(goalStr[i]);
                }
                window.dispose();
                generatePuzzle(Integer.parseInt(n),nums,goal,algo);
            }
        });
    }

    public void swapButtons(int row, int col,int brow,int bcol){
        JButton temp = this.buttons[row][col];
        this.buttons[row][col] = this.buttons[brow][bcol];
        this.buttons[brow][bcol] = temp;
        this.buttons[row][col].putClientProperty("row",row);
        this.buttons[row][col].putClientProperty("col",col);
        this.buttons[brow][bcol].putClientProperty("col",bcol);
        this.buttons[brow][bcol].putClientProperty("row",brow);
        this.buttons[row][col].putClientProperty("butt",this.buttons);
        this.buttons[brow][bcol].putClientProperty("butt",this.buttons);
        this.blankRow = brow;
        this.blankCol = bcol;
    }

    public void moveTile(JFrame main, JButton[][] buttons,int row,int col){
        this.buttons = buttons;
        this.puzzleWindow = main;
        System.out.println(this.buttons+" "+row+" "+col+" "+this.blankRow+" "+this.blankCol+" "+this.buttons[row][col].getClientProperty("val"));
        if(row+1 < this.buttons.length && this.buttons[row+1][col].getClientProperty("val") == ""){
            this.swapButtons(row,col,row+1,col);
        }else if(row-1 > 0 && this.buttons[row-1][col].getClientProperty("val") == ""){
            this.swapButtons(row,col,row-1,col);
        }else if(col-1 > 0 && this.buttons[row][col-1].getClientProperty("val") == ""){
            this.swapButtons(row,col,row,col-1);
        }else if(col+1 > 0 && this.buttons[row][col+1].getClientProperty("val") == ""){
            this.swapButtons(row,col,row,col+1);
        }else{
            System.out.println("Cannot Move this tile");
        }
        JPanel buttonPanel = new JPanel(new GridLayout(buttons.length,buttons[0].length));
        for(int i=0;i<buttons.length;i++){
            for(int j=0;j<buttons[0].length;j++){
                buttonPanel.add(buttons[i][j]);
            }
        }
        this.puzzleWindow.add(buttonPanel);
        this.puzzleWindow.revalidate();
    }


    private JPanel setButtonPanel(int n, int[] nVals){
        int k=0;
        this.buttons = new JButton[n][n];
        JPanel buttonPanel = new JPanel(new GridLayout(n,n));
        for(int i=0;i<this.buttons.length;i++){
            for(int j=0;j<this.buttons[0].length;j++){
                if(nVals[k]==0){
                    this.b_index = k;
                    this.buttons[i][j] = new JButton(String.valueOf(""));
                    this.buttons[i][j].setVisible(false);
                    this.blankRow = i;
                    this.blankCol = j;
                    this.buttons[i][j].setPreferredSize(new Dimension(80,80));
                    this.buttons[i][j].putClientProperty("val","");
                    this.buttons[i][j].putClientProperty("row",i);
                    this.buttons[i][j].putClientProperty("col",j);
                    k++;
                }else{
                    this.buttons[i][j] = new JButton(String.valueOf(nVals[k]));
                    this.buttons[i][j].setPreferredSize(new Dimension(80,80));
                    this.buttons[i][j].putClientProperty("val",nVals[k++]);
                    this.buttons[i][j].putClientProperty("row",i);
                    this.buttons[i][j].putClientProperty("col",j);

                }
                this.buttons[i][j].putClientProperty("butt",this.buttons);
                this.buttons[i][j].putClientProperty("main",this.puzzleWindow);
                this.buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        UIComponent ui = new UIComponent();
                        ui.moveTile((JFrame)((JButton)e.getSource()).getClientProperty("main"),
                                (JButton[][]) ((JButton)e.getSource()).getClientProperty("butt"),
                                (int)((JButton)e.getSource()).getClientProperty("row"),
                                (int)((JButton)e.getSource()).getClientProperty("col"));
                        System.out.println("here");

                    }
                });
                buttonPanel.add(buttons[i][j]);
            }
        }
        return buttonPanel;
    }

    private void showSummary(String msg){
        JFrame summary = new JFrame("Summary");
        summary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel main = new JPanel(new GridLayout(2,1,10,10));
        JLabel sumMsg = new JLabel(msg);
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });
        main.add(sumMsg);
        main.add(ok);
        summary.add(main);
        summary.setVisible(true);
        summary.setLocationRelativeTo(null);
        summary.pack();
    }

    public void generatePuzzle(int n, int[] nVals,int[] goal,String algo) {
        this.arr = nVals;
        String title = (n*n)-1+" Puzzle";
        this.puzzleWindow =new JFrame(title);
        puzzleWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = this.setButtonPanel(n,nVals);
        puzzleWindow.add(buttonPanel);
        puzzleWindow.pack();
        puzzleWindow.setLocationRelativeTo(null);
        puzzleWindow.setVisible(true);
        int gb_index = -1;
        for(int i=0;i<goal.length;i++){
            if(goal[i] == 0){
                gb_index = i;
            }
        }
        boolean manualBool = false;
        SearchMethods sm = new SearchMethods(new Node(goal,gb_index,null));
        switch (algo){
            case "BFS":
                this.path = sm.bfsSolution(new Node(nVals,this.b_index,null));
                break;
            case "IDS":
                this.path = sm.idsAlgorithm(new Node(nVals,this.b_index,null));
                break;
            case "A* (Misplaced Tiles)":
                this.path = sm.aStar1(new Node(nVals,this.b_index,null),this);
                break;
            case "A* (Hamilton Distances)":
                this.path = sm.aStar2(new Node(nVals,this.b_index,null));
                break;
            case "A* (Nilsson's sequence score)":
                this.path = sm.aStar3(new Node(nVals,this.b_index,null));
                break;
            default:
                // manual play
                manualBool = true;
                break;
        }
        if(manualBool){
            JFrame manual = new JFrame();
            manual.add(this.setButtonPanel(n,nVals));
            manual.pack();
            manual.setLocationRelativeTo(null);
            manual.setVisible(true);
        }else{
            System.out.println(sm.enque);
            this.state = sm.enque;
            if(this.path == null){
                System.out.println("Reaching a Depth limit of 10 the "+ algo +" algo was not able to find the solution");
                String msg = "Reaching a Depth limit of 10 the "+ algo +" algo was not able to find the solution with enqueued states :"+this.state;
                this.showSummary(msg);
            }else{
                this.start();
            }
        }

    }

    public void start(){
        if(this.thread == null){
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    @Override
    public void run() {
//        System.out.println("hererere");
        this.state = this.path.size();
        while(!this.path.isEmpty()){
            Node pop = this.path.pop();
            System.out.println(pop);
            try
            {
                Thread.sleep(1500);
            }
            catch(InterruptedException _ex)
            {
                System.out.print("Program has been interrupted");
            }
            JPanel buttonPanel = this.setButtonPanel(3,pop.puz);
            this.puzzleWindow.add(buttonPanel);
            this.puzzleWindow.revalidate();
        }
        String msg = "The Selected Algorithm took "+this.state+" states to reach the final goal";
        this.showSummary(msg);

    }
}


// 2 1 3 8 0 4 6 7 5
// 1 2 3 8 0 4 7 6 5