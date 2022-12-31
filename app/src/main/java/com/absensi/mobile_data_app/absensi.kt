package com.absensi.mobile_data_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
//import com.absensi.mobile_data_app.databinding.ActivityAbsensiBinding
import com.kelompok_4.mobile_data_app.databinding.ActivityAbsensiBinding
import org.json.JSONObject


class absensi : AppCompatActivity() {
    private lateinit var binding : ActivityAbsensiBinding
    private var ab : String? = "Present"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbsensiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rgAb.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.abP.id) {
                ab= "Present"
            }else if (checkedId == binding.abA.id) {
                ab = "Absent"
            }else {
                ab = "Late"
            }
        }
        binding.btSimpan.setOnClickListener {
            val tanggal = binding.etTanggal.text.toString()
            val nama = binding.etNama.text.toString()
            val nim = binding.etNim.text.toString()
            val matakuliah = binding.etMatakuliah.text.toString()
            val jurusan = binding.etJurusan.text.toString()
            if (tanggal.isEmpty()) {
                binding.etTanggal.error = "Kosong"
                binding.etTanggal.requestFocus()
            } else if (nama.isEmpty()) {
                binding.etNama.error = "Kosong"
                binding.etNama.requestFocus()
            } else if (nim.isEmpty()) {
                binding.etNim.error = "Kosong"
                binding.etNim.requestFocus()
            }else if (matakuliah.isEmpty()){
                binding.etMatakuliah.error = "Kosong"
                binding.etMatakuliah.requestFocus()
            }else if (jurusan.isEmpty()){
                binding.etJurusan.error = "Kosong"
                binding.etJurusan.requestFocus()
            }else{
                save(tanggal,nama,nim,matakuliah, ab.toString(),jurusan)
            }
        }
    }

    fun save(tanggal: String,nama: String,nim: String, matakuliah: String, ab: String, jurusan: String){
        AndroidNetworking.post("http://192.168.100.13/data_api/simpan.php")
            .addBodyParameter("tanggal", tanggal)
            .addBodyParameter("nama", nama)
            .addBodyParameter("nim", nim)
            .addBodyParameter("matakuliah", matakuliah)
            .addBodyParameter("absensi", ab)
            .addBodyParameter("jurusan", jurusan)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    if (response.getInt("success") == 1){
                        Toast.makeText(this@absensi,response.getString("pesan"),Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@absensi,response.getString("pesan"),Toast.LENGTH_LONG).show()
                    }

                }

                override fun onError(error: ANError) {

                    Toast.makeText(this@absensi,error.toString(),Toast.LENGTH_LONG).show()
                }
            })
    }
}