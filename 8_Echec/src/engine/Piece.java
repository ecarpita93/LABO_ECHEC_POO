package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public abstract class Piece {

    protected ChessBoard chessboard;
    public PlayerColor player;     // Couleur blanc ou noir
    public PieceType pieceType;     // Type de la pièce
    public Point position;          // Position de la pièce
    public int pieceID;             // Pour différencier les pièces d'une même couleur. 2Tours, 2Fous, 8pions ...

    private Point[] diagonalMatrix = {new Point(1, 1), new Point(1, -1), new Point(-1, -1), new Point(-1, 1)};

    // Constructeur
    public Piece(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position, int piece_ID) {
        this.chessboard = chessboard;
        this.player = player;
        this.pieceType = piece_type;
        this.position = position;
        this.pieceID = piece_ID;
        addPieceIntoGame();
    }

    public abstract boolean canMoveTo(int toX, int toY);

    public boolean canEatTo(Piece piece_to_eat) {
        return this.canMoveTo((int) piece_to_eat.getPosition().getX(), (int) piece_to_eat.getPosition().getY());
    }


    public void removePieceFromGame() {
        System.out.println(pieceType + " has been eaten");
        position = new Point(-1, -1);
        chessboard.removePieceList(this);
    }

    public void addPieceIntoGame() {
        chessboard.addPieceList(this);
        chessboard.setPieceAtPosition(this, (int) position.getX(), (int) position.getY());
    }

    boolean checkVerticalMove(int toX) {
        return position.getX() == toX;
    }

    boolean checkHorizontalMove(int toY) {
        return position.getY() == toY;
    }

    boolean checkDiagonalMove(int toX, int toY) {
        for (int i = 0; i < 4; i++) {
            Piece obstacle;
            Point diagonal = diagonalMatrix[i];
            Point tester = new Point(position);
            do {
                tester.translate((int) diagonal.getX(), (int) diagonal.getY());
                if ((tester.getX() >= 0 && tester.getX() < 8) && (tester.getY() >= 0 && tester.getY() < 8)) {
                    obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                    if (tester.getX() == toX && tester.getY() == toY) {
                        return true;
                    }
                } else {
                    break;
                }
            } while (obstacle == null);
        }
        return false;
    }

    // Getters Setters - tous mis par defaut - à corriger
    public PlayerColor getPlayer() {
        return player;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getPieceID() {
        return pieceID;
    }

    public Point getPosition() {
        return position;
    }

    public void setPlayer(PlayerColor player) {
        this.player = player;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setPieceID(int pieceID) {
        this.pieceID = pieceID;
    }
}
