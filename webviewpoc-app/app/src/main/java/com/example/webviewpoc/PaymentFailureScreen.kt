import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun PaymentFailureScreen(
    onRetry: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFEF5F5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Custom "X" icon using Compose
        Canvas(modifier = Modifier.size(100.dp)) {
            val canvasSize = size
            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(canvasSize.width * 0.2f, canvasSize.height * 0.2f)
                lineTo(canvasSize.width * 0.8f, canvasSize.height * 0.8f)
                moveTo(canvasSize.width * 0.8f, canvasSize.height * 0.2f)
                lineTo(canvasSize.width * 0.2f, canvasSize.height * 0.8f)
            }

            drawPath(
                path = path,
                color = Color(0xFFD32F2F),
                style = Stroke(
                    width = 8.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Payment Failed",
            style = TextStyle(
                color = Color(0xFFD32F2F),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We couldn't process your payment. Please try again",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRetry,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Try Again")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onCancel) {
            Text(
                text = "Cancel Payment",
                color = Color(0xFFF44336)
            )
        }
    }
}
