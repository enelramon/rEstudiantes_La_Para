package com.tutorial.myapplication.ui.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutorial.myapplication.R
import com.tutorial.myapplication.data.AppDataBase
import com.tutorial.myapplication.databinding.FragmentStudentsBinding
import com.tutorial.myapplication.repository.StudentRepository
import com.tutorial.myapplication.ui.student.adapter.StudentsAdapter
import com.tutorial.myapplication.ui.student.dialog.OptionsDialogFragment

class StudentsFragment : Fragment() {
    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentsViewModel by viewModels {
        StudentViewModelFactory(StudentRepository(
            AppDataBase.getInstance(requireContext()).studentDao()
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = StudentsAdapter().apply {
                // showing a context menu after a long press on a item
                // in the recyclerView
                setOnItemLongClickListener { view, student ->
                    OptionsDialogFragment().apply {
                        onClick { dialog, which ->
                            when (which) {
                                OptionsDialogFragment.EDIT_OPTION -> {
                                    findNavController().navigate(
                                        StudentsFragmentDirections
                                            .actionStudentsFragmentToStudentEditFragment(student.id)
                                    )
                                    dismiss()
                                }
                                OptionsDialogFragment.DELETE_OPTION -> {
                                    viewModel.delete(student)
                                }
                            }
                        }
                    }.show(childFragmentManager, OptionsDialogFragment.TAG)
                    true
                }
            }

            // add a decoration for the items in the recyclerView
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }

        // navigate to student registry fragment
        binding.studentsFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_studentsFragment_to_studentRegistryFragment)
        }

        subscribe()
    }

    private fun subscribe() {
        viewModel.getAll().observe(viewLifecycleOwner, Observer {
            (binding.studentsRecyclerView.adapter as StudentsAdapter).submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}