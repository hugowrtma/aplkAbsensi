package com.absensi.mobile_data_app


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
//import com.absensi.mobile_data_app.databinding.ActivitySimpanBinding
import com.kelompok_4.mobile_data_app.databinding.ActivitySimpanBinding
import org.json.JSONObject


class simpan : AppCompatActivity() {
    private lateinit var binding: ActivitySimpanBinding
    var Ab = "Present"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpanBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            }else if (nim.isEmpty()) {
                binding.etMatakuliah.error = "Kosong"
                binding.etMatakuliah.requestFocus()
            }else if (jurusan.isEmpty()) {
                binding.etJurusan.error = "Kosong"
                binding.etJurusan.requestFocus()
            }else{
                //simpan data
                AndroidNetworking.post("https://192.168.100.13/data_api/simpan.php")
                    .addBodyParameter("tanggal", tanggal)
                    .addBodyParameter("nama", nama)
                    .addBodyParameter("nim", nim)
                    .addBodyParameter("matakuliah", matakuliah)
                    .addBodyParameter("jurusan", jurusan)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            if (response.getInt("succes") == 1){
                                Toast.makeText(this@simpan,response.getString("pesan"),Toast.LENGTH_SHORT).show()
                                finish()
                            }else{
                                Toast.makeText(this@simpan,response.getString("pesan"),Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onError(error: ANError) {
                            Toast.makeText(this@simpan,error.toString(),Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
        binding.rgAb.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.abP.id){
                Ab = "Present"
            }else if(checkedId == binding.abL.id){
                Ab = "Absent"
            }else {
                Ab = "Late"
            }
        }
    }
}
