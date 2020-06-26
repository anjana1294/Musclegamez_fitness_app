package com.musclegamez.fitness_app.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.base.BaseFragment
import com.musclegamez.fitness_app.network.NetworkClient
import com.musclegamez.fitness_app.ui.settings.model.chatSupport.ChatRequest
import com.musclegamez.fitness_app.util.AlertUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_chat_support.*

class ChatSupportFragment : BaseFragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_chat_support, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_query_submit.setOnClickListener {
            var title = tv_query_title.text.toString()
            var email = tv_query_email.text.toString()
            var description = tv_quer_desc.text.toString()

            if (title.isEmpty())
                tv_query_title.error = resources.getString(R.string.desc_required)
            else if (email.isEmpty())
                tv_query_email.error = resources.getString(R.string.phone_required)
            else if (description.isEmpty())
                tv_quer_desc.error = resources.getString(R.string.phone_required)
            else
                onChatSupport(title, email, description)
        }

    }

    fun onChatSupport(title: String, email: String, desc: String) {
        showProgressBar()
        var apiServices = NetworkClient.create("dsfsdfsdf");
        apiServices.onChatSupport(ChatRequest(title, email, desc))
                .map({ response ->
                    return@map ChatSupportParser.parse(response)

                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    hideProgressBar()
                    AlertUtil.showSnackBar(chat_layout, response)

                    Log.d("Data", response)
                }, { error ->
                    hideProgressBar()
                    AlertUtil.showSnackBar(chat_layout, error.message.toString())
                    Log.d("error", error.toString())

                })
    }


    override fun showProgressBar() {
        super.showProgressBar()
        btn_query_submit.visibility = View.GONE
        chatProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        super.hideProgressBar()
        btn_query_submit.visibility = View.VISIBLE
        chatProgressBar.visibility = View.GONE
    }


}