package com.musclegamez.fitness_app.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.base.BaseFragment
import com.musclegamez.fitness_app.ui.tasks.adapter.TaskListAdapter
import kotlinx.android.synthetic.main.fragment_task.*

class TasksFragment : BaseFragment() {
    val animals: ArrayList<String> = ArrayList()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_task, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAnimals()
        rv_task.layoutManager= LinearLayoutManager(activity)
        rv_task.adapter=TaskListAdapter(animals,context());
    }
    fun addAnimals() {
        animals.add("Task 1")
        animals.add("Task 2")
        animals.add("Task 3")
        animals.add("Task 4")
        animals.add("Task 5")
        animals.add("Task 6")
    }
}
