package com.musclegamez.fitness_app.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.base.BaseFragment
import com.musclegamez.fitness_app.network.NetworkClient
import com.musclegamez.fitness_app.ui.settings.model.profile.EditProfileRequest
import com.musclegamez.fitness_app.util.AlertUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment() {
    private val ARG_OBJECT = "object"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
//            val textView: TextView = view.findViewById(android.R.id.text1)
//            textView.text = getInt(ARG_OBJECT).toString()
//        }

        btn_profile_edit.setOnClickListener {
            btn_submit.visibility = View.VISIBLE
            btn_profile_edit.visibility = View.GONE
            ed_address.isCursorVisible = true
            ed_address.requestFocus()
        }
        btn_submit.setOnClickListener {
            var address = ed_address.text.toString()
            var phone = ed_phone.text.toString()
            if (address.isEmpty())
                ed_address.error = resources.getString(R.string.address_required)
            else if (phone.isEmpty())
                ed_phone.error = resources.getString(R.string.phone_required)
            else
                updateProfile(address, phone)
        }
        getProfile()
    }


    fun getProfile() {
        showProgressBar()
        var apiServices = NetworkClient.create("dsfsdfsdf");
        apiServices.getProfile()
                .map({ response ->
                    return@map ProfileParser.parse(response)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ profileData ->
//                    try{
//                        Picasso.with(context()).load(profileData.picture)
//                                .placeholder(R.mipmap.app_icon).error(R.mipmap.app_icon)
//                                .into(profile_image)}
//                    catch (e:Exception){
//                        Picasso.with(context()).load(R.mipmap.app_icon)
//                                .into(profile_image)
//                    }
                    ed_address.setText(profileData.address)
                    tv_email.setText(profileData.email)
                    ed_phone.setText(profileData.phone)
                    hideProgressBar()
                }, { error ->
                    hideProgressBar()

                    Log.d("error", error.toString())
                })
    }

    fun updateProfile(address: String, phone: String) {
        showProgressBar()
        var apiServices = NetworkClient.create("dsfsdfsdf");
        apiServices.updateProfile(EditProfileRequest(address, phone))
                .map({ response ->
                    return@map ProfileParser.parse(response)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ userProfile ->
                    hideProgressBar()
                    // iv_edit.visibility = View.GONE
                    ed_address.isEnabled = false
                    ed_phone.isEnabled = false
                    btn_submit.visibility = View.GONE
                    btn_profile_edit.visibility = View.VISIBLE
                    AlertUtil.showSnackBar(profile_layout, "Profile updated successfully")

                }, { error ->
                    hideProgressBar()
                    AlertUtil.showSnackBar(profile_layout, error.message.toString())
                    Log.d("error", error.toString())
                })
    }

    override fun showProgressBar() {
        super.showProgressBar()
        btn_profile_edit.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        super.hideProgressBar()
        btn_profile_edit.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}