package com.example.jettodoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.jettodoapp.components.EditDialog
import com.example.jettodoapp.components.TaskList
import com.example.jettodoapp.ui.theme.JetTodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint  // hiltによる依存関係の注入（DI）。「この画面・コンポーネントに依存注入してよい」とHiltに伝える印
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTodoAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),

                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MainContent()
                    }
                }
            }
        }
    }
}

/*
 画面右下の新規作成であるプラスマークの部分を作る
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContent(viewModel: MainViewModel = hiltViewModel()) {
    // ダイアログUIの表示非表示を管理するmutable state
    if (viewModel.isShowDialog) {
        EditDialog()
    }

    Scaffold(floatingActionButton = {
        // フローティングボタンが押されたらフラグをtrueに
        FloatingActionButton(onClick = { viewModel.isShowDialog = true }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "新規作成")
        }
    }) {
        /*
        DBのタスク一覧を画面側で受け取り、変更があればUIを更新できるようにする
        collectAsState: Flow の値をCompose側で購読して、値が更新されたら再コンポーズできる形にしています
        initial = emptyList(): DBからまだ値が来ていない最初の瞬間は、空リストを使うという意味です
        by: Kotlin の委譲で、State<List<Task>> から中身の List<Task> をそのまま tasks として使えるようにしています。
         */
        val tasks by viewModel.tasks.collectAsState(initial = emptyList())
        Log.d("COUNT", tasks.size.toString())

        TaskList(
            tasks = tasks,
            onClickRow = {
                // カードをクリックしたときに、そのカードの情報がitに入りviewModelにセットされる
                viewModel.setEditingTask(it)
                viewModel.isShowDialog = true
            },
            onClickDelete = { viewModel.deleteTask(it) },
        )
    }
}
