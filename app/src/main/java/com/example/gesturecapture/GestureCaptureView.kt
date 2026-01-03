package com.example.gesturecapture

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class GestureCaptureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    data class GesturePoint(val x: Float, val y: Float, val t: Long)

    private val points = mutableListOf<GesturePoint>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 6f
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                points.clear()
                recordEventPoints(event)
                invalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                recordEventPoints(event)
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                recordEventPoints(event)
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun recordEventPoints(event: MotionEvent) {
        for (i in 0 until event.historySize) {
            points.add(
                GesturePoint(
                    event.getHistoricalX(i),
                    event.getHistoricalY(i),
                    event.getHistoricalEventTime(i)
                )
            )
        }
        points.add(GesturePoint(event.x, event.y, event.eventTime))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (points.size < 2) return

        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (point in points.drop(1)) {
                lineTo(point.x, point.y)
            }
        }
        canvas.drawPath(path, paint)
    }

    fun getGesturePoints(): List<GesturePoint> = points.toList()
}
