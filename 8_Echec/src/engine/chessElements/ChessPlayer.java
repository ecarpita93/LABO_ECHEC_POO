/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: ChessPlayer.java
 * Brief: fichier avec la classe qui s'occupe de la gestion des joueurs pour le plateau des jeu
 *
 *
 **********************************************************************************************************************/

package engine.chessElements;

import engine.chessPieces.FirstMovePiece;
import engine.chessPieces.Piece;

import java.util.ArrayList;

public class ChessPlayer {

    private FirstMovePiece king;                    /* reference sur le Roi du joueur */
    private FirstMovePiece little_castling_rook;    /* reference sur la Tour du petit roque du joueur */
    private FirstMovePiece big_castling_rook;       /* reference sur la Tour du grand roque du joueur */
    private ArrayList<Piece> pieces;                /* la liste des pieces du joueur */

    /**
     * Constructeur, on initialise la liste des pieces
     */
    ChessPlayer() {
        pieces = new ArrayList<>();
    }

    /**
     * Methode qui permet de nettoyer le joueur pour recommencer un nouveau jeu
     */
    void clearPlayerPieces() {
        pieces.clear();
        little_castling_rook = null;
        big_castling_rook = null;
        king = null;
    }

    /**
     * Methode pour ajouter une piece à la liste du joueur
     * @param piece la piece a ajouter
     */
    void addPieceList(Piece piece) {
        pieces.add(piece);
    }

    /**
     * Methode pour supprimer une piece à la liste du joueur
     * @param piece la piece a supprimer
     */
    void removePieceList(Piece piece) {
        pieces.remove(piece);
    }

    /**
     * Permet de recuperer les pieces d'un joueur
     * @return la liste des pieces du joueur
     */
    ArrayList<Piece> getPieces() {
        return pieces;
    }

    /**
     * Permet de recuperer le Roi d'un joueur
     * @return le Roi du joueur
     */
    Piece getKing() {
        return king;
    }

    /**
     * Permet de definir quel piece est le Roi du joueur
     * @param king le Roi à definir
     */
    void setKing(FirstMovePiece king) {
        this.king = king;
    }

    /**
     * Permet de definir quel piece est la Tour du petit Roque du joueur
     * @param little_castling_Rook la tour à definir
     */
    void setLittleCastlingRook(FirstMovePiece little_castling_Rook) {
        this.little_castling_rook = little_castling_Rook;
    }

    /**
     * Permet de definir quel piece est la Tour du grand Roque du joueur
     * @param big_castling_Rook la tour à definir
     */
    void setBigCastlingRook(FirstMovePiece big_castling_Rook) {
        this.big_castling_rook = big_castling_Rook;
    }

    /**
     * Permet de verifier si les pieces du petit roque ont encore leur place originelle. Ceci est une des conditions
     * pour effectuer le roque
     * @return true si les pieces sont encore à leur place, false si condition non vraie;
     */
    boolean areLittleCastlingPiecesInPosition() {
        if (king.getFirstMove()) {                            /* si le Roi n'est plus à sa position d'origine aucun roque est plus possible*/
            if (little_castling_rook != null) {               /* si la tour fais toujour partie du jeu */
                return little_castling_rook.getFirstMove();   /* et si elle est encore aussi à sa place */
            }
        }
        return false;
    }

    /**
     * Permet de verifier si les pieces du grand roque ont encore leur place originelle. Ceci est une des conditions
     * pour effectuer le roque
     * @return true si les pieces sont encore à leur place, false si condition non vraie;
     */
    boolean areBigCastlingPiecesInPosition() {
        if (king.getFirstMove()) {                           /* si le Roi n'est plus à sa position d'origine aucun roque est plus possible*/
            if (big_castling_rook != null) {                 /* si la tour fais toujour partie du jeu */
                return big_castling_rook.getFirstMove();     /* et si elle est encore aussi à sa place */
            }
        }
        return false;
    }

    /**
     * Permet de recuperer la tour qui s'occupe du petit Roque
     * @return la Tour du petit roque
     */
    public FirstMovePiece getLittleCastlingRook() {
        return little_castling_rook;
    }

    /**
     * Permet de recuperer la tour qui s'occupe du grand Roque
     * @return la Tour du grand roque
     */
    public FirstMovePiece getBigCastlingRook() {
        return big_castling_rook;
    }


}
