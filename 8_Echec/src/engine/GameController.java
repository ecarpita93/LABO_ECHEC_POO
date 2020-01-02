package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.awt.*;
import java.util.Stack;

public class GameController implements ChessController {

    private Stack coupJouee;  // Pour avoir l'historic des coups
    ChessView view;
    private int turn;

    // TODO : Un tableau 8x8 Point
    public Piece[][] echequier;


    public GameController() {

    }

    // Clear board
    public void clearBoard(ChessView view) {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                view.removePiece(x, y);
            }
        }
    }

    // Init game
    public void init_Game(ChessView view) {
        coupJouee = new Stack();
        echequier = new Piece[8][8];

        Piece tour1Blanc = new Tour(PlayerColor.WHITE, PieceType.ROOK, new Point(0, 0), 1);
        Piece tour2Blanc = new Tour(PlayerColor.WHITE, PieceType.ROOK, new Point(7, 0), 2);

        Piece cavalier1Blanc = new Cavalier(PlayerColor.WHITE, PieceType.KNIGHT, new Point(1, 0), 1);
        Piece cavalier2Blanc = new Cavalier(PlayerColor.WHITE, PieceType.KNIGHT, new Point(6, 0), 2);

        Piece fou1Blanc = new Fou(PlayerColor.WHITE, PieceType.BISHOP, new Point(2, 0), 1);
        Piece fou2Blanc = new Fou(PlayerColor.WHITE, PieceType.BISHOP, new Point(5, 0), 2);

        Piece DameBlanc = new Dame(PlayerColor.WHITE, PieceType.QUEEN, new Point(3, 0), 1);
        Piece RoiBlanc = new Roi(PlayerColor.WHITE, PieceType.KING, new Point(4, 0), 1);

        Piece pion1Blanc = new Pion(PlayerColor.WHITE, PieceType.PAWN, new Point(0, 1), 1);
        Piece pion2Blanc = new Pion(PlayerColor.WHITE, PieceType.PAWN, new Point(1, 1), 2);
        Piece pion3Blanc = new Pion(PlayerColor.WHITE, PieceType.PAWN, new Point(2, 1), 3);
        Piece pion4Blanc = new Pion(PlayerColor.WHITE, PieceType.PAWN, new Point(3, 1), 4);
        Piece pion5Blanc = new Pion(PlayerColor.WHITE, PieceType.PAWN, new Point(4, 1), 5);
        Piece pion6Blanc = new Pion(PlayerColor.WHITE, PieceType.PAWN, new Point(5, 1), 6);
        Piece pion7Blanc = new Pion(PlayerColor.WHITE, PieceType.PAWN, new Point(6, 1), 7);
        Piece pion8Blanc = new Pion(PlayerColor.WHITE, PieceType.PAWN, new Point(7, 1), 8);

        Piece tour1Noir = new Tour(PlayerColor.BLACK, PieceType.ROOK, new Point(0, 7), 1);
        Piece tour2Noir = new Tour(PlayerColor.BLACK, PieceType.ROOK, new Point(7, 7), 2);

        Piece cavalier1Noir = new Cavalier(PlayerColor.BLACK, PieceType.KNIGHT, new Point(1, 7), 1);
        Piece cavalier2Noir = new Cavalier(PlayerColor.BLACK, PieceType.KNIGHT, new Point(6, 7), 2);

        Piece fou1Noir = new Fou(PlayerColor.BLACK, PieceType.BISHOP, new Point(2, 7), 1);
        Piece fou2Noir = new Fou(PlayerColor.BLACK, PieceType.BISHOP, new Point(5, 7), 2);

        Piece DameNoir = new Dame(PlayerColor.BLACK, PieceType.QUEEN, new Point(3, 7), 1);
        Piece RoiNoir = new Roi(PlayerColor.BLACK, PieceType.KING, new Point(4, 7), 1);

        Piece pion1Noir = new Pion(PlayerColor.BLACK, PieceType.PAWN, new Point(0, 6), 1);
        Piece pion2Noir = new Pion(PlayerColor.BLACK, PieceType.PAWN, new Point(1, 6), 2);
        Piece pion3Noir = new Pion(PlayerColor.BLACK, PieceType.PAWN, new Point(2, 6), 3);
        Piece pion4Noir = new Pion(PlayerColor.BLACK, PieceType.PAWN, new Point(3, 6), 4);
        Piece pion5Noir = new Pion(PlayerColor.BLACK, PieceType.PAWN, new Point(4, 6), 5);
        Piece pion6Noir = new Pion(PlayerColor.BLACK, PieceType.PAWN, new Point(5, 6), 6);
        Piece pion7Noir = new Pion(PlayerColor.BLACK, PieceType.PAWN, new Point(6, 6), 7);
        Piece pion8Noir = new Pion(PlayerColor.BLACK, PieceType.PAWN, new Point(7, 6), 8);

        echequier[0][0] = tour1Blanc;
        echequier[7][0] = tour2Blanc;
        echequier[1][0] = cavalier1Blanc;
        echequier[6][0] = cavalier2Blanc;
        echequier[2][0] = fou1Blanc;
        echequier[5][0] = fou2Blanc;
        echequier[3][0] = DameBlanc;
        echequier[4][0] = RoiBlanc;
        echequier[0][1] = pion1Blanc;
        echequier[1][1] = pion2Blanc;
        echequier[2][1] = pion3Blanc;
        echequier[3][1] = pion4Blanc;
        echequier[4][1] = pion5Blanc;
        echequier[5][1] = pion6Blanc;
        echequier[6][1] = pion7Blanc;
        echequier[7][1] = pion8Blanc;

        echequier[0][7] = tour1Noir;
        echequier[7][7] = tour2Noir;
        echequier[1][7] = cavalier1Noir;
        echequier[6][7] = cavalier2Noir;
        echequier[2][7] = fou1Noir;
        echequier[5][7] = fou2Noir;
        echequier[3][7] = DameNoir;
        echequier[4][7] = RoiNoir;
        echequier[0][6] = pion1Noir;
        echequier[1][6] = pion2Noir;
        echequier[2][6] = pion3Noir;
        echequier[3][6] = pion4Noir;
        echequier[4][6] = pion5Noir;
        echequier[5][6] = pion6Noir;
        echequier[6][6] = pion7Noir;
        echequier[7][6] = pion8Noir;

        view.putPiece(tour1Blanc.getPieceType(), tour1Blanc.getCouleur(), tour1Blanc.getPosition().x, tour1Blanc.getPosition().y);
        view.putPiece(tour2Blanc.getPieceType(), tour2Blanc.getCouleur(), tour2Blanc.getPosition().x, tour2Blanc.getPosition().y);
        view.putPiece(cavalier1Blanc.getPieceType(), cavalier1Blanc.getCouleur(), cavalier1Blanc.getPosition().x, cavalier1Blanc.getPosition().y);
        view.putPiece(cavalier2Blanc.getPieceType(), cavalier2Blanc.getCouleur(), cavalier2Blanc.getPosition().x, cavalier2Blanc.getPosition().y);
        view.putPiece(fou1Blanc.getPieceType(), fou1Blanc.getCouleur(), fou1Blanc.getPosition().x, fou1Blanc.getPosition().y);
        view.putPiece(fou2Blanc.getPieceType(), fou2Blanc.getCouleur(), fou2Blanc.getPosition().x, fou2Blanc.getPosition().y);
        view.putPiece(DameBlanc.getPieceType(), DameBlanc.getCouleur(), DameBlanc.getPosition().x, DameBlanc.getPosition().y);
        view.putPiece(RoiBlanc.getPieceType(), RoiBlanc.getCouleur(), RoiBlanc.getPosition().x, RoiBlanc.getPosition().y);
        view.putPiece(pion1Blanc.getPieceType(), pion1Blanc.getCouleur(), pion1Blanc.getPosition().x, pion1Blanc.getPosition().y);
        view.putPiece(pion2Blanc.getPieceType(), pion2Blanc.getCouleur(), pion2Blanc.getPosition().x, pion2Blanc.getPosition().y);
        view.putPiece(pion3Blanc.getPieceType(), pion3Blanc.getCouleur(), pion3Blanc.getPosition().x, pion3Blanc.getPosition().y);
        view.putPiece(pion4Blanc.getPieceType(), pion4Blanc.getCouleur(), pion4Blanc.getPosition().x, pion4Blanc.getPosition().y);
        view.putPiece(pion5Blanc.getPieceType(), pion5Blanc.getCouleur(), pion5Blanc.getPosition().x, pion5Blanc.getPosition().y);
        view.putPiece(pion6Blanc.getPieceType(), pion6Blanc.getCouleur(), pion6Blanc.getPosition().x, pion6Blanc.getPosition().y);
        view.putPiece(pion7Blanc.getPieceType(), pion7Blanc.getCouleur(), pion7Blanc.getPosition().x, pion7Blanc.getPosition().y);
        view.putPiece(pion8Blanc.getPieceType(), pion8Blanc.getCouleur(), pion8Blanc.getPosition().x, pion8Blanc.getPosition().y);

        view.putPiece(tour1Noir.getPieceType(), tour1Noir.getCouleur(), tour1Noir.getPosition().x, tour1Noir.getPosition().y);
        view.putPiece(tour2Noir.getPieceType(), tour2Noir.getCouleur(), tour2Noir.getPosition().x, tour2Noir.getPosition().y);
        view.putPiece(cavalier1Noir.getPieceType(), cavalier1Noir.getCouleur(), cavalier1Noir.getPosition().x, cavalier1Noir.getPosition().y);
        view.putPiece(cavalier2Noir.getPieceType(), cavalier2Noir.getCouleur(), cavalier2Noir.getPosition().x, cavalier2Noir.getPosition().y);
        view.putPiece(fou1Noir.getPieceType(), fou1Noir.getCouleur(), fou1Noir.getPosition().x, fou1Noir.getPosition().y);
        view.putPiece(fou2Noir.getPieceType(), fou2Noir.getCouleur(), fou2Noir.getPosition().x, fou2Noir.getPosition().y);
        view.putPiece(DameNoir.getPieceType(), DameNoir.getCouleur(), DameNoir.getPosition().x, DameNoir.getPosition().y);
        view.putPiece(RoiNoir.getPieceType(), RoiNoir.getCouleur(), RoiNoir.getPosition().x, RoiNoir.getPosition().y);
        view.putPiece(pion1Noir.getPieceType(), pion1Noir.getCouleur(), pion1Noir.getPosition().x, pion1Noir.getPosition().y);
        view.putPiece(pion2Noir.getPieceType(), pion2Noir.getCouleur(), pion2Noir.getPosition().x, pion2Noir.getPosition().y);
        view.putPiece(pion3Noir.getPieceType(), pion3Noir.getCouleur(), pion3Noir.getPosition().x, pion3Noir.getPosition().y);
        view.putPiece(pion4Noir.getPieceType(), pion4Noir.getCouleur(), pion4Noir.getPosition().x, pion4Noir.getPosition().y);
        view.putPiece(pion5Noir.getPieceType(), pion5Noir.getCouleur(), pion5Noir.getPosition().x, pion5Noir.getPosition().y);
        view.putPiece(pion6Noir.getPieceType(), pion6Noir.getCouleur(), pion6Noir.getPosition().x, pion6Noir.getPosition().y);
        view.putPiece(pion7Noir.getPieceType(), pion7Noir.getCouleur(), pion7Noir.getPosition().x, pion7Noir.getPosition().y);
        view.putPiece(pion8Noir.getPieceType(), pion8Noir.getCouleur(), pion8Noir.getPosition().x, pion8Noir.getPosition().y);


/*
        coupJouee.empty();
        // Les blancs en place
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 0);
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 7, 0);
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 1, 0);
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 6, 0);
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 2, 0);
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 5, 0);
        view.putPiece(PieceType.QUEEN, PlayerColor.WHITE, 3, 0);
        view.putPiece(PieceType.KING, PlayerColor.WHITE, 4, 0);
        // Pions blancs et noir
        for (int i = 0; i < 8; i++){
            view.putPiece(PieceType.PAWN, PlayerColor.WHITE, i, 1);
            view.putPiece(PieceType.PAWN, PlayerColor.BLACK, i, 6);
        }
        // Les noirs en place
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 0, 7);
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 7, 7);
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 1, 7);
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 6, 7);
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 2, 7);
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 5, 7);
        view.putPiece(PieceType.QUEEN, PlayerColor.BLACK, 3, 7);
        view.putPiece(PieceType.KING, PlayerColor.BLACK, 4, 7);
 */
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        this.view.startView();
        init_Game(this.view);
        //boolean moveOk = move(0, 0, 0, 5);
        //view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 5);
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {

        Piece pieceABouger = echequier[fromX][fromY];


        if (pieceABouger != null) {
                if (pieceABouger.couleur == PlayerColor.values()[turn % 2]) {
                    if (pieceABouger.canMoveTo(toX, toY)) {
                        view.removePiece(fromX, fromY);
                        pieceABouger.setPosition(new Point(toX, toY));
                        view.putPiece(pieceABouger.getPieceType(), pieceABouger.getCouleur(), pieceABouger.getPosition().x, pieceABouger.getPosition().y);

                        echequier[fromX][fromY] = null;
                        echequier[toX][toY] = pieceABouger;
                        turn++;
                        return true;
                    }
                }
        }



        return false;
    }

    @Override
    public void newGame() {
        clearBoard(view);
        init_Game(view);
    }

}
