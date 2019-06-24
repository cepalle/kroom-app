package io.kroom.app.view.activitymain.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Response
import io.kroom.app.graphql.UserAddFriendMutation
import io.kroom.app.graphql.UserNameAutocompletionQuery
import io.kroom.app.repo.UserRepo
import io.kroom.app.repo.webservice.GraphClient
import io.kroom.app.util.SharedPreferences

class UserFriendsViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val userRepo = UserRepo(client)
    private val userId = SharedPreferences.getId(getApplication())

    // ---

    val autoCompletion: MutableLiveData<Array<String>> = MutableLiveData()
    val friendsList: MutableLiveData<Array<String>> = MutableLiveData()

    val userFriend = userId?.let { userRepo.user(it) }

    fun updateAutoComplet(prefix: String): LiveData<Result<Response<UserNameAutocompletionQuery.Data>>> {
        userRepo.userNameAutocompletion(prefix).observe(viewLifecycleOwner,)

        return
    }

    fun addFriend(friendId: Int): LiveData<Result<Response<UserAddFriendMutation.Data>>>? {
        return userId?.let {
            userRepo.addFriend(it, friendId)
        }
    }

    // first fetch
    // fetch on update auto
    // mutation add + update
}