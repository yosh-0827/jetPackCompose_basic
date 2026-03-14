import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CompanySection() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth(),  // 引き伸ばす
    ) {
        // 会社名
        Text(
            text = "arg-z",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        //  部署、グループ
        Text(
            text = "すごい部署 パネエチーム",
            color = Color.Gray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Email
        // Emailは横並びにするのでRowかつ、中央寄せなのでCenterVertically
        // Row : 横並びにUI配置できるコンポーザブル関数
        Label(icon = Icons.Default.Email, text = "Email")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "arg-z@arigon.jp", fontSize = 16.sp) // 文字はsp
        Spacer(modifier = Modifier.height(5.dp))
        // 下線を作る
        HorizontalDivider(
            // RoundedCornerShape1000dpで角丸
            modifier = Modifier.clip(RoundedCornerShape(1000.dp)),
            thickness = 2.dp,
        )
    }
}