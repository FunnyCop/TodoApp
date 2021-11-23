package com.example.todoapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.adapter.ListFragmentRvAdapter
import com.example.todoapp.databinding.ListFragmentBinding
import com.example.todoapp.repo.local.model.TodoEntity
import com.example.todoapp.util.convert
import com.example.todoapp.util.onClick
import com.example.todoapp.viewmodel.DatastoreViewModel
import com.example.todoapp.viewmodel.KnexViewModel
import com.example.todoapp.viewmodel.TodoViewModel

class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private val datastoreViewModel by viewModels<DatastoreViewModel>()
    private val knexViewModel by viewModels<KnexViewModel>()
    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) = ListFragmentBinding.inflate(

        inflater,
        container,
        false

    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        activity?.title = "All"

        initObservers()
        initListeners()

    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null

    }

    /** Initialize LiveData Observers */
    private fun initObservers() {

        /** API Todos */
        knexViewModel.todos.observe(viewLifecycleOwner) {

            /** If no Todos are stored in Room DB */
            if (todoViewModel.todos.value.isNullOrEmpty())
                /** Convert TodoResponse to TodoEntity and add to Room DB */
                it?.convert()?.let { todoList -> todoViewModel.addTodos(todoList) }

        }

        /** Room DB Todos */
        todoViewModel.todos.observe(viewLifecycleOwner) {

            /** Create RecyclerView Adapter and assign to layout */
            binding.rvMain.adapter = ListFragmentRvAdapter(it, ::deleteTodo, ::todoDetail)

        }

        /** Get Todos from API */
        knexViewModel.getTodos(context)

    }

    /** Initialize Event Listeners */
    private fun initListeners() = with(binding) {

        /** Add Button */
        btnAdd.onClick {

            /** Navigate to Compose Fragment */
            findNavController().navigate(ListFragmentDirections.actionListFragmentToComposeFragment())

        }

        /** Log Out Button */
        btnLogOut.onClick {

            /** Room DB Todos */
            with(todoViewModel) {

                /** Delete all Todos in Room DB */
                todos.observe(viewLifecycleOwner) { deleteTodos(it) }

            }

            /** Preferences Datastore */
            with(datastoreViewModel) {

                /** Reset all saved Preferences */
                setUsername(context, "")
                setPassword(context, "")
                setToken(context, "")

            }

            /** Navigate to Login Fragment */
            findNavController().navigate(ListFragmentDirections.actionListFragmentToLoginFragment())

        }

    }

    /** Delete Button On Click Event */
    private fun deleteTodo(todo: TodoEntity) =
        /** Delete with API */
        knexViewModel.deleteTodo(todo, todoViewModel, context)

    /** Detail Button On Click Event */
    private fun todoDetail(todo: TodoEntity) =
        /** Navigate to Detail Fragment */
        findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(todo))

}