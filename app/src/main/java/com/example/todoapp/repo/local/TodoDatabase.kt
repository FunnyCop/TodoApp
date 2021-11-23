package com.example.todoapp.repo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.repo.local.model.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {

        @Volatile
        private var todoDatabase: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {

            val tempInstance = todoDatabase

            if (tempInstance != null)
                return tempInstance

            synchronized(this) {

                val instance = Room.databaseBuilder(

                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"

                ).build()

                todoDatabase = instance

                return instance

            }

        }

    }

}