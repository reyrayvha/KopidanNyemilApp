package com.kopinyemil.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kopinyemil.app.ui.theme.KopiTheme

sealed interface Screen {
    object Welcome : Screen
    object SignUp : Screen
    object Login : Screen
    object Register : Screen
    object Home : Screen
    data class Detail(val item: MenuItem) : Screen
    object OrderDetail : Screen
    object Profile : Screen
    object Address : Screen
    object Confirmation : Screen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KopiTheme {
                val vm: AppViewModel = viewModel()
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }

                when (val screen = currentScreen) {
                    Screen.Welcome -> WelcomeScreen(
                        onGetStarted = { currentScreen = Screen.SignUp }
                    )
                    Screen.SignUp -> SignUpScreen(
                        onRegister = { currentScreen = Screen.Register },
                        onLogin = { currentScreen = Screen.Login }
                    )
                    Screen.Login -> LoginScreen(
                        vm = vm,
                        onLogin = { currentScreen = Screen.Home },
                        onGoRegister = { currentScreen = Screen.Register }
                    )
                    Screen.Register -> RegisterScreen(
                        vm = vm,
                        onRegister = { currentScreen = Screen.Home },
                        onGoLogin = { currentScreen = Screen.Login }
                    )
                    Screen.Home -> HomeScreen(
                        vm = vm,
                        onItemClick = { selectedItem ->
                            currentScreen = Screen.Detail(selectedItem)
                        },
                        onOpenOrder = { currentScreen = Screen.OrderDetail },
                        onOpenProfile = { currentScreen = Screen.Profile }
                    )
                    is Screen.Detail -> DetailScreen(
                        vm = vm,
                        item = screen.item,
                        onBack = { currentScreen = Screen.Home },
                        onBuyNow = { currentScreen = Screen.OrderDetail }
                    )
                    Screen.OrderDetail -> OrderDetailScreen(
                        vm = vm,
                        onHome = { currentScreen = Screen.Home },
                        onOpenAddress = { currentScreen = Screen.Address },
                        onOpenProfile = { currentScreen = Screen.Profile }
                    )
                    Screen.Profile -> ProfileScreen(
                        vm = vm,
                        onHome = { currentScreen = Screen.Home },
                        onOrder = { currentScreen = Screen.OrderDetail },
                        onLogout = {
                            vm.logout()
                            currentScreen = Screen.Welcome
                        }
                    )
                    Screen.Address -> AddressScreen(
                        vm = vm,
                        onBack = { currentScreen = Screen.OrderDetail },
                        onOrderPlaced = { currentScreen = Screen.Confirmation }
                    )
                    Screen.Confirmation -> ConfirmationScreen(
                        vm = vm,
                        onOk = { currentScreen = Screen.Home }
                    )
                }
            }
        }
    }
}
