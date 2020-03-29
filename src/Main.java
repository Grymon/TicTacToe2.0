import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static ArrayList<Integer> player1Positions = new ArrayList<>();
    static ArrayList<Integer> player2Positions = new ArrayList<>();
    private static int round = 1;

    public static void main(String[] args) {

        String player1name;
        String player2name;
        String[][] board = initBoard();
        String result = "";

        Scanner in = new Scanner(System.in);

        System.out.println("Input Player 1 name");
        player1name = in.nextLine();

        System.out.println("Input Player 2 name");
        player2name = in.nextLine();

        System.out.printf("Player 1 - %s - plays with O, Player 2 - %s - plays with X\n", player1name, player2name);

        playGame(player1name, player2name, board, result, in);

    }

    public static void playGame(String player1name, String player2name, String[][] board, String result, Scanner in) {
        int playerPosition;
        do {
            System.out.println("\nRound number " + round);
            String playerSymobl = determinSymbol(round);
            System.out.println("Now " + ((round %2 == 0)?player2name:player1name));
            System.out.printf("Where do you want to place your token? (%s)\n", playerSymobl);

            System.out.print("Specify place on the board(1-9)");
            playerPosition = Integer.parseInt(in.nextLine());
            playerPosition = checkIfPositionTaken(playerPosition, in);
            if (checkPosition(playerPosition)) continue;
            placePiece(board, playerSymobl,playerPosition);
            drawBoard(board);
            round++;
            result = checkWinner(player1name, player2name);
            System.out.println(result);

        }while (result.isEmpty());
    }

    public static int checkIfPositionTaken(int playerPosition, Scanner in) {
        while(player1Positions.contains(playerPosition)||player2Positions.contains(playerPosition)){
            System.out.println("Position taken, place your token again");
            playerPosition = Integer.parseInt(in.nextLine());
        }
        return playerPosition;
    }

    private static void addPlayerPositionsToArray(int playerPosition, String playerSymobl) {
        if("X".equals(playerSymobl)){
            player2Positions.add(playerPosition);
        }else if("O".equals(playerSymobl)){
            player1Positions.add(playerPosition);
        }
    }

    private static String determinSymbol(int tura) {
        return (tura%2 == 0)?"X":"O";
    }

    private static boolean checkPosition(int playerPosition) {
        if(playerPosition<1 || playerPosition >9){
            System.out.println("Incorrect position, try again");
            return true;
        }
        return false;
    }


    public static void placePiece(String[][] board, String playerSymobl, int playerPosition) {
        addPlayerPositionsToArray(playerPosition, playerSymobl);
        switch (playerPosition){
            case 1:
                board[0][0] = playerSymobl;
                break;
            case 2:
                board[0][1] = playerSymobl;
                break;
            case 3:
                board[0][2] = playerSymobl;
                break;
            case 4:
                board[1][0] = playerSymobl;
                break;
            case 5:
                board[1][1] = playerSymobl;
                break;
            case 6:
                board[1][2] = playerSymobl;
                break;
            case 7:
                board[2][0] = playerSymobl;
                break;
            case 8:
                board[2][1] = playerSymobl;
                break;
            case 9:
                board[2][2] = playerSymobl;
                break;
        }
    }

    private static void drawBoard(String[][] board) {
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j]);
                if (j < board.length - 1)
                    System.out.print("|");
            }

            if (i != board.length - 1)
                System.out.println("\n- - - ");
        }
    }

    private static String[][] initBoard() {
        String[][] board = new String[3][3];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = " ";
            }
        }
        return board;
    }

    public static String checkWinner(String player1name, String player2name) {

        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List lefCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(3, 4, 7);

        List<List> winningConditions = new ArrayList<>();
        winningConditions.add(topRow);
        winningConditions.add(midRow);
        winningConditions.add(botRow);
        winningConditions.add(lefCol);
        winningConditions.add(midCol);
        winningConditions.add(rightCol);
        winningConditions.add(cross1);
        winningConditions.add(cross2);

        for (List l : winningConditions) {
            if (player1Positions.containsAll(l)) {
                return "\nPlayer one " + player1name + " wins!";
            } else if (player2Positions.containsAll(l)) {
                return "\nPlayer two " + player2name + " wins!";
            }else if(player1Positions.size() + player2Positions.size() == 9){
                return "\nDraw";
            }
        }
        return "";
    }
}