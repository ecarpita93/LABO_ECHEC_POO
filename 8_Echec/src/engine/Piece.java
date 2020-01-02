package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public abstract class Piece {
    public PlayerColor couleur;     // Couleur blanc ou noir
    public PieceType pieceType;     // Type de la pièce
    public Point position;          // Position de la pièce
    public int pieceID;             // Pour différencier les pièces d'une même couleur. 2Tours, 2Fous, 8pions ...

    private Point diagonalMatrix[] = {new Point(1, 1), new Point(1, -1), new Point(-1, -1), new Point(-1, 1)};

    // Constructeur
    public Piece(PlayerColor couleur, PieceType pieceType, Point position, int pieceID) {
        this.couleur = couleur;
        this.pieceType = pieceType;
        this.position = position;
        this.pieceID = pieceID;
    }

    public abstract boolean canMoveTo(int toX, int toY);

    public boolean checkVerticalMove(int toX) {
        if (position.getX() == toX) {
            return true;
        }
        return false;
    }

    public boolean checkHorizontalMove(int toY) {
        if (position.getY() == toY) {
            return true;
        }
        return false;
    }

    public boolean checkDiagonalMove(int toX, int toY) {

        for (int i = 0; i < 4; i++) {
            Point diagonal = diagonalMatrix[i];
            Point tester = new Point(position);
            do {
                tester.translate((int) diagonal.getX(), (int) diagonal.getY());
                if (tester.getX() == toX && tester.getY() == toY) {
                    return true;
                }
            } while ((tester.getX() > 0 && tester.getX() < 8) || (tester.getY() > 0 && tester.getY() < 8));
        }

        return false;
    }

    // Getters Setters - tous mis par defaut - à corriger
    public PlayerColor getCouleur() {
        return couleur;
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

    public void setCouleur(PlayerColor couleur) {
        this.couleur = couleur;
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
