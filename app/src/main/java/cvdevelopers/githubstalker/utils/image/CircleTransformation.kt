package cvdevelopers.githubstalker.utils.image

import android.graphics.*

import com.squareup.picasso.Transformation

/**
 * A transformation that can be leveraged by Picasso to display
 * a rectangular image as a circle.  When applying borders, it is important that
 * incoming images are of a uniform size / resolution, otherwise the border may
 * become inconsistent.
 *
 *
 * Adapted from:
 * https://gist.github.com/berkkaraoglu/ab4caa8b1fe48231dec2
 */
class CircleTransformation: Transformation {
    private val borderThicknessPix = 0f
    private var strokePaint: Paint? = null

    /**
     * Initialize a transform that has no stroke.
     */
    constructor() {}

    /**
     * Initialize a tranform with stroke.
     * @param borderColor
     */
    constructor(borderColor: Int) {
        strokePaint = Paint()
        strokePaint!!.style = Paint.Style.STROKE
        strokePaint!!.strokeWidth = 0f
        strokePaint!!.color = borderColor
        strokePaint!!.isAntiAlias = true
    }

    override fun transform(source: Bitmap): Bitmap {
        val srcWidth = source.width
        val srcHeight = source.height
        try {
            val size = Math.min(source.width, source.height)

            val squaredBitmap: Bitmap

            // if the image is not already square, make it so:
            if (source.height != source.width) {
                val x = (srcWidth - size) / TWO
                val y = (srcHeight - size) / TWO

                squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
                source.recycle()
            } else {
                // img is already square so use the source:
                squaredBitmap = source
            }

            val bitmap = Bitmap.createBitmap(size, size, getConfig(squaredBitmap))

            val canvas = Canvas(bitmap)
            val paint = Paint()
            val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP)
            paint.shader = shader
            paint.isAntiAlias = true

            val r = size / FLOAT_TWO

            // draw the rounded image:
            canvas.drawCircle(r, r, r, paint)

            if (strokePaint != null) {
                // draw stroke border:
                canvas.drawCircle(r, r, r - borderThicknessPix / FLOAT_TWO, strokePaint!!)
            }

            squaredBitmap.recycle()
            return bitmap
        } catch (e: Exception) {
            throw RuntimeException("Circle transform failed. src height=" +
                    srcHeight + ", width=" + srcWidth, e)
        }

    }

    private fun getConfig(bitmap: Bitmap): Bitmap.Config {
        // Per https://gist.github.com/julianshen/5829333
        val config = bitmap.config
        return config ?: Bitmap.Config.ARGB_8888
    }

    override fun key(): String {
        return KEY
    }

    companion object {

        private val KEY = "circle"
        private val TWO = 2
        private val FLOAT_TWO = 2f
    }
}
