/***********************************************************************************************************************
 *
 * Auteurs:    Edoardo Carpita - Dimitri Lambert
 * Date:      12-01-2020
 *
 * Projet: Laboratoire POO1 - Implementation jeu d'echec
 * Fichier: King.java
 * Brief: classe avec l'implementation du Roi
 *
 *
 **********************************************************************************************************************/

package engine.chessPieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.chessElements.ChessBoard;

import java.awt.*;


public class King extends FirstMovePiece {

    /************************************** Matrices de rotations du Roi **********************************************/

    private static final Point[] KING_MOVE_MATRIX = {new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0), new Point(1, -1), new Point(-1, 1), new Point(1, 1), new Point(-1, -1)};
    private static final Point[] KING_BIG_CASTLING_MATRIX = {new Point(-1, 0), new Point(-1, 0)};
    private static final Point[] KING_LITTLE_CASTLING_MATRIX = {new Point(1, 0), new Point(1, 0)};

    /*******************************************************************************************************************/

    private static final int BIG_CASTLING_OFFSET = 2;     /*offesets utilises pour definir les roques*/
    private static final int LITTLE_CASTLING_OFFSET = -2;

    /**
     * Constructeur de Roi, on ajoute au chessPlayer correspondant la reference sur cette piece
     * @param chessboard le plateau du jeu
     * @param player le joueur qui possède cette piece
     * @param piece_type le type de la piece
     * @param position la position qu'elle va prendre
     */
    public King(ChessBoard chessboard, PlayerColor player, PieceType piece_type, Point position) {
        super(chessboard, player, piece_type, position);
        chessboard.setPlayerKing(player, this);
    }

    /**
     * Pendant un deplacement, pour comprendre si le Roi veut effectuer un grand roque on va
     * calculer le offset entre la position courante et la position future. Seulement un grand roque aura un offset
     * strictement plus grand que 1
     * @param toX la position X future du roi qui est en train de se deplacer
     * @return true si la condition est vraie, false autrement
     */
    public boolean isBigCastling(int toX) {
        return (position.getX() - toX == BIG_CASTLING_OFFSET);
    }

    /**
     * Pendant un deplacement, pour comprendre si le Roi veut effectuer un grand roque on va
     * calculer le offset entre la position courante et la position future. Un peit roque aura un offset
     * strictement plus grand que -1
     * @param toX la position X future du roi qui est en train de se deplacer
     * @return true si la condition est vraie, false autrement
     */
    public boolean isLittleCastling(int toX) {
        return (position.getX() - toX == LITTLE_CASTLING_OFFSET);
    }

    /**
     * Renvoie le offset du grand Roque, utilisé dans les methodes qui effectuent le Roque sur la tour
     * @return le offset du grand Roque
     */
    public static int getBigCastlingOffset() {
        return BIG_CASTLING_OFFSET;
    }

    /**
     * Renvoie le offset du petit Roque, utilisé dans les methodes qui effectuent le Roque sur la tour
     * @return le offset du petit Roque
     */
    public static int getLittleCastlingOffset() {
        return LITTLE_CASTLING_OFFSET;
    }


    /**
     * Pour calculer si le Roque est faisable, on utilise une methode hybride entre la version normale de l'algo de
     * deplacement et la version untilPossible. Les matrices de translation on ete chargees avec 3 mouvements egaux
     * cette fois ci et on va iterer sur chaque position. En plus des autres verification, on doit verifier que toute
     * position sur lequelle le Roi vas passer ne le met pas en condition de danger. Si c'est le cas, le Roque n'est
     * pas possible. Si on arrive au dernier point de la matrice de translation et le mouvement possible, on enregistre
     * donc seulement cette position dans les deplacements
     * @param otherMatrix la matrice de Roque à tester
     */
    private void checkCastlingMatrix(Point[] otherMatrix) {
        Piece obstacle;
        PlayerColor other_player = this.player == PlayerColor.BLACK ? PlayerColor.WHITE : PlayerColor.BLACK;
        System.out.println(player);
        Point tester = new Point(position);
        for (int i = 0; i < otherMatrix.length; i++) {  /* la boucle for ici vas nous permettre de savoir quand on est au dernier point de la matrice */
            tester.translate((int) otherMatrix[i].getX(), (int) otherMatrix[i].getY());
            if (chessboard.checkPositionInBoardLimits(tester)) {
                obstacle = chessboard.getPieceAtPosition((int) tester.getX(), (int) tester.getY());
                if (obstacle == null) {
                    if (chessboard.isPieceInDangerAtPosition(tester, chessboard.getPlayerPieces(other_player))) {
                        return; /* si la piece à la position courante sera en danger, le roque n est pas possible, et on arrete de verifier */
                    }
                    if (i == otherMatrix.length - 1) {  /* si on est la ca veut dire que les deplacements sont bons et on peut effectuer le roque */
                        System.out.println(player + " :adding roque :" + tester);
                        possible_moves.add(new Point(tester));
                    }
                } else {  /*dans le cas aussi d'un ostacel sur le parcours, le roque n est pas faisable */
                    return;
                }
            }
        }
    }

    /**
     * Pour le Roi, on calcule donc le mouvements et prise simples avec sa propre matrices, et si la condition
     * le permet, on verifie aussi les Roques
     */
    @Override
    public void calculatePossibleMoves() {
        super.calculatePossibleMoves();
        checkMovesAndEatsMatrix(KING_MOVE_MATRIX);

        if (chessboard.areBigCastlingPiecesInPosition(player)) {
            checkCastlingMatrix(KING_BIG_CASTLING_MATRIX);
        }

        if (chessboard.areLittleCastlingPiecesInPosition(player)) {
            checkCastlingMatrix(KING_LITTLE_CASTLING_MATRIX);
        }
    }


}
