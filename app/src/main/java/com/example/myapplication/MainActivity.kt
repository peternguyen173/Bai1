package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var addressHelper: AddressHelper
    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerDistrict: Spinner
    private lateinit var spinnerWard: Spinner
    private lateinit var calendarView: CalendarView
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressHelper = AddressHelper(this)
        addressHelper.init()

        // Initialize views
        spinnerProvince = findViewById(R.id.spinnerProvince)
        spinnerDistrict = findViewById(R.id.spinnerDistrict)
        spinnerWard = findViewById(R.id.spinnerWard)
        calendarView = findViewById(R.id.calendarView)

        setupProvinces()
        setupCalendar()

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            if (validateForm()) {
                Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_LONG).show()  // Thay đổi ở đây
            }
        }
    }

    private fun setupProvinces() {
        val provinces = addressHelper.provinces
        if (provinces != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
            spinnerProvince.adapter = adapter

            spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedProvince = provinces[position]
                    setupDistricts(selectedProvince)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        } else {
            Toast.makeText(this, "No provinces available", Toast.LENGTH_LONG).show()  // Thay đổi ở đây
        }
    }

    private fun setupDistricts(province: String) {
        val districts = addressHelper.getDistricts(province) ?: return
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        spinnerDistrict.adapter = adapter

        spinnerDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDistrict = districts[position]
                setupWards(province, selectedDistrict)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupWards(province: String, district: String) {
        val wards = addressHelper.getWards(province, district) ?: return
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wards)
        spinnerWard.adapter = adapter
    }

    private fun setupCalendar() {
        val calendarButton = findViewById<Button>(R.id.btnShowCalendar)
        calendarButton.setOnClickListener {
            calendarView.visibility = if (calendarView.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            calendarView.visibility = View.GONE
        }
    }

    private fun validateForm(): Boolean {
        val mssv = findViewById<EditText>(R.id.etMSSV).text.toString()
        val hoTen = findViewById<EditText>(R.id.etHoTen).text.toString()
        val isChecked = findViewById<CheckBox>(R.id.cbAcceptTerms).isChecked

        if (mssv.isEmpty() || hoTen.isEmpty() || !isChecked) {
            Toast.makeText(this, "Please fill all fields and accept terms", Toast.LENGTH_LONG).show()  // Thay đổi ở đây
            return false
        }
        return true
    }
}
