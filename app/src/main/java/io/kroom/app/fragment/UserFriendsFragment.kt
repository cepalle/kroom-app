package io.kroom.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.Main
import io.kroom.app.R
import io.kroom.app.client.KroomApolloClient
import io.kroom.app.graphql.UserAddFriendMutation
import io.kroom.app.session.Session
import io.kroom.app.util.Dialogs
import io.kroom.app.util.SuccessOrFail
import kotlinx.android.synthetic.main.fragment_user_friends.*


class UserFriendsFragment : Fragment(), SuccessOrFail<UserAddFriendMutation.UserAddFriend, ApolloException> {

    val users = KroomApolloClient.Users

    lateinit var adapterAutocompletion: ArrayAdapter<String>
    lateinit var adapterFriendsList: ArrayAdapter<String>

    var usersFetchedCache: HashMap<String, Int> = HashMap()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "Friends management"
        return inflater.inflate(R.layout.fragment_user_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterAutocompletion = ArrayAdapter(activity!!, R.layout.select_dialog_item_material, java.util.ArrayList())
        adapterFriendsList = ArrayAdapter(activity!!, R.layout.select_dialog_item_material, java.util.ArrayList())

        userFriendsAdd.setAdapter(adapterAutocompletion)
        userFriendsList.adapter = adapterFriendsList

        users.user(Session.getId()!!) { s, _ ->
            s?.let {
                if (it.errors().isEmpty()) {
                    val userGetById = it.data()!!.UserGetById()
                    if (userGetById.errors().isEmpty()) {
                        updateFriendsList(it.data()!!.UserGetById().user()!!.friends()!!.map { friend ->
                            Pair(
                                friend.userName(),
                                friend.id()!!
                            )
                        })
                    }
                }
            }
        }

        userFriendsAdd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (userFriendsAdd.error != null)
                    userFriendsAdd.error = null

                users.userNameAutocompletion(userFriendsAdd.text.toString()) { res, err ->
                    res?.let {
                        if (checkErrorsAutocompletion(res.errors()))
                            return@let

                        adapterAutocompletion.clear()
                        adapterAutocompletion.addAll(res.data()!!.UserNameAutocompletion().map { x ->
                            usersFetchedCache[x.userName()] = x.id()!!
                            x.userName()
                        })

                        adapterAutocompletion.notifyDataSetChanged()
                    }
                    err?.let { println("ERROR on fetching user by username ${err.message}") }
                }
            }
        })

        userFriendsAddAction.setOnClickListener { onAddFriend() }

        userFriendsList.setOnItemClickListener { parent, view, position, id ->
            adapterFriendsList.getItem(position)?.let { username ->
                users.deleteFriend(usersFetchedCache[username]!!) { s, e ->
                    s?.let {
                        if (s.errors().isNotEmpty()) {
                            Dialogs.errorDialog(Main.app, "You encounter an error ${s.errors()[0].messages()[0]}")
                                .show()
                            return@let
                        }
                        val user = s.user()!!
                        Dialogs.successDialog(Main.app, "Deleted user $username")
                            .show()
                        updateFriendsList(user.friends()!!.map { friend ->
                            Pair(
                                friend.userName(),
                                friend.id()!!
                            )
                        })
                    }

                    e?.let {
                        Dialogs.errorDialog(Main.app, "You encounter an error ${it.message}")
                            .show()
                    }
                }
            }
        }
    }

    private fun updateFriendsList(friends: List<Pair<String, Int>>) {
        friends.forEach { usersFetchedCache[it.first] = it.second }
        adapterFriendsList.clear()
        adapterFriendsList.addAll(friends.map { it.first })
        adapterFriendsList.notifyDataSetChanged()
    }

    private fun checkErrorsAutocompletion(errors: List<Error>): Boolean {
        if (errors.isNotEmpty()) {
            userFriendsAdd.error = errors[0].message()
            return true
        }
        return false
    }

    private fun onAddFriend() {
        if (userFriendsAdd.error != null)
            userFriendsAdd.error = null

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
            updateFriendsList(s.user()!!.friends()!!.map { friend ->
                Pair(
                    friend.userName(),
                    friend.id()!!
                )
            })
            Dialogs.successDialog(Main.app, "You added ${s.user()!!.userName()} to your friend list!")
        }
    }

    override fun onFail(f: ApolloException) {
        Log.println(Log.INFO, "fail-add-friend", "fail: $f")
        Dialogs.errorDialog(Main.app, "You encounter an error ${f.message}")
            .show()
    }

}
