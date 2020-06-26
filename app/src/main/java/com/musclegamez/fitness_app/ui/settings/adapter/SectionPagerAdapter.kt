package com.musclegamez.fitness_app.ui.settings.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.musclegamez.fitness_app.ui.settings.AboutUsFragment
import com.musclegamez.fitness_app.ui.settings.ChatSupportFragment
import com.musclegamez.fitness_app.ui.settings.ProfileFragment

class SectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val ARG_OBJECT = "object"

    override fun getCount(): Int = 3


    override fun getItem(i: Int): Fragment {
//        val fragment = AboutUsFragment()
//        fragment.arguments = Bundle().apply {
//            // Our object is just an integer :-P
//            putInt(ARG_OBJECT, i + 1)
//        }
//        return fragment

        var fragment: Fragment? = null
        if (i==0)
        {
            fragment = ProfileFragment()
        }
        else if(i==1) {
            fragment = AboutUsFragment()
        }
        else if(i==2){
            fragment = ChatSupportFragment()
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence {
       var title:String?=null
        if (position == 0)
        {
            title = "My Profile";
        }
        else if (position == 1)
        {
            title = "About Us";
        }
        else if (position == 2)
        {
            title = "Chat Support";
        }
        return title.toString();
    }

}


