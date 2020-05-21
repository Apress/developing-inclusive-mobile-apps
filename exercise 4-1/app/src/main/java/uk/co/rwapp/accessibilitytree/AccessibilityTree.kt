package uk.co.rwapp.accessibilitytree

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.FrameLayout
import java.util.*

class AccessibilityTree: AccessibilityService() {

    var mLayout: FrameLayout? = null

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {}
    override fun onInterrupt() {}

    override fun onServiceConnected() {
        super.onServiceConnected()

        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayout = FrameLayout(this)
        val lp = WindowManager.LayoutParams()
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        lp.format = PixelFormat.TRANSLUCENT
        lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.TOP
        val inflater = LayoutInflater.from(this)
        inflater.inflate(R.layout.overlay, mLayout)
        wm.addView(mLayout, lp)

        configureScrollButton()
    }

    private fun configureScrollButton() {
        val scrollButton =
            mLayout!!.findViewById<View>(R.id.tree) as Button
        scrollButton.setOnClickListener {
            getTree(rootInActiveWindow)
        }
    }

    private fun getTree(root: AccessibilityNodeInfo) {
        val deque: Deque<AccessibilityNodeInfo> =
            ArrayDeque()
        deque.add(root)
        while (!deque.isEmpty()) {
            val node = deque.removeFirst()
            for (i in 0 until node.childCount) {
                deque.addLast(node.getChild(i))
            }
            if (node.text != null) {
                val text = node.text.toString()
                Log.d("NODE: TEXT", text)
            }
            if (node.contentDescription != null) {
                val description = node.contentDescription.toString()
                Log.d("NODE: DESCRIPTION", description)
            }
        }
    }
}