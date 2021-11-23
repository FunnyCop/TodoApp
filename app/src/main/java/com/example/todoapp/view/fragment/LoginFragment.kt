package com.example.todoapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.LoginFragmentBinding
import com.example.todoapp.util.onClick
import com.example.todoapp.util.stringText
import com.example.todoapp.viewmodel.DatastoreViewModel
import com.example.todoapp.viewmodel.KnexViewModel

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val datastoreViewModel by viewModels<DatastoreViewModel>()
    private val knexViewModel by viewModels<KnexViewModel>()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) = LoginFragmentBinding.inflate(

        inflater,
        container,
        false

    ).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Login"

        initObservers()
        initListeners()

    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null

    }

    /** Initialize LiveData Observers */
    private fun initObservers() {

        /** Datastore Token */
        datastoreViewModel.token(context)?.observe(viewLifecycleOwner) {

            /** If Token is not empty */
            if (it != "")
                /** Navigate to List Fragment */
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToListFragment())

        }

    }

    /** Initialize Event Listeners */
    private fun initListeners() = with(binding) {

        /** Submit Button */
        btnSubmit.onClick {

            val username = etUsername.stringText
            val password = etPassword.stringText

            /** If Username and Password are not empty */
            if (username != "" && password != "")
                /** Login with API View Model */
                knexViewModel.login(username, password, context)

        }

        /** Register Button */
        btnRegister.onClick {

            /** Navigate to Register Fragment */
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())

        }

    }

}