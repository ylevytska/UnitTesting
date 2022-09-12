package com.example.android.unittestingapp.ui.commits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.repositories.RepoRepository
import com.example.android.unittestingapp.data.util.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommitViewModel @Inject constructor(
    private val repo: RepoRepository,
) : ViewModel() {

    private val _commits = MutableLiveData<List<Commit>>()
    val commit: LiveData<List<Commit>> = _commits

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getCommitsForRepo(username: String, repoTitle: String) {
        _loading.postValue(true)
        viewModelScope.launch {
            when (val result = repo.getCommitsForRepo(username, repoTitle)) {
                is Result.Success -> _commits.postValue(result.data)
                is Result.Error -> _error.postValue(result.exception.message)
            }
            _loading.postValue(false)
        }
    }

}