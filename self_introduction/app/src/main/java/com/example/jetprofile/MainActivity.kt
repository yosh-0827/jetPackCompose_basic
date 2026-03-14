package com.example.jetprofile

import CompanySection
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetprofile.components.DetailSection
import com.example.jetprofile.ui.theme.JetProfileTheme

class MainActivity : ComponentActivity() {
    // アプリを立ち上げたときに呼ばれるメソッド
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // system UI controllerがEOLなので下記を使う。（ステータスバー対応）
        enableEdgeToEdge(
            // テーマに合わせて自動切替
            statusBarStyle = SystemBarStyle.auto(
                // ステータスバーアイコン（明/暗）を設定
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            // ナビゲーションバーアイコンを設定
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        setContent {
            // システムのダークテーマ設定に合わせて、システムバー背景を 白/黒 で自動切替
            val systemBarBackgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White
            JetProfileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        // ステータスバー領域だけ背景色を塗る（Android 15以降のEdge-to-Edge対応）
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsTopHeight(WindowInsets.statusBars)
                                .background(systemBarBackgroundColor)
                                .align(Alignment.TopCenter)
                        )
                        // ナビゲーションバー領域も同じ背景色にする
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                                .background(systemBarBackgroundColor)
                                .align(Alignment.BottomCenter)
                        )

                        // 本文はScaffoldの余白を適用して表示する
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                        ) {
                            MainContent()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    // Column: コンポーネントが上から下に並ぶようにする
    // horizontalAlignment: 画面中央よせ
    // Modifier: UIの機能調整するオブジェクトっぽい何か
    // https://developer.android.com/develop/ui/compose/modifiers-list?hl=ja
    Column(
        modifier = Modifier
            .fillMaxSize()  // 横に引き伸ばす
            .padding(20.dp)  // 画面上部とその下（画像あたり）を間隔開ける
            .verticalScroll(rememberScrollState()),  // 画面スクロールできるようにする
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // プロフィール
        /*
        * Image（）で画像を読み込める
        * R : resディレクトリ
        * drawable : drawableディレクトリのこと
        * contentDescription : 画像の注釈
        */
        Image(
            painter = painterResource(id = R.drawable.persona_profile), // persona_profileはdrawable配下におく。（今は消してる）
            contentDescription = "プロフィール画像",
            modifier = Modifier
                .size(100.dp)  // 縦横のサイズ
                .clip(RoundedCornerShape(10.dp))  // 角を丸める
        )
        Spacer(modifier = Modifier.height(20.dp))  // 空間
        // 名前のテキスト部分
        Text(
            text = "鳳凰院京間",
            color = Color.Gray,   // 好きな色に変更OK
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold, // めっちゃ太いやつ
        )
        Spacer(modifier = Modifier.height(20.dp)) // 行間を開ける

        // 職業のテキスト部分
        Text(
            text = "職業: データサイエンティスト", color = Color.Gray, fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        CompanySection()

        Spacer(modifier = Modifier.height(20.dp))

        // 詳細表示ボタン
        // valueの中身が変わるとその変数を使ってるコンポーネントが画面を再レンダリングするステート(ミュータブルステート)
        // rememberでメモリ内で情報を保持できる。
        var isShowDetail by remember { mutableStateOf(false) }
        Button(
            modifier = Modifier.fillMaxWidth(),  // 画面いっぱいに広げる
            shape = RoundedCornerShape(5.dp),  // デフォは角丸らしいのでちょっと四角にする
            // ボタンの背景色
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF85F6A)),
            onClick = { isShowDetail =  !isShowDetail }
        ) {
            // ここにボタンに関する中身を作る
            Text(text = "詳細を表示", color = Color.White)
        }
        Spacer(modifier = Modifier.height(20.dp))

        // 趣味・居住地セクション
        if (isShowDetail) {
            DetailSection()
        }
    }
}

// このアノテーションの中の関数だけプレビューしたいができる。
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    JetProfileTheme {
//        Greeting("Pixel")
//    }
//}
