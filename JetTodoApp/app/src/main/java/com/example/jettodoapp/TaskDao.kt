package com.example.jettodoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/*
  RoomがDB操作をするオブジェクト
  ・Daoは以下の観点から非同期処理が推奨される。
  ①UIスレッド（MainThread）でDB操作やHTTP通信といった時間のかかる処理を実行すると
    ユーザーがアプリを重いと感じる原因になったり、他の操作ができないなとUXを損ねる
  ②Room自体がMainThreadでのクエリ実行を許可していない。つまりRoomが非同期処理を
  　推奨している。
  ・非同期化の方法
  ①非同期ワンショットクエリ
  　一回だけクエリが実行され、DBのスナップショットを返す。
    coroutinesを使う。
  ②オブザーバブルクエリ
  　テーブルに変更があるたびに、新しいデータを取得しにいくクエリ。
  　Flowを使う。
 */
@Dao
interface TaskDao {
    @Insert
    // suspend: 非同期ワンショット
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM Task")  //Taskエンティティから全権取得する
    // オブザーバブル
    // 「DBのタスク一覧を取得し、内容が変わるたびに最新の一覧を通知する」
    // Flow<>は Kotlin Coroutines の仕組みで、「値を1回だけ返す」のではなく、「更新があるたびに<>の中の新しい値を流す」ための型
    fun loadAllTasks(): Flow<List<Task>>

    @Update
    // suspend: 非同期ワンショット
    suspend fun updateTask(task: Task)

    @Delete
    // suspend: 非同期ワンショット
    suspend fun deleteTask(task: Task)
}