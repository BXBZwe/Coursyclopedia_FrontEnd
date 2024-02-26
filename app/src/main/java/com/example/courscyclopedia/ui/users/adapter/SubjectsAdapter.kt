package com.example.courscyclopedia.ui.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courscyclopedia.R
import com.example.courscyclopedia.model.Subject

class SubjectsAdapter : RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder>() {

    private var subjectsList: List<Subject> = listOf()

    class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewSubjectName: TextView = itemView.findViewById(R.id.textViewSubjectName)

        fun bind(subject: Subject) {
            textViewSubjectName.text = subject.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(subjectsList[position])
    }

    override fun getItemCount() = subjectsList.size

    fun submitList(subjects: List<Subject>) {
        subjectsList = subjects
        notifyDataSetChanged()
    }
}
