import java.io.Serializable;
import java.util.ArrayList;

public class CFourInfo implements Serializable {
    boolean twoPlayers;
    boolean win;
    int row;
    int col;
    boolean turn;

    int winPos1;
    int winPos2;
    int winPos3;
    int winPos4;
    int restart;

    CFourInfo() {
    }

    CFourInfo(boolean twoPlayers) {
        this.twoPlayers = twoPlayers;
        this.row = -1;
        this.restart = 0;
    }

    CFourInfo(int row, int col, boolean win) {
        this.row = row;
        this.col = col;
        this.win = win;
        this.twoPlayers = true;
        this.restart = 0;
    }

    CFourInfo(int restart) {
        this.restart = restart;
        this.row = -1;
    }

    CFourInfo(int row, int col, boolean win, int winPos1, int winPos2, int winPos3, int winPos4) {
        this.row = row;
        this.col = col;
        this.win = win;
        this.twoPlayers = true;
        this.restart = 0;
        this.winPos1 = winPos1;
        this.winPos2 = winPos2;
        this.winPos3 = winPos3;
        this.winPos4 = winPos4;
    }
}