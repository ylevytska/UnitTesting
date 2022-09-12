package com.example.android.unittestingapp.data.source.network.responses

import org.junit.Test
import org.assertj.core.api.Assertions.*


internal class ReposMapperTest {

    @Test
    fun `mapFromEntity receive RepoNetworkEntity returns Repo`() {
        val reposNetworkEntity = ReposNetworkEntity(
            repoId = 1,
            repoName = "Name",
            repoDesc = "Desc",
            repoUrl = "Url"
        )

        val repo = RepoNetworkMapper.mapFromEntity(reposNetworkEntity)

        assertThat(repo.repoId).isEqualTo(reposNetworkEntity.repoId)
        assertThat(repo.repoName).isEqualTo(reposNetworkEntity.repoName)
        assertThat(repo.repoDesc).isEqualTo(reposNetworkEntity.repoDesc)
        assertThat(repo.repoUrl).isEqualTo( reposNetworkEntity.repoUrl)
    }
}