package club.denkyoku.TicTacToe;

public class TicTacToe {
    public void start() {
        Menu menu = new Menu(new String[]{"Single Player", "Multiplayer", "Settings", "Exit"},
                "Tic-Tac-Toe", "\nCopyright (c) 2022 Denkyoku. All Rights Reserved.");
        int ret = menu.start();

        switch (ret) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                ConsoleHelper.println("See you~");
        }
    }

    private void singlePlayer() {
        System.out.println("");
        System.out.println("Difficulty");
        System.out.println("");
        System.out.println("i can win");
        System.out.println("normal");
        System.out.println("hardcore");
        System.out.println("nightmare");
    }
}
