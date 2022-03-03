package club.denkyoku.TicTacToe.Input;

import club.denkyoku.TicTacToe.Library.Terminal.ConsoleHelper;

/**
 * KeyHandler for Examine all key presses.
 */
public class ExamineKeyHandler extends KeyHandler {
    @Override
    protected void onKeyEsc() { ConsoleHelper.print(" Esc");}
    @Override
    protected void onKeyF1() { ConsoleHelper.print(" F1");}
    @Override
    protected void onKeyF2() { ConsoleHelper.print(" F2");}
    @Override
    protected void onKeyF3() { ConsoleHelper.print(" F3");}
    @Override
    protected void onKeyF4() { ConsoleHelper.print(" F4");}
    @Override
    protected void onKeyF5() { ConsoleHelper.print(" F5");}
    @Override
    protected void onKeyF6() { ConsoleHelper.print(" F6");}
    @Override
    protected void onKeyF7() { ConsoleHelper.print(" F7");}
    @Override
    protected void onKeyF8() { ConsoleHelper.print(" F8");}
    @Override
    protected void onKeyF9() { ConsoleHelper.print(" F9");}
    @Override
    protected void onKeyF10() { ConsoleHelper.print(" F10");}
    @Override
    protected void onKeyF11() { ConsoleHelper.print(" F11");}
    @Override
    protected void onKeyF12() { ConsoleHelper.print(" F12");}
    @Override
    protected void onKeyEnter() { ConsoleHelper.print(" Enter");}
    @Override
    protected void onKeyUp() { ConsoleHelper.print(" Up");}
    @Override
    protected void onKeyDown() { ConsoleHelper.print(" Down");}
    @Override
    protected void onKeyLeft() { ConsoleHelper.print(" Left");}
    @Override
    protected void onKeyRight() { ConsoleHelper.print(" Right");}
    @Override
    protected void onKeyHome() { ConsoleHelper.print(" Home");}
    @Override
    protected void onKeyEnd() { ConsoleHelper.print(" End");}
    @Override
    protected void onKeyPgUp() { ConsoleHelper.print(" PgUp");}
    @Override
    protected void onKeyPgDown() { ConsoleHelper.print(" PgDown");}
    @Override
    protected void onKeyInsert() { ConsoleHelper.print(" Insert");}
    @Override
    protected void onKeyDelete() { ConsoleHelper.print(" Delete");}
    @Override
    protected void onKeyBackspace() { ConsoleHelper.print(" Backspace");}
    @Override
    protected void onKeyTab() { ConsoleHelper.print(" Tab");}
    @Override
    protected void onNormalKey(char key) {
        ConsoleHelper.print(key);
    }
}
