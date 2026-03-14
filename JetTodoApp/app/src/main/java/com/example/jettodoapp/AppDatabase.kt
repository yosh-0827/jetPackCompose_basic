package com.example.jettodoapp

import androidx.room.Database
import androidx.room.RoomDatabase

/*
  Room データベースクラス
  概要: DBを保持し、アプリの永続データに対する基礎的な接続のメインアクセスポイントとして機能。
  　　　アプリがDAOのインスタンスを使えるように提供する役割がある。
 */
@Database(entities = [Task::class], version = 1)
// RoomDatabaseを継承
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
