package com.example.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splashscreen.db.UserDao
import kotlinx.coroutines.launch

class LoginViewModel(private val userDao:UserDao) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus:LiveData<Boolean>
        get() = _loginStatus

    fun loginUser() {
        val emailValue = email.value
        val passwordValue = password.value

        if (emailValue.isNullOrEmpty() || passwordValue.isNullOrEmpty()) {
            _loginStatus.value = false
        } else {
            viewModelScope.launch {
                val user = userDao.getUserByEmail(emailValue!!)
                if (user != null && user.value!!.password == passwordValue) {
                    _loginStatus.value = true
                } else {
                    _loginStatus.value = false
                }
            }
        }
    }
}
