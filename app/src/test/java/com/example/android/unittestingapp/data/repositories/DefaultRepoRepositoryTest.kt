package com.example.android.unittestingapp.data.repositories

import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.source.RepoDataSource
import com.example.android.unittestingapp.data.util.Result
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


class DefaultRepoRepositoryTest {

    private lateinit var remoteDataSource: RepoDataSource
    private lateinit var localDataSource: RepoDataSource
    private lateinit var defaultRepoRepository: DefaultRepoRepository

    private val repo1 = Repo(1, "name1", "desc1", "url1")
    private val repo2 = Repo(2, "name2", "desc2", "url2")
    private val repo3 = Repo(3, "name3", "desc3", "url3")

    private val username = "default"
    private val remoteRepos = mutableListOf(repo1, repo2)
    private val localRepos = mutableListOf(repo3)

    @Before
    fun setup() {
        remoteDataSource = mock(RepoDataSource::class.java)
        localDataSource = mock(RepoDataSource::class.java)
        defaultRepoRepository = DefaultRepoRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `getRepos requests remote repos, should return updated local repos`() = runBlocking {
        Mockito.`when`(remoteDataSource.getReposForUser(username))
            .thenReturn(Result.Success(remoteRepos))
        Mockito.`when`(localDataSource.getReposForUser()).thenReturn(Result.Success(remoteRepos))
        val result = defaultRepoRepository.getReposForUser(username, true) as Result.Success
        assertThat(result.data, IsEqual(remoteRepos))
    }

    @Test
    fun `getRepos requests local repos, should return local repos`() = runBlocking {
        Mockito.`when`(localDataSource.getReposForUser()).thenReturn(Result.Success(localRepos))
        val result = defaultRepoRepository.getReposForUser(username, false) as Result.Success
        assertThat(result.data, IsEqual(localRepos))
    }
}