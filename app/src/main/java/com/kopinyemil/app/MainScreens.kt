package com.kopinyemil.app

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.kopinyemil.app.ui.theme.CaramelGold
import com.kopinyemil.app.ui.theme.CardCream
import com.kopinyemil.app.ui.theme.Coffee
import com.kopinyemil.app.ui.theme.DarkBrownHeader
import com.kopinyemil.app.ui.theme.DarkButton
import com.kopinyemil.app.ui.theme.Latte
import com.kopinyemil.app.ui.theme.Matcha
import com.kopinyemil.app.ui.theme.PillBorder
import com.kopinyemil.app.ui.theme.PillTextUnselected
import com.kopinyemil.app.ui.theme.TextDark
import com.kopinyemil.app.ui.theme.TextMuted
import com.kopinyemil.app.ui.theme.WarmBeige
import kotlinx.coroutines.launch

// ============ 5. Home Screen (Sesuai Gambar Desain 2) ============

@Composable
fun HomeScreen(
    vm: AppViewModel,
    onItemClick: (MenuItem) -> Unit,
    onOpenOrder: () -> Unit,
    onOpenProfile: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val categoryTabs = listOf("All", "Coffee", "Non Coffee", "Meals", "Desserts", "Orders")

    val filteredAll = DummyData.menu.filter {
        query.isBlank() || it.name.contains(query.trim(), ignoreCase = true) || it.description.contains(query.trim(), ignoreCase = true)
    }

    val coffeeList = filteredAll.filter { it.category == Category.COFFEE }
    val nonCoffeeList = filteredAll.filter { it.category == Category.NON_COFFEE }
    val mealsList = filteredAll.filter { it.category == Category.MEALS }
    val dessertsList = filteredAll.filter { it.category == Category.DESSERTS }

    Scaffold(
        containerColor = WarmBeige,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            Column {
                if (vm.cartCount > 0) {
                    CartBar(vm.cartCount, vm.cartSubtotal, onClick = onOpenOrder)
                }
                AppBottomBar(
                    selected = "home",
                    cartCount = vm.cartCount,
                    onHome = {},
                    onOrder = onOpenOrder,
                    onProfile = onOpenProfile
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Cokelat Melengkung dengan Foto Biji Kopi & Profil
            HomeHeader(vm.displayName, query, onQueryChange = { query = it })

            // Bar Tabs Kategori
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(categoryTabs) { tab ->
                    val isSelected = vm.activeTab == tab
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { vm.activeTab = tab }
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = tab,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) TextDark else TextMuted
                        )
                        if (isSelected) {
                            Spacer(Modifier.height(4.dp))
                            Box(
                                Modifier
                                    .width(24.dp)
                                    .height(3.dp)
                                    .background(DarkButton, RoundedCornerShape(50))
                            )
                        }
                    }
                }
            }

            // Konten Dinamis Berdasarkan Tab yang Dipilih
            when (vm.activeTab) {
                "All" -> {
                    // Seksi Coffee
                    if (coffeeList.isNotEmpty()) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                                SectionHeader(title = "Coffee", action = "See categories", onActionClick = { vm.activeTab = "Coffee" })
                            }
                            Spacer(Modifier.height(12.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                items(coffeeList, key = { it.id }) { item ->
                                    ProductCard(
                                        item = item,
                                        isFavorite = vm.isFavorite(item.id),
                                        onToggleFav = { vm.toggleFavorite(item.id) },
                                        onClick = { onItemClick(item) },
                                        onAdd = { onItemClick(item) }
                                    )
                                }
                            }
                        }
                    }

                    // Seksi Non Coffee
                    if (nonCoffeeList.isNotEmpty()) {
                        Column(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()) {
                            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                                SectionHeader(title = "Non Coffee", action = "See categories", onActionClick = { vm.activeTab = "Non Coffee" })
                            }
                            Spacer(Modifier.height(12.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                items(nonCoffeeList, key = { it.id }) { item ->
                                    ProductCard(
                                        item = item,
                                        isFavorite = vm.isFavorite(item.id),
                                        onToggleFav = { vm.toggleFavorite(item.id) },
                                        onClick = { onItemClick(item) },
                                        onAdd = { onItemClick(item) }
                                    )
                                }
                            }
                        }
                    }

                    // Seksi Meals
                    if (mealsList.isNotEmpty()) {
                        Column(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()) {
                            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                                SectionHeader(title = "Meals", action = "See categories", onActionClick = { vm.activeTab = "Meals" })
                            }
                            Spacer(Modifier.height(12.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                items(mealsList, key = { it.id }) { item ->
                                    ProductCard(
                                        item = item,
                                        isFavorite = vm.isFavorite(item.id),
                                        onToggleFav = { vm.toggleFavorite(item.id) },
                                        onClick = { onItemClick(item) },
                                        onAdd = { onItemClick(item) }
                                    )
                                }
                            }
                        }
                    }

                    // Seksi Desserts
                    if (dessertsList.isNotEmpty()) {
                        Column(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()) {
                            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                                SectionHeader(title = "Desserts", action = "See categories", onActionClick = { vm.activeTab = "Desserts" })
                            }
                            Spacer(Modifier.height(12.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                items(dessertsList, key = { it.id }) { item ->
                                    ProductCard(
                                        item = item,
                                        isFavorite = vm.isFavorite(item.id),
                                        onToggleFav = { vm.toggleFavorite(item.id) },
                                        onClick = { onItemClick(item) },
                                        onAdd = { onItemClick(item) }
                                    )
                                }
                            }
                        }
                    }
                }
                "Coffee" -> {
                    CategoryVerticalSection(
                        title = "Coffee",
                        subtitle = "Nikmati racikan kopi terbaik dari kami",
                        items = coffeeList,
                        vm = vm,
                        onItemClick = onItemClick
                    )
                }
                "Non Coffee" -> {
                    CategoryVerticalSection(
                        title = "Non Coffee",
                        subtitle = "Minuman segar tanpa kopi",
                        items = nonCoffeeList,
                        vm = vm,
                        onItemClick = onItemClick
                    )
                }
                "Meals" -> {
                    CategoryVerticalSection(
                        title = "Meals",
                        subtitle = "Makanan berat untuk mengisi tenaga",
                        items = mealsList,
                        vm = vm,
                        onItemClick = onItemClick
                    )
                }
                "Desserts" -> {
                    CategoryVerticalSection(
                        title = "Desserts",
                        subtitle = "Camilan manis teman minum kopimu",
                        items = dessertsList,
                        vm = vm,
                        onItemClick = onItemClick
                    )
                }

                "Orders" -> {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            "Orders",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextDark
                        )
                        Text(
                            "Status pesanan dan keranjang belanja aktifmu",
                            fontSize = 13.sp,
                            color = TextMuted
                        )

                        Spacer(Modifier.height(14.dp))

                        if (vm.cartCount == 0 && vm.lastOrder == null) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = CardCream)
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        Icons.Filled.ShoppingBag,
                                        contentDescription = null,
                                        tint = TextMuted,
                                        modifier = Modifier.size(44.dp)
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        "Belum Ada Pesanan Aktif",
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark,
                                        fontSize = 16.sp
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        "Pilih kopi atau makanan favoritmu lalu pesan sekarang!",
                                        textAlign = TextAlign.Center,
                                        color = TextMuted,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        } else {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = CardCream)
                            ) {
                                Column(
                                    modifier = Modifier.padding(18.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(
                                        "Ringkasan Keranjang (${vm.cartCount} item)",
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark,
                                        fontSize = 16.sp
                                    )

                                    if (vm.cartCount > 0) {
                                        vm.cart.forEach { cartItem ->
                                            DummyData.byId(cartItem.menuItemId)?.let { item ->
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text("${item.emoji} ${item.name} (${cartItem.size}) × ${cartItem.quantity}", color = TextDark, fontSize = 14.sp)
                                                    Text((cartItem.unitPrice * cartItem.quantity).toRupiah(), fontWeight = FontWeight.Bold, color = DarkButton, fontSize = 14.sp)
                                                }
                                            }
                                        }

                                        HorizontalDivider(color = PillBorder)

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("Subtotal", fontWeight = FontWeight.Bold, color = TextDark)
                                            Text(vm.cartSubtotal.toRupiah(), fontWeight = FontWeight.Bold, color = CaramelGold)
                                        }

                                        Spacer(Modifier.height(4.dp))

                                        Button(
                                            onClick = onOpenOrder,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp),
                                            shape = RoundedCornerShape(50),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = DarkButton,
                                                contentColor = Color.White
                                            )
                                        ) {
                                            Text("Lanjut ke Pembayaran", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        }
                                    }

                                    vm.lastOrder?.let { order ->
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            "Pesanan Terakhir Berhasil 🎉",
                                            fontWeight = FontWeight.Bold,
                                            color = Coffee,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            "Dikirim ke: ${order.address} (${order.itemCount} item - ${order.total.toRupiah()})",
                                            color = TextMuted,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(name: String, query: String, onQueryChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(HeaderShape)
            .background(HeaderBrush)
    ) {
        // Gambar latar belakang tekstur biji kopi yang halus
        Image(
            painter = painterResource(id = R.drawable.bg_coffee_beans),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(HeaderShape),
            contentScale = ContentScale.Crop,
            alpha = 0.28f
        )

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            // Teks "Welcome back,\nHani" sesuai gambar
            Text(
                text = "Welcome back,\n$name",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    lineHeight = 34.sp
                ),
                color = Color.White
            )

            Spacer(Modifier.height(20.dp))

            // Capsule Search Bar (Search dengan background krem kapsul sesuai gambar)
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = null,
                            tint = PillTextUnselected,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Search", color = PillTextUnselected, fontSize = 14.sp)
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE4D5C5),
                    unfocusedContainerColor = Color(0xFFE4D5C5),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = DarkButton,
                    focusedTextColor = TextDark,
                    unfocusedTextColor = TextDark
                )
            )
        }
    }
}

@Composable
private fun CategoryVerticalSection(
    title: String,
    subtitle: String,
    items: List<MenuItem>,
    vm: AppViewModel,
    onItemClick: (MenuItem) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(title, style = MaterialTheme.typography.titleLarge, color = TextDark)
        Text(subtitle, fontSize = 13.sp, color = TextMuted)
        Spacer(Modifier.height(14.dp))

        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        ProductCard(
                            item = item,
                            isFavorite = vm.isFavorite(item.id),
                            onToggleFav = { vm.toggleFavorite(item.id) },
                            onClick = { onItemClick(item) },
                            onAdd = { onItemClick(item) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(14.dp))
        }
    }
}

@Composable
private fun ProductCard(
    item: MenuItem,
    isFavorite: Boolean,
    onToggleFav: () -> Unit,
    onClick: () -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardModifier = if (modifier == Modifier) modifier.width(160.dp) else modifier
    Card(
        modifier = cardModifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Container Foto Produk Asli + Rating Badge + Bookmark Icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(WarmBeige),
                contentAlignment = Alignment.Center
            ) {
                // Foto Makanan / Minuman Asli
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Bookmark / Heart Icon (Kiri Atas)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.28f))
                        .clickable(onClick = onToggleFav),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorit",
                        tint = if (isFavorite) Color.Red else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Rating Badge (Kanan Bawah)
                RatingBadge(
                    rating = item.rating,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = TextDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.priceDisplay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextDark
                )

                // Tombol (+) Cokelat Karamel Melingkar
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(CaramelGold)
                        .clickable(onClick = onAdd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Tambah",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

// ============ 5b. Detail Screen (Sesuai Gambar Desain 3) ============

@Composable
fun DetailScreen(
    vm: AppViewModel,
    item: MenuItem,
    onBack: () -> Unit,
    onBuyNow: () -> Unit
) {
    var selectedSize by remember { mutableStateOf(item.sizes.firstOrNull() ?: "Short") }
    var selectedFlavor by remember { mutableStateOf(item.flavors.firstOrNull() ?: "Hazelnut") }
    var quantity by remember { mutableIntStateOf(1) }
    var isExpanded by remember { mutableStateOf(false) }
    
    val sizePriceExtra = when (selectedSize) {
        "Short", "Single", "Regular", "Pack of 3", "Single slice" -> 0
        "Tall", "Jumbo", "Double" -> 4_000
        "Grande", "Large", "Twin pack", "Pack of 6" -> 8_000
        else -> 0
    }
    val currentPrice = item.price + sizePriceExtra

    val isFav = vm.isFavorite(item.id)

    Scaffold(
        containerColor = WarmBeige,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            Surface(
                color = WarmBeige,
                shadowElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tombol tas belanja kecil di Kiri Bawah
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(50))
                            .border(1.2.dp, PillBorder, RoundedCornerShape(50))
                            .clickable { /* action */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.ShoppingBag,
                            contentDescription = "Cart",
                            tint = PillTextUnselected,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    // Tombol Kapsul "Buy now" Sesuai Desain
                    Button(
                        onClick = {
                            vm.addToCart(item.id, selectedSize, quantity, currentPrice)
                            onBuyNow()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkButton,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Buy now", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Image & Description Card (Sesuai Gambar 3)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                color = DarkBrownHeader,
                shadowElevation = 8.dp
            ) {
                Column {
                    // Bagian Gambar Full Lebar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    ) {
                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        
                        // Gradient Hitam Tipis di Atas untuk visibilitas ikon
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Brush.verticalGradient(listOf(Color.Black.copy(alpha=0.6f), Color.Transparent)))
                        )
                        
                        // Rating Badge (Kanan Bawah Gambar)
                        RatingBadge(
                            rating = item.rating,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        )

                        // Navigation Bar (Tombol Kembali & Bookmark)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.White, CircleShape)
                                    .background(Color.Black.copy(alpha = 0.3f))
                                    .clickable(onClick = onBack),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Kembali",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.White, CircleShape)
                                    .background(Color.Black.copy(alpha = 0.3f))
                                    .clickable { vm.toggleFavorite(item.id) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    if (isFav) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = if (isFav) CaramelGold else Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    // Wadah Deskripsi Cokelat Gelap
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 12.dp)
                            )
                            Text(
                                text = currentPrice.toRupiah(),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                color = Color.White,
                                maxLines = 1,
                                softWrap = false
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        Text(
                            "Description",
                            fontSize = 13.sp,
                            color = Latte
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = item.description,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.85f),
                            lineHeight = 20.sp
                        )

                        if (isExpanded && item.philosophy.isNotBlank()) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "Filosofi & Cerita Rasa 📖",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CaramelGold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = item.philosophy,
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                lineHeight = 20.sp,
                                fontFamily = FontFamily.Serif
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = if (isExpanded) "Read less ▲" else "Read more... ▼",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = CaramelGold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { isExpanded = !isExpanded }
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }

            // Seksi Pilihan Kustomisasi di Bagian Krem Bawah (Cup sizes & Flavors)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Seksi Cup sizes (Short, Tall, Grande)
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Cup sizes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextDark
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item.sizes.forEach { size ->
                            PillChip(
                                text = size,
                                selected = selectedSize == size,
                                onClick = { selectedSize = size }
                            )
                        }
                    }
                }

                // Seksi Flavors (Hazelnut, Caramel, Vanilla)
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Flavors",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextDark
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item.flavors.forEach { flavor ->
                            PillChip(
                                text = flavor,
                                selected = selectedFlavor == flavor,
                                onClick = { selectedFlavor = flavor }
                            )
                        }
                    }
                }

                // Baris Volume & Stepper Kuantitas [- 1 +]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Volume ${item.volume}",
                        fontSize = 14.sp,
                        color = TextMuted,
                        fontWeight = FontWeight.Medium
                    )

                    // Tombol Kuantitas (- 1 +)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, PillBorder, RoundedCornerShape(8.dp))
                                .clickable { if (quantity > 1) quantity-- },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Remove, contentDescription = "Kurang", tint = PillTextUnselected, modifier = Modifier.size(16.dp))
                        }

                        Text("$quantity", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, CaramelGold, RoundedCornerShape(8.dp))
                                .background(CaramelGold)
                                .clickable { quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Tambah", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CartBar(count: Int, subtotal: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CaramelGold)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$count item  •  ${subtotal.toRupiah()}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text("Pesan sekarang  ›", color = Color.White, fontWeight = FontWeight.Bold)
    }
}

// ============ 5c. Detail Pesanan (Order Review / Cart Screen) ============

@Composable
fun OrderDetailScreen(
    vm: AppViewModel,
    onHome: () -> Unit,
    onOpenAddress: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Scaffold(
        containerColor = WarmBeige,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            AppBottomBar(
                selected = "order",
                cartCount = vm.cartCount,
                onHome = onHome,
                onOrder = {},
                onProfile = onOpenProfile
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header Melengkung
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(HeaderShape)
                    .background(HeaderBrush)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_coffee_beans),
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(HeaderShape),
                    contentScale = ContentScale.Crop,
                    alpha = 0.28f
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Detail Pesanan", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Cek item pesananmu sebelum lanjut ke pengiriman", fontSize = 13.sp, color = Latte)
                    }
                }
            }

            if (vm.cart.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.ShoppingBag, contentDescription = null, modifier = Modifier.size(64.dp), tint = TextMuted)
                        Spacer(Modifier.height(12.dp))
                        Text("Keranjang Belanja Masih Kosong", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                        Spacer(Modifier.height(4.dp))
                        Text("Pilih kopi atau makanan favoritmu terlebih dahulu", color = TextMuted, fontSize = 14.sp)
                        Spacer(Modifier.height(20.dp))
                        PrimaryButton("Pilih Menu", onClick = onHome)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Text("Item yang Dipesan (${vm.cartCount})", style = MaterialTheme.typography.titleMedium, color = TextDark, fontWeight = FontWeight.Bold)

                            vm.cart.forEach { cartItem ->
                                DummyData.byId(cartItem.menuItemId)?.let { item ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = item.imageRes),
                                            contentDescription = item.name,
                                            modifier = Modifier
                                                .size(54.dp)
                                                .clip(RoundedCornerShape(12.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(item.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
                                            Text("Ukuran: ${cartItem.size}", fontSize = 13.sp, color = TextMuted)
                                            Text(cartItem.unitPrice.toRupiah(), fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Coffee)
                                        }

                                        // Stepper Kuantitas (+/-)
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .border(1.dp, PillBorder, RoundedCornerShape(6.dp))
                                                    .clickable { vm.updateCartQuantity(cartItem.id, cartItem.quantity - 1) },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(Icons.Filled.Remove, contentDescription = "Kurang", tint = PillTextUnselected, modifier = Modifier.size(14.dp))
                                            }
                                            Text("${cartItem.quantity}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark)
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(CaramelGold)
                                                    .clickable { vm.updateCartQuantity(cartItem.id, cartItem.quantity + 1) },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(Icons.Filled.Add, contentDescription = "Tambah", tint = Color.White, modifier = Modifier.size(14.dp))
                                            }
                                        }
                                    }
                                }
                            }

                            HorizontalDivider(color = Latte)

                            OrderRow("Subtotal", vm.cartSubtotal.toRupiah())
                            OrderRow("Ongkos Kirim", DELIVERY_FEE.toRupiah())
                            OrderRow("Total Bayar", (vm.cartSubtotal + DELIVERY_FEE).toRupiah())
                        }
                    }

                    // Button Lanjut ke Alamat & Pembayaran
                    Surface(color = Color.White, shadowElevation = 8.dp) {
                        Column(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(16.dp)
                        ) {
                            PrimaryButton("Lanjut ke Alamat & Pembayaran", onClick = onOpenAddress)
                        }
                    }
                }
            }
        }
    }
}

// ============ 6. Profile ============

@Composable
fun ProfileScreen(
    vm: AppViewModel,
    onHome: () -> Unit,
    onOrder: () -> Unit,
    onLogout: () -> Unit
) {
    val user = vm.currentUser
    var name by remember { mutableStateOf(user?.fullName ?: "") }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = WarmBeige,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            AppBottomBar(
                selected = "profile",
                cartCount = vm.cartCount,
                onHome = onHome,
                onOrder = onOrder,
                onProfile = {}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HeaderBrush, HeaderShape)
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Halo, ${vm.displayName}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                        Text("@${user?.username ?: "-"}", color = Latte, fontSize = 14.sp)
                    }
                }
            }

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("Profile Saya", style = MaterialTheme.typography.titleLarge, color = TextDark)
                Text("Profile Setting", color = TextMuted, fontSize = 14.sp)

                AppTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Nama",
                    leadingIcon = Icons.Filled.Person
                )
                AppTextField(
                    value = user?.username ?: "",
                    onValueChange = {},
                    label = "Username",
                    leadingIcon = Icons.Filled.AlternateEmail,
                    enabled = false
                )

                Spacer(Modifier.height(8.dp))
                PrimaryButton("Save", onClick = {
                    scope.launch {
                        if (name.isBlank()) {
                            snackbar.showSnackbar("Nama tidak boleh kosong")
                        } else {
                            vm.updateName(name)
                            snackbar.showSnackbar("Profil berhasil disimpan")
                        }
                    }
                })
                SecondaryButton("Logout", onClick = onLogout)
            }
        }
    }
}

// ============ 7. Alamat Pengiriman (Order) ============

@Composable
fun AddressScreen(
    vm: AppViewModel,
    onBack: () -> Unit,
    onOrderPlaced: () -> Unit
) {
    var receiver by remember { mutableStateOf(vm.currentUser?.fullName ?: "") }
    var address by remember { mutableStateOf("") }
    var detail by remember { mutableStateOf("") }

    val cartItems = vm.cart.mapNotNull { cartItem ->
        DummyData.byId(cartItem.menuItemId)?.let { it to cartItem }
    }
    val canOrder = cartItems.isNotEmpty() && receiver.isNotBlank() && address.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBeige)
            .imePadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(HeaderShape)
                .background(HeaderBrush)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_coffee_beans),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .clip(HeaderShape),
                contentScale = ContentScale.Crop,
                alpha = 0.28f
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Alamat", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Isi alamat pengiriman pesananmu", fontSize = 13.sp, color = Latte)
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Ringkasan Pesanan", style = MaterialTheme.typography.titleMedium, color = TextDark)
                    if (cartItems.isEmpty()) {
                        Text(
                            "Keranjang masih kosong. Pilih menu favoritmu di Home dulu ya.",
                            color = TextMuted,
                            fontSize = 14.sp
                        )
                        TextButton(onClick = onBack) { Text("Pilih menu", color = Coffee) }
                    } else {
                        cartItems.forEach { (item, cartItem) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = item.imageRes),
                                    contentDescription = item.name,
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("${item.name} (${cartItem.size})", color = TextDark, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                    Text("${cartItem.quantity} × ${cartItem.unitPrice.toRupiah()}", color = TextMuted, fontSize = 12.sp)
                                }
                                Text((cartItem.unitPrice * cartItem.quantity).toRupiah(), color = TextDark, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        HorizontalDivider(color = Latte)
                        SummaryRow("Ongkos kirim", DELIVERY_FEE.toRupiah())
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Alamat Pengiriman", style = MaterialTheme.typography.titleMedium, color = TextDark)

                    AppTextField(
                        value = receiver,
                        onValueChange = { receiver = it },
                        label = "Nama Lengkap",
                        leadingIcon = Icons.Filled.Person
                    )
                    AppTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Alamat",
                        leadingIcon = Icons.Filled.LocationOn,
                        minLines = 3
                    )
                    AppTextField(
                        value = detail,
                        onValueChange = { detail = it },
                        label = "Patokan atau detail lengkap",
                        leadingIcon = Icons.Filled.EditNote,
                        minLines = 3
                    )
                }
            }
        }

        Surface(color = Color.White, shadowElevation = 12.dp) {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (cartItems.isNotEmpty()) {
                    SummaryRow(
                        "Total bayar",
                        (vm.cartSubtotal + DELIVERY_FEE).toRupiah(),
                        bold = true
                    )
                }
                PrimaryButton(
                    text = "Order dan Kirim",
                    enabled = canOrder,
                    onClick = {
                        val fullAddress = address.trim() +
                            if (detail.isNotBlank()) " (${detail.trim()})" else ""
                        vm.placeOrder(receiver, fullAddress)
                        onOrderPlaced()
                    }
                )
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, bold: Boolean = false) {
    val weight = if (bold) FontWeight.Bold else FontWeight.Normal
    Row(Modifier.fillMaxWidth()) {
        Text(label, fontWeight = weight, color = TextDark, modifier = Modifier.weight(1f))
        Text(value, fontWeight = weight, color = if (bold) Coffee else TextDark)
    }
}

// ============ 8. Konfirmasi Pesanan ============

@Composable
fun ConfirmationScreen(vm: AppViewModel, onOk: () -> Unit) {
    BackHandler(onBack = onOk)

    val order = vm.lastOrder
    var shown by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { shown = true }
    val checkScale by animateFloatAsState(
        targetValue = if (shown) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "check"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HeaderBrush)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    "Halo ${order?.receiver ?: vm.displayName},",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextDark,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .scale(checkScale)
                        .background(Matcha, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "Pesanan berhasil",
                        tint = Color.White,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Text(
                    "Terima kasih sudah memesan, tunggu aja sebentar kurir kami segera mengantarkan pesananmu!",
                    textAlign = TextAlign.Center,
                    color = TextMuted,
                    fontSize = 15.sp
                )

                if (order != null) {
                    HorizontalDivider(color = Latte)
                    Text(
                        "Rincian Pesanan:",
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        fontSize = 15.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    order.items.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = item.imageRes),
                                contentDescription = item.name,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("${item.name} (${item.size})", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                                Text("${item.quantity} × ${item.unitPrice.toRupiah()}", fontSize = 12.sp, color = TextMuted)
                            }
                            Text((item.unitPrice * item.quantity).toRupiah(), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                        }
                    }

                    HorizontalDivider(color = Latte)
                    InfoRow("Jumlah item", "${order.itemCount} item")
                    InfoRow("Total bayar", order.total.toRupiah(), highlight = true)
                    InfoRow("Dikirim ke", order.address)
                }

                Spacer(Modifier.height(4.dp))
                PrimaryButton("Oke", onClick = onOk)
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String, highlight: Boolean = false) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = TextMuted, fontSize = 14.sp)
        Spacer(Modifier.width(12.dp))
        Text(
            value,
            color = if (highlight) Coffee else TextDark,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Medium,
            fontSize = 14.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}
