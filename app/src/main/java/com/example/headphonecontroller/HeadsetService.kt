package com.example.headphonecontroller

import android.app.Service
import android.content.*
import android.graphics.ImageFormat
import android.graphics.PixelFormat
import android.media.AudioManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.UnsupportedOperationException

private const val DEBUG_TAG = "HeadsetService"

class HeadsetService : Service() {

    private lateinit var mHeadsetReceiver: BroadcastReceiver
    private lateinit var mAudioManager : AudioManager
    private lateinit var mReceiveComponent : ComponentName

    private var mWindowManager : WindowManager? = null
    private var mMonitorView : MonitorView? = null

    override fun onCreate() {
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mReceiveComponent = ComponentName(this, HeadsetReceiver::class.java)
        mAudioManager.registerMediaButtonEventReceiver(mReceiveComponent)
        showWindow()

//        mHeadsetReceiver = HeadsetReceiver()
//        val filter = IntentFilter(Intent.ACTION_ALL_APPS)
//        registerReceiver(mHeadsetReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        unregisterReceiver(mHeadsetReceiver)
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    private fun showWindow() {
        if (mWindowManager == null) {
            mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            mMonitorView = MonitorView(this)
        }
        val params = WindowManager.LayoutParams(
            1, 1, //Must be at least 1x1
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            //Don't know if this is a safe default
            PixelFormat.TRANSLUCENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0+,不设置这个flag可能会报错
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        params.format= ImageFormat.NV16;//设置背景图片
        //Don't set the preview visibility to GONE or INVISIBLE
        mWindowManager?.addView(mMonitorView, params);
    }

    private fun hideWindow() {
            mWindowManager?.removeView(mMonitorView)
    }

    private fun addWindow() {
        val params = WindowManager.LayoutParams(
            1, 1,
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0+,不设置这个flag可能会报错
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                params.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            params.format = ImageFormat.NV16;//设置背景图片
            mWindowManager?.addView(mMonitorView, params);
    }

    class HeadsetReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(DEBUG_TAG, "Service")
            if (Intent.ACTION_MEDIA_BUTTON == intent?.action) {
                val keyEvent = intent.getParcelableArrayListExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)!!
                Log.d(DEBUG_TAG, keyEvent.toString())
            }
        }
    }

}