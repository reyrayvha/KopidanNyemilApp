package com.kopinyemil.app

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.kopinyemil.app.ui.theme.Coffee
import com.kopinyemil.app.ui.theme.DarkBrownHeader
import com.kopinyemil.app.ui.theme.DarkButton
import com.kopinyemil.app.ui.theme.Latte
import com.kopinyemil.app.ui.theme.PillBorder
import com.kopinyemil.app.ui.theme.TextDark
import com.kopinyemil.app.ui.theme.TextMuted
import com.kopinyemil.app.ui.theme.WarmBeige

// ============ 1. Welcome / Splash Screen (Sesuai Gambar Desain 1) ============

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    // Background gradient dari cokelat kopi gelap di atas ke krem hangat di bawah
    val splashGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF2C140E),
            Color(0xFF4A2518),
            Color(0xFF8A5338),
            Color(0xFFDCC8B6),
            Color(0xFFF4ECE4)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(splashGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Logo Cafe
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cafe_logo),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(72.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "KopiDanNyemil",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            // Tombol Kapsul "Get started →" di Bagian Bawah
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 40.dp)
            ) {
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE2D0BE),
                        contentColor = DarkBrownHeader
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = "Get started →",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = DarkBrownHeader
                    )
                }
            }
        }
    }
}

// ============ 2. Sign Up Choice Screen (Layar 2 Wireframe) ============

@Composable
fun SignUpScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(DarkButton),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cafe_logo),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(Modifier.height(28.dp))

            Text(
                "Sign up",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                color = TextDark
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Join as a member & experience more fun from various delicious meals & coffee at Kopi dan Nyemil",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(48.dp))

            PrimaryButton(text = "Register", onClick = onRegister)

            Spacer(Modifier.height(16.dp))

            SecondaryButton(text = "Login", onClick = onLogin)
        }
    }
}

// ============ 3. Login Screen ============

@Composable
fun LoginScreen(
    vm: AppViewModel,
    onLogin: () -> Unit,
    onGoRegister: () -> Unit
) {
    var email by remember { mutableStateOf("hani@email.com") }
    var password by remember { mutableStateOf("123456") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Coffee icon
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(DarkButton),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cafe_logo),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Welcome Back",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                color = TextDark
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Masuk untuk mulai memesan kopi favoritmu",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; error = "" },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DarkButton,
                    unfocusedBorderColor = Latte,
                    cursorColor = DarkButton,
                    focusedLabelColor = DarkButton,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(Modifier.height(14.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; error = "" },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DarkButton,
                    unfocusedBorderColor = Latte,
                    cursorColor = DarkButton,
                    focusedLabelColor = DarkButton,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Text(error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val err = vm.login(email, password)
                    if (err == null) {
                        error = ""
                        onLogin()
                    } else {
                        error = err
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkButton,
                    contentColor = Color.White
                )
            ) {
                Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onGoRegister) {
                Text(
                    "Belum punya akun? Register",
                    color = Coffee,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}

// ============ 3. Register Screen ============

@Composable
fun RegisterScreen(
    vm: AppViewModel,
    onRegister: () -> Unit,
    onGoLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(DarkButton),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cafe_logo),
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Buat Akun Baru",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                color = TextDark
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Daftar untuk menikmati beragam menu kopi favorit",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(28.dp))

            val fieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkButton,
                unfocusedBorderColor = Latte,
                cursorColor = DarkButton,
                focusedLabelColor = DarkButton,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; error = "" },
                label = { Text("Nama Lengkap") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; error = "" },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; error = "" },
                label = { Text("Password (min. 6 karakter)") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; error = "" },
                label = { Text("Konfirmasi Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                singleLine = true
            )

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Text(error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            Spacer(Modifier.height(22.dp))

            Button(
                onClick = {
                    when {
                        name.isBlank() || email.isBlank() || password.isBlank() ->
                            error = "Semua field wajib diisi"
                        password.length < 6 ->
                            error = "Password minimal 6 karakter"
                        password != confirmPassword ->
                            error = "Konfirmasi password tidak cocok"
                        else -> {
                            val err = vm.register(name, email, password)
                            if (err == null) {
                                error = ""
                                onRegister()
                            } else {
                                error = err
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkButton,
                    contentColor = Color.White
                )
            ) {
                Text("Register", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onGoLogin) {
                Text(
                    "Sudah punya akun? Login",
                    color = Coffee,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}
