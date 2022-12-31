package com.absensi.mobile_data_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.absensi.mobile_data_app.databinding.ActivityMainBinding
import com.kelompok_4.mobile_data_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btMulai.setOnClickListener {
            val intent = Intent(this@MainActivity, MenuUtama::class.java)
            startActivity(intent)
        }
    }
}