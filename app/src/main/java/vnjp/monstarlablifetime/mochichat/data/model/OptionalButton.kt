package vnjp.monstarlablifetime.mochichat.data.model

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat

class OptionalButton(
    private val context: Context,
    private val text: String,
    private val textSize: Int,
    private val imageResId: Int,
    private val color: Int,
    private val listener: OptionalClickListener
) {
    private var pos: Int = 0
    private var clickRegion: RectF? = null
    private var resources: Resources = context.resources

    fun onClick(x: Float, y: Float): Boolean {
        if (clickRegion != null && clickRegion!!.contains(x, y)) {
            listener.onClick(pos)
            return true
        }
        return false
    }

    fun onDraw(c: Canvas, rectF: RectF, pos: Int) {
        val p = Paint()
        p.isAntiAlias = true
        p.isSubpixelText = true
        p.color = color
        c.drawRect(rectF, p)

        p.color = Color.WHITE
        p.textSize = textSize.toFloat()

        val r = Rect()
        val cHeight = rectF.height()
        val cWith = rectF.width()
        p.textAlign = Paint.Align.CENTER
        p.getTextBounds(text, 0, text.length, r)
        var x = 0f
        var y = 0f
        x = cWith / 2f - r.width() / 2f - r.left.toFloat()
        y = cHeight / 2f - r.height() / 2f - r.bottom.toFloat()
        if (imageResId == 0) {
            x = cWith / 2f - r.width() / 2f - r.left.toFloat()
            y = cHeight / 2f + r.height() / 2f - r.bottom.toFloat()
            c.drawText(text, rectF.left + x, rectF.top + y, p)
        } else {
            val d = ContextCompat.getDrawable(context, imageResId)
            val bitmap = drawableToBitmap(d)
            Log.d("bitmap", "${rectF.left} & ${rectF.right}  & ${(rectF.left + rectF.right) / 2}")
            c.drawBitmap(
                bitmap,
                (3 * rectF.left + rectF.right) / 4,
                (3 * rectF.top + rectF.bottom) / 4,
                p
            )
        }

        clickRegion = rectF
        this.pos = pos
    }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap {
        if (drawable is BitmapDrawable) return drawable.bitmap
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}