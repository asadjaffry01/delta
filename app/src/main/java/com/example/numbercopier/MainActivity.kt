package com.example.numbercopier

import android.content.*
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var numbers: List<String>
    private var index = 0
    private var cc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputNumbers = findViewById<EditText>(R.id.inputNumbers)
        val inputInitial = findViewById<EditText>(R.id.inputInitial)
        val inputCC = findViewById<EditText>(R.id.inputCC)
        val btnStart = findViewById<Button>(R.id.btnStart)

        btnStart.setOnClickListener {
            numbers = inputNumbers.text.toString()
                .split("\n")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            index = if (inputInitial.text.isEmpty()) 0
                    else inputInitial.text.toString().toInt() - 1

            cc = inputCC.text.toString()
            showFloatingButton()
        }
    }

    private fun showFloatingButton() {
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val button = Button(this)
        button.text = "Copy"

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            android.graphics.PixelFormat.TRANSLUCENT
        )

        button.setOnClickListener {
            if (index < numbers.size) {
                var num = numbers[index]

                if (cc.isNotEmpty() && num.startsWith(cc)) {
                    num = num.substring(cc.length)
                }

                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("num", num))

                index++
                Toast.makeText(this, "Copied: $num", Toast.LENGTH_SHORT).show()
            }
        }

        windowManager.addView(button, params)
    }
}
