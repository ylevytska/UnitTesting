package com.example.android.unittestingapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.repositories.RepoRepository
import com.example.android.unittestingapp.data.util.Result
import com.example.android.unittestingapp.ui.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repoRepository: RepoRepository) : ViewModel() {

    private val _repos = MutableLiveData<List<Repo>>()
    val repos: LiveData<List<Repo>> = _repos

    private val _error = MutableLiveData<Event<String?>>()
    val error: LiveData<Event<String?>> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        getReposForUser()
    }

    fun getReposForUser(username: String = "default", forcedUpdated: Boolean = false) {
        _loading.postValue(true)
        viewModelScope.launch {
            when (val result = repoRepository.getReposForUser(username, forcedUpdated)) {
                is Result.Success -> _repos.postValue(result.data)
                is Result.Error -> {
                    if (result.exception.message?.trim() == "HTTP 404") _repos.postValue(emptyList())
                    else _error.postValue(Event(result.exception.message))
                }
            }
            _loading.postValue(false)
        }
    }
}