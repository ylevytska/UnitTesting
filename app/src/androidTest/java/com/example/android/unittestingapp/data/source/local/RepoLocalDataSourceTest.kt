package com.example.android.unittestingapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.unittestingapp.data.models.Owner
import com.example.android.unittestingapp.data.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class RepoLocalDataSourceTest {

    private lateinit var database: RepoDatabase
    private lateinit var repoLocalDataSource: RepoLocalDataSource

    private val repoDatabaseEntity1 = RepoDatabaseEntity(
        repoId = 1L,
        repoName = "Name 1",
        repoDesc = "Desc 1",
        repoUrl = "Url 1",
        repoOwner = Owner("Login 1", 1L))
    private val repoDatabaseEntity2 = RepoDatabaseEntity(
        repoId = 2L,
        repoName = "Name 2",
        repoDesc = "Desc 2",
        repoUrl = "Url 2",
        repoOwner = Owner("Login 2", 2L))
    private val repoDatabaseEntity3 = RepoDatabaseEntity(
        repoId = 3L,
        repoName = "Name 3",
        repoDesc = "Desc 3",
        repoUrl = "Url 3",
        repoOwner = Owner("Login 3", 3L))

    private val repoEntityList =
        listOf(repoDatabaseEntity1, repoDatabaseEntity2, repoDatabaseEntity3)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepoDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        repoLocalDataSource = RepoLocalDataSource(database.getRepoDao())
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getReposForUser_InsertRepos_GetRepos() = runBlocking {
        // Given
        val repos = RepoDBMapper.mapFromEntityList(repoEntityList)
        database.getRepoDao().insertAllRepos(repoEntityList)

        // When
        val result = repoLocalDataSource.getReposForUser() as Result.Success

        // Then
        assertThat(result.data.size, `is`(repos.size))
        assertThat(result.data, IsEqual(repos))
    }

    @Test
    fun deleteAllRepos_ReturnEmpty() = runBlocking {
        // Given
        database.getRepoDao().insertAllRepos(repoEntityList)

        // When
        database.getRepoDao().deleteAllRepos()
        val repos = database.getRepoDao().getRepos()

        // Then
        assertThat(repos.size, `is`(0))
    }

    @Test
    fun getReposForUser_WhenDbIsEmpty_ReturnEmpty() = runBlocking {
        // When
        val result = repoLocalDataSource.getReposForUser() as Result.Success

        // Then
        assertThat(result.data.size, `is`(0))
    }
}