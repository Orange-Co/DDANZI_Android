package co.orange.core.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MultiLineTextView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : AppCompatTextView(context, attrs, defStyleAttr) {
        // 텍스트가 그려지기 전에 줄바꿈 공백 제거
        override fun onDraw(canvas: Canvas) {
            val text = text?.toString()?.replace(leadingWhitespaceRegex, "$1")
            setText(text)
            super.onDraw(canvas)
        }

        companion object {
            private val leadingWhitespaceRegex = Regex("(^|\n)\\s+")
        }
    }
