package com.clarice.checkersnotchess

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.PrintWriter
import java.net.Socket
import java.util.*
import java.util.concurrent.Executors

const val TAG ="MainActivity"

//MVC: Model, View, Controller(Main Activity)
class MainActivity : AppCompatActivity() ,CheckersDelegate {


    private lateinit var checkersView: CheckersView
    private  var printWriter:PrintWriter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkersView = findViewById<CheckersView>(R.id.checkers_view)
        checkersView.checkersDelegate= this
        //Interface btwn service implementor and service consumer
        findViewById<Button>(R.id.reset_btn).setOnClickListener {
         CheckersGame.reset()
            checkersView.invalidate()
            //Above is the code for the rst btn
        }
        findViewById<Button>(R.id.listen_btn).setOnClickListener{
            Log.d(TAG , "Socket client connecting to addr:port..")
            Executors.newSingleThreadExecutor().execute {
                val socket = Socket("192.168.0.13" ,50000)//ip address android crushes if we use local host
                val scanner =Scanner(socket.getInputStream())
                printWriter =PrintWriter(socket.getOutputStream(), true)
                while(scanner.hasNextLine()){
                 //   Log.d(TAG , "${scanner.nextLine()}")
                    val move: List<Int> =scanner.nextLine().split(",").map{it.toInt()}
                    runOnUiThread {
                        movePiece(move[0] ,move[1],move[2],move[3])
                    }
                }
            }
        }
        findViewById<Button>(R.id.connect_btn).setOnClickListener{
            Log.d(TAG , "Socket server listening on port..")

        }


    }

    override fun pieceAt(col: Int, row: Int): CheckersPieces?= CheckersGame.pieceAt(col,row)


    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        CheckersGame.movePiece(fromCol,fromRow,toCol,toRow)

        checkersView.invalidate()
        //Invalidate redraws everything on the screen
        printWriter?.let {
            val moveStr ="$fromCol ,$fromRow ,$toCol , $toRow"
            Executors.newSingleThreadExecutor().execute {
              it.println(moveStr)
        }
        }
    }
}