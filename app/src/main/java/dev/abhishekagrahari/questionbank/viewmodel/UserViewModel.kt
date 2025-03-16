package dev.abhishekagrahari.questionbank.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.abhishekagrahari.questionbank.model.User
import dev.abhishekagrahari.questionbank.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    /** Load user data */
    fun loadUser(userId: String) {
        viewModelScope.launch {
            _user.value = repository.getUser(userId)
        }
    }

    /** Add or update user info */
    fun addUser(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.addUser(user)
            onResult(success)
        }
    }
}
