package club.denkyoku.TicTacToe;

public class TicTacToe {
    public void start() {
        Menu menu = new Menu(new String[]{"Single Player", "Multi Player", "Exit"},
                "Tic-Tac-Toe", "\nCopyright (c) 2021 Denkyoku");
        menu.start();
    }

    private void singlePlayer() {
        System.out.println("");
        System.out.println("Difficulty");
        System.out.println("");
        System.out.println("  i can win");
        System.out.println("  2. normal");
        System.out.println("  3. hard");
        System.out.println("  Nightmare");
    }
}
