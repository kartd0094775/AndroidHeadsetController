package com.example.headphonecontroller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.headphonecontroller.permissions.AppPermission
import kotlinx.android.synthetic.main.activity_main.*

private const val DEBUG_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    enum class KeyMode {
        On,
        Off
    }

    companion object {
        var disabledKeyMode = KeyMode.Off
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainActivityToggleBtn.setOnClickListener {
            if (disabledKeyMode == KeyMode.Off) {
                MainActivityToggleBtn.text = "Disabled Key Now"
                disabledKeyMode = KeyMode.On
            }
            else {
                MainActivityToggleBtn.text = "Enabled Key Now"
                disabledKeyMode = KeyMode.Off
            }
        }


        val intent = Intent(this, HeadsetService::class.java)
        startService(intent)
    }
}
