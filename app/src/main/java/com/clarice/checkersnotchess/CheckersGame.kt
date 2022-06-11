package com.clarice.checkersnotchess

import com.clarice.checkersnotchess.CheckersRank.BLACK_PAWN
import com.clarice.checkersnotchess.CheckersRank.RED_PAWN

object CheckersGame {
    var piecesBox = mutableSetOf<CheckersPieces>()

    init {
        reset()
    }

    fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if (fromCol == toCol && fromRow == toRow) return
        val movingPiece = pieceAt(fromCol, fromRow) ?: return
        pieceAt(toCol, toRow)?.let {
            if (it.players == movingPiece.players) {
                return
                //Does not allow the pieces to move to location where there's a piece
            }
            piecesBox.remove(it)
        }

        piecesBox.remove(movingPiece)
        // val newPiece = movingPiece.copy(col=toCol , row =toRow , rank =KING) then just add the newPiece
        piecesBox.add(CheckersPieces(toCol, toRow, movingPiece.players, movingPiece.resId))

    }

    private fun pieceAt(fromCol: Int, fromRow: Int): Nothing? {
        return

    }


    fun reset() {
        piecesBox.removeAll(piecesBox)



        fun pieceAt(col: Int, row: Int): CheckersPieces? {
            for (piece in piecesBox) {
                if (col == piece.col && row == piece.row) {
                    return piece
                }
            }
            return null


        }

//        fun toString(): String {
//         var description = "\n"
//           for (row in 7 downTo 0) {
//                description += "$row"
//               for (col in 0..7) {
//                    val piece = pieceAt(col, row)
////                    if (piece == null) {
//                        description += "."
//                    } else {
//                        // val red = piece.players == CheckersPlayers.RED
//                        description += ""
//                        //  description += when (piece.rank) {
//
//                        //  CheckersRank.BLACK_PAWN -> {
//                        //      if (red) "R" else "r"
//                    }
                    //CheckersRank.BLACK_PAWN -> {
                    //    if (red) "B" else "b"
                }

            }


//
//
//            description += "\n"
//
//            description += "0 1 2 3 4 5 6 7"
//
//            return description
//        }
//    }
//}
//
//















