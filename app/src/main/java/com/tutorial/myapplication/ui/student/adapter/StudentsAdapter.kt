package com.tutorial.myapplication.ui.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.myapplication.databinding.ItemStudentBinding
import com.tutorial.myapplication.model.Student

class StudentsAdapter :
    RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {
    private lateinit var _onItemLongClickListener: OnItemLongClickListener
    private var list: List<Student> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding, _onItemLongClickListener)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setOnItemLongClickListener(onItemLongClick: (view: View, student: Student) -> Boolean) {
        _onItemLongClickListener = object : OnItemLongClickListener {
            override fun itemLongClick(view: View, student: Student): Boolean =
                onItemLongClick(view, student)
        }
    }

    fun submitList(list: List<Student>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class StudentViewHolder(
        private val binding: ItemStudentBinding,
        private val onItemLongClickListener: OnItemLongClickListener
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Student) {
            binding.nameItemTextView.text = item.name
            binding.nationalityItemTextView.text = item.nationality
            binding.semesterItemTextView.text = item.semester.toString()

            binding.root.setOnLongClickListener {
                onItemLongClickListener.itemLongClick(it, item)
            }
        }
    }

    interface OnItemLongClickListener {
        fun itemLongClick(view: View, student: Student): Boolean
    }
}