package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etSourceAmount: EditText
    private lateinit var etDestinationAmount: EditText
    private lateinit var spinnerSourceCurrency: Spinner
    private lateinit var spinnerDestinationCurrency: Spinner

    private val exchangeRates = mapOf(
        "USD" to 1.0,  // Base currency
        "EUR" to 0.85,
        "JPY" to 110.0,
        "VND" to 24000.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etSourceAmount = findViewById(R.id.etSourceAmount)
        etDestinationAmount = findViewById(R.id.etDestinationAmount)
        spinnerSourceCurrency = findViewById(R.id.spinnerSourceCurrency)
        spinnerDestinationCurrency = findViewById(R.id.spinnerDestinationCurrency)

        // Tạo adapter cho Spinner với danh sách đơn vị tiền tệ
        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSourceCurrency.adapter = adapter
        spinnerDestinationCurrency.adapter = adapter

        // Lắng nghe sự thay đổi của số tiền nguồn
        etSourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Lắng nghe sự thay đổi của Spinner tiền tệ
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerSourceCurrency.onItemSelectedListener = spinnerListener
        spinnerDestinationCurrency.onItemSelectedListener = spinnerListener
    }

    private fun convertCurrency() {
        val sourceAmountText = etSourceAmount.text.toString()

        if (sourceAmountText.isEmpty()) {
            etDestinationAmount.setText("")
            return
        }

        // Lấy đơn vị tiền tệ đã chọn
        val sourceCurrency = spinnerSourceCurrency.selectedItem.toString()
        val destinationCurrency = spinnerDestinationCurrency.selectedItem.toString()

        // Lấy tỷ giá quy đổi
        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val destinationRate = exchangeRates[destinationCurrency] ?: 1.0

        // Chuyển đổi số tiền
        val sourceAmount = sourceAmountText.toDoubleOrNull() ?: return
        val destinationAmount = sourceAmount * (destinationRate / sourceRate)

        // Hiển thị số tiền sau khi chuyển đổi
        etDestinationAmount.setText(String.format("%.2f", destinationAmount))
    }
}
