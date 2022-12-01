import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class GameBoard extends ArrayList<GameButton> {
    static ArrayList<Integer> tempWinMoves = new ArrayList<>();
    static ArrayList<Integer> board = new ArrayList<>();
    static boolean foundWin = false;
    //Constructor
    GameBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board.add(0);
            }
        }
    }

    void resetBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board.set(i*7+j, 0);
            }
        }
    }

    boolean checkWin(int row, int col) {
        int countH = 0, countV = 0, countD1 = 0, countD2 = 0;
        int tempRow = row, tempCol = col;

        //vertical win
        while (tempRow > -1) {
            if(board.get(tempRow*7+tempCol) == 1) {
                if (!foundWin) {
                    tempWinMoves.add(tempRow*7+tempCol);
                }
                countV++;
                tempRow--;
            } else {
                break;
            }
        }
        tempRow = row + 1;
        while (tempRow < 6) {
            if (board.get(tempRow*7+tempCol) == 1) {
                if (!foundWin) {
                    tempWinMoves.add(tempRow*7+tempCol);
                }
                countV++;
                tempRow++;
            } else {
                break;
            }
        }
        if (countV < 4 && !foundWin) {
            tempWinMoves.clear();
        } else {
            foundWin = true;
        }

        //horizontal win
        tempRow = row;
        while (tempCol > -1) {
            if (board.get(tempRow*7+tempCol) == 1) {
                if (!foundWin) {
                    tempWinMoves.add(tempRow*7+tempCol);
                }
                countH++;
                tempCol--;
            } else {
                break;
            }
        }
        tempCol = col + 1;
        while (tempCol < 7) {
            if (board.get(tempRow*7+tempCol) == 1) {
                if (!foundWin) {
                    tempWinMoves.add(tempRow*7+tempCol);
                }
                countH++;
                tempCol++;
            } else {
                break;
            }
        }
        if (board.get(tempRow*7+tempCol) == 1) {
            tempWinMoves.clear();
        } else {
            foundWin = true;
        }

        tempCol = col;
        //diagonal 1 win left lower
        while (tempRow > -1 && tempCol > -1) {
            if (board.get(tempRow*7+tempCol) == 1) {
                if (!foundWin) {
                    tempWinMoves.add(tempRow*7+tempCol);
                }
                countD1++;
                tempRow--;
                tempCol--;
            } else {
                break;
            }
        }
        tempCol = col+1;
        tempRow = row+1;
        while (tempRow < 6 && tempCol < 7) {
            if (board.get(tempRow*7+tempCol) == 1) {
                if (!foundWin) {
                    tempWinMoves.add(tempRow*7+tempCol);
                }
                countD1++;
                tempRow++;
                tempCol++;
            } else {
                break;
            }
        }
        if (countD1 < 4 && !foundWin) {
            tempWinMoves.clear();
        } else {
            foundWin = true;
        }

        tempCol = col;
        tempRow = row;
        while (tempRow < 6 && tempCol > -1) {
            if (board.get(tempRow*7+tempCol) == 1) {
                if (!foundWin) {
                    tempWinMoves.add(tempRow*7+tempCol);
                }
                countD2++;
                tempRow++;
                tempCol--;
            } else {
                break;
            }
        }
        tempCol = col+1;
        tempRow = row-1;
        while (tempRow > -1 && tempCol < 7) {
            if (board.get(tempRow*7+tempCol) == 1) {
                if (!foundWin) {
                    tempWinMoves.add(tempRow*7+tempCol);
                }
                countD2++;
                tempRow--;
                tempCol++;
            } else {
                break;
            }
        }
        if (countD2 < 4 && !foundWin) {
            tempWinMoves.clear();
        } else {
            foundWin = true;
        }

        if (countV >= 4 || countH >= 4 || countD1 >= 4 || countD2 >= 4) {
            return true;
        }
        return false;
    }
}