package com.example.movieapps.data.moviedbapi.module

import com.example.movieapps.data.moviedbapi.GetMovieGenre
import com.example.movieapps.data.moviedbapi.MovieDbService
import com.example.movieapps.data.moviedbapi.datasource.ListMovieDataSource
import com.example.movieapps.data.moviedbapi.datasource.ListMovieDataSourceImpl
import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import com.example.movieapps.data.moviedbapi.repo.ListMovieRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListMovieModule {
    @Singleton
    @Provides
    fun provideListMovieDataSource(service: MovieDbService):ListMovieDataSource{
        return ListMovieDataSourceImpl(service)
    }

    @Singleton
    @Provides
    fun provideListMovieRepo(dataSource: ListMovieDataSource):ListMovieRepo{
        return ListMovieRepoImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideGetMovieGenre(repo: ListMovieRepo):GetMovieGenre{
        return GetMovieGenre(repo)
    }
}