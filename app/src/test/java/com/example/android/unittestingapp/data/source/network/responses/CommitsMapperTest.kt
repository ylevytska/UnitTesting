package com.example.android.unittestingapp.data.source.network.responses

import com.example.android.unittestingapp.data.models.Author
import com.example.android.unittestingapp.data.models.Commit
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

class CommitsMapperTest {
    @Test
    fun `mapFromEntity should return valid commit`() {
        // Given
        val primaryCommit = Commit(Author("date", "email", "name"), "message")
        val commitNetworkEntity = CommitNetworkEntity(commit = primaryCommit, sha = "sha", url = "url")

        // When
        val commit = CommitNetworkMapper.mapFromEntity(commitNetworkEntity)

        // Then
        assertThat(commit, IsEqual(primaryCommit))
    }
}