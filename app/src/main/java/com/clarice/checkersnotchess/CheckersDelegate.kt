package com.clarice.checkersnotchess

interface CheckersDelegate {
    fun pieceAt(col: Int, row: Int): CheckersPieces?
    //We define the function without its body
    fun movePiece(fromCol :Int ,fromRow:Int,toCol: Int, toRow:Int)
}