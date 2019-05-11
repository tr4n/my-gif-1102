package com.example.mygif1102.gifview

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Movie
import android.util.AttributeSet
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL

class CustomView : ImageView {

    private var movieStart: Long = 0
    private var placeholderResource: Int = 0
    var movieWidth: Int = 0
    var movieHeight: Int = 0
    private var gifUrl: String? = null
    private var movie: Movie? = null
    private var inputStream: InputStream? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        isFocusable = true
        movie = null
        movieWidth = 0
        movieHeight = 0
        setBackgroundColor(Color.RED)
    }

    var gifResource: Int
        get() = this.gifResource
        set(resourceId) {
            this.gifResource = resourceId
            movie = Movie.decodeStream(resources.openRawResource(resourceId))
            requestLayout()
        }

    var imageUrl: String?
        get() = this.gifUrl
        set(urlString) {
            this.gifUrl = urlString
            Thread(Runnable {
                inputStream = URL(urlString).openConnection().inputStream
                Thread.sleep(2000)
                movie = Movie.decodeStream(inputStream)
//                movie?.let {
//                    movieWidth = it.width()
//                    movieHeight = it.height()
//                }
                (context as Activity).runOnUiThread {
                    invalidate()
                    requestLayout()
                }
            }).start()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)

    }


    override fun onDraw(canvas: Canvas?) {
        val now = android.os.SystemClock.uptimeMillis()
        if (movieStart == 0L) movieStart = now

        movie?.let {
            val movieDuration = if (it.duration() == 0) 1000 else it.duration()
            val relTime = ((now - movieStart) % movieDuration).toInt()

            it.setTime(relTime)
            canvas?.scale(measuredWidth.toFloat() / it.width(), measuredHeight.toFloat() / it.height())
            it.draw(canvas, 0f, 0f)
            invalidate()
        }
    }

    companion object {
        private val DECODE_STREAM = true

        internal var gifAddr =
            "https://media.giphy.com/media/fVzviTajPfdutFp7qM/giphy.gif"

        private fun streamToBytes(`is`: InputStream): ByteArray {
            val os = ByteArrayOutputStream(1024)
            val buffer = ByteArray(1024)
            var len: Int
            try {
                while ((`is`.read(buffer)) >= 0) {
                    os.write(buffer, 0, `is`.read(buffer))
                }
            } catch (e: IOException) {
            }

            return os.toByteArray()
        }
    }
}