package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;

public abstract class Piece {
    public PlayerColor couleur;     // Couleur blanc ou noir
    public PieceType pieceType;     // Type de la pièce
    public Point position;          // Position de la pièce
    public int pieceID;             // Pour différencier les pièces d'une même couleur. 2Tours, 2Fous, 8pions ...

    // Constructeur
    public Piece(PlayerColor couleur, PieceType pieceType, Point position, int pieceID){
        this.couleur = couleur;
        this.pieceType = pieceType;
        this.position = position;
        this.pieceID = pieceID;
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
