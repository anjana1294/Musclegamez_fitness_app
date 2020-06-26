package com.musclegamez.fitness_app.ui.camera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.ui.camera.adapter.SetsListAdapter
import kotlinx.android.synthetic.main.activity_show_sets.*

class ShowSetsActivity : AppCompatActivity() {
    val animals: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_sets)

        addAnimals()
        rv_set.layoutManager= LinearLayoutManager(this)
        rv_set.adapter= SetsListAdapter(animals,this)

    }
    fun addAnimals() {
        animals.add("dog")
        animals.add("cat")
        animals.add("owl")
        animals.add("cheetah")
        animals.add("raccoon")
        animals.add("bird")
    }

}