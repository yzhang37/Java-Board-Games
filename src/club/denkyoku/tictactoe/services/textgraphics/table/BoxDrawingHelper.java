package club.denkyoku.tictactoe.services.textgraphics.table;

public class BoxDrawingHelper {
    public static char getLeftTop(BoxStyle boxStyle) {
        return switch (boxStyle) {
            case Engraved -> '┏';
            case Emboss, Default -> '┌';
        };
    }

    public static char getLeftBottom(BoxStyle boxStyle) {
        return switch (boxStyle) {
            case Engraved -> '┖';
            case Emboss -> '┕';
            case Default -> '└';
        };
    }

    public static char getRightTop(BoxStyle boxStyle) {
        return switch (boxStyle) {
            case Engraved -> '┑';
            case Emboss -> '┒';
            case Default -> '┐';
        };
    }

    public static char getRightBottom(BoxStyle boxStyle) {
        return switch (boxStyle) {
            case Engraved, Default -> '┘';
            case Emboss -> '┛';
        };
    }

    public static char getTop(BoxStyle boxStyle) {
        return switch (boxStyle) {
            case Engraved -> '━';
            case Emboss, Default -> '─';
        };
    }

    public static char getBottom(BoxStyle boxStyle) {
        return switch (boxStyle) {
            case Engraved, Default -> '─';
            case Emboss -> '━';
        };
    }

    public static char getLeft(BoxStyle boxStyle) {
        return switch (boxStyle) {
            case Engraved -> '┃';
            case Emboss, Default -> '│';
        };
    }

    public static char getRight(BoxStyle boxStyle) {
        return switch (boxStyle) {
            case Engraved, Default -> '│';
            case Emboss -> '┃';
        };
    }
}
