package com.example.jetbmicalc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetbmicalc.ui.theme.JetBMICalcTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    // 定義したviewModelを使えるようにする。
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ログをはくライブラリ。画面左下のLogCatで表示される。
        Log.d("Test", viewModel.test)

        enableEdgeToEdge()
        setContent {
            JetBMICalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // 左寄せ
                        Column(horizontalAlignment = Alignment.Start,
                            // 前後左右に20dp分だけパディング（余白を作る）
                            modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "BMI計算アプリ",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                            )
                            Spacer(modifier = Modifier.height(30.dp))

                            // 身長
                            /* テキスト入力→onValueChange→viewModel→PinkLabeledTextFieldが再描画 */
                            PinkLabeledTextField(
                                value = viewModel.height,
                                onValueChange = { viewModel.height = it },  // it にユーザーが入力したテキストい情報が入る
                                label = "身長(cm)",
                                placeholder = "170",
                            )
                            Spacer(modifier = Modifier.height(20.dp))

                            // 体重
                            PinkLabeledTextField(
                                value = viewModel.weight,
                                onValueChange = { viewModel.weight = it },
                                label = "体重(kg)",
                                placeholder = "65"
                            )
                            Spacer(modifier = Modifier.height(30.dp))

                            //  ボタン
                            Button(
                                onClick = { viewModel.calculateBMI() },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF85F6A)),

                            ) {
                                Text(
                                    text = "計算する",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    )
                            }
                            Spacer(modifier = Modifier.height(20.dp))

                            // 結果表示部分
                            Text(
                                text = "あなたのBMIは${viewModel.bmi}です",
                                modifier = Modifier.fillMaxWidth(),  // 画面幅いっぱい
                                textAlign = TextAlign.Center,  // 中央よせ
                                color = Color.Gray,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,  // 超太く
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PinkLabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
) {
    // Column: 縦方向にUIを積み重ねる
    Column() {
        Text(text = label,
            color = Color(0xfff85f6a),
            fontWeight = FontWeight.Bold,
        )
        // テキストボックスを作る
        TextField(
            // 画面横いっぱいに広げる
            modifier = Modifier.fillMaxWidth(),
            // テキストフィールドに入力された文字列
            value = value,
            // ユーザーがテキストを打ち込んだときに、どんな処理を走らせるか
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                // テキストフィールドの色
                focusedContainerColor = Color.Transparent,  // 選択してる時の背景色
                unfocusedContainerColor = Color.Transparent  // 非選択時の背景色
            ),
            placeholder = {
                // フィールドにヒントとして表示させたいもの
                Text(text = placeholder)
            },
            // 入力するキーボードは数字であると指定する。
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,  // 入力キーボードの改行をさせない。
        )
    }
}