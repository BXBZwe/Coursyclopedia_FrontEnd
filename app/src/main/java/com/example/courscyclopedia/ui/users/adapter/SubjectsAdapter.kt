package com.example.courscyclopedia.ui.users.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courscyclopedia.R
import com.example.courscyclopedia.model.Subject
import android.widget.Filter
import android.widget.Filterable
import java.util.Locale

class SubjectsAdapter(private val onSubjectClick: (Subject) -> Unit) :
    RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder>(), Filterable {

    private var subjectsList: List<Subject> = listOf()
    private var subjectsListFiltered: List<Subject> = subjectsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view, onSubjectClick)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(subjectsListFiltered[position]) // Use the filtered list
    }

    override fun getItemCount(): Int = subjectsListFiltered.size

    fun submitList(subjects: List<Subject>) {
        this.subjectsList = subjects
        this.subjectsListFiltered = subjects // Update both lists
        notifyDataSetChanged()
    }

    // Filter Class
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                subjectsListFiltered = if (charString.isEmpty()) subjectsList else {
                    subjectsList.filter {
                        it.subjectname.contains(charString, ignoreCase = true)
                    }
                }
                return FilterResults().apply { values = subjectsListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                subjectsListFiltered = results?.values as List<Subject>
                notifyDataSetChanged()
            }
        }
    }


    class SubjectViewHolder(itemView: View, val onSubjectClick: (Subject) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val textViewSubjectName: TextView = itemView.findViewById(R.id.textViewSubjectName)

        fun bind(subject: Subject) {
            textViewSubjectName.text = subject.subjectname
            itemView.setOnClickListener { onSubjectClick(subject) }
        }
    }
}

/*
class SubjectsAdapter(private val onSubjectClick: (Subject) -> Unit) : RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder>() {

    private var subjectsList: List<Subject> = listOf()

    class SubjectViewHolder(itemView: View, val onSubjectClick: (Subject) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val textViewSubjectName: TextView = itemView.findViewById(R.id.textViewSubjectName)

        fun bind(subject: Subject) {
            Log.d("SubjectsAdapter", "Binding subject: ${subject.subjectname}")
            textViewSubjectName.text = subject.subjectname
            itemView.setOnClickListener {
                onSubjectClick(subject) // Invoke the click listener, passing the subject
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view, onSubjectClick)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(subjectsList[position])
    }

    override fun getItemCount() = subjectsList.size

    fun submitList(subjects: List<Subject>) {
        this.subjectsList = subjects
        notifyDataSetChanged()
    }
}
*/