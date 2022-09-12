package com.example.android.unittestingapp.data.source.network.responses

import com.example.android.unittestingapp.data.models.Commit
import com.example.android.unittestingapp.data.util.EntityMapper

object CommitNetworkMapper : EntityMapper<CommitNetworkEntity, Commit> {

    override fun mapFromEntity(entity: CommitNetworkEntity): Commit {
        return Commit(
            author = entity.commit.author.copy(),
            message = entity.commit.message
        )
    }

    override fun mapFromEntityList(entities: List<CommitNetworkEntity>): List<Commit> {
        return entities.map { mapFromEntity(it) }
    }
}