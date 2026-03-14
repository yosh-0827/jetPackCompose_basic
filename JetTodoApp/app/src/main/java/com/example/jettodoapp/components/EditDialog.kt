package com.example.jettodoapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.jettodoapp.MainViewModel


/*
* 新規作成画面
*/
@Composable
fun EditDialog(
viewModel: MainViewModel = hiltViewModel(),  // viewModelを渡す
) {
    // UIからこのコンポーザブル関数を定義されてる部分(AlertDialog)が非表示になるとonDisposeが呼ばれる
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetProperties()
        }
    }
    AlertDialog(
        onDismissRequest = { viewModel.isShowDialog = false },  // 画面外の余白をタップした時の挙動
        title = { Text(text = if (viewModel.isEditing) "タスク更新" else "タスク新規作成") },
        text = {
            Column {
                // タイトルのフィールド
                Text(text = "タイトル")
                TextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.title = it }
                )
                // 詳細のテキストフィールド
                Text(text = "詳細")
                TextField(
                    value = viewModel.description,
                    onValueChange = { viewModel.description = it }
                )
            }
        },
        confirmButton = {
            // OK,キャンセルボタンの横並びコンポーネント
            Row(
                modifier = Modifier.padding(8.dp),
            ) {
                // 1fで余ってるスペースもいい感じに埋めてくれるらしい
                Spacer(modifier = Modifier.weight(1f))
                // キャンセルボタン
                Button(
                    modifier = Modifier.width(120.dp),
                    onClick = { viewModel.isShowDialog = false }
                ) {
                    Text(text = "キャンセル")
                }
                Spacer(modifier = Modifier.width(10.dp))
                // OKボタン
                Button(
                    modifier = Modifier.width(120.dp),
                    onClick = {
                        viewModel.isShowDialog = false
                        if (viewModel.isEditing) {
                            viewModel.updateTask()
                        } else {
                            viewModel.createTask()  // DBに登録する
                        }
                    }
                ) {
                    Text(text = "OK")
                }
            }
        },
    )
}
