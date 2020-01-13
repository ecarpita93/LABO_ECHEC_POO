/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: GameController.java
 * Brief: fichier principal apres le main de gestion du jeux; ils s'occupe de la gestion mechaniques du jeux tels que
 *        les mouvements, les prises, les checks et leurs executions.
 *
 *
 **********************************************************************************************************************/

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

public class GameController implements ChessController {

    private int game_turn;                                 // indique le numero de tour courant et utilisé aussi pour la gestion des joueurs
    private PlayerColor current_player;                    // indique le joueur qui va jouer ce tour
    private PlayerColor other_player;                      // indique le joueur adversaire au joueur_courant
    private ChessView view;
    private ChessBoard chessboard;
    private Piece piece_to_be_eliminated_if_valid_move;
    private boolean en_passant_move;                       // indique si on est en train d'effectuer un en passant aux fonctions
    private int en_passant_vector;                         // offset de calcul pour la position des prise par en passant

    private static final Point OUT_OF_THE_GAME = new Point (-30,-30);                                        // position temporaire arbitraire en dehors du jeu
    private static final Promotion[] promotion_possibilities = new Promotion[]{new Promotion(PieceType.QUEEN),    // liste des promotions disponibles pour les pions à promouvoir
                                                                                new Promotion(PieceType.BISHOP),
                                                                                new Promotion(PieceType.ROOK),
                                                                                new Promotion(PieceType.KNIGHT)};

    /**
     * Mets à jours selon la le tour courant qui vas jouer ce tour, si le joueur Blanc ou Noir et indique l'autre comme l'adversaire
     */
    private void setPlayersForThisTurn(){
        current_player = PlayerColor.values()[game_turn % 2];
        other_player = PlayerColor.values()[(game_turn + 1) % 2];
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
     */
    private void checkForEnemyCheck() {
        Piece other_player_king = chessboard.getPlayerKing(other_player);
        ArrayList<Piece> current_player_pieces = chessboard.getPlayerPieces(current_player);

        if (chessboard.isPieceInDangerAtPosition(other_player_king.getPosition(), current_player_pieces)) {
            view.displayMessage(other_player + " king is in check!");
        }
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
     * Fonction qui s'occupe de verifier si le pion va effectuer un mouvement de type double etenregistre cette
     * information dans un boolean de la piece meme
     * @param pawn pion à verifier
     * @param toY la position Y finale pour calculer le offset de position
     */
    private void checkForPawnDoubleStart(Pawn pawn, int toY) {
            pawn.hasDoubleStarted(toY);
    }

    /**
     * Fonction qui s'occupe de verifier si le pion est eligible pour une promotion avec une fonction auxiliaire dans pion.
     * Si c'est le cas on effectue la promotion avec une fonction de type do
     * @param pawn le pion à promouvoir eventuellemnt
     */
    private void checkForPawnPromotion(Pawn pawn) {
        if (pawn.canBePromoted()) {
            doPromotion(pawn);
        }
    }

    /**
     * Fonction qui s'occupe de verifier si le pion qui est en train de bouger veut faire une prise en passant
     * @param pawn le pion qui est en train de bouger
     * @param toX position X destination du pion
     * @param toY position Y destination du pion
     */
    private void checkForPawnEnPassant(Pawn pawn, int toX, int toY){
        if (pawn.isDoingEnPassant(toX,toY)){
            doEnPassant(toX,toY);
        }
    }

    /******************************* Fonctions de actuation (type do) *************************************************/

    /**
     * Fonction qui s'occupe de l'initialisation des composantes pour un nouveau jeu
     */
    private void doInitGame() {
        game_turn = 0;
        chessboard = new ChessBoard();
        piece_to_be_eliminated_if_valid_move = null;
        en_passant_move = false;
        chessboard.setView(view);
        chessboard.initStandardBoard();
    }

    /**
     * Fonction qui s'occupe du nettoyage de la board à la fin d'un jeu
     */
    private void doEndGame() {
        chessboard.clearGameBoard();
        chessboard.clearPlayers();
    }

    /**
     * Fonction qui permet d'effectuer un coup de type en passant. Ce qu'on fait en faite c'est de signaler qu'on a mange
     * une piece sans effectivement y aller dessus. La fonction doMove fera le reste
     * @param toX position X finale de la piece qui vas faire le enpassant
     * @param toY position Y finale de la piece qui vas faire le enpassant
     */
    private void doEnPassant(int toX, int toY){
        en_passant_vector = current_player == PlayerColor.WHITE ? 1 : -1;    //le vecteur d'attaque vas etre different selon le joueur
        piece_to_be_eliminated_if_valid_move = chessboard.getPieceAtPosition(toX,toY - en_passant_vector);
        en_passant_move = true;
    }

    /**
     * Fonction qui s'occupe de promouvoir les pions par rapport aux choix de l'utilisateur
     * @param pawn_to_promote pion à promouvoir
     */
    private void doPromotion(Pawn pawn_to_promote) {
        Promotion choice = view.askUser("Pawn promotion", "How should be promoted your pawn? ", promotion_possibilities);
        Point position = pawn_to_promote.getPosition();
        pawn_to_promote.removePieceFromGame();
        chessboard.addPromotedPiece(current_player, choice.getPromoteTo(), position);
    }

    /**
     * Fonction qui s'occupe d'effectuer le gran roque (et donc de bouger la tour si le roi effectue ce mouvement)
     * @param king_castling le roi qui effectue le grand roque
     */
    private void doBigCastling(King king_castling) {
        Piece rook = chessboard.getPlayers()[king_castling.getPlayer().ordinal()].getBigCastlingRook();
        if (doMove(rook, (int) rook.getPosition().getX(), (int) rook.getPosition().getY(), (int) king_castling.getPosition().getX() - 1, (int) king_castling.getPosition().getY())) {
            game_turn--;  // on effectue un double mouvement donc si on arrive à le faire on doit "reculer" d'un tour pour rester synchrones
        }
    }

    /**
     * Fonction qui s'occupe d'effectuer le petit roque (et donc de bouger la tour si le roi effectue ce mouvement)
     * @param king_castling le roi qui effectue le petit roque
     */
    private void doLittleCastling(King king_castling) {
        Piece rook = chessboard.getPlayers()[king_castling.getPlayer().ordinal()].getLittleCastlingRook();
        System.out.println("little");
        if (doMove(rook, (int) rook.getPosition().getX(), (int) rook.getPosition().getY(), (int) king_castling.getPosition().getX() + 1, (int) king_castling.getPosition().getY())) {
            game_turn--; // on effectue un double mouvement donc si on arrive à le faire on doit "reculer" d'un tour pour rester synchrones
        }
    }


    /**
     * Fonction qui nous permet de retourner à l'etat initial avant un mouvement si ce mouvement à été recalculé comme invalide
     * si les conditions Roi du joueur sont evalués comme dangereuses apres ce meme mouvement
     * @param piece_to_move piece à restaurer à sa place initiale
     * @param fromX la position X de la piece temporairement eliminé du jeux (s'il y en avait une)
     * @param fromY la position Y de la piece temporairement eliminé du jeux (s'il y en avait une)
     * @param toX la position X originale de la piece avant le mouvement
     * @param toY la position Y originale de la piece avant le mouvement
     */
    private void unDoMove(Piece piece_to_move, int fromX, int fromY, int toX, int toY) {

        chessboard.removePieceFromPosition((int) piece_to_move.getPosition().getX(), (int) piece_to_move.getPosition().getY());
        chessboard.setPieceAtPosition(piece_to_move, toX, toY);

        if (piece_to_be_eliminated_if_valid_move != null) {
            chessboard.setPieceAtPosition(piece_to_be_eliminated_if_valid_move, fromX, fromY);
            piece_to_be_eliminated_if_valid_move = null;
        }

        chessboard.updateBoardMoves();
    }

    /**
     * Fonction qui s'occupe d'effectuer le vrai mouvement sur le board et verifier toutes les conditions avants et apres
     * chaque mouvement effectif pour verifier si d'autres actions sont necessaires ou si le mouvement est dangereux
     * @param piece_to_move piece qui va effectuer le changement de position
     * @param fromX la position X originale de la piece avant le mouvement
     * @param fromY la position Y originale de la piece avant le mouvement
     * @param toX la position X que la piece va prendre apres le mouvement si valide
     * @param toY la position Y que la piece va prendre apres le mouvement si valide
     * @return true si le mouvement a ete valide et effectue, false dans le cas contraire
     */
    private boolean doMove(Piece piece_to_move, int fromX, int fromY, int toX, int toY) {

        chessboard.removePieceFromPosition((int) piece_to_move.getPosition().getX(), (int) piece_to_move.getPosition().getY());

        /* -------- Verifications avant d'effectuer le mouvement ------- */
        if (piece_to_move instanceof King) {
            checkForCastling((King) piece_to_move, toX);  // on regarde si on veut faire un roque
        }

        if (piece_to_move instanceof Pawn){
            checkForPawnDoubleStart((Pawn) piece_to_move, toY);      // on regarde si on peut promouvoir un pion
            checkForPawnEnPassant((Pawn) piece_to_move, toX, toY);   // ou s'il est en train d'effectuer un en passant
        }

        /* -------- On effectue le mouvement --------------------------- */
        chessboard.setPieceAtPosition(piece_to_move, toX, toY);

        /* -------- mise à jour du board -------------------------------- */
        chessboard.updateBoardMoves();                       // mise à jour de toutes les positions de toutes les pieces de la board

        /* -------- Verifications apres le mouvement -------------------- */

        if (checkForFriendlyCheck()) {                       // on regarde si le roi du joueur en cours à ete mis en danger par le mouvement
            unDoMove(piece_to_move, toX, toY, fromX, fromY); // si c'est le cas le mouvement n'est pas valide et on doit restaurer la board
            return false;                                    // à l'etat avant le mouvement
        } else {

            if (piece_to_move instanceof Pawn) {
                checkForPawnPromotion((Pawn) piece_to_move);  // on regarde si on peut promouvoir un pion
            }

            if (piece_to_be_eliminated_if_valid_move != null) {               // si le mouvement est au contraire valide on regarde si on avait
                piece_to_be_eliminated_if_valid_move.removePieceFromGame();   // sauve une piece et si oui on peut l'eliminier proprement du jeu

                if (en_passant_move){                                         // si on effectue un en passant il faut eliminer la piece
                    view.displayMessage("En passant");                   // adversaire manuellement de la view
                    chessboard.removePieceFromPosition(toX,toY- en_passant_vector);
                    en_passant_move = false;
                }
                piece_to_be_eliminated_if_valid_move = null;
            }

            checkForEnemyCheck();                             // on regarde si avec le mouvement qu'on a fait on a mis en danger le roi adversaire
            game_turn++;                                      // on avance dans les tour du jeux
            return true;
        }
    }

    /**
     * Fonction qui s'occupe d'effectuer une "sauvegarde" en dehors de la board de la piece qui se trouve à la position
     * future de la piece qui vas se deplacer. Ceci nous permettera de verifier la validite de la board a l'etat futur
     * sans en faite perdre completement la piece a manger (qui est juste deplacé hors du jeux). Si le mouvement sera jugé
     * comme non valide alors la piece pourra etre restauré a sa position d'origine via cette "sauvegarde"
     * @param piece_to_move piece qui va effectuer le changement de position
     * @param piece_to_eat piece a "sauver" qui vas etre elimine du jeux si le mouvement de l'autre piece sera valide
     * @param fromX la position X originale de la piece avant le mouvement
     * @param fromY la position Y originale de la piece avant le mouvement
     * @param toX la position X que la piece va prendre apres le mouvement si valide
     * @param toY la position Y que la piece va prendre apres le mouvement si valide
     * @return
     */
    private boolean doMoveAndEat(Piece piece_to_move, Piece piece_to_eat, int fromX, int fromY, int toX, int toY) {
        piece_to_be_eliminated_if_valid_move = piece_to_eat;
        piece_to_be_eliminated_if_valid_move.setPosition(OUT_OF_THE_GAME);
        return doMove(piece_to_move, fromX, fromY, toX, toY);
    }

    /**
     * Fonction qui vas faire partir le jeux
     * @param view la vue à utiliser
     */
    @Override
    public void start(ChessView view) {
        this.view = view;
        this.view.startView();
        doInitGame();
    }

    /**
     * Fonction qui "effectue" le mouvement de pieces (via des appels aux fonctions de type do ci dessus).
     *
     * @param fromX la position X  de la piece avant le mouvement
     * @param fromY la position Y  de la piece avant le mouvement
     * @param toX la position X que la piece va prendre apres le mouvement si valide
     * @param toY la position Y que la piece va prendre apres le mouvement si valide
     * @return true si le mouvement a ete effectué, false si le mouvement n'as pas pu etre effectué
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        setPlayersForThisTurn();                        // mise à jour des joueurs au debut de chaque mouvement

        Piece piece_to_move = chessboard.getPieceAtPosition(fromX, fromY);
        Piece piece_at_new_position = chessboard.getPieceAtPosition(toX, toY);

        if (piece_to_move != null && piece_to_move.getPlayer() == current_player) {  // si la piece à la position de depart voulue existe et appartien au joueur de ce tour

            if (piece_at_new_position != null) {                                     // s'il y a deja une autre piece à la case d'arrivé
                if (piece_to_move.canEatTo(toX, toY)) {                              // on verifie que cette position d 'arrivé a ete bien enregistre comme position valide de la piece
                    return doMoveAndEat(piece_to_move, piece_at_new_position, fromX, fromY, toX, toY);
                }
            } else {
                if (piece_to_move.canMoveTo(toX, toY)) {                             // sinon on s'occupe seulement de bouger la piece normalement si la position est valide aussi
                    return doMove(piece_to_move, fromX, fromY, toX, toY);
                }
            }
        }
        return false;
    }

    /**
     * Fonction qui mets en place le nouveau jeu via des appels aux fonctions de type do
     */
    @Override
    public void newGame() {
       if (chessboard != null) {    // si la board n'est pas nulle signifie qu'un jeu etait deja en cours
           doEndGame();             // on procede au nettoyage de la board avant de commencer un nouveau jeu
       }
        doInitGame();
    }

    /**
     * Promotion:
     * Implementation de la classe interne Promotion à utiliser quand on va promouvoir un pion
     */
    public static class Promotion implements ChessView.UserChoice {

        private PieceType promote_to;

        Promotion(PieceType possible_promotion) {
            promote_to = possible_promotion;
        }

        PieceType getPromoteTo() {
            return promote_to;
        }

        @Override
        public String toString(){
            return promote_to.name();
        }

        @Override
        public String textValue() {
            return promote_to.name();
        }
    }

}
