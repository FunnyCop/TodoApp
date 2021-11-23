package com.example.todoapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.DetailFragmentBinding
import com.example.todoapp.repo.local.model.TodoEntity
import com.example.todoapp.util.onClick

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val todo by lazy { arguments?.getParcelable<TodoEntity>("todo") }

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) = DetailFragmentBinding.inflate(

        inflater,
        container,
        false

    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Detail"

        initViews()
        initListeners()

    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null

    }

    /** Initialize Views */
    private fun initViews() = with(binding) {

        todo?.let {

            /** Set TextView Text */
            tvTitle.text = it.title
            tvDescription.text = it.description
            tvDate.text = it.date

        }

    }

    /** Initialize Event Listeners */
    private fun initListeners() = with(binding) {

        /** Edit Button */
        btnEdit.onClick {

            /** Navigate to Edit Fragment */
            todo?.let { findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToEditFragment(it)) }

        }

        /** Back Button */
        btnBack.onClick {

            /** Navigate to List Fragment */
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToListFragment())

        }

    }

}