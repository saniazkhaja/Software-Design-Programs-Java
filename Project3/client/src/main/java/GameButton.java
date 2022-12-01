import javafx.scene.control.Button;

public class GameButton extends Button {
    int row;
    int col;
    boolean playable; //if the square can be played
    int player; //which player played it

    GameButton(int row, int col, boolean playable) {
        this.row = row;
        this.col = col;
        this.playable = playable;
        if(this.playable == false) {
            this.setDisable(true);
        }
        this.player = 0;
    }
}
