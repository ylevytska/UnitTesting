package com.example.android.unittestingapp.data.source.local

import com.example.android.unittestingapp.data.models.Owner
import com.example.android.unittestingapp.data.models.Repo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class RepoDBMapperTest {

    @Test
    fun `mapFromEntity should return valid repo`() {
        // Given
        val repoDatabaseEntity = RepoDatabaseEntity(1L, "title", "desc", "url", Owner("login", 1L))

        // When
        val repo = RepoDBMapper.mapFromEntity(repoDatabaseEntity)

        // Then
        assertThat(repo.id, `is`(1L))
        assertThat(repo.title, `is`("title"))
        assertThat(repo.desc, `is`("desc"))
        assertThat(repo.url, `is`("url"))
        assertThat(repo.owner.id, `is`(1L))
        assertThat(repo.owner.login, `is`("login"))
    }


    @Test
    fun `mapToEntity should return valid repoDatabaseEntity`() {
        // Given
        val repo = Repo(1L, "title", "desc", "url", Owner("login", 1L))

        // When
        val repoDatabaseEntity = RepoDBMapper.mapToEntity(repo)

        // Then
        assertThat(repoDatabaseEntity.repoId, `is`(repo.id))
        assertThat(repoDatabaseEntity.repoName, `is`(repo.title))
        assertThat(repoDatabaseEntity.repoDesc, `is`(repo.desc))
        assertThat(repoDatabaseEntity.repoUrl, `is`(repo.url))
        assertThat(repoDatabaseEntity.repoOwner.id, `is`(repo.owner.id))
        assertThat(repoDatabaseEntity.repoOwner.login, `is`(repo.owner.login))
    }
}