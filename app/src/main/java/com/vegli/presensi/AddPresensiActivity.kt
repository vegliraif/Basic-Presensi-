package com.vegli.presensi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.vegli.presensi.databinding.ActivityAddPresensiBinding
import com.vegli.presensi.model.Presensi

class AddPresensiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPresensiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPresensiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ref = FirebaseDatabase
            .getInstance()
            .getReference("presensi")

        binding.btnSimpan.setOnClickListener {

            val nama = binding.edtNama.text.toString().trim()
            val nim = binding.edtNim.text.toString().trim()
            val tanggal = binding.edtTanggal.text.toString().trim()
            val status = binding.edtStatus.text.toString().trim()

            // VALIDASI WAJIB
            if (nama.isEmpty() || nim.isEmpty() || tanggal.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // AMAN (tidak pakai !!)
            val id = ref.push().key
            if (id == null) {
                Toast.makeText(this, "Gagal generate ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val presensi = Presensi(
                id = id,
                nama = nama,
                nim = nim,
                tanggal = tanggal,
                status = status
            )

            ref.child(id).setValue(presensi)
                .addOnSuccessListener {
                    Toast.makeText(this, "Presensi berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    finish() // KEMBALI ke MainActivity
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal menyimpan: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
