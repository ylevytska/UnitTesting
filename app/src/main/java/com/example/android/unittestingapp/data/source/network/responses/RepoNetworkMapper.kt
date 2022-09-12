package com.example.android.unittestingapp.data.source.network.responses

import com.example.android.unittestingapp.data.models.Repo
import com.example.android.unittestingapp.data.util.EntityMapper

object RepoNetworkMapper : EntityMapper<ReposNetworkEntity, Repo> {
    override fun mapFromEntity(entity: ReposNetworkEntity): Repo {
        return Repo(
            id = entity.repoId,
            title = entity.repoName,
            desc = entity.repoDesc ?: "No description",
            url = entity.repoUrl,
            owner = entity.owner
        )
    }

    override fun mapFromEntityList(entities: List<ReposNetworkEntity>): List<Repo> {
        return entities.map { mapFromEntity(it) }
    }
}