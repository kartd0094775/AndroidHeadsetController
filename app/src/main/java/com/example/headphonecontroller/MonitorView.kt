package com.example.headphonecontroller


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager
import android.util.Log;
import android.view.KeyEvent;
import android.view.View
import com.example.headphonecontroller.MainActivity.Companion.disabledKeyMode
import kotlinx.android.synthetic.main.activity_main.*

private const val DEBUG_TAG = "MonitorView"

class MonitorView(context: Context) : View(context) {
    private val wakeLock : PowerManager.WakeLock? = null


    override fun dispatchKeyEvent(event : KeyEvent): Boolean {
        Log.d(DEBUG_TAG, "keyCode " + event.keyCode)
        return super.dispatchKeyEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        when (keyCode) {
            827 -> {
                return true
            }
            831 -> {
                return true
            }
            KeyEvent.KEYCODE_VOICE_ASSIST-> {
                return true
            }
            KeyEvent.KEYCODE_HEADSETHOOK-> {
                return true
            }
            KeyEvent.KEYCODE_MEDIA_PLAY -> {
                return true
            }
        }

        if (disabledKeyMode == MainActivity.KeyMode.On) return true
        Log.d(DEBUG_TAG, KeyEvent.keyCodeToString(keyCode))
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode : Int, event : KeyEvent) : Boolean {
        return super.onKeyUp(keyCode, event);
    }
}
