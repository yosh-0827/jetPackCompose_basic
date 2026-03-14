package com.example.jettodoapp

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/*
  Hiltモジュール
  ・Hiltモジュールは@Moduleアノテーションがついたクラス。
  ・特定の型のインスタンス提供方法をHiltに知らせる役割がある。
  ・@InstallInアノテーションをつけて各モジュールが使用またはインストールされるAndroidクラスを知らせる必要がある。

  Hiltが自動生成できない依存（例: Retrofit, Room, Repository実装）の作り方を登録
 */
@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides // Hiltにインスタンス生成方法を教える
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "task_database").build()

    @Provides  // taskDAOのインスタンスを作る方法を伝える
    fun provideDao(db: AppDatabase) = db.taskDao()
}