package com.github.abhinavchauhan97.circle_through_3_points

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView

class MyImageView(context: Context, attributeSet: AttributeSet) : ImageView(context, attributeSet) {


    var centerX = 0
    var centerY = 0
    var radius = 0.0
    var totalPoints = 0
    var x1 = 0
    var x2 = 0
    var x3 = 0
    var y1 = 0
    var y2 = 0
    var y3 = 0
    var paint = Paint().apply {
        color = Color.RED
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    var paintPoint = Paint().apply {
        color = Color.YELLOW
        isAntiAlias = true
        strokeWidth = 20f
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        if (event?.action == MotionEvent.ACTION_UP) {
                ++totalPoints
                when (totalPoints) {
                    1 -> {
                        x1 = event.x.toInt()
                        y1 = event.y.toInt()
                    }
                    2 -> {
                        x2 = event.x.toInt()
                        y2 = event.y.toInt()
                    }
                    3 -> {
                        x3 = event.x.toInt()
                        y3 = event.y.toInt()
                        val x12 = (x1 - x2).toInt()
                        val x13 = (x1 - x3).toInt()

                        val y12 = (y1 - y2).toInt()
                        val y13 = (y1 - y3).toInt()

                        val y31 = (y3 - y1).toInt()
                        val y21 = (y2 - y1).toInt()

                        val x31 = (x3 - x1).toInt()
                        val x21 = (x2 - x1).toInt()

                        val sx13 = (Math.pow(x1.toDouble(), 2.0) -
                                Math.pow(x3.toDouble(), 2.0)).toInt()

                        val sy13 = (Math.pow(y1.toDouble(), 2.0) -
                                Math.pow(y3.toDouble(), 2.0)).toInt()

                        val sx21 = (Math.pow(x2.toDouble(), 2.0) -
                                Math.pow(x1.toDouble(), 2.0)).toInt()

                        val sy21 = (Math.pow(y2.toDouble(), 2.0) -
                                Math.pow(y1.toDouble(), 2.0)).toInt()

                        centerX = (((sx13 * x12 + sy13 * x12 + sx21 * x13 + sy21 * x13)
                                / (2 * (y31 * x12 - y21 * x13))))
                        centerY = (((sx13 * y12 + sy13 * y12 + sx21 * y13 + sy21 * y13)
                                / (2 * (x31 * y12 - x21 * y13))))

                        val c = ((-Math.pow(x1.toDouble(), 2.0)).toInt() - Math.pow(y1.toDouble(), 2.0)
                            .toInt() - 2 * centerY * x1 - 2 * centerX * y1).toInt()

                        val h = -centerY
                        val k = -centerX
                        val sqr_of_r = h * h + k * k - c
                        radius = Math.sqrt(sqr_of_r.toDouble())
                        invalidate()
                    }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(x1.toFloat(), y1.toFloat(),20f,paintPoint)
        canvas.drawCircle(x2.toFloat(), y2.toFloat(),20f,paintPoint)
        canvas.drawCircle(x3.toFloat(), y3.toFloat(),20f,paintPoint)
        canvas.drawCircle(-centerY.toFloat(),-centerX.toFloat(),radius.toFloat(),paint)
        totalPoints = 0
    }
}