package fpt.adtrue.horoscope.customview

import android.content.Context
import android.graphics.Canvas
import android.text.StaticLayout
import android.util.AttributeSet
import kotlin.math.ceil

class JustifyTextView(context: Context?, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatTextView(
    context!!, attrs) {
    private var mLineY = 0
    private var mViewWidth = 0
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        val paint = paint
        paint.color = currentTextColor
        paint.drawableState = drawableState
        mViewWidth = measuredWidth
        val text = text.toString()
        mLineY = 0
        mLineY += textSize.toInt()
        val layout = layout ?: return

        val fm = paint.fontMetrics
        var textHeight = ceil((fm.descent - fm.ascent).toDouble()).toInt()
        textHeight = (textHeight * layout.spacingMultiplier + layout.spacingAdd).toInt()
        for (i in 0 until layout.lineCount) {
            val lineStart = layout.getLineStart(i)
            val lineEnd = layout.getLineEnd(i)
            val width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint())
            val line = text.substring(lineStart, lineEnd)
            if (needScale(line) && i < layout.lineCount - 1) {
                drawScaledText(canvas, lineStart, line, width)
            } else {
                canvas.drawText(line, 0f, mLineY.toFloat(), paint)
            }
            mLineY += textHeight
        }
    }

    private fun drawScaledText(canvas: Canvas, lineStart: Int, line: String, lineWidth: Float) {
        var line = line
        var x = 0f
        if (isFirstLineOfParagraph(lineStart, line)) {
            val blanks = "  "
            canvas.drawText(blanks, x, mLineY.toFloat(), paint)
            val bw = StaticLayout.getDesiredWidth(blanks, paint)
            x += bw
            line = line.substring(3)
        }
        val gapCount = line.length - 1
        var i = 0
        if (line.length > 2 && line[0].toInt() == 12288 && line[1].toInt() == 12288) {
            val substring = line.substring(0, 2)
            val cw = StaticLayout.getDesiredWidth(substring, paint)
            canvas.drawText(substring, x, mLineY.toFloat(), paint)
            x += cw
            i += 2
        }
        val d = (mViewWidth - lineWidth) / gapCount
        while (i < line.length) {
            val c = line[i].toString()
            val cw = StaticLayout.getDesiredWidth(c, paint)
            canvas.drawText(c, x, mLineY.toFloat(), paint)
            x += cw + d
            i++
        }
    }

    private fun isFirstLineOfParagraph(lineStart: Int, line: String): Boolean {
        return line.length > 3 && line[0] == ' ' && line[1] == ' '
    }

    private fun needScale(line: String?): Boolean {
        return if (line == null || line.length == 0) {
            false
        } else {
            line[line.length - 1] != '\n'
        }
    }

    companion object {
        const val TWO_CHINESE_BLANK = "  "
    }
}