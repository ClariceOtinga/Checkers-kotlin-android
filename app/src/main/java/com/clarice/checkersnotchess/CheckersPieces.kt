package com.clarice.checkersnotchess

data class CheckersPieces(
    val row: Int,
    val col: Int,
    val players: CheckersRank,
    val resId: Int
) {
}
//Note the the element in a mutable set cannot be moved if it is modified in kotlin