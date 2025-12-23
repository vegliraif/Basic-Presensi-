package com.vegli.presensi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vegli.presensi.model.Presensi

class PresensiAdapter(
    private val list: List<Presensi>
) : RecyclerView.Adapter<PresensiAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNama: TextView = view.findViewById(R.id.txtNama)
        val txtNim: TextView = view.findViewById(R.id.txtNim)
        val txtTanggal: TextView = view.findViewById(R.id.txtTanggal)
        val txtStatus: TextView = view.findViewById(R.id.txtStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_presensi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val presensi = list[position]

        holder.txtNama.text = presensi.nama ?: "-"
        holder.txtNim.text = presensi.nim ?: "-"
        holder.txtTanggal.text = presensi.tanggal ?: "-"
        holder.txtStatus.text = presensi.status ?: "-"
    }

    override fun getItemCount(): Int = list.size
}
