package com.keixxdd.weewee.di

import com.keixxdd.weewee.data.repository.DefaultRepository
import com.keixxdd.weewee.domain.usecases.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @ViewModelScoped
    @Provides
    fun provideRegisterUserUseCase(repository: DefaultRepository): RegisterUserUseCase = RegisterUserUseCase(repository)

}