package com.example.android.unittestingapp.data.source.local

import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.util.EntityMapper

object RepoDBMapper : EntityMapper<RepoDatabaseEntity, Repo> {
    override fun mapFromEntity(entity: RepoDatabaseEntity): Repo {
        return Repo(
            id = entity.repoId,
            title = entity.repoName,
            desc = entity.repoDesc,
            url = entity.repoUrl,
            owner = entity.repoOwner
        )
    }

    fun mapToEntity(repo: Repo): RepoDatabaseEntity {
        return RepoDatabaseEntity(
            repoId = repo.id,
            repoName = repo.title,
            repoDesc = repo.desc,
            repoUrl = repo.url,
            repoOwner = repo.owner
        )
    }

    fun mapToEntityList(repos: List<Repo>): List<RepoDatabaseEntity> {
        return repos.map { mapToEntity(it) }
    }

    override fun mapFromEntityList(entities: List<RepoDatabaseEntity>): List<Repo> {
        return entities.map { mapFromEntity(it) }
    }
}