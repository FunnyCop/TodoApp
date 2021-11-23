package com.example.todoapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.ComposeFragmentBinding
import com.example.todoapp.util.onClick
import com.example.todoapp.util.stringText
import com.example.todoapp.viewmodel.KnexViewModel
import com.example.todoapp.viewmodel.TodoViewModel

class ComposeFragment : Fragment() {

    private var _binding: ComposeFragmentBinding? = null
    private val binding get() = _binding!!

    private val knexViewModel by viewModels<KnexViewModel>()
    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) = ComposeFragmentBinding.inflate(

        inflater,
        container,
        false

    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Compose"

        /** Get Todos from API */
        knexViewModel.getTodos(context)

        initListeners()

    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null

    }

    /** Initialize Event Listeners */
    private fun initListeners() = with(binding) {

        /** Submit Button */
        btnSubmit.onClick {

            val title = etTitle.stringText
            val description = etDescription.stringText

            /** Title is not empty */
            if (title != "")
                /** Add with API */
                knexViewModel.addTodo(title, description, context, todoViewModel) {

                    /** Navigate to List Fragment if successful */
                    findNavController().navigate(ComposeFragmentDirections.actionComposeFragmentToListFragment())

                }

        }

        /** Back Button */
        btnBack.onClick {

            /** Navigate to List Fragment */
            findNavController().navigate(ComposeFragmentDirections.actionComposeFragmentToListFragment())

        }

    }

}