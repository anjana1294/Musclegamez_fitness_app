package com.musclegamez.fitness_app.ui.settings

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.app.livefree.util.PickImageUtils
import com.google.android.material.tabs.TabLayout
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.base.BaseFragment
import com.musclegamez.fitness_app.network.NetworkClient
import com.musclegamez.fitness_app.ui.settings.adapter.SectionPagerAdapter
import com.musclegamez.fitness_app.ui.settings.model.setting.EditSettingRequest
import com.musclegamez.fitness_app.util.AlertUtil
import com.musclegamez.fitness_app.util.Utils
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : BaseFragment() {

    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private lateinit var viewPager: ViewPager
    private val mtabLayout: TabLayout? = null
    var imageBase64 = ""


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sectionPagerAdapter = SectionPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = sectionPagerAdapter

        iv_edit.setOnClickListener {
            PickImageUtils.imagePick(childFragmentManager, {
                var uri = it;

                Utils.encodeToBase64(it) {
                    imageBase64 = it
                    hideProgressDialog()
                }
                profile_image.setImageBitmap(it)
            })

            iv_save.visibility=View.VISIBLE
            iv_edit.visibility=View.GONE
           // iv_setting_save.visibility=View.VISIBLE

        }



        iv_setting_edit.setOnClickListener {
            ed_username.isEnabled = true
            iv_setting_save.visibility = View.VISIBLE
            ed_username.isCursorVisible=true
            ed_username.requestFocus()
            iv_setting_edit.visibility=View.GONE
        }

        iv_save.setOnClickListener {
            var name = ed_username.text.toString()
            if (name.isEmpty())
                ed_username.error = resources.getString(R.string.name_required)
            else if (TextUtils.isEmpty(imageBase64))
                AlertUtil.showSnackBar(setting_layout, resources.getString(R.string.required_image))
            else
                updateSetting(name)

        }

        iv_setting_save.setOnClickListener {
            var name =ed_username.text.toString()
            if (name.isEmpty())
                ed_username.error = resources.getString(R.string.name_required)
            else if (TextUtils.isEmpty(imageBase64))
                AlertUtil.showSnackBar(setting_layout, resources.getString(R.string.required_image))
            else
               updateSetting(name)

        }


        getSettingProfile()
    }

    fun getSettingProfile() {
       // showProgressDialog()
        var apiServices = NetworkClient.create("dsfsdfsdf");
        apiServices.getProfileImage()
                .map({ response ->
                    return@map SettingParser.parse(response)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ settingData ->
                    try {
                        Picasso.with(context()).load(settingData.picture)
                                .placeholder(R.mipmap.app_icon).error(R.mipmap.app_icon)
                                .into(profile_image)
                    } catch (e: Exception) {
                        Picasso.with(context()).load(R.mipmap.app_icon)
                                .into(profile_image)
                    }
                    ed_username.setText(settingData.name)
                    imageBase64 = settingData.picture
                  //  hideProgressDialog()
                }, { error ->
                  //  hideProgressDialog()
                    AlertUtil.showSnackBar(setting_layout, error.message.toString())
                    Log.d("error", error.toString())
                })
    }

    fun updateSetting(name: String) {
     //   showProgressDialog()
        var apiServices = NetworkClient.create("dsfsdfsdf");
        imageBase64 = imageBase64.replace("data:image/png;base64", "")
        apiServices.updateProfileImage(EditSettingRequest(name,imageBase64))
                .map({ response ->
                    return@map SettingParser.parse(response)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ userProfile ->
                 //   hideProgressDialog()
                    iv_setting_edit.visibility = View.VISIBLE
                    iv_edit.visibility = View.VISIBLE
                    ed_username.isEnabled = false
                    iv_setting_save.visibility = View.GONE
                    iv_save.visibility=View.GONE
                    AlertUtil.showSnackBar(setting_layout, "Updated successfully!")
                }, { error ->
                //    hideProgressDialog()
                    AlertUtil.showSnackBar(setting_layout, error.message.toString())
                    Log.d("error", error.toString())
                })
    }

}
