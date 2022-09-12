package com.example.android.unittestingapp.data.source.local

import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.source.RepoDataSource
import com.example.android.unittestingapp.data.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoLocalDataSource @Inject constructor(
    private val repoDao: RepoDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    RepoDataSource {
    override suspend fun getReposForUser(): Result<List<Repo>> = withContext(dispatcher) {
        return@withContext try {
            val reposDatabaseEntity = repoDao.getRepos()
            val repos = RepoDBMapper.mapFromEntityList(reposDatabaseEntity)
            Result.Success(repos)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getReposForUser(username: String): Result<List<Repo>> =
        withContext(dispatcher) {
            return@withContext try {
                val reposDatabaseEntity = repoDao.getRepos()
                val repos = RepoDBMapper.mapFromEntityList(reposDatabaseEntity)
                Result.Success(repos)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun insertAllRepos(repos: List<Repo>) = withContext(dispatcher) {
        val reposDatabaseEntity = RepoDBMapper.mapToEntityList(repos)
        repoDao.insertAllRepos(reposDatabaseEntity)
    }

    override suspend fun deleteAllRepos() = withContext(dispatcher) {
        repoDao.deleteAllRepos()
    }

    override suspend fun getCommits(username: String, repoTitle: String): Result<List<Commit>> {
        return Result.Success(emptyList())
    }
}


//class RepoLocalDataSource @Inject constructor(
//    private val repoDao: RepoDao,
//) :
//    RepoDataSource {
//    override suspend fun getReposForUser(): Result<List<Repo>> {
//        return try {
//            val reposDatabaseEntity = repoDao.getRepos()
//            val repos = RepoDBMapper.mapFromEntityList(reposDatabaseEntity)
//            Result.Success(repos)
//        } catch (e: Exception) {
//            Result.Error(e)
//        }
//    }
//
//    override suspend fun getReposForUser(username: String): Result<List<Repo>> {
//        return try {
//            val reposDatabaseEntity = repoDao.getRepos()
//            val repos = RepoDBMapper.mapFromEntityList(reposDatabaseEntity)
//            Result.Success(repos)
//        } catch (e: Exception) {
//            Result.Error(e)
//        }
//    }
//
//    override suspend fun insertAllRepos(repos: List<Repo>) {
//        val reposDatabaseEntity = RepoDBMapper.mapToEntityList(repos)
//        repoDao.insertAllRepos(reposDatabaseEntity)
//    }
//
//    override suspend fun deleteAllRepos() {
//        repoDao.deleteAllRepos()
//    }
//
//    override suspend fun getCommits(username: String, repoTitle: String): Result<List<Commit>> {
//        return Result.Success(emptyList())
//    }
//}