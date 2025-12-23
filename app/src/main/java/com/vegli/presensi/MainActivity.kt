package com.vegli.presensi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.vegli.presensi.databinding.ActivityMainBinding
import com.vegli.presensi.model.Presensi

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = mutableListOf<Presensi>()
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Firebase Realtime Database
        ref = FirebaseDatabase
            .getInstance("https://presensi-cce50-default-rtdb.firebaseio.com/")
            .getReference("presensi")

        // RecyclerView
        binding.rvPresensi.layoutManager = LinearLayoutManager(this)
        binding.rvPresensi.adapter = PresensiAdapter(list)

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddPresensiActivity::class.java))
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (s in snapshot.children) {
                    val presensi = s.getValue(Presensi::class.java)
                    presensi?.let { list.add(it) }
                }
                binding.rvPresensi.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
