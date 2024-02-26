package com.example.courscyclopedia.ui.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courscyclopedia.R
import com.example.courscyclopedia.model.Faculty

class FacultyAdapter(private val facultyList: List<Faculty>, private val onFacultyClick: (Faculty) -> Unit) : RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_faculty, parent, false)
        return FacultyViewHolder(view, onFacultyClick)
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        val faculty = facultyList[position]
        holder.bind(faculty)
    }

    override fun getItemCount() = facultyList.size

    class FacultyViewHolder(itemView: View, private val onFacultyClick: (Faculty) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val tvFacultyName: TextView = itemView.findViewById(R.id.tvFacultyName)

        fun bind(faculty: Faculty) {
            tvFacultyName.text = faculty.facultyName
            itemView.setOnClickListener {
                onFacultyClick(faculty) // Pass the entire Faculty object
            }
        }
    }

}