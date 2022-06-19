package com.tutorial.myapplication.ui.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tutorial.myapplication.R
import com.tutorial.myapplication.data.AppDataBase
import com.tutorial.myapplication.databinding.FragmentStudentRegistryBinding
import com.tutorial.myapplication.model.Student
import com.tutorial.myapplication.repository.StudentRepository
import com.tutorial.myapplication.util.Utils

class StudentRegistryFragment : Fragment() {
    private var _binding: FragmentStudentRegistryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentRegistryViewModel by viewModels {
        StudentRegistryViewModelFactory(
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
        _binding = FragmentStudentRegistryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, Utils.nationalityList)
        binding.nationalityDropdown.setAdapter(adapter)

        binding.saveButton.setOnClickListener {
            if (canSave()) {
                viewModel.insertStudent(createStudent())
                toastMessage(getString(R.string.student_registry_save_message))
                findNavController().navigateUp()
            }
            else
                toastMessage(getString(R.string.student_registry_failed_save_message))
        }
    }

    private fun canSave(): Boolean {
        if (binding.nameTextInput.text.toString() == "" ||
            binding.semesterTextInput.text.toString() == "" ||
            binding.semesterTextInput.text.toString() == "0" ||
            binding.nationalityDropdown.text.toString() == "") {

            binding.nameTextInputLayout.error =
                if (binding.nameTextInput.text.toString() == "")
                getString(R.string.name_input_error_message)
            else
                null

            binding.semesterTextInputLayout.error =
                when (binding.semesterTextInput.text.toString()) {
                "" -> getString(R.string.semester_input_error_message)
                "0" -> getString(R.string.semester_input_error_message_2)
                else -> null
            }

            binding.nationalityDropdownLayout.error =
                if (binding.nationalityDropdown.text.toString() == "")
                    getString(R.string.nationality_input_error_message)
                else
                    null

            return false
        }

        binding.nameTextInputLayout.error = null
        binding.semesterTextInputLayout.error = null
        binding.nationalityDropdownLayout.error = null

        return true
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun createStudent(): Student {
        return Student(
            0,
            binding.nameTextInput.text.toString(),
            binding.semesterTextInput.text.toString().toInt(),
            binding.nationalityDropdown.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}