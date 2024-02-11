package com.example.two_zero_four_eight.di

import com.example.two_zero_four_eight.use_cases.AddNumberToBoardGameUseCase
import com.example.two_zero_four_eight.use_cases.CreateBoardGameUseCase
import com.example.two_zero_four_eight.use_cases.IsTherePossibleMovesUseCase
import com.example.two_zero_four_eight.use_cases.MoveNumbersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideAddNumberToBoardGameUseCase(
    ): AddNumberToBoardGameUseCase {
        return AddNumberToBoardGameUseCase()
    }

    @Provides
    fun provideCreateBoardGameUseCase(
        addNumberUseCase: AddNumberToBoardGameUseCase
    ): CreateBoardGameUseCase {
        return CreateBoardGameUseCase(addNumberUseCase)
    }

    @Provides
    fun provideIsTherePossibleMovesUseCase(
    ): IsTherePossibleMovesUseCase {
        return IsTherePossibleMovesUseCase()
    }

    @Provides
    fun provideMoveNumbersUseCase(
        useCase1: AddNumberToBoardGameUseCase,
        useCase2: IsTherePossibleMovesUseCase
    ): MoveNumbersUseCase {
        return MoveNumbersUseCase(useCase1, useCase2)
    }
}