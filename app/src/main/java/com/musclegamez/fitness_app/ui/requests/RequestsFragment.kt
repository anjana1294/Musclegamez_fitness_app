package com.musclegamez.fitness_app.ui.requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.base.BaseFragment
import com.musclegamez.fitness_app.ui.requests.adapater.RequestListAdapter
import kotlinx.android.synthetic.main.fragment_requests.*

class RequestsFragment : BaseFragment() {
    val animals: ArrayList<String> = ArrayList()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_requests, container, false)


        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAnimals()
        rv_request.layoutManager= LinearLayoutManager(activity)
        rv_request.adapter= RequestListAdapter(animals,context());
    }
    fun addAnimals() {
        animals.add("Challenge by Jack")
        animals.add("Challenge by Ben")
        animals.add("Workout Today")
        animals.add("Workout Today")
        animals.add("Challenge by Ben")

    }
}
