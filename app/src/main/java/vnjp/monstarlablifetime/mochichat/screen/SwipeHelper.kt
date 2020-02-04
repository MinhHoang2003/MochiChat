package vnjp.monstarlablifetime.mochichat.screen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import vnjp.monstarlablifetime.mochichat.data.model.OptionalButton
import java.util.*
import kotlin.collections.ArrayList

abstract class SwipeHelper(
    context: Context,
    private val recyclerView: RecyclerView,
    var buttonWidth: Int
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private var buttons: MutableList<OptionalButton>? = null
    lateinit var gestureDetector: GestureDetector
    var swipePosition = -1
    var swipeThreshold = 0.5f
    var buttonBuffer: MutableMap<Int, MutableList<OptionalButton>>
    lateinit var removeQueue: LinkedList<Int>


    abstract fun initOptionalButton(
        viewHolder: RecyclerView.ViewHolder,
        buffer: MutableList<OptionalButton>
    )

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            for (button in buttons!!)
                if (button.onClick(e!!.x, e.y)) break
            return true
        }
    }

    private val onTouchListener = View.OnTouchListener { _, motionEvent ->
        if (swipePosition < 0) return@OnTouchListener false
        val point = Point(motionEvent.rawX.toInt(), motionEvent.rawY.toInt())
        val swipeViewHolder = recyclerView.findViewHolderForAdapterPosition(swipePosition)
        val swipedItem = swipeViewHolder!!.itemView
        val rect = Rect()
        swipedItem.getGlobalVisibleRect(rect)

        if (motionEvent.action == MotionEvent.ACTION_DOWN ||
            motionEvent.action == MotionEvent.ACTION_MOVE ||
            motionEvent.action == MotionEvent.ACTION_UP
        ) {
            if (rect.top < point.y && rect.bottom > point.y) {
                gestureDetector.onTouchEvent(motionEvent)
            } else {
                removeQueue.add(swipePosition)
                swipePosition--
                recoverSwipedItem()
            }
        }
        false
    }


    @Synchronized
    private fun recoverSwipedItem() {
        while (removeQueue.isNotEmpty()) {
            val pos = removeQueue.poll().toInt()
            if (pos > -1) {
                recyclerView.adapter!!.notifyItemChanged(pos)
            }
        }
    }

    init {
        this.buttons = ArrayList()
        gestureDetector = GestureDetector(context, this.gestureListener)
        this.recyclerView.setOnTouchListener(onTouchListener)
        this.buttonBuffer = HashMap()
        this.removeQueue = InitLinkedList()

        attachSwiped()
    }

    private fun attachSwiped() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    class InitLinkedList : LinkedList<Int>() {
        override fun contains(element: Int): Boolean {
            return false
        }

        override fun lastIndexOf(element: Int): Int {
            return element
        }

        override fun remove(element: Int): Boolean {
            return false
        }

        override fun indexOf(element: Int): Int {
            return element
        }

        override fun add(element: Int): Boolean {
            return if (contains(element)) false
            else super.add(element)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        if (swipePosition != pos) removeQueue.add(swipePosition)
        swipePosition = pos
        if (buttonBuffer.containsKey(swipePosition)) {
            buttons = buttonBuffer[swipePosition]
        } else {
            buttonBuffer.clear()
            buttons!!.clear()
            swipeThreshold = 0.5f * buttons!!.size.toFloat() * buttonWidth.toFloat()
            recoverSwipedItem()
        }
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        var itemView = viewHolder.itemView
        if (pos < 0) {
            swipePosition = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer: MutableList<OptionalButton> = ArrayList()
                if (!buttonBuffer.containsKey(pos)) {
                    initOptionalButton(viewHolder, buffer)
                    buttonBuffer[pos] = buffer
                } else {
                    buffer = buttonBuffer[pos]!!
                }
                translationX = dX * buffer.size.toFloat() * buttonWidth.toFloat() / itemView.width
                drawButton(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            translationX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    private fun drawButton(
        c: Canvas,
        itemView: View,
        buffer: MutableList<OptionalButton>,
        pos: Int,
        translationX: Float
    ) {
        var right = itemView.right.toFloat()
        val dButtonWith = -1 * translationX / buffer.size
        for (button in buffer) {
            val left = right - dButtonWith
            button.onDraw(
                c,
                RectF(left, itemView.top.toFloat(), right, itemView.bottom.toFloat()),
                pos
            )
            right = left
        }
    }
}