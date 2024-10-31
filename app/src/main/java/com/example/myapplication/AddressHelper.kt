package com.example.myapplication

import android.content.Context
import org.json.JSONObject
import java.io.InputStreamReader

class AddressHelper(private val context: Context) {

    private var data: JSONObject? = null

    // Initialize the AddressHelper by loading the JSON file.
    fun init() {
        val inputStream = context.resources.openRawResource(R.raw.tree)
        val reader = InputStreamReader(inputStream)
        val content = reader.readText()
        reader.close()

        data = JSONObject(content)
    }

    // Get list of provinces
    val provinces: List<String>?
        get() {
            val list = mutableListOf<String>()
            data?.keys()?.forEach { key ->
                val provinceName = data!!.getJSONObject(key).getString("name")
                list.add(provinceName)
            }
            return list
        }

    // Get list of districts based on province
    fun getDistricts(province: String): List<String>? {
        val list = mutableListOf<String>()

        // Find the province
        val jProvince = data?.keys()?.asSequence()?.mapNotNull { key ->
            data?.getJSONObject(key)?.takeIf { it.getString("name") == province }
        }?.firstOrNull()

        // If province is found, get its districts
        jProvince?.getJSONObject("quan-huyen")?.keys()?.forEach { key ->
            val districtName = jProvince.getJSONObject("quan-huyen").getJSONObject(key).getString("name")
            list.add(districtName)
        }

        return list
    }

    // Get list of wards based on province and district
    fun getWards(province: String, district: String): List<String>? {
        val list = mutableListOf<String>()

        // Find the province
        val jProvince = data?.keys()?.asSequence()?.mapNotNull { key ->
            data?.getJSONObject(key)?.takeIf { it.getString("name") == province }
        }?.firstOrNull()

        // Find the district within the province
        val jDistrict = jProvince?.getJSONObject("quan-huyen")?.keys()?.asSequence()?.mapNotNull { key ->
            jProvince.getJSONObject("quan-huyen").getJSONObject(key).takeIf {
                it.getString("name") == district
            }
        }?.firstOrNull()

        // If district is found, get its wards
        jDistrict?.getJSONObject("xa-phuong")?.keys()?.forEach { key ->
            val wardName = jDistrict.getJSONObject("xa-phuong").getJSONObject(key).getString("name")
            list.add(wardName)
        }

        return list
    }
}
