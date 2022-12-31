package com.absensi.mobile_data_app

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
//mport com.absensi.mobile_data_app.databinding.OutputDataBinding
import com.kelompok_4.mobile_data_app.databinding.OutputDataBinding
import org.json.JSONObject
import java.util.ArrayList


class adapter(private val context: Context, private val result: ArrayList<model>) : RecyclerView.Adapter<adapter.MyHolder>() {
    private var Items = ArrayList<model>()

    init {
        this.Items = result
    }

    inner class MyHolder(val binding: OutputDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            OutputDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var result = Items[position]
        with(holder) {
            binding.tvTanggal.text = result.tanggal
            binding.tvNama.text = result.nama
            binding.tvNim.text = result.nim
            binding.tvMatakuliah.text = result.matakuliah
            if (result.absensi == "present") {
                binding.tvAbsensi.text = "Present"
            } else if(result.absensi == "absent") {
                binding.tvAbsensi.text = "Absent"
            }else{
                binding.tvAbsensi.text = "late"
            }
            binding.tvJurusan.text = result.jurusan
            binding.root.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Pilih Pengaturan")
                val pilihan = arrayOf("Edit", "Delete", "Cancel")
                builder.setItems(pilihan) { dialog, which ->
                    when (which) {
                        0 -> {
                            val a = Intent(context, edit::class.java)
                            a.putExtra("id", result.id)
                            context.startActivity(a)
                        }

                        1 -> {
                            delete(result.id)
                        }

                        2 -> {
                            dialog.dismiss()
                        }
                    }
                }
                val dialog = builder.create()
                dialog.show()
            }
        }

    }


    override fun getItemCount(): Int {
        return Items.size
        TODO("Not yet implemented")
    }
    fun delete(id: String){
        AndroidNetworking.post("http://192.168.100.13/data_api/hapus.php")
            .addBodyParameter("id", id)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if (response.getInt("success") == 1) {
                        Toast.makeText(context, response.getString("pesan"), Toast.LENGTH_LONG).show()
                        (context as Activity).finish()
                    } else {
                        Toast.makeText(context, response.getString("pesan"), Toast.LENGTH_LONG).show()
                    }

                }

                override fun onError(error: ANError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                }
            })
    }
}

