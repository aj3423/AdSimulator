package ad.sim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdSimulatorApp()
        }
    }
}

@Composable
fun AdSimulatorApp() {
    var showSplashAd by remember { mutableStateOf(true) }
    var showPopupAd by remember { mutableStateOf(false) }

    MaterialTheme {
        if (showSplashAd) {
            SplashAdScreen(onClose = {
                showSplashAd = false
                // Show popup ad shortly after entering main UI
                showPopupAd = true
            })
        } else {
            MainScreen(onShowPopup = { showPopupAd = true })

            // Popup Ad Dialog
            if (showPopupAd) {
                PopupAdDialog(onClose = { showPopupAd = false })
            }
        }
    }
}

@Composable
fun SplashAdScreen(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A)) // Dark ad-like background
    ) {
        // Fake Ad Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎉 SPONSOR AD 🎉",
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "This is a full-screen splash ad simulation.\n\nDownload our premium product today!",
                color = Color.LightGray,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        // Close Button (Top Right)
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("✕", color = Color.White, fontSize = 24.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onShowPopup: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ad Simulator") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Welcome to the Main UI!",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    // Auto-show popup ad after a tiny (feels more natural)
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(200.milliseconds) // 0.2 seconds after main screen appears
        onShowPopup()
    }
}

@Composable
fun PopupAdDialog(onClose: () -> Unit) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "📢 POPUP AD 📢",
                    fontSize = 22.sp,
                    color = Color(0xFF1A1A1A)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Special offer! Limited time only.\nTap below to claim.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { /* Simulate ad click */ }) {
                    Text("Claim Offer")
                }
            }

            // Close Button (Top Right)
            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text("✕", fontSize = 20.sp, color = Color.Gray)
            }
        }
    }
}
