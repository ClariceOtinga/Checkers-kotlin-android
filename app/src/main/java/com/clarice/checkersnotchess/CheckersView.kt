package com.clarice.checkersnotchess

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min
import kotlin.collections.forEach

class CheckersView(context : Context?, attrs: AttributeSet?): View(context , attrs) {
    private final val scaleFactor = 0.9F
    private final var axisX = 20F
    private final var axisY = 200F
    private final var cellSide = 120F

    //private final val lightColor = Color.parseColor("#EEEEEE")
    //private final val darkColor = Color.parseColor("#BBBBB")
    //Create a dictionary of the images//
    private final val imgResId = setOf(
        R.drawable.redimage,
        R.drawable.blackimage
    )
    private  val bitmaps = mutableMapOf<Int, Bitmap>()
    private final val paint = Paint()
    private var movingPieceBitmap: Bitmap? = null
    private var movingPiece: CheckersPieces? = null
    private var fromCol: Int = -1
    private var fromRow: Int = -1
    private var movingPieceX = -1F
    private var movingPieceY = -1F


    var checkersDelegate: CheckersDelegate? = null

    init {
        populateBitmaps()
    }

    //Code that ensures board doesn't use all the ares so theres space for reset btn
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val smaller = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(smaller, smaller)

    }


    override fun onDraw(canvas: Canvas?) {

        //Log.d(TAG ,"${canvas?.width}.${canvas?.height}") Prints the size of the board
        //canvas ?: return -To avoid using too many null statements then we remove all the null statements in canvas
        canvas?.let {
            val boardSize = min(width, height) * scaleFactor
            cellSide = boardSize / 8F
            axisX = (width - boardSize) / 2F
            axisY = (height - boardSize) / 2F

            drawBoard(canvas)
            drawPieces(canvas)
            //The above code centers the board or scales the board

        }
        fun onTouchEvent(event: MotionEvent?): Boolean {
            event ?: return false
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    fromCol = ((event.x - axisX) / cellSide).toInt()
                    fromRow = 7 - ((event.y - axisY) / cellSide).toInt()


                    checkersDelegate?.pieceAt(fromCol, fromRow)?.let {
                        movingPiece = it
                        movingPieceBitmap = bitmaps[it.resId]
                    }


                }
                MotionEvent.ACTION_MOVE -> {
                    movingPieceX = event.x
                    movingPieceY = event.y
                    invalidate()

                }
                MotionEvent.ACTION_UP -> {
                    val col = ((event.x - axisX) / cellSide).toInt()
                    val row = ((event.y - axisY) / cellSide).toInt()
                    Log.d(TAG, "from($fromCol ,$fromRow) to ($col, $row")
                    checkersDelegate?.movePiece(fromCol, fromRow, col, row)
                    movingPiece = null
                    movingPieceBitmap = null

                }
            }
            return true
        }
    }

    private fun drawPieces(canvas: Canvas?) {
        //If the checkersModel Class is working we can instead have,
        val checkersModel = CheckersGame //Note:if we're using the delegate we don't need the model so we can delete it out
         checkersModel.reset()
        //Loop through all the 24 pieces
       for (row in 0..7) {
            for (col in 0..7) { //After te loops, then, there's the last //line
               val piece = checkersDelegate?.pieceAt( col, row)// we can use checkersdelegate instead of the model heere
               if (piece != null) {
                    drawPiecesAt(canvas, col, row, piece.resId)
               }
        //The line below does the same job as the if statement
                // checkersModel.pieceAt(col,row)?.let{drawPieceAt(canvas, col,row, it.resId}} }}

                drawPiecesAt(canvas, 1, 7, R.drawable.redimage)
                drawPiecesAt(canvas, 3, 7, R.drawable.redimage)
                drawPiecesAt(canvas, 5, 7, R.drawable.redimage)
                drawPiecesAt(canvas, 7, 7, R.drawable.redimage)
                drawPiecesAt(canvas, 0, 6, R.drawable.redimage)
                drawPiecesAt(canvas, 2, 6, R.drawable.redimage)
                drawPiecesAt(canvas, 6, 6, R.drawable.redimage)
                drawPiecesAt(canvas, 6, 6, R.drawable.redimage)
                drawPiecesAt(canvas, 1, 5, R.drawable.redimage)
                drawPiecesAt(canvas, 3, 5, R.drawable.redimage)
                drawPiecesAt(canvas, 5, 5, R.drawable.redimage)
                drawPiecesAt(canvas, 7, 5, R.drawable.redimage)
                drawPiecesAt(canvas, 0, 0, R.drawable.blackimage)
                drawPiecesAt(canvas, 2, 0, R.drawable.blackimage)
                drawPiecesAt(canvas, 4, 0, R.drawable.blackimage)
                drawPiecesAt(canvas, 6, 0, R.drawable.blackimage)
                drawPiecesAt(canvas, 1, 1, R.drawable.blackimage)
                drawPiecesAt(canvas, 3, 1, R.drawable.blackimage)
                drawPiecesAt(canvas, 5, 1, R.drawable.blackimage)
                drawPiecesAt(canvas, 7, 1, R.drawable.blackimage)
                drawPiecesAt(canvas, 0, 2, R.drawable.blackimage)
                drawPiecesAt(canvas, 2, 2, R.drawable.blackimage)
                drawPiecesAt(canvas, 4, 2, R.drawable.blackimage)
                drawPiecesAt(canvas, 6, 2, R.drawable.blackimage)

               // if(row != fromRow || col =! fromCol){ this if statement will be deleted if the 2nd if  works
              //  chessDelegate?.pieceAt(col ,row)?.let{
                //if (it != movingPiece) {
                   // drawPiecesAt(canvas, col, row, it.resID)

            }
        }

            // The commented lines will only work if I sort the issue with the CheckersGame


                movingPieceBitmap?.let {
                    canvas?.drawBitmap(
                        it,
                        null,
                        RectF(
                            movingPieceX - cellSide / 2,
                            movingPieceY - cellSide / 2,
                            movingPieceX + cellSide / 2,
                            movingPieceY + cellSide / 2
                        ), paint
                    )

                }
            }


            fun drawPiecesAt(canvas: Canvas?, col: Int, row: Int, resId: Int) {
                val bitmap = bitmaps(resId)!!
                canvas?.drawBitmap(
                    bitmap,
                    null,
                    RectF(
                        axisX + col * cellSide,
                        axisY + (7 - row) * cellSide,
                        axisX + (col + 1),
                        axisY + (6 - row)
                    ),
                    paint
                )

            }

            fun populateBitmaps() {
                imgResId.forEach {
                    BitmapFactory.decodeResource(resources, it).also { it.also { bitmaps(it) = it } }
                }

            }

            fun drawPieces(canvas: Canvas?, col: Int, row: Int, resId: Int) {

            }


            private fun drawBoard(canvas: Canvas?) {
                for (i in 0..7) {
                    for (j in 0..7) {
                        paint.color = if ((i + j) % 2 != 0) Color.DKGRAY else Color.LTGRAY
                        canvas?.drawRect(
                            axisX + i * cellSide,
                            axisY + j * cellSide,
                            axisX + (i + 1) * cellSide,
                            axisY + (j + 1) * cellSide,
                            paint
                        )

                    }

                }
            }

        }



