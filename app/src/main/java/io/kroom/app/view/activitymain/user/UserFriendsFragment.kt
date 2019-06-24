package io.kroom.app.view.activitymain.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.kroom.app.R
import kotlinx.android.synthetic.main.fragment_user_friends.*


class UserFriendsFragment : Fragment() {

    lateinit var adapterAutocompletion: ArrayAdapter<String>
    lateinit var adapterFriendsList: ArrayAdapter<String>
    var model: UserFriendsViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Friends management"
        return inflater.inflate(R.layout.fragment_user_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterAutocompletion = ArrayAdapter(activity!!, R.layout.select_dialog_item_material, java.util.ArrayList())
        adapterFriendsList = ArrayAdapter(activity!!, R.layout.select_dialog_item_material, java.util.ArrayList())

        userFriendsAddInput.setAdapter(adapterAutocompletion)
        userFriendsList.adapter = adapterFriendsList

        model = ViewModelProviders.of(this).get(UserFriendsViewModel::class.java)
        val autoCompletion = model?.getAutoCompletion()
        val friendsList = model?.getFriendsList()
        val errorMessage = model?.getErrorMessage()

        updateAutoCompletion(autoCompletion?.value)
        autoCompletion?.observe(this, Observer {
            updateAutoCompletion(it)
        })

        updateListFriends(friendsList?.value)
        friendsList?.observe(this, Observer {
            updateListFriends(it)
        })

        errorMessage?.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })

        // ---

        userFriendsAddInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                model?.updateAutoComplet(userFriendsAddInput.text.toString())
            }
        })

        userFriendsAddSubmit.setOnClickListener {
            userFriendsAddInput.text.toString()
            val friendID = model?.getAutoCompletion()?.value?.find {
                it.first == userFriendsAddInput.text.toString()
            }?.second
            friendID?.let {
                model?.addFriend(it)
            }
        }

        userFriendsList.setOnItemClickListener { _, _, position, _ ->
            adapterFriendsList.getItem(position)?.let { username ->
                model?.getAutoCompletion()?.value?.find {
                    it.first == username
                }?.second?.let {
                    model?.delFriend(it)
                }
            }
        }
    }

    private fun updateAutoCompletion(ls: List<Pair<String, Int>>?) {
        ls ?: return
        adapterAutocompletion.clear()
        adapterAutocompletion.addAll(ls.map { it.first })
        adapterAutocompletion.notifyDataSetChanged()
    }

    private fun updateListFriends(ls: List<Pair<String, Int>>?) {
        ls ?: return
        adapterFriendsList.clear()
        adapterFriendsList.addAll(ls.map { it.first })
        adapterFriendsList.notifyDataSetChanged()
    }

}
