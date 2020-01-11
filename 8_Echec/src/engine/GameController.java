package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;
import engine.chessPieces.King;
import engine.chessPieces.Pawn;
import engine.chessPieces.Piece;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * Class Game Controller
 * Cette classe s'occupe de la gestion des mechaniques du jeux tels que les mouvements, les prises, les checks et
 * leurs executions
 *
 *
 *
 */
public class GameController implements ChessController {

    private int game_turn;
    private PlayerColor current_player;
    private PlayerColor other_player;
    private ChessView view;
    private ChessBoard chessboard;
    private Piece piece_to_be_eliminated_if_valid_move;

    private static final Point OUT_OF_THE_GAME = new Point (-30,-30);
    private static final Promotion[] promotion_possibilities = new Promotion[]{new Promotion(PieceType.QUEEN),
                                                                                new Promotion(PieceType.BISHOP),
                                                                                new Promotion(PieceType.ROOK),
                                                                                new Promotion(PieceType.KNIGHT)};

    private void initGame() {
        game_turn = 0;
        chessboard = new ChessBoard();
        piece_to_be_eliminated_if_valid_move = null;
        chessboard.setView(view);
        chessboard.initStandardBoard();
    }

    private void endGame() {
        chessboard.clearGameBoard();
        chessboard.clearPlayers();
    }

    /******************************* Fonctions de verifications (type check) ******************************************/

    /**
     * Fonction qui s'occupe de regarder si apres un mouvement, le roi de la piece qui a bougé est resté dans une situation
     * dangereuse. Permet de definir si un mouvement est valide ou pas
     * @return true si le roi reste en condition de danger, false si le roi est pas en danger et donc le mouvement est faisable
     */
    private boolean checkForFriendlyCheck() {
        Piece current_player_king = chessboard.getPlayerKing(current_player);
        ArrayList<Piece> other_player_pieces  = chessboard.getPlayerPieces(other_player);

        return chessboard.isPieceInDangerAtPosition(current_player_king.getPosition(), other_player_pieces);

    }

    /**
     * Fonction qui s'occupe de regarder si apres un mouvement, le roi adversaire de la piece qui a bougé est resté dans
     * une situation dangereuse. Permet de definir le check et signale à la view si c'est le cas.
     * @return true si le roi est condition de danger, false si le roi est pas en danger
     */
    private boolean checkForEnemyCheck() {
        Piece other_player_king = chessboard.getPlayerKing(other_player);
        ArrayList<Piece> current_player_pieces = chessboard.getPlayerPieces(current_player);


        if (chessboard.isPieceInDangerAtPosition(other_player_king.getPosition(), current_player_pieces)) {
            view.displayMessage(other_player + " king is in check!");
            chessboard.setCheck(other_player, true);
            return true;
        }

        chessboard.setCheck(other_player, false);
        return false;

    }

    /**
     * Fonction qui s'occupe de verifier si le roi qui est en train de bouger veux effectuer un roque. Si c'est le cas on
     * l'effectue avec ine fonction de type do
     * Appelle deux fonctions auxiliaires dans la piece Roi pour definir si on est en train d'effecuter le petit ou grand roque
     * @param king piece roi à verifier
     * @param toX  a position horizontale qui va etre utilisé pour calculer quel roque est effectué via un calcul d'offset
     */
    private void checkForCastling(King king, int toX) {
        if (king.isBigCastling(toX)) {
            doBigCastling(king);
        } else if (king.isLittleCastling(toX)) {
            doLittleCastling(king);
        }
    }

    /**
     * Fonction qui s'occupe de verifier si le pion est eligible pour une promotion avec une fonction auxiliaire dans pion.
     * Si c'est le cas on effectue la promotion avec une fonction de type do
     * @param pawn
     */
    private void checkForPawnPromotion(Pawn pawn) {
        if (pawn.canBePromoted()) {
            doPromotion(pawn);
        }
    }

    /******************************* Fonctions de actuation (type do) *************************************************/


    private void doPromotion(Pawn pawn_to_promote) {
        Promotion choice = view.askUser("Pawn promotion", "How should be promoted your pawn? ", promotion_possibilities);
        Point position = pawn_to_promote.getPosition();
        pawn_to_promote.removePieceFromGame();
        chessboard.addPromotedPiece(current_player, choice.getPromoteTo(), position);
    }

    private void doBigCastling(King king_castling) {
        Piece rook = chessboard.getPlayers()[king_castling.getPlayer().ordinal()].getBigCastlingRook();
        doMove(rook, (int) rook.getPosition().getX(), (int) rook.getPosition().getY(), (int) king_castling.getPosition().getX() - 2, (int) king_castling.getPosition().getY());
        game_turn--;

    }

    private void doLittleCastling(King king_castling) {
        Piece rook = chessboard.getPlayers()[king_castling.getPlayer().ordinal()].getLittleCastlingRook();
        doMove(rook, (int) rook.getPosition().getX(), (int) rook.getPosition().getY(), (int) king_castling.getPosition().getX() + 1, (int) king_castling.getPosition().getY());
        game_turn--;
    }


    private void unDoMove(Piece piece_to_move, int fromX, int fromY, int toX, int toY) {

        chessboard.removePieceFromPosition((int) piece_to_move.getPosition().getX(), (int) piece_to_move.getPosition().getY());
        chessboard.setPieceAtPosition(piece_to_move, toX, toY);

        if (piece_to_be_eliminated_if_valid_move != null) {
            chessboard.setPieceAtPosition(piece_to_be_eliminated_if_valid_move, fromX, fromY);
            piece_to_be_eliminated_if_valid_move = null;
        }

        chessboard.updateBoardMoves();
    }

    private boolean doMove(Piece piece_to_move, int fromX, int fromY, int toX, int toY) {

        chessboard.removePieceFromPosition((int) piece_to_move.getPosition().getX(), (int) piece_to_move.getPosition().getY());

        if (piece_to_move instanceof King) {
            checkForCastling((King) piece_to_move, toX);
        }

        chessboard.setPieceAtPosition(piece_to_move, toX, toY);

        if (piece_to_move instanceof Pawn) {
            checkForPawnPromotion((Pawn) piece_to_move);
        }

        chessboard.updateBoardMoves();

        if (checkForFriendlyCheck()) {
            unDoMove(piece_to_move, toX, toY, fromX, fromY);
            return false;
        } else {

            if (piece_to_be_eliminated_if_valid_move != null) {
                piece_to_be_eliminated_if_valid_move.removePieceFromGame();
                piece_to_be_eliminated_if_valid_move = null;
            }

            if (checkForEnemyCheck()) {
                chessboard.updateBoardMoves();
            }

            game_turn++;
            return true;
        }
    }

    private boolean doMoveAndEat(Piece piece_to_move, Piece piece_to_eat, int fromX, int fromY, int toX, int toY) {
        piece_to_be_eliminated_if_valid_move = piece_to_eat;
        piece_to_be_eliminated_if_valid_move.setPosition(OUT_OF_THE_GAME);
        return doMove(piece_to_move, fromX, fromY, toX, toY);
    }


    @Override
    public void start(ChessView view) {
        this.view = view;
        this.view.startView();
        initGame();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        current_player = PlayerColor.values()[game_turn % 2];
        other_player = PlayerColor.values()[(game_turn + 1) % 2];

        Piece piece_to_move = chessboard.getPieceAtPosition(fromX, fromY);
        Piece piece_at_new_position = chessboard.getPieceAtPosition(toX, toY);

        if (piece_to_move != null && piece_to_move.getPlayer() == current_player) {

            if (piece_at_new_position != null) {
                if (piece_to_move.canEatTo(toX, toY)) {
                    return doMoveAndEat(piece_to_move, piece_at_new_position, fromX, fromY, toX, toY);
                }
            } else {
                if (piece_to_move.canMoveTo(toX, toY)) {
                    return doMove(piece_to_move, fromX, fromY, toX, toY);
                }
            }

        }
        return false;
    }

    @Override
    public void newGame() {
       if (chessboard != null) {
           endGame();
       }
        initGame();
    }


    public static class Promotion implements ChessView.UserChoice {

        private PieceType promote_to;

        Promotion(PieceType possible_promotion) {
            promote_to = possible_promotion;
        }

        PieceType getPromoteTo() {
            return promote_to;
        }

        @Override
        public String textValue() {
            return promote_to.name();
        }
    }

}
