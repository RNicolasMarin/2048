package com.example.two_zero_four_eight.di

import android.content.Context
import com.example.two_zero_four_eight.data.local.TwoZeroFourEightDatabase
import com.example.two_zero_four_eight.data.local.daos.RecordDao
import com.example.two_zero_four_eight.data.repository.RecordRepository
import com.example.two_zero_four_eight.data.repository.RecordRepositoryImpl
import com.example.two_zero_four_eight.use_cases.AddNumberToBoardGameUseCase
import com.example.two_zero_four_eight.use_cases.CombineAndMoveNumbersUseCase
import com.example.two_zero_four_eight.use_cases.CreateBoardGameUseCase
import com.example.two_zero_four_eight.use_cases.HasWonTheGameUseCase
import com.example.two_zero_four_eight.use_cases.IsTherePossibleMovesUseCase
import com.example.two_zero_four_eight.use_cases.MoveNumbersUseCase
import com.example.two_zero_four_eight.use_cases.UpdateCurrentRecordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun getTwoZeroFourEightDatabase(@ApplicationContext appContext: Context) =
        TwoZeroFourEightDatabase.getInstance(appContext)

    @Provides
    fun provideRecordDao(db: TwoZeroFourEightDatabase) =
        db.recordDao()
    @Provides
    fun provideRecordRepository(dao: RecordDao): RecordRepository =
        RecordRepositoryImpl(dao)

    @Provides
    fun provideCombineAndMoveNumberUseCase() =
        CombineAndMoveNumbersUseCase()

    @Provides
    fun provideAddNumberToBoardGameUseCase() =
        AddNumberToBoardGameUseCase()

    @Provides
    fun provideCreateBoardGameUseCase(
        addNumberUseCase: AddNumberToBoardGameUseCase,
        repository: RecordRepository
    ) =
        CreateBoardGameUseCase(addNumberUseCase, repository)

    @Provides
    fun provideIsTherePossibleMovesUseCase() =
        IsTherePossibleMovesUseCase()

    @Provides
    fun provideHasWonTheGameUseCase() =
        HasWonTheGameUseCase()

    @Provides
    fun provideUpdateCurrentRecordsUseCase() =
        UpdateCurrentRecordsUseCase()

    @Provides
    fun provideMoveNumbersUseCase(
        useCase1: CombineAndMoveNumbersUseCase,
        useCase2: AddNumberToBoardGameUseCase,
        useCase3: IsTherePossibleMovesUseCase,
        useCase4: HasWonTheGameUseCase,
        useCase5: UpdateCurrentRecordsUseCase
    ): MoveNumbersUseCase {
        return MoveNumbersUseCase(useCase1, useCase2, useCase3, useCase4, useCase5)
    }
}