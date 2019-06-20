package io.kroom.app.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.client.KroomClient
import io.kroom.app.graphql.UserAddFriendMutation
import io.kroom.app.session.Session
import io.kroom.app.views.util.Dialogs
import io.kroom.app.views.util.SuccessOrFail
import kotlinx.android.synthetic.main.fragment_user_friends.*


class UserFriendsFragment : Fragment(), SuccessOrFail<UserAddFriendMutation.UserAddFriend, ApolloException> {


    val users = KroomClient.Users
    lateinit var adapter: ArrayAdapter<String>
    var usersFetchedCache: HashMap<String, Int> = HashMap()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Friends management"
        return inflater.inflate(R.layout.fragment_user_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArrayAdapter(activity!!, R.layout.select_dialog_item_material, java.util.ArrayList())
        userFriendsAdd.setAdapter(adapter)

        userFriendsAdd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                println("CHANGED ${userFriendsAdd.text}   ${Session.getToken()!!}")

                users.userNameAutocompletion(userFriendsAdd.text.toString()) { res, err ->
                    res?.let {
                        adapter.clear()
                        adapter.addAll(res.map { x ->
                            usersFetchedCache[x.userName()] = x.id()!!
                            x.userName()
                        })

                        adapter.notifyDataSetChanged()
                    }
                    err?.let { println("ERROR on fetching user by username ${err.message}") }
                }
            }
        })

        userFriendsAddAction.setOnClickListener { onAddFriend() }

    }

    private fun onAddFriend() {
        userFriendsAdd.error = ""

        val friendId = usersFetchedCache[userFriendsAdd.text.toString()]
        if (friendId != null) {
            users.addFriend(friendId) { res, exc ->
                res?.let(this::onSuccess)
                exc?.let(this::onFail)
            }
        }

    }

    override fun onSuccess(s: UserAddFriendMutation.UserAddFriend) {
        val errors = s.errors()
        if (errors.size > 0) {
            userFriendsAdd.error = errors[0].messages()[0]
            return
        } else {
            Dialogs.successDialog(Main.app, "You added ${s.user()!!.userName()} to your friend list!")
        }
    }

    override fun onFail(f: ApolloException) {
        Log.println(Log.INFO, "fail-add-friend", "fail: $f")
        Dialogs.errorDialog(Main.app, "You encounter an error ${f.message}")
            .show()
    }

}
