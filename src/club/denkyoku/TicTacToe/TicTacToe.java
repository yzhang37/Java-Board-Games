package club.denkyoku.TicTacToe;

public class TicTacToe {
    public void start() {
        Menu menu = new Menu(new String[]{"Single Player", "Multiplayer", "Settings", "Exit"},
                "Tic-Tac-Toe", "\nCopyright (c) 2022 Denkyoku. All Rights Reserved.");
        while (true) {
            int ret = menu.start();
            switch (ret) {
                case 0:
                    singlePlayer();
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    ConsoleHelper.println("See you~");
                    return;
            }
        }
    }

    protected void singlePlayer() {
        Menu menu = new Menu(
                new String[]{"i can win", "bring it on", "hardcore", "nightmare"},
                "Choose difficulty", ""
        );

        int ret = menu.start();
        switch (ret) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}
