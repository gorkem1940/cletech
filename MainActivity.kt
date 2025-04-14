package com.example.playeropener

import android.app.*
import android.content.*
import android.graphics.*
import android.os.*
import android.view.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var windowManager: WindowManager
    private var overlayView: View? = null
    private var downTime: Long = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Overlay View (görünmez)
        overlayView = View(this).apply {
            setBackgroundColor(Color.TRANSPARENT)

            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downTime = System.currentTimeMillis()
                        false
                    }
                    MotionEvent.ACTION_UP -> {
                        val duration = System.currentTimeMillis() - downTime
                        if (duration >= 1000) {
                            openWebViewAndRemoveOverlay()
                            true
                        } else {
                            false
                        }
                    }
                    else -> false
                }
            }
        }

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.addView(overlayView, layoutParams)

        moveTaskToBack(true)
    }

    private fun openWebViewAndRemoveOverlay() {
        val intent = Intent(this, ZoomedWebViewActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("unique_id", System.currentTimeMillis()) // Benzersiz veri ile yeni aktivite
        }
        startActivity(intent)

        overlayView?.let {
            windowManager.removeView(it)
            overlayView = null
        }

        handler.postDelayed({
            moveToHomeScreen()

            handler.postDelayed({
                setupOverlayView()
                setupLongPressListener()
            }, 50)
        }, 60000)
    }


    private fun moveToHomeScreen() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun setupOverlayView() {
        overlayView = View(this).apply {
            setBackgroundColor(Color.TRANSPARENT)
        }

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        windowManager.addView(overlayView, layoutParams)
    }

    private fun setupLongPressListener() {
        overlayView?.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downTime = System.currentTimeMillis()
                    false
                }
                MotionEvent.ACTION_UP -> {
                    val duration = System.currentTimeMillis() - downTime
                    if (duration >= 50) {
                        openWebViewAndRemoveOverlay()
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        overlayView?.let {
            windowManager.removeView(it)
        }
        handler.removeCallbacksAndMessages(null)
    }
}
