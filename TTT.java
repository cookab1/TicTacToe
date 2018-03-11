import java.util.Scanner;

public class TTT extends Game
{
    /*
     * Implementing the TTTBoard as such
     *
     *      7|8|9
     *      4|5|6
     *      1|2|3
     * 
     * The boolean arrays are the two players past moves.
     * True == has placed a marker there.
     */
    private boolean[] P1;
    private boolean[] P2;
    private boolean turn; //Who's turn is it? false == P1
                          //                  true  == P2
    private int turnCount;  //counting the turn number
    private boolean p1Won;
    private boolean p2Won;
    private boolean cat;

    private String[] names = new String[2];         //holds the names of the players
                 //what the Computer will say to the players


    private Scanner sc;
    private boolean first;  //a boolean that is used throughout to
                            //keep track of the first iteration

    public void initialize()
    { 
        P1 = new boolean[10];
        P2 = new boolean[10];
        names = new String[2];
        p1Won = false;
        p2Won = false;
        cat = false;
        doContinue = true;
        first = true;
        turnCount = 0;
    }
    public void loadContent(String[] name)
    {
        names = name;
        sc = new Scanner(System.in);
        //System.out.print("Enter in Players' names.\nPlayer 1: ");
        //names[0] = sc.next();
        //System.out.print("Player 2: ");
        //names[1] = sc.next();
        boolean notEntered = true;
        System.out.println("\nWho's going first? " + names[0] + " or " + names[1]);
        first = true;
        do
        {
            if(!first)
                System.out.println("Who?");
            String input = sc.next();
            if(input.equals(names[0]) || input.equals(names[1]))
            {
                if(input.equals(names[0]))
                    turn = false;
                else
                    turn = true;
                notEntered = false;
            }
            first = false;
        } while(notEntered);
        System.out.println("Put your markers on the board\n"    
                            + "  using numbers 1 - 9 as shown.");
        printOrientation();
    }
    
    public void printOrientation()
    {
        System.out.println("\n"
             + "    7|8|9\n"
             + "    4|5|6\n"
             + "    1|2|3\n");
    }

    /*
     * who's turn is it?
     * ask that person to place a marker
     * get where the player would like to place his marker
     * only if the marker is in the range of 1-9,
     * place the marker
     */
    public void userAction()
    {
        String[] forP1 = {"\nGo " + names[0] + "! Start us off strong!",
                              "Aww. You've totally got this " + names[0] + ".",
                              "Make another killer move " + names[0] + ".",
                              "In and out in a tight 15. Eh, " + names[0] + "?",
                              "Finish him off " + names[0] + "."};
        String[] forP2 = {"Make a move " + names[1] + ".",
                              "Do whatever you want to " + names[1] + ". We all know who's going to win.",
                              "Do you need me to explain the rules " + names[1] + "? The goal is to get 3 in a row.",
                              "Just remember, nobody likes a sore loser " + names[1] + ".",
                              "You're not doing so hot. Eh, " + names[1] + "?"};
        if(!turn)
            System.out.println(forP1[turnCount]);
        else
        {
            System.out.println(forP2[turnCount]);
            if(turnCount >= 4)
                turnCount = 2;
            else
                turnCount++;
        }
        int input;
        boolean[] error = {false, false};
        first = true;
        do
        {
            if(!turn)
            {
                if(!first && error[0])
                    System.out.println("\npst... positions are 1 - 9.");
                if(!first && error[1])
                    System.out.println("\nThat must have been a typo. " 
                                        + "Try a different spot that's not taken.");
            }
            else
            {
                if(!first && error[0])
                {
                    System.out.println("\nLet me remind you of your options.");
                    printOrientation();
                }
                if(!first && error[1])
                    System.out.println("\nMaybe I didn't explain the game well enough.\n"
                                        + "  You have to put your marker on an unoccupied space.");
            }
            //input = getInput();
            input = sc.nextInt();
            first = false;
            error = isValid(input);
        } while (error[0] || error[1]);
        
        if(!turn)
            P1[input] = true;
        else
            P2[input] = true;;

        turn = !turn;
    }
    
    /*
    public int getInput()
    {
        try
        {
            return sc.nextInt();
        } catch (InputMismatchException e)
        {
            System.out.println("\nPlease enter a number from 1 - 9.");
            return getInput();
        }
    }
    */

    public boolean[] isValid(int input)
    {
        boolean[] error = {false, false};
        if(input < 1 || input > 9)
            error[0] = true;
        else if(P1[input] || P2[input])
            error[1] = true;
        return error;
    }

    public void draw()
    {
        printBoard();
    }

    public boolean evaluateState()
    {
        if(hasWon(P1))
            p1Won = true;
        if(hasWon(P2))
            p2Won = true;
        if(isCat())
            cat = true;
        if(p1Won || p2Won || cat)
            return false;
        return true;
    }

    public boolean isCat()
    {
        for(int i = 1; i < 10; i++)
        {
            if(P1[i] == false && P2[i] == false)
                return false;
        }
        return true;
    }

    public boolean hasWon(boolean[] p)
    {
       if((p[1] && p[2] && p[3])
           || (p[4] && p[5] && p[6])
           || (p[7] && p[8] && p[9])
           || (p[1] && p[4] && p[7])
           || (p[2] && p[5] && p[8])
           || (p[3] && p[6] && p[9])
           || (p[1] && p[5] && p[9])
           || (p[3] && p[5] && p[7]))
           return true;
       return false;
    }

    public void report()
    {
        if(p1Won)
        {
            System.out.println("\n" + names[0] + " has won!");
            System.out.println("Were you even trying, " + names[1] + "\n");
        }
        else if(p2Won)
        {
            System.out.println("\nWow! Wasn't expecting that.");
            System.out.println(names[1] + " has won.\n");
        }
        else
        {
            System.out.println("\n");
        }
    }

    public void printBoard()
    {
        char[] board = new char[10];
        for(int i = 1; i < 10; i++)
        {
            if(P1[i] == true)
                board[i] = 'O';
            else if(P2[i] == true)
                board[i] = 'X';
            else
                if(i == 1 || i == 2 || i == 3)
                    board[i] = ' ';
                else
                    board[i] = '_';
        }
        System.out.println("    " + board[7] + "|" + board[8] + "|" + board[9] + "\n"
                         + "    " + board[4] + "|" + board[5] + "|" + board[6] + "\n"
                         + "    " + board[1] + "|" + board[2] + "|" + board[3] + "\n");
    }
}
