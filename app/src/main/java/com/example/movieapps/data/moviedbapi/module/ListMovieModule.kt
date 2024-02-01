package com.example.movieapps.data.moviedbapi.module

import com.example.movieapps.data.moviedbapi.usecase.GetMovieGenreUseCase
import com.example.movieapps.data.moviedbapi.MovieDbService
import com.example.movieapps.data.moviedbapi.datasource.ListMovieDataSource
import com.example.movieapps.data.moviedbapi.datasource.ListMovieDataSourceImpl
import com.example.movieapps.data.moviedbapi.repo.ListMovieRepo
import com.example.movieapps.data.moviedbapi.repo.ListMovieRepoImpl
import com.example.movieapps.data.moviedbapi.usecase.GetMovieListByGenreUseCase
import com.example.movieapps.data.moviedbapi.usecase.GetMovieListByGenreWithPagingUseCase
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
    fun provideGetMovieGenreUseCase(repo: ListMovieRepo): GetMovieGenreUseCase {
        return GetMovieGenreUseCase(repo)
    }
    @Singleton
    @Provides
    fun provideGetMovieListByGenreUseCase(repo: ListMovieRepo): GetMovieListByGenreUseCase{
        return GetMovieListByGenreUseCase(repo)
    }
    @Singleton
    @Provides
    fun provideGetMovieListByGenreWithPagingUseCase(repo: ListMovieRepo): GetMovieListByGenreWithPagingUseCase{
        return GetMovieListByGenreWithPagingUseCase(repo)
    }
}