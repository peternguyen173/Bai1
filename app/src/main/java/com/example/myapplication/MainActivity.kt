package com.example.myapplication

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView
    private var lastButton: Button? = null // Lưu trữ nút đã bấm cuối cùng
    private var lastButtonColor: Int = Color.parseColor("#dedede") // Màu gốc của nút trước đó

    var state: Int = 1 // state 1: nhập số đầu tiên, state 2: nhập số thứ hai
    var op: Int = 0 // phép toán (1: cộng, 2: trừ, 3: nhân, 4: chia)
    var op1: Int = 0 // số đầu tiên
    var op2: Int = 0 // số thứ hai

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text_result)

        // Gán sự kiện click cho các nút số
        val numberButtons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener(this)
        }

        // Gán sự kiện click cho các nút phép toán
        findViewById<Button>(R.id.buttonAdd).setOnClickListener(this)
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener(this)
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener(this)
        findViewById<Button>(R.id.buttonDivide).setOnClickListener(this)
        findViewById<Button>(R.id.buttonEqual).setOnClickListener(this)
        findViewById<Button>(R.id.buttonClear).setOnClickListener(this) // Nút C để reset
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        val clickedButton = p0 as? Button
        resetButtonColor(clickedButton) // Đổi màu nút được bấm

        when (id) {
            R.id.button0 -> addDigit(0)
            R.id.button1 -> addDigit(1)
            R.id.button2 -> addDigit(2)
            R.id.button3 -> addDigit(3)
            R.id.button4 -> addDigit(4)
            R.id.button5 -> addDigit(5)
            R.id.button6 -> addDigit(6)
            R.id.button7 -> addDigit(7)
            R.id.button8 -> addDigit(8)
            R.id.button9 -> addDigit(9)
            R.id.buttonAdd -> {
                op = 1
                state = 2
            }
            R.id.buttonSubtract -> {
                op = 2
                state = 2
            }
            R.id.buttonMultiply -> {
                op = 3
                state = 2
            }
            R.id.buttonDivide -> {
                op = 4
                state = 2
            }
            R.id.buttonEqual -> {
                var result = 0
                when (op) {
                    1 -> result = op1 + op2
                    2 -> result = op1 - op2
                    3 -> result = op1 * op2
                    4 -> result = if (op2 != 0) op1 / op2 else 0 // Kiểm tra chia cho 0
                }
                textResult.text = "$result"
                op1 = result
                op2 = 0
                op = 0
                state = 1 // Quay lại nhập số tiếp theo
            }
            R.id.buttonClear -> { // Nút C để reset tất cả
                reset()
            }
        }
    }

    private fun addDigit(c: Int) {
        if (state == 1) {
            op1 = op1 * 10 + c
            textResult.text = "$op1"
        } else {
            op2 = op2 * 10 + c
            textResult.text = "$op2"
        }
    }

    private fun reset() {
        state = 1
        op1 = 0
        op2 = 0
        op = 0
        textResult.text = "0"
        resetButtonColor(null) // Reset màu cho nút cuối cùng khi reset
    }

    private fun resetButtonColor(clickedButton: Button?) {
        // Trả lại màu gốc cho nút đã bấm trước đó
        lastButton?.setBackgroundColor(lastButtonColor) // trả về màu đã lưu trước đó

        // Đổi màu cho nút hiện tại
        clickedButton?.let {
            val background = it.background as? ColorDrawable
            lastButtonColor = background?.color ?: Color.parseColor("#efefef") // Lưu lại màu hiện tại của nút
            it.setBackgroundColor(Color.YELLOW) // Đổi sang màu vàng
        }

        // Ghi nhớ nút đã bấm
        lastButton = clickedButton
    }
}
