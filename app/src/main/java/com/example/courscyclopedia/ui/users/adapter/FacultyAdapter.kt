package com.example.courscyclopedia.ui.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courscyclopedia.R
import com.example.courscyclopedia.model.Faculty
import android.widget.ImageView;
import com.bumptech.glide.Glide;

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

//    class FacultyViewHolder(itemView: View, private val onFacultyClick: (Faculty) -> Unit) : RecyclerView.ViewHolder(itemView) {
//        private val tvFacultyName: TextView = itemView.findViewById(R.id.tvFacultyName)
//
//        fun bind(faculty: Faculty) {
//            tvFacultyName.text = faculty.facultyName
//            itemView.setOnClickListener {
//                onFacultyClick(faculty) // Pass the entire Faculty object
//            }
//        }
//    }
class FacultyViewHolder(itemView: View, private val onFacultyClick: (Faculty) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val tvFacultyName: TextView = itemView.findViewById(R.id.tvFacultyName)
    private val ivFacultyImage: ImageView = itemView.findViewById(R.id.ivFacultyImage)

    fun bind(faculty: Faculty) {
        tvFacultyName.text = faculty.facultyName
        if (faculty.imageUrl != null && faculty.imageUrl.isNotEmpty()) {
            Glide.with(itemView.context)
                .load(faculty.imageUrl)
                .placeholder(R.drawable.ic_default_image) // A default image in drawable
                .into(ivFacultyImage)
        } else {
            ivFacultyImage.setImageResource(R.drawable.ic_default_image)
        }

        itemView.setOnClickListener {
            onFacultyClick(faculty) // Pass the entire Faculty object
        }
    }
}

}