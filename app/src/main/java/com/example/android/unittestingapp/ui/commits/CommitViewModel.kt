package com.example.android.unittestingapp.ui.commits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.repositories.RepoRepository
import com.example.android.unittestingapp.data.util.Result
import com.example.android.unittestingapp.ui.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommitViewModel @Inject constructor(val repo: RepoRepository) : ViewModel() {

    private val _commits = MutableLiveData<List<Commit>>()
    val commits: LiveData<List<Commit>> = _commits

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>> = _error

    fun getCommitsForRepo(username: String, repoTitle: String) {
        _loading.postValue(true)
        viewModelScope.launch {
            when (val result = repo.getCommitsForRepo(username, repoTitle)) {
                is Result.Success -> _commits.postValue(result.data)
                is Result.Error -> _error.postValue(Event(result.exception.message.toString()))
            }
            _loading.postValue(false)
        }
    }
}