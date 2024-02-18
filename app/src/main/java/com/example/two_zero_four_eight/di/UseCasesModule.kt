package com.example.two_zero_four_eight.di

import com.example.two_zero_four_eight.use_cases.AddNumberToBoardGameUseCase
import com.example.two_zero_four_eight.use_cases.CombineAndMoveNumbersUseCase
import com.example.two_zero_four_eight.use_cases.CreateBoardGameUseCase
import com.example.two_zero_four_eight.use_cases.HasWonTheGameUseCase
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
    fun provideCombineAndMoveNumberUseCase() =
        CombineAndMoveNumbersUseCase()

    @Provides
    fun provideAddNumberToBoardGameUseCase() =
        AddNumberToBoardGameUseCase()

    @Provides
    fun provideCreateBoardGameUseCase(
        addNumberUseCase: AddNumberToBoardGameUseCase
    ) =
        CreateBoardGameUseCase(addNumberUseCase)

    @Provides
    fun provideIsTherePossibleMovesUseCase() =
        IsTherePossibleMovesUseCase()

    @Provides
    fun provideHasWonTheGameUseCase() =
        HasWonTheGameUseCase()

    @Provides
    fun provideMoveNumbersUseCase(
        useCase1: CombineAndMoveNumbersUseCase,
        useCase2: AddNumberToBoardGameUseCase,
        useCase3: IsTherePossibleMovesUseCase,
        useCase4: HasWonTheGameUseCase
    ): MoveNumbersUseCase {
        return MoveNumbersUseCase(useCase1, useCase2, useCase3, useCase4)
    }
}