package com.example.todoapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.ComposeFragmentBinding
import com.example.todoapp.repo.local.model.TodoEntity
import com.example.todoapp.util.editable
import com.example.todoapp.util.onClick
import com.example.todoapp.util.stringText
import com.example.todoapp.viewmodel.KnexViewModel
import com.example.todoapp.viewmodel.TodoViewModel

class EditFragment : Fragment() {

    private var _binding: ComposeFragmentBinding? = null
    private val binding get() = _binding!!

    private val knexViewModel by viewModels<KnexViewModel>()
    private val todoViewModel by viewModels<TodoViewModel>()

    private val todo by lazy { arguments?.getParcelable<TodoEntity>("todo") }

    private var _todos: List<TodoEntity>? = null
    private val todos get() = _todos!!

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

        activity?.title = "Edit"

        initViews()
        initObservers()
        initListeners()

    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null

    }

    /** Initialize Views */
    private fun initViews() = with(binding) {

        todo?.let {

            /** Set EditText Text */
            etTitle.text = it.title.editable
            etDescription.text = it.description?.editable

        }

    }

    /** Initialize LiveData Observers */
    private fun initObservers() {

        /** Room DB Todos */
        todoViewModel.todos.observe(viewLifecycleOwner) {

            /** Store Todos */
            _todos = it

        }

    }

    /** Initialize Event Listeners */
    private fun initListeners() = with(binding) {

        /** Submit Button */
        btnSubmit.onClick {

            val id = todo?.id
            val title = etTitle.stringText
            val description =  etDescription.stringText

            /** If Title is not empty and ID is not null */
            if (title != "" && id != null)
                knexViewModel.updateTodo(id, title, description, context) {

                    /** Delete all Todos in Room DB */
                    todoViewModel.deleteTodos(todos)

                    todo?.let {

                        /** Create shallow copy of entity */
                        val editedTodo = TodoEntity(id, title, description, it.completed, it.userId, it.date, it.updatedAt)

                        /** Navigate to Detail Fragment */
                        findNavController().navigate(EditFragmentDirections.actionEditFragmentToDetailFragment(editedTodo))

                    }

                }

        }

        /** Back Button */
        btnBack.onClick {

            /** Navigate to Detail Fragment */
            todo?.let { findNavController().navigate(EditFragmentDirections.actionEditFragmentToDetailFragment(it)) }

        }

    }

}