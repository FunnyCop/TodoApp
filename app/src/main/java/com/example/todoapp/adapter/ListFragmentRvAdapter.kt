package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ListFragmentItemBinding
import com.example.todoapp.repo.local.model.TodoEntity

class ListFragmentRvAdapter(

    private val todos: List<TodoEntity>,
    private val deleteTodo: (todo: TodoEntity) -> Unit,
    private val todoDetail: (todo: TodoEntity) -> Unit

) : RecyclerView.Adapter<ListFragmentRvAdapter.ListFragmentRvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListFragmentRvViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: ListFragmentRvViewHolder, position: Int) =
        holder.loadData(todos[position], deleteTodo, todoDetail)

    override fun getItemCount() = todos.size

    class ListFragmentRvViewHolder(

        private val binding: ListFragmentItemBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun getInstance(parent: ViewGroup) = ListFragmentItemBinding.inflate(

               LayoutInflater.from(parent.context),
               parent,
               false

            ).run { ListFragmentRvViewHolder(this) }

        }

        fun loadData(

            todo: TodoEntity,
            deleteTodo: (todo: TodoEntity) -> Unit,
            todoDetail: (todo: TodoEntity) -> Unit

        ) {

            binding.tvTitle.text = todo.title
            binding.tvDescription.text = todo.description

            binding.btnDelete.setOnClickListener { deleteTodo(todo) }
            binding.btnDetail.setOnClickListener { todoDetail(todo) }

        }

    }

}