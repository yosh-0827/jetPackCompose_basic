package com.example.jettodoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
  Taskテーブルを定義
 */
@Entity
data class Task(
    // idはプライマリキーなので誰も更新できないval型
    // @PrimaryKey(autoGenerate = true)にすることで、勝手に初期値0で作ってくれる
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // titleとdescriptionは後々更新するのでvar型
    var title: String,
    var description: String
)
