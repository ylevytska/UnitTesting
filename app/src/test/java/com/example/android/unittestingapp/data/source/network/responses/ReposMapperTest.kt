package com.example.android.unittestingapp.data.source.network.responses

import com.example.android.unittestingapp.data.models.Owner
import org.junit.Test
import org.assertj.core.api.Assertions.*


internal class ReposMapperTest {

    @Test
    fun `mapFromEntity() receive RepoNetworkEntity, should returns Repo`() {
        val reposNetworkEntity = ReposNetworkEntity(
            repoId = 1,
            repoName = "Name",
            repoDesc = "Desc",
            repoUrl = "Url",
            repoOwner = Owner("login", 1L)
        )

        val repo = RepoNetworkMapper.mapFromEntity(reposNetworkEntity)

        assertThat(repo.id).isEqualTo(reposNetworkEntity.repoId)
        assertThat(repo.title).isEqualTo(reposNetworkEntity.repoName)
        assertThat(repo.desc).isEqualTo(reposNetworkEntity.repoDesc)
        assertThat(repo.url).isEqualTo(reposNetworkEntity.repoUrl)
        assertThat(repo.owner).isEqualTo(reposNetworkEntity.repoOwner)
    }
}