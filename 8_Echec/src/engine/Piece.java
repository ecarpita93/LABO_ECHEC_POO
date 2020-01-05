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
    public int piece_ID;             // Pour différencier les pièces d'une même couleur. 2Tours, 2Fous, 8pions ...
    ArrayList<Point> possible_moves;

    public ArrayList<Point> getPossibleEats() {
        return possible_eats;
    }

    ArrayList<Point> possible_eats;

    private Point[] diagonalMatrix = {new Point(1, 1), new Point(1, -1), new Point(-1, -1), new Point(-1, 1)};
    private Point[] verticalMatrix = {new Point(0, 1), new Point(0, -1)};
    private Point[] horizontalMatrix = {new Point(1, 0), new Point(-1, 0)};

    // Constructeur
    public Piece(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position, int piece_ID) {
        this.chessboard = chessboard;
        this.player = player;
        this.piece_type = piece_type;
        this.position = position;
        this.piece_ID = piece_ID;

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

    public boolean canMoveTo(int toX, int toY) {
        for (Point possible_move : possible_moves) {
            if (possible_move.getX() == toX && possible_move.getY() == toY) {
                return true;
            }
        }
        return false;
    }

    public boolean canEatTo(int toX, int toY) {
        for (Point possible_eat : possible_eats) {
            if (possible_eat.getX() == toX && possible_eat.getY() == toY) {
                return true;
            }
        }
        return false;
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
        for (Point horizontal : horizontalMatrix) {
            Piece obstacle;
            Point tester = new Point(position);
            do {
                tester.translate((int) horizontal.getX(), (int) horizontal.getY());
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

    void checkVerticalMovesAndEats() {
        for (Point vertical : verticalMatrix) {
            Piece obstacle;
            Point tester = new Point(position);
            do {
                tester.translate((int) vertical.getX(), (int) vertical.getY());
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

    void checkDiagonalMovesAndEats() {
        for (Point diagonal : diagonalMatrix) {
            Piece obstacle;
            Point tester = new Point(position);
            do {
                tester.translate((int) diagonal.getX(), (int) diagonal.getY());
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

    // Getters Setters - tous mis par defaut - à corriger
    public PlayerColor getPlayer() {
        return player;
    }

    public PieceType getPiece_type() {
        return piece_type;
    }

    public int getPiece_ID() {
        return piece_ID;
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

    public void setPiece_ID(int piece_ID) {
        this.piece_ID = piece_ID;
    }
}
