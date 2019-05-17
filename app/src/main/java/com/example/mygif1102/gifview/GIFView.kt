package com.example.mygif1102.gifview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Movie
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.mygif1102.utils.COLORS
import kotlin.random.Random

class GIFView : View {


    private var gifResourceId = 0
    private var gifUrl: String? = null
    private var startTime: Long = 0
    private var movie: Movie? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        isFocusable = true
        setBackgroundResource(COLORS.random())
    }

    var resourceId: Int
        get() = this.gifResourceId
        set(id) {
            this.gifResourceId = id
            movie = Movie.decodeStream(resources.openRawResource(id))
            requestLayout()
        }

    var gifStream: String?
        get() = this.gifUrl
        set(url) {
            this.gifUrl = url
            url?.let {
                //                Thread(Runnable {
//                    val inputStream = URL(url).openConnection().inputStream
//                    Thread.sleep(2000)
//                    movie = Movie.decodeStream(inputStream)
//                    //  gif = MovieGIF(inputStream)
//                    (context as Activity).runOnUiThread {
//                        invalidate()
//                        requestLayout()
//                    }
//                }).start()
                GIFCache(context, url).getInputStream { inputStream ->
                    movie = Movie.decodeStream(inputStream)
                    invalidate()
                    requestLayout()
                }

            }

        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        // super.onDraw(canvas)
        val currentTime = android.os.SystemClock.uptimeMillis()
        if (startTime == 0L) startTime = currentTime
        movie?.let {
            val movieDuration = if (it.duration() == 0) 1000 else it.duration()
            val updatedTime = ((currentTime - startTime) % movieDuration).toInt()

            it.setTime(updatedTime)
            canvas?.scale(measuredWidth.toFloat() / it.width(), measuredHeight.toFloat() / it.height())
            it.draw(canvas, 0f, 0f)
            invalidate()
        }
    }

    fun setLayoutParams(originWidth: Int, originHeight: Int, scaleSize: Int, scaleBase: Int) {
        val width = if (scaleBase == WIDTH_SCALE) scaleSize else (scaleSize * originWidth) / originHeight
        val height = if (scaleBase == HEIGHT_SCALE) scaleSize else (scaleSize * originHeight) / originWidth
        layoutParams = LinearLayout.LayoutParams(width, height)
    }


    companion object {
        const val WIDTH_SCALE = 1
        const val HEIGHT_SCALE = 2
    }
}