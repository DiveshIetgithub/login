package com.example.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splashscreen.db.User
import com.example.splashscreen.db.UserDao
import kotlinx.coroutines.launch

class RegistrationViewModel(private val userDao:UserDao) : ViewModel() {

    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus:LiveData<Boolean>
        get() = _registrationStatus

    fun registerUser() {
        val nameValue = name.value
        val phoneValue = phone.value
        val emailValue = email.value
        val passwordValue = password.value
        val confirmPasswordValue = confirmPassword.value

        if (nameValue.isNullOrEmpty() || phoneValue.isNullOrEmpty() || emailValue.isNullOrEmpty() ||
            passwordValue.isNullOrEmpty() || confirmPasswordValue.isNullOrEmpty()
        ) {
            _registrationStatus.value = false
        } else if (passwordValue != confirmPasswordValue) {
            _registrationStatus.value = false
        } else {
            val user = User(name = nameValue!!, phone = phoneValue!!, email = emailValue!!, password = passwordValue!!)
            viewModelScope.launch {
                userDao.insertUser(user)
                _registrationStatus.value = true
            }
        }
    }
}
