package com.example.android.unittestingapp.data.source.local

import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.util.EntityMapper

object RepoDBMapper : EntityMapper<RepoDatabaseEntity, Repo> {
    override fun mapFromEntity(entity: RepoDatabaseEntity): Repo {
        return Repo(
            repoId = entity.repoId,
            repoName = entity.repoName,
            repoDesc = entity.repoDesc,
            repoUrl = entity.repoUrl
        )
    }

    fun mapToEntity(repo: Repo): RepoDatabaseEntity {
        return RepoDatabaseEntity(
            repoId = repo.repoId,
            repoName = repo.repoName,
            repoDesc = repo.repoDesc,
            repoUrl = repo.repoUrl
        )
    }

    fun mapToEntityList(repos: List<Repo>): List<RepoDatabaseEntity> {
        return repos.map { mapToEntity(it) }
    }

   override fun mapFromEntityList(entities: List<RepoDatabaseEntity>): List<Repo> {
        return entities.map { mapFromEntity(it) }
    }
}