package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {

    ChessBoard chessboard;
    PlayerColor player;       // Couleur blanc ou noir
    PieceType piece_type;     // Type de la pièce
    Point position;           // Position de la pièce
    ArrayList<Point> possible_moves;
    ArrayList<Point> possible_eats;

    private static final Point[] DIAGONAL_MATRIX = {new Point(1, 1), new Point(1, -1), new Point(-1, -1), new Point(-1, 1)};
    private static final Point[] VERTICAL_MATRIX = {new Point(0, 1), new Point(0, -1)};
    private static final Point[] HORIZONTAL_MATRIX = {new Point(1, 0), new Point(-1, 0)};

    Piece(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        this.chessboard = chessboard;
        this.player = player;
        this.piece_type = piece_type;
        this.position = position;

        possible_moves = new ArrayList<>();
        possible_eats = new ArrayList<>();
        addPieceIntoGame();
    }

    public void calculatePossibleMoves() {
        resetMovesArrays();
    }

    public boolean canMoveTo(int toX, int toY) {
        return findInArray(possible_moves, toX, toY);
    }

    public boolean canEatTo(int toX, int toY) {
        return findInArray(possible_eats, toX, toY);
    }

    private void resetMovesArrays() {
        possible_moves.clear();
        possible_eats.clear();
    }

    private boolean findInArray(ArrayList<Point> possible_goes, int toX, int toY) {
        for (Point possible_go : possible_goes) {
            if (possible_go.getX() == toX && possible_go.getY() == toY) {
                return true;
            }
        }
        return false;
    }

    private void addPieceIntoGame() {
        chessboard.addPieceList(this);
        chessboard.setPieceAtPosition(this, (int) position.getX(), (int) position.getY());
    }

    public void removePieceFromGame() {
        System.out.println(piece_type + " has been eaten");
        position = new Point(-1, -1);
        chessboard.removePieceList(this);
    }

    void checkHorizontalMovesAndEats() {
        checkMovesAndEatsMatrixUntilNotPossible(HORIZONTAL_MATRIX);
    }

    void checkVerticalMovesAndEats() {
        checkMovesAndEatsMatrixUntilNotPossible(VERTICAL_MATRIX);
    }

    void checkDiagonalMovesAndEats() {
        checkMovesAndEatsMatrixUntilNotPossible(DIAGONAL_MATRIX);
    }

    private void checkMovesAndEatsMatrixUntilNotPossible(Point[] otherRecursiveMatrix) {
        for (Point other : otherRecursiveMatrix) {
            Piece obstacle;
            Point tester = new Point(position);
            do {
                tester.translate((int) other.getX(), (int) other.getY());
                if (chessboard.checkPositionInBoardLimits(tester)) {
                    obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                    if (obstacle == null) {
                        possible_moves.add(new Point(tester));
                    } else if (obstacle.getPlayer() != this.getPlayer()) {
                        possible_eats.add(new Point(tester));
                    }
                } else {
                    break;
                }
            } while (obstacle == null);
        }
    }

    void checkMovesAndEatsMatrix(Point[] otherMatrix) {
        for (Point other : otherMatrix) {
            Piece obstacle;
            Point tester = new Point(position);

            tester.translate((int) other.getX(), (int) other.getY());
            if (chessboard.checkPositionInBoardLimits(tester)) {
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle == null) {
                    possible_moves.add(new Point(tester));
                } else if (obstacle.getPlayer() != this.getPlayer()) {
                    possible_eats.add(new Point(tester));
                }
            }
        }
    }

    public ArrayList<Point> getPossibleEats() {
        return possible_eats;
    }

    public ArrayList<Point> getPossibleMoves() {
        return possible_moves;
    }

    public PlayerColor getPlayer() {
        return player;
    }

    public PieceType getPiece_type() {
        return piece_type;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
