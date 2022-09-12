package com.example.android.unittestingapp.data.source.network.responses

import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.util.EntityMapper

object RepoNetworkMapper : EntityMapper<ReposNetworkEntity, Repo> {
    override fun mapFromEntity(entity: ReposNetworkEntity): Repo {
        return Repo(
            repoId = entity.repoId,
            repoName = entity.repoName,
            repoDesc = entity.repoDesc ?: "No description",
            repoUrl = entity.repoUrl
        )
    }

    override fun mapFromEntityList(entities: List<ReposNetworkEntity>): List<Repo> {
        return entities.map { mapFromEntity(it) }
    }
}