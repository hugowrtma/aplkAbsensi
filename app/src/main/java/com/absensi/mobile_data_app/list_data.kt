package com.absensi.mobile_data_app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
//import com.absensi.mobile_data_app.databinding.ActivityListDataBinding
import com.kelompok_4.mobile_data_app.databinding.ActivityListDataBinding
import org.json.JSONObject



class list_data : AppCompatActivity() {
    private lateinit var binding: ActivityListDataBinding
    var result = ArrayList<model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
        tampil_data()

    }
    fun tampil_data (){
        AndroidNetworking.get("http://192.168.100.13/data_api/list_data.php")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    result.clear()
                    if (response.getInt("success") == 1){
                        val jsonArray = response.optJSONArray("data")

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.optJSONObject(i)
                            result.add(
                                model(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("tanggal"),
                                    jsonObject.getString("nama"),
                                    jsonObject.getString("nim"),
                                    jsonObject.getString("matakuliah"),
                                    jsonObject.getString("absensi"),
                                    jsonObject.getString("jurusan")

                                )
                            )
                        }
                        val tampil_data = adapter (this@list_data, result)
                        binding.rvList.adapter = tampil_data

                    }else{
                        Toast.makeText(this@list_data,response.getString("pesan"),Toast.LENGTH_LONG).show()
                    }

                }

                override fun onError(error: ANError) {
                    Log.d("list_data", error.toString())
                }
            })
    }
}