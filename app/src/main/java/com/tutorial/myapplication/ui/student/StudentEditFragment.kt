package com.tutorial.myapplication.ui.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tutorial.myapplication.R
import com.tutorial.myapplication.data.AppDataBase
import com.tutorial.myapplication.databinding.FragmentStudentEditBinding
import com.tutorial.myapplication.model.Student
import com.tutorial.myapplication.repository.StudentRepository
import com.tutorial.myapplication.util.Utils

class StudentEditFragment : Fragment() {
    private val args: StudentEditFragmentArgs by navArgs()
    private var _binding: FragmentStudentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentEditViewModel by viewModels {
        StudentEditViewModelFactory(
            StudentRepository(
                AppDataBase.getInstance(requireContext()).studentDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, Utils.nationalityList)
        binding.nationalityEditDropdown.setAdapter(adapter)
        
        binding.updateEditButton.setOnClickListener { 
            if (canUpdate()) {
                viewModel.updateStudent(updatedStudent())
                toastMessage(getString(R.string.student_edit_update_message))
                findNavController().navigateUp()
            }
            else
                toastMessage(getString(R.string.student_edit_failed_update_message))
        }

        viewModel.getStudent(args.studentId).observe(viewLifecycleOwner, Observer {
            fillFields(it)
        })
    }

    private fun canUpdate(): Boolean {
        if (binding.nameEditTextInput.text.toString() == "" ||
            binding.semesterEditTextInput.text.toString() == "" ||
            binding.semesterEditTextInput.text.toString() == "0" ||
            binding.nationalityEditDropdown.text.toString() == "") {

            binding.nameEditTextInputLayout.error =
                if (binding.nameEditTextInput.text.toString() == "")
                    getString(R.string.name_input_error_message)
                else
                    null

            binding.semesterEditTextInputLayout.error =
                when (binding.semesterEditTextInput.text.toString()) {
                    "" -> getString(R.string.semester_input_error_message)
                    "0" -> getString(R.string.semester_input_error_message_2)
                    else -> null
                }

            binding.nationalityEditDropdownLayout.error =
                if (binding.nationalityEditDropdown.text.toString() == "")
                    getString(R.string.nationality_input_error_message)
                else
                    null

            return false
        }

        binding.nameEditTextInputLayout.error = null
        binding.semesterEditTextInputLayout.error = null
        binding.nationalityEditDropdownLayout.error = null

        return true
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun fillFields(student: Student) {
        binding.nameEditTextInput.setText(student.name)
        binding.semesterEditTextInput.setText(student.semester.toString())
        binding.nationalityEditDropdown.setText(student.nationality,false)
    }

    private fun updatedStudent(): Student {
        return Student(
            args.studentId,
            binding.nameEditTextInput.text.toString(),
            binding.semesterEditTextInput.text.toString().toInt(),
            binding.nationalityEditDropdown.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}