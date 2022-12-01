import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestClient {
    GameBoard testingBoard = new GameBoard();
    @Test
    public void testRowWin1() {
        testingBoard.resetBoard();
        for (int row = 0; row < 6; row++) {
            testingBoard.resetBoard();
            for (int col = 0; col < 4; col++) {
                testingBoard.board.set(row*7+col, 1);
                if (col == 3) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Row issue at " + row);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Row issue at " + row);
                }
            }
        }
    }
    @Test
    public void testRowWin2() {
        testingBoard.resetBoard();
        for (int row = 0; row < 6; row++) {
            testingBoard.resetBoard();
            for (int col = 1; col < 5; col++) {
                testingBoard.board.set(row*7+col, 1);
                if (col == 4) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Row issue at " + row);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Row issue at " + row);
                }
            }
        }
    }
    @Test
    public void testRowWin3() {
        testingBoard.resetBoard();
        for (int row = 0; row < 6; row++) {
            testingBoard.resetBoard();
            for (int col = 2; col < 6; col++) {
                testingBoard.board.set(row*7+col, 1);
                if (col == 5) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Row issue at " + row);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Row issue at " + row);
                }
            }
        }
    }
    @Test
    public void testRowWin4() {
        testingBoard.resetBoard();
        for (int row = 0; row < 6; row++) {
            testingBoard.resetBoard();
            for (int col = 3; col < 7; col++) {
                testingBoard.board.set(row*7+col, 1);
                if (col == 6) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Row issue at " + row);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Row issue at " + row);
                }
            }
        }
    }

    @Test
    public void testReverseRowWin1() {
        testingBoard.resetBoard();
        for (int row = 0; row < 6; row++) {
            testingBoard.resetBoard();
            for (int col = 3; col >= 0; col--) {
                testingBoard.board.set(row*7+col, 1);
                if (col == 0) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Row issue at " + row);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Row issue at " + row);
                }
            }
        }
    }
    @Test
    public void testReverseRowWin2() {
        testingBoard.resetBoard();
        for (int row = 0; row < 6; row++) {
            testingBoard.resetBoard();
            for (int col = 4; col >= 1; col--) {
                testingBoard.board.set(row*7+col, 1);
                if (col == 1) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Row issue at " + row);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Row issue at " + row);
                }
            }
        }
    }
    @Test
    public void testReverseRowWin3() {
        testingBoard.resetBoard();
        for (int row = 0; row < 6; row++) {
            testingBoard.resetBoard();
            for (int col = 5; col >= 2; col--) {
                testingBoard.board.set(row*7+col, 1);
                if (col == 2) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Row issue at " + row);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Row issue at " + row);
                }
            }
        }
    }
    @Test
    public void testReverseRowWin4() {
        testingBoard.resetBoard();
        for (int row = 0; row < 6; row++) {
            testingBoard.resetBoard();
            for (int col = 6; col >= 3; col--) {
                testingBoard.board.set(row*7+col, 1);
                if (col == 3) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Row issue at " + row);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Row issue at " + row);
                }
            }
        }
    }


    @Test
    public void testColWin1() {
        testingBoard.resetBoard();
        for (int col = 0; col < 7; col++) {
            testingBoard.resetBoard();
            for (int row = 0; row < 4; row++) {
                testingBoard.board.set(row*7+col, 1);
                if (row == 3) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Col issue at " + col);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Col issue at " + col);
                }
            }
        }
    }
    @Test
    public void testColWin2() {
        testingBoard.resetBoard();
        for (int col = 0; col < 7; col++) {
            testingBoard.resetBoard();
            for (int row = 1; row < 5; row++) {
                testingBoard.board.set(row*7+col, 1);
                if (row == 4) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Col issue at " + col);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Col issue at " + col);
                }
            }
        }
    }
    @Test
    public void testColWin3() {
        testingBoard.resetBoard();
        for (int col = 0; col < 7; col++) {
            testingBoard.resetBoard();
            for (int row = 2; row < 6; row++) {
                testingBoard.board.set(row*7+col, 1);
                if (row == 5) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Col issue at " + col);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Col issue at " + col);
                }
            }
        }
    }

    @Test
    public void testReverseColWin1() {
        testingBoard.resetBoard();
        for (int col = 0; col < 7; col++) {
            testingBoard.resetBoard();
            for (int row = 3; row >= 0; row--) {
                testingBoard.board.set(row*7+col, 1);
                if (row == 0) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Col issue at " + col);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Col issue at " + col);
                }
            }
        }
    }
    @Test
    public void testReverseColWin2() {
        testingBoard.resetBoard();
        for (int col = 0; col < 7; col++) {
            testingBoard.resetBoard();
            for (int row = 4; row >= 1; row--) {
                testingBoard.board.set(row*7+col, 1);
                if (row == 1) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Col issue at " + col);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Col issue at " + col);
                }
            }
        }
    }
    @Test
    public void testReverseColWin3() {
        testingBoard.resetBoard();
        for (int col = 0; col < 7; col++) {
            testingBoard.resetBoard();
            for (int row = 5; row >= 2; row--) {
                testingBoard.board.set(row*7+col, 1);
                if (row == 2) {
                    assertEquals(true, testingBoard.checkWin(row, col), "should have won. Col issue at " + col);
                } else {
                    assertEquals(false, testingBoard.checkWin(row, col), "no win should exist. Col issue at " + col);
                }
            }
        }
    }

    @Test
    public void testDiagonal1() {
        testingBoard.resetBoard();
        testingBoard.resetBoard();
        testingBoard.board.set(5*7+3, 1);
        assertEquals(false, testingBoard.checkWin(5, 3), "no win should exist");
        testingBoard.board.set(4*7+4, 1);
        assertEquals(false, testingBoard.checkWin(4, 4), "no win should exist");
        testingBoard.board.set(3*7+5, 1);
        assertEquals(false, testingBoard.checkWin(3, 5), "no win should exist");
        testingBoard.board.set(2*7+6, 1);
        assertEquals(true, testingBoard.checkWin(5, 3), "should have won");
        assertEquals(true, testingBoard.checkWin(4, 4), "should have won");
        assertEquals(true, testingBoard.checkWin(3, 5), "should have won");
        assertEquals(true, testingBoard.checkWin(2, 6), "should have won");
    }

    @Test
    public void testDiagonal2() {
        testingBoard.resetBoard();
        testingBoard.resetBoard();
        testingBoard.board.set(5*7+4, 1);
        assertEquals(false, testingBoard.checkWin(5, 4), "no win should exist");
        testingBoard.board.set(4*7+3, 1);
        assertEquals(false, testingBoard.checkWin(4, 3), "no win should exist");
        testingBoard.board.set(3*7+2, 1);
        assertEquals(false, testingBoard.checkWin(3, 2), "no win should exist");
        testingBoard.board.set(2*7+1, 1);
        assertEquals(true, testingBoard.checkWin(5, 4), "should have won");
        assertEquals(true, testingBoard.checkWin(4, 3), "should have won");
        assertEquals(true, testingBoard.checkWin(3, 2), "should have won");
        assertEquals(true, testingBoard.checkWin(2, 1), "should have won");
    }
}