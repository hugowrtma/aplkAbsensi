package com.absensi.mobile_data_app

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
//import com.absensi.mobile_data_app.databinding.ActivityMenuUtamaBinding
import com.kelompok_4.mobile_data_app.databinding.ActivityMenuUtamaBinding


class MenuUtama : AppCompatActivity() {
    private lateinit var binding : ActivityMenuUtamaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuUtamaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btAbsensi.setOnClickListener {
            startActivity(Intent(this@MenuUtama,absensi::class.java))
        }

        binding.btData.setOnClickListener {
            startActivity(Intent(this@MenuUtama, list_data::class.java))
        }
        binding.btInfo.setOnClickListener {
            startActivity(Intent(this@MenuUtama, info_aplikasi::class.java))
        }
        binding.btCatatan.setOnClickListener {
            startActivity(Intent(this@MenuUtama,MenuCatatan::class.java))
        }
    }
}
