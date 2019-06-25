package io.kroom.app.view.activitymain.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.kroom.app.repo.UserRepo
import io.kroom.app.webservice.GraphClient
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
    private var disposeDelFriend: Disposable? = null

    // ---

    private val autoCompletion: MutableLiveData<List<Pair<String, Int>>> = MutableLiveData()
    private val friendsList: MutableLiveData<List<Pair<String, Int>>> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        Log.i("TEST", "init")

        // TODO if not connected
        disposeUserById = userId?.let {
            userRepo.user(it).subscribe { r ->
                r.onFailure {
                    errorMessage.postValue(it.message)
                    friendsList.postValue(null)
                }
                r.onSuccess {
                    friendsList.postValue(
                        it.data()?.UserGetById()?.user()?.friends()?.map {
                            val id = it.id()
                            if (id != null) Pair(it.userName(), id)
                            else null
                        }?.filterNotNull()
                    )
                    it.errors().forEach {
                        errorMessage.postValue(it.message())
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposeUserById?.dispose()
        disposeAuto?.dispose()
        disposeAddFriend?.dispose()
        disposeDelFriend?.dispose()
    }

    fun updateAutoComplet(prefix: String) {
        Log.i("TEST", "updateAutoComplet")

        disposeAuto?.dispose()
        disposeAuto = userRepo.userNameAutocompletion(prefix).subscribe { r ->
            Log.i("TEST", "subscribe")

            r.onFailure {
                Log.i("TEST", "onFailure")

                errorMessage.postValue(it.message)
                autoCompletion.postValue(null)
            }
            r.onSuccess {
                autoCompletion.postValue(
                    it.data()?.UserNameAutocompletion()?.map {
                        val id = it.id()
                        if (id != null) Pair(it.userName(), id)
                        else null
                    }?.filterNotNull()
                )
                it.errors().forEach {
                    errorMessage.postValue(it.message())
                }
            }
        }
    }

    fun addFriend(friendId: Int) {
        disposeAddFriend?.dispose()
        disposeAddFriend = userId?.let {
            userRepo.addFriend(it, friendId).subscribe { r ->
                r.onFailure {
                    errorMessage.postValue(it.message)
                    friendsList.postValue(null)
                }
                r.onSuccess {
                    friendsList.postValue(
                        it.data()?.UserAddFriend()?.user()?.friends()?.map {
                            val id = it.id()
                            if (id != null) Pair(it.userName(), id)
                            else null
                        }?.filterNotNull()
                    )
                    it.errors().forEach {
                        errorMessage.postValue(it.message())
                    }
                }
            }
        }
    }

    fun delFriend(friendId: Int) {
        disposeDelFriend?.dispose()
        disposeDelFriend = userId?.let {
            userRepo.deleteFriend(it, friendId).subscribe { r ->
                r.onFailure {
                    errorMessage.postValue(it.message)
                    friendsList.postValue(null)
                }
                r.onSuccess {
                    friendsList.postValue(
                        it.data()?.UserDelFriend()?.user()?.friends()?.map {
                            val id = it.id()
                            if (id != null) Pair(it.userName(), id)
                            else null
                        }?.filterNotNull()
                    )
                    it.errors().forEach {
                        errorMessage.postValue(it.message())
                    }
                }
            }
        }
    }

    fun getAutoCompletion(): LiveData<List<Pair<String, Int>>> {
        return autoCompletion
    }

    fun getFriendsList(): LiveData<List<Pair<String, Int>>> {
        return friendsList
    }

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

}
