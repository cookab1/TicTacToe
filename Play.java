import java.util.Scanner;

public class Play
{
    public static void main(String[] args)
    {
        System.out.println("\n     Welcome!  To the game of TicTacToe!\n");
        Game ttt = new TTT();
        Scanner sc = new Scanner(System.in);
        String[] names = new String[2];
        String answer;
        boolean playAgain = true;
        boolean first = true;
        while(playAgain)
        {
            if(first)
            {
                getNames(sc, names);
            }
            else
            {
                System.out.println("\nWould you like to use the same names?");
                System.out.println("Yes or No?");
                answer = Character.toString(Character.toLowerCase((sc.next().charAt(0))));
                if(answer.equals("n"))
                    getNames(sc, names);
            }
            ttt.run(names);
            System.out.println("\nWould you like to play again?");
            System.out.println("Yes or No?");
            answer = Character.toString(Character.toLowerCase((sc.next().charAt(0))));
            if(answer.equals("n"))
                playAgain = false;
            first = false;      
        }
    }
    
    public static void getNames(Scanner sc, String[] names)
    {
        System.out.print("Enter in Players' names.\nPlayer 1: ");
        names[0] = sc.next();
        System.out.print("Player 2: ");
        names[1] = sc.next();
    }
}
