package com.jlmillan.sudokumaster.logic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.compose.ui.graphics.toArgb
import com.jlmillan.sudokumaster.ui.theme.LighterBlack
import com.jlmillan.sudokumaster.ui.theme.TextBlack
import com.jlmillan.sudokumaster.ui.theme.VeryLightCyan

class SudokuBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var board: Array<IntArray> = Array(9) { IntArray(9) }
    private var cellSize = 0f
    private var selectedRow = -1
    private var selectedCol = -1
    private var cellTapListener: ((Int, Int) -> Unit)? = null

    private val gridPaint = Paint().apply {
        color = LighterBlack.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private val thickGridPaint = Paint().apply {
        color = LighterBlack.toArgb()
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    private val cellPaint = Paint().apply {
        color = TextBlack.toArgb()
        style = Paint.Style.FILL
        textSize = 64f
        textAlign = Paint.Align.CENTER
    }

    private val selectedCellPaint = Paint().apply {
        color = VeryLightCyan.toArgb()
        style = Paint.Style.FILL
    }

    fun setOnCellTapListener(listener: (Int, Int) -> Unit) {
        cellTapListener = listener
    }

    fun updateBoard(board: Array<IntArray>) {
        this.board = board
        invalidate()
    }

    fun setNumber(row: Int, col: Int, number: Int) {
        board[row][col] = number
        invalidate()
    }

    fun selectCell(row: Int, col: Int) {
        selectedRow = row
        selectedCol = col
        invalidate()
    }

    fun deselectCell() {
        selectedRow = -1
        selectedCol = -1
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = minOf(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
        cellSize = size / 9f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGrid(canvas)
        drawNumbers(canvas)
        drawSelectedCell(canvas)
    }

    private fun drawGrid(canvas: Canvas) {
        for (i in 0..9) {
            val pos = i * cellSize
            val paint = if (i % 3 == 0) thickGridPaint else gridPaint
            canvas.drawLine(pos, 0f, pos, width.toFloat(), paint)
            canvas.drawLine(0f, pos, width.toFloat(), pos, paint)
        }
    }

    private fun drawNumbers(canvas: Canvas) {
        val textOffset = (cellPaint.descent() + cellPaint.ascent()) / 2
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val number = board[row][col]
                if (number != 0) {
                    val x = col * cellSize + cellSize / 2
                    val y = row * cellSize + cellSize / 2 - textOffset
                    canvas.drawText(number.toString(), x, y, cellPaint)
                }
            }
        }
    }

    private fun drawSelectedCell(canvas: Canvas) {
        if (selectedRow != -1 && selectedCol != -1) {
            val left = selectedCol * cellSize
            val top = selectedRow * cellSize
            val right = left + cellSize
            val bottom = top + cellSize
            canvas.drawRect(left, top, right, bottom, selectedCellPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val row = (event.y / cellSize).toInt()
            val col = (event.x / cellSize).toInt()
            cellTapListener?.invoke(row, col)
            return true
        }
        return super.onTouchEvent(event)
    }
}
