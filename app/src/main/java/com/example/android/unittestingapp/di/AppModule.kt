package com.example.android.unittestingapp.di

import android.content.Context
import androidx.room.Room
import com.example.android.unittestingapp.BuildConfig
import com.example.android.unittestingapp.common.Constants
import com.example.android.unittestingapp.data.repositories.DefaultRepoRepository
import com.example.android.unittestingapp.data.repositories.RepoRepository
import com.example.android.unittestingapp.data.source.RepoDataSource
import com.example.android.unittestingapp.data.source.local.RepoDao
import com.example.android.unittestingapp.data.source.local.RepoDatabase
import com.example.android.unittestingapp.data.source.local.RepoLocalDataSource
import com.example.android.unittestingapp.data.source.network.GithubApi
import com.example.android.unittestingapp.data.source.network.RepoRemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader(
                "Authorization",
                BuildConfig.API_KEY
            )
            chain.proceed(requestBuilder.build())
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

    @Provides
    @Singleton
    @Named("RemoteDataSource")
    fun provideRemoteDataSource(api: GithubApi): RepoDataSource =
        RepoRemoteDataSource(api)

    @Provides
    @Singleton
    @Named("LocalDataSource")
    fun provideLocalDataSource(repoDao: RepoDao): RepoDataSource =
        RepoLocalDataSource(repoDao)

    @Provides
    @Singleton
    fun provideRemoteDataSourceForDefaultRepo(api: GithubApi): RepoRemoteDataSource =
        RepoRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideLocalDataSourceForDefaultRepo(repoDao: RepoDao): RepoLocalDataSource =
        RepoLocalDataSource(repoDao)


    @Provides
    @Singleton
    fun provideDefaultRepository(
        repoRemoteDataSource: RepoRemoteDataSource,
        repoLocalDataSource: RepoLocalDataSource,
    ): RepoRepository =
        DefaultRepoRepository(
            repoRemoteDataSource, repoLocalDataSource)

    @Provides
    @Singleton
    fun provideRepoDatabase(@ApplicationContext context: Context): RepoDatabase =
        Room.databaseBuilder(context, RepoDatabase::class.java, "repo.db").build()

    @Provides
    @Singleton
    fun provideRepoDao(repoDatabase: RepoDatabase): RepoDao = repoDatabase.getRepoDao()
}
