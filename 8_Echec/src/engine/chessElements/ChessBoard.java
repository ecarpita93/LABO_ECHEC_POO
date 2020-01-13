/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: ChessBoard.java
 * Brief: fichier avec la classe qui s'occupe de la gestion du plateau de jeu et des positions des pieces sur ce dernier
 *
 *
 **********************************************************************************************************************/

package engine.chessElements;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.chessPieces.*;

import java.awt.*;
import java.util.ArrayList;

public class ChessBoard {

    private Piece[][] game_board;       // la board c'est implemente sous forme de tableau de Pieces 8x8
    private ChessPlayer[] players;      // un tableau de players pour regrouper les pieces en equipes
    private ChessView view;             // par logique, les changements des position de la view vont etre geres par cette
                                        // classe, donc on l'ajoute aux attributs de cette classe

    private static final int BOARD_X = 8;
    private static final int BOARD_Y = 8;

    /**
     * Constructeur de ChessBoard, on instancie le board et les joueurs.
     */
    public ChessBoard() {
        players = new ChessPlayer[]{new ChessPlayer(), new ChessPlayer()};
        game_board = new Piece[BOARD_X][BOARD_Y];
    }

    /**
     * Comme une seule board est recyclé pour chaque jeu, on utilise deux methodes auxilisaires pour nettoyer
     * la board et le joueur. Celle ci nettoye la board via la methode privé de retraite de piece du jeu
     */
    public void clearGameBoard() {
        for (int x = 0; x < BOARD_X; x++) {
            for (int y = 0; y < BOARD_Y; y++) {
                removePieceFromPosition(x, y);
            }
        }
    }

    /**
     * Comme une seule board est recyclé pour chaque jeu, on utilise deux methodes auxilisaires pour nettoyer
     * la board et le joueur. Celle ci nettoye la la liste des chaque joueur
     */
    public void clearPlayers(){
        for (ChessPlayer player : players){
            player.clearPlayerPieces();
        }
    }

    /**
     * Permet de definir la view pour les mises à jour des positions
     * @param view la view à definir
     */
    public void setView(ChessView view) {
        this.view = view;
    }

    /**
     * Fonction qui s'occupe d'instancier un jeu d'echec standard prét pour un nouveau jeu. Ceci est fait dans
     * sa propre methode afin de garder l'implementation du code plus evolutive (admetton pour instancier un jeu
     * non initialisé au debut, par exemple)
     */
    public void initStandardBoard() {

        Piece piece;

        piece = new Rook(this, PlayerColor.WHITE, PieceType.ROOK, new Point(0, 0));
        players[PlayerColor.WHITE.ordinal()].setBigCastlingRook((FirstMovePiece) piece);
        /* au contraire du king, on indique ici quel sont les tours qui vont devoir effectuer les roques car on peut pas
        savoir au  niveau du constructeur quel position ces pieces vont avoir (dans le cas d'une initialisation custom)*/

        piece = new Rook(this, PlayerColor.WHITE, PieceType.ROOK, new Point(7, 0));
        players[PlayerColor.WHITE.ordinal()].setLittleCastlingRook((FirstMovePiece) piece);
        piece = new Knight(this, PlayerColor.WHITE, PieceType.KNIGHT, new Point(1, 0));
        piece = new Knight(this, PlayerColor.WHITE, PieceType.KNIGHT, new Point(6, 0));

        piece = new Bishop(this, PlayerColor.WHITE, PieceType.BISHOP, new Point(2, 0));
        piece = new Bishop(this, PlayerColor.WHITE, PieceType.BISHOP, new Point(5, 0));

        piece = new Queen(this, PlayerColor.WHITE, PieceType.QUEEN, new Point(3, 0));
        piece = new King(this, PlayerColor.WHITE, PieceType.KING, new Point(4, 0));

        for (int i = 0; i < BOARD_X; i++) {
          piece = new Pawn(this, PlayerColor.WHITE, PieceType.PAWN, new Point(i, 1));
        }

        piece = new Rook(this, PlayerColor.BLACK, PieceType.ROOK, new Point(0, 7));
        players[PlayerColor.BLACK.ordinal()].setBigCastlingRook((FirstMovePiece) piece);

        piece = new Rook(this, PlayerColor.BLACK, PieceType.ROOK, new Point(7, 7));
        players[PlayerColor.BLACK.ordinal()].setLittleCastlingRook((FirstMovePiece) piece);

        piece = new Knight(this, PlayerColor.BLACK, PieceType.KNIGHT, new Point(1, 7));
        piece = new Knight(this, PlayerColor.BLACK, PieceType.KNIGHT, new Point(6, 7));

        piece = new Bishop(this, PlayerColor.BLACK, PieceType.BISHOP, new Point(2, 7));
        piece = new Bishop(this, PlayerColor.BLACK, PieceType.BISHOP, new Point(5, 7));

        piece = new Queen(this, PlayerColor.BLACK, PieceType.QUEEN, new Point(3, 7));
        piece = new King(this, PlayerColor.BLACK, PieceType.KING, new Point(4, 7));

        for (int i = 0; i < BOARD_X; i++) {
           piece = new Pawn(this, PlayerColor.BLACK, PieceType.PAWN, new Point(i, 6));
        }

        updateBoardMoves(); /* on mets à jour les positions seulement une fois que toutes les pieces ont ete places sur
                               le plateau*/

    }

    /**
     * Cette fonction est utilisé pour mettre à jour tous les arrays des mouvements et des prise pour chaque pieces
     * actuellement dans la liste de chaque joueur. Elle est appellé par le GameController chaque fois que une piece
     * change de position.
     * */
    public void updateBoardMoves() {
        for (ChessPlayer player : players) {
            for (Piece p : player.getPieces()) {
                p.calculatePossibleMoves();
            }
        }
    }

    /**
     * Cette fonction de verification nous permet de verifier pour toute position donnée si cette derniere est comprise
     * dans le plateau du jeu
     * @param position le point de position à verifier
     * @return true si le point est compris dans les limite du plateau, false autremement
     */
    public boolean checkPositionInBoardLimits(Point position) {
        return (position.getX() >= 0 && position.getX() < BOARD_X) && (position.getY() >= 0 && position.getY() < BOARD_Y);
    }

    /**
     * Fonction qui permet de recuperer l'eventuelle piece sur le plateau à la position X Y donnee
     * @param fromX coordonnée X de la position dont on veut recuperer la piece
     * @param fromY coordonnée Y de la position dont on veut recuperer la piece
     * @return la Piece trouvé à la position (XY) du plateau (peut etre aussi null, sera utilisé par les methodes
     * qui appellent cette fonction)
     */
    public Piece getPieceAtPosition(int fromX, int fromY) {
        return game_board[fromX][fromY];
    }

    /**
     * Fonction qui permet de positionner la piece en parametre sur le plateau à la position X Y donnee. Cette fonction
     * mets à jour aussi la position dans la View
     * @param piece piece à deplacer
     * @param toX coordonnée X de la position future de la piece
     * @param toY coordonnée Y de la position future de la piece
     */
    public void setPieceAtPosition(Piece piece, int toX, int toY) {

        piece.setPosition(new Point(toX, toY));                     /* mise à jour de la position interne à la piece */
        game_board[toX][toY] = piece;                               /* mise à jour de la position sur le plateau */

        if (piece instanceof FirstMovePiece) {                      /* si la piece est de type FirstMovePiece */
            if (((FirstMovePiece) piece).getFirstMove()) {          /* mise à jour du flag qui indique le premier deplacement si necessaire */
                ((FirstMovePiece) piece).setFirstMove(false);
            }
        }

        view.putPiece(piece.getPieceType(), piece.getPlayer(), (int) piece.getPosition().getX(), (int) piece.getPosition().getY());
        /* mise à jour de la view */
    }

    /**
     * Fonction de remotion de piece du plateau de jeu
     * @param fromX coordonnée X de la position de la piece a supprimer
     * @param fromY coordonnée Y de la position de la piece a supprimer
     */
    public void removePieceFromPosition(int fromX, int fromY) {
        game_board[fromX][fromY] = null;     /* mise a jour du plateau */
        view.removePiece(fromX, fromY);      /* mise a jour de la view */
    }

    /**
     * Permet d'ajouter une piece a la liste d'un joueur. Effectue avec un appel sur une methode de ChessPlayer
     * @param piece piece à rajouter au joueur
     */
    public void addPieceList(Piece piece) {
        players[piece.getPlayer().ordinal()].addPieceList(piece);
        /* avec la structure de l'array de players on a juste à savoir la valeur numerique de l'enum representant le
        * joueur de la piece pour l'ajouter dans sa propre liste, cette methode est utilisé dans le methodes suivantes*/

    }

    /**
     * Permet de supprimer une piece a la liste d'un joueur. Effectue avec un appel sur une methode de ChessPlayer
     * @param piece piece à supprimer au joueur
     */
    public void removePieceList(Piece piece) {
        players[piece.getPlayer().ordinal()].removePieceList(piece);
        /*voire commentaire interne dans la methode addPieceList*/
    }

    /**
     * Permet de recuperer toutes les pieces d'un joueur donné
     * @param player le joueur dont on veut recuperer les pieces
     * @return la liste des pieces
     */
    public ArrayList<Piece> getPlayerPieces(PlayerColor player) {
        return players[player.ordinal()].getPieces();
        /*voire commentaire interne dans la methode addPieceList*/
    }

    /**
     * Permet de recuperer la piece Roi d'un joueur donné
     * @param player le joueur dont on veut recuperer le Roi
     * @return le Roi du joueur
     */
    public Piece getPlayerKing(PlayerColor player) {
        return players[player.ordinal()].getKing();
        /*voire commentaire interne dans la methode addPieceList*/


    }

    /**
     * Permet de definir quel piece est consideré comme le Roi d'un joueur donné
     * @param player le joueur dont on veut definir le Roi
     * @param king le Roi
     */
    public void setPlayerKing(PlayerColor player, Piece king) {
        players[player.ordinal()].setKing((FirstMovePiece) king);
        /*voire commentaire interne dans la methode addPieceList*/

    }

    /**
     * Cette fonction permet de verifier si la condition de depart du grand roque est respecté
     * (tour et roi à la position initiale)
     * @param player joueur dont on veut verifier les conditions de depart
     * @return true si la condition est respecte, sinon false
     */
    public boolean areLittleCastlingPiecesInPosition(PlayerColor player) {
        return players[player.ordinal()].areLittleCastlingPiecesInPosition();
        /*voire commentaire interne dans la methode addPieceList*/
    }

    /**
     * Cette fonction permet de verifier si la condition de depart du grand roque est respecté
     * (tour et roi à la position initiale)
     * @param player joueur dont on veut verifier les conditions de depart
     * @return true si la condition est respecte, sinon false
     */
    public boolean areBigCastlingPiecesInPosition(PlayerColor player) {
        return players[player.ordinal()].areBigCastlingPiecesInPosition();
        /*voire commentaire interne dans la methode addPieceList*/
    }


    /**
     * Cette fonction permet de verifier si une piece d'un jouer sera en condition de danger si elle se deplace à cette
     * position eventuelle.
     * @param piece_in_danger_position position eventuelle à verifier
     * @param other_player_pieces l'array des pieces des autres joueurs
     * @return La condition de danger est true si au moins une correspondance est trouvé, false sinon
     */
    public boolean isPieceInDangerAtPosition(Point piece_in_danger_position, ArrayList<Piece> other_player_pieces) {

        for (Piece piece : other_player_pieces) {  /* pour toutes les pieces de l'adversaire */

            for (Point possible_moves : piece.getPossibleMoves()) {  /* pour tous les mouvements de cette piece */
                if (piece_in_danger_position.getX() == possible_moves.getX() && piece_in_danger_position.getY() == possible_moves.getY()) {
                    return true; /* des que une coordonne correspond on quitte la fonction avec un true car la condition est valide au moins pour une position*/
                }
            }
            for (Point possible_eats : piece.getPossibleEats()) {    /* pour toutes les prises de cette piece */
                if (piece_in_danger_position.getX() == possible_eats.getX() && piece_in_danger_position.getY() == possible_eats.getY()) {
                    return true;  /* des que une coordonne correspond on quitte la fonction avec un true car la condition est valide au moins pour une position*/
                }
            }
        }
        return false;
    }

    /**
     * Permet de recuperer l'array des joueurs
     * @return le tableau des joueurs
     */
    public ChessPlayer[] getPlayers() {
        return players;
    }

    /**
     * Fonction qui s'occupe de "promouvoir" (via la creation d'une nouvelle piece) une piece dans le jeu
     * @param current_player le joueur qui va recevoir la piece promue
     * @param promoteTo le type de la piece à promouvoir
     * @param position la position de la piece à promouvoir
     */
    public void addPromotedPiece(PlayerColor current_player, PieceType promoteTo, Point position) {

        Piece piece;

        switch(promoteTo){
            case ROOK:
                piece = new Rook(this, current_player, PieceType.ROOK, position);
                break;
            case BISHOP:
                piece = new Bishop(this, current_player, PieceType.BISHOP, position);
                break;
            case KNIGHT:
                piece = new Knight(this, current_player, PieceType.KNIGHT, position);
                break;
            case QUEEN:
            default:
                piece = new Queen(this, current_player, PieceType.QUEEN, position);
        }

    }
}
