package com.example.android.unittestingapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.unittestingapp.MainCoroutineRule
import com.example.android.unittestingapp.data.models.Owner
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.repositories.DefaultRepoRepository
import com.example.android.unittestingapp.data.repositories.RepoRepository
import com.example.android.unittestingapp.data.source.FakeDataSource
import com.example.android.unittestingapp.data.util.Result
import com.example.android.unittestingapp.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var remoteDataSource: FakeDataSource
    private lateinit var localDataSource: FakeDataSource
    private lateinit var repoRepository: DefaultRepoRepository
    private val username = "default"

    private val repo1 = Repo(1, "name1", "desc1", "url1", Owner("login 1", 1L))
    private val repo2 = Repo(2, "name2", "desc2", "url2", Owner("login 2", 2L))
    private val repo3 = Repo(3, "name3", "desc3", "url3", Owner("login 3", 3L))

    private val remoteRepos = mutableListOf(repo1, repo2)
    private val localRepos = mutableListOf(repo3)


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setup() {
        remoteDataSource = FakeDataSource(remoteRepos)
        localDataSource = FakeDataSource(localRepos)
        repoRepository = DefaultRepoRepository(remoteDataSource, localDataSource)
        homeViewModel = HomeViewModel(repoRepository)
    }

    @Test
    fun `getRepoForUser forceUpdate=true return updated local repos`() {
        // Given
        homeViewModel.getReposForUser(forcedUpdated = true)

        // When
        val value = homeViewModel.repos.getOrAwaitValue()

        // Then
        assertThat(value, IsEqual(remoteRepos))
    }

    @Test
    fun `getRepoForUser forceUpdate=false return local repos`() {
        // Given
        homeViewModel.getReposForUser(forcedUpdated = false)

        // When
        val value = homeViewModel.repos.getOrAwaitValue()

        // Then
        assertThat(value, IsEqual(localRepos))
    }

    @Test
    fun `check loading states`() {
        // Pause coroutine execution
        mainCoroutineRule.pauseDispatcher()

        homeViewModel.getReposForUser(forcedUpdated = false)

        // Check that loading is true
        assertThat(homeViewModel.loading.getOrAwaitValue(), `is`(true))

        // Resume coroutine execution
        mainCoroutineRule.resumeDispatcher()

        // Check that loading is false
        assertThat(homeViewModel.loading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun `getRepoForUser return error`() = runBlocking {
        // Given
        val mockRepository = mockk<RepoRepository>()
        coEvery { mockRepository.getReposForUser(username, false) } coAnswers {
            Result.Error(Exception("Error occurred"))
        }
        homeViewModel = HomeViewModel(mockRepository)

        // When
        homeViewModel.getReposForUser(username, false)

        // Then
        assertThat(homeViewModel.error.getOrAwaitValue().getContentIfNotHandled(),
            `is`("Error occurred"))
    }
}