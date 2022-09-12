package com.example.android.unittestingapp.ui.commits

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.unittestingapp.MainCoroutineRule
import com.example.android.unittestingapp.data.models.Author
import com.example.android.unittestingapp.data.models.Commit
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
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CommitViewModelTest {

    private val username = "Owner"
    private val repoTitle = "Repo"

    private val commit1 = Commit(Author("date1", "email1", "name1"), "message1")
    private val commit2 = Commit(Author("date2", "email2", "name2"), "message2")
    private val commit3 = Commit(Author("date3", "email3", "name3"), "message3")
    private val commits = mutableListOf(commit1, commit2, commit3)

    private lateinit var commitViewModel: CommitViewModel
    private lateinit var repository: RepoRepository
    private lateinit var remoteDataSource: FakeDataSource
    private lateinit var localDataSource: FakeDataSource


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setup() {
        remoteDataSource = FakeDataSource(commits = commits)
        localDataSource = FakeDataSource(commits = commits)
        repository = DefaultRepoRepository(remoteDataSource, localDataSource)
        commitViewModel = CommitViewModel(repository)
    }

    @Test
    fun `getCommitsForRepo return commits`() {
        // Given
        commitViewModel.getCommitsForRepo(username, repoTitle)

        // When
        val result = commitViewModel.commits.getOrAwaitValue()

        // Then
        assertThat(result, IsEqual(commits))
    }

    @Test
    fun `check loading states`() {
        // Pause coroutine execution
        mainCoroutineRule.pauseDispatcher()

        commitViewModel.getCommitsForRepo(username, repoTitle)

        // Check that loading is true
        assertThat(commitViewModel.loading.getOrAwaitValue(), CoreMatchers.`is`(true))

        // Resume coroutine execution
        mainCoroutineRule.resumeDispatcher()

        // Check that loading is false
        assertThat(commitViewModel.loading.getOrAwaitValue(), CoreMatchers.`is`(false))
    }

    @Test
    fun `getCommitsForRepo return error`() = runBlocking {
        // Given
        val mockRepository = mockk<RepoRepository>()
        coEvery { mockRepository.getCommitsForRepo(username, repoTitle) } coAnswers {
            Result.Error(Exception("Error occurred"))
        }
        commitViewModel = CommitViewModel(mockRepository)

        // When
        commitViewModel.getCommitsForRepo(username, repoTitle)

        // Then
        assertThat(commitViewModel.error.getOrAwaitValue().getContentIfNotHandled(),
            CoreMatchers.`is`("Error occurred"))
    }
}