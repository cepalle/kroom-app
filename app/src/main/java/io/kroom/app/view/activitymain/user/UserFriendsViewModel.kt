package io.kroom.app.view.activitymain.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.repo.UserRepo
import io.kroom.app.repo.webservice.GraphClient
import io.kroom.app.util.SharedPreferences
import io.reactivex.disposables.Disposable

class UserFriendsViewModel(app: Application) : AndroidViewModel(app) {
    private val client = GraphClient {
        SharedPreferences.getToken(getApplication())
    }.client

    private val userRepo = UserRepo(client)
    private val userId = SharedPreferences.getId(getApplication())

    private var disposeUserById: Disposable? = null
    private var disposeAuto: Disposable? = null
    private var disposeAddFriend: Disposable? = null

    // ---

    private val autoCompletion: MutableLiveData<List<String>> = MutableLiveData()
    private val friendsList: MutableLiveData<List<String>> = MutableLiveData()
    private val error: MutableLiveData<String> = MutableLiveData()

    init {
        disposeUserById = userId?.let {
            userRepo.user(it).subscribe { r ->
                r.onFailure {
                    error.value = it.message
                    friendsList.value = null
                }
                r.onSuccess {
                    friendsList.value = it.data()?.UserGetById()?.user()?.friends()?.map { it.userName() }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposeUserById?.dispose()
        disposeAuto?.dispose()
        disposeAddFriend?.dispose()
    }

    fun updateAutoComplet(prefix: String) {
        disposeAuto?.dispose()
        disposeAuto = userRepo.userNameAutocompletion(prefix).subscribe { r ->
            r.onFailure {
                error.value = it.message
                autoCompletion.value = null
            }
            r.onSuccess {
                autoCompletion.value = it.data()?.UserNameAutocompletion()?.map { it.userName() }
            }
        }
    }

    fun addFriend(friendId: Int) {
        disposeAddFriend?.dispose()
        disposeAddFriend = userId?.let {
            userRepo.addFriend(it, friendId).subscribe { r ->
                r.onFailure {
                    error.value = it.message
                    friendsList.value = null
                }
                r.onSuccess {
                    friendsList.value = it.data()?.UserAddFriend()?.user()?.friends()?.map { it.userName() }
                }
            }
        }
    }

    fun getAutoCompletion(): LiveData<List<String>> {
        return autoCompletion
    }

    fun getFriendsList(): LiveData<List<String>> {
        return friendsList
    }

    fun getError(): LiveData<String> {
        return error
    }

}
