package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {

    protected ChessBoard chessboard;
    public PlayerColor player;       // Couleur blanc ou noir
    public PieceType piece_type;     // Type de la pièce
    public Point position;           // Position de la pièce
    ArrayList<Point> possible_moves;
    ArrayList<Point> possible_eats;

    private Point[] diagonalMatrix = {new Point(1, 1), new Point(1, -1), new Point(-1, -1), new Point(-1, 1)};
    private Point[] verticalMatrix = {new Point(0, 1), new Point(0, -1)};
    private Point[] horizontalMatrix = {new Point(1, 0), new Point(-1, 0)};

    // Constructeur
    public Piece(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        this.chessboard = chessboard;
        this.player = player;
        this.piece_type = piece_type;
        this.position = position;

        possible_moves = new ArrayList<>();
        possible_eats = new ArrayList<>();
        addPieceIntoGame();
    }

    private void resetMovesArrays() {
        possible_moves.clear();
        possible_eats.clear();
    }

    public void calculatePossibleMoves() {
        resetMovesArrays();
    }

    private boolean findInArray(ArrayList<Point> possible_goes, int toX, int toY) {
        for (Point possible_go : possible_goes) {
            if (possible_go.getX() == toX && possible_go.getY() == toY) {
                return true;
            }
        }
        return false;
    }

    public boolean canMoveTo(int toX, int toY) {
        return findInArray(possible_moves, toX, toY);
    }

    public boolean canEatTo(int toX, int toY) {
        return findInArray(possible_eats, toX, toY);

    }

    public void removePieceFromGame() {
        System.out.println(piece_type + " has been eaten");
        position = new Point(-1, -1);
        chessboard.removePieceList(this);
    }

    public void addPieceIntoGame() {
        chessboard.addPieceList(this);
        chessboard.setPieceAtPosition(this, (int) position.getX(), (int) position.getY());
    }

    void checkHorizontalMovesAndEats() {
        checkMovesAndEatsMatrixRecursive(horizontalMatrix);
    }

    void checkVerticalMovesAndEats() {
        checkMovesAndEatsMatrixRecursive(verticalMatrix);
    }

    void checkDiagonalMovesAndEats() {
        checkMovesAndEatsMatrixRecursive(diagonalMatrix);
    }

    private void checkMovesAndEatsMatrixRecursive(Point[] otherRecursiveMatrix) {
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

    // Getters Setters - tous mis par defaut - à corriger
    public PlayerColor getPlayer() {
        return player;
    }

    public PieceType getPiece_type() {
        return piece_type;
    }

    public Point getPosition() {
        return position;
    }

    public void setPlayer(PlayerColor player) {
        this.player = player;
    }

    public void setPiece_type(PieceType piece_type) {
        this.piece_type = piece_type;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
