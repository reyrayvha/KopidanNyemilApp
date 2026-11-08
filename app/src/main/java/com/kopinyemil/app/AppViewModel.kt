package com.kopinyemil.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.util.Locale

// ---------- Model ----------

data class CartItem(
    val id: Int,
    val menuItemId: Int,
    val size: String,
    val quantity: Int,
    val unitPrice: Int
)

enum class Category(val label: String) {
    COFFEE("Coffee"),
    NON_COFFEE("Non Coffee"),
    MEALS("Meals"),
    DESSERTS("Desserts")
}

data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val category: Category,
    val rating: Double = 4.8,
    val isTopPick: Boolean = false,
    val sizes: List<String> = listOf("Short", "Tall", "Grande"),
    val flavors: List<String> = listOf("Hazelnut", "Caramel", "Vanilla"),
    val volume: String = "160 ml",
    val imageUrl: String = "",
    val imageRes: Int = 0,
    val priceDisplay: String = "Rp 0",
    val emoji: String = "☕",
    val philosophy: String = ""
)

data class User(
    val fullName: String,
    val username: String,
    val password: String
)

data class OrderItem(
    val name: String,
    val size: String,
    val quantity: Int,
    val unitPrice: Int,
    val imageRes: Int
)

data class Order(
    val receiver: String,
    val address: String,
    val itemCount: Int,
    val total: Int,
    val items: List<OrderItem>
)

// ---------- Data dummy dengan Foto Asli Makanan & Minuman ----------

const val DELIVERY_FEE = 5_000

object DummyData {
    val menu = listOf(
        MenuItem(
            id = 1,
            name = "Americano",
            description = "Rich espresso shot diluted with hot water, delivering a smooth, bold aroma with a clean and pure coffee finish.",
            price = 22_000,
            priceDisplay = 22_000.toRupiah(),
            category = Category.COFFEE,
            rating = 4.8,
            isTopPick = true,
            sizes = listOf("Short", "Tall", "Grande"),
            flavors = listOf("Hazelnut", "Caramel", "Vanilla"),
            volume = "160 ml",
            imageRes = R.drawable.americano,
            emoji = "☕",
            philosophy = "Lahir dari masa Perang Dunia II saat tentara Amerika menyiram espresso dengan air panas agar rasanya lebih ringan. Melambangkan kesederhanaan, kejujuran, dan keteguhan hati dalam menghadapi tantangan."
        ),
        MenuItem(
            id = 2,
            name = "Cappuccino",
            description = "A classic espresso drink crafted with equal parts espresso, steamed milk, and silky smooth milk foam with delicate heart latte art.",
            price = 25_000,
            priceDisplay = 25_000.toRupiah(),
            category = Category.COFFEE,
            rating = 4.8,
            isTopPick = true,
            sizes = listOf("Short", "Tall", "Grande"),
            flavors = listOf("Hazelnut", "Caramel", "Vanilla"),
            volume = "160 ml",
            imageRes = R.drawable.cappucino,
            emoji = "☕",
            philosophy = "Diinspirasi dari jubah cokelat rahib Capuchin di Italia. Keseimbangan sempurna antara pahitnya espresso dan manisnya foam susu melambangkan harmoni dalam kehidupan."
        ),
        MenuItem(
            id = 3,
            name = "Matcha Latte",
            description = "Premium ceremonial grade Japanese matcha blended smoothly with steamed fresh milk for a soothing taste.",
            price = 27_000,
            priceDisplay = 27_000.toRupiah(),
            category = Category.NON_COFFEE,
            rating = 4.7,
            isTopPick = false,
            sizes = listOf("Short", "Tall", "Grande"),
            flavors = listOf("Vanilla", "Caramel", "Hazelnut"),
            volume = "180 ml",
            imageRes = R.drawable.matcha,
            emoji = "🍵",
            philosophy = "Terinspirasi dari tradisi Zen Jepang (Chanoyu). Warna hijau zamrud melambangkan ketenangan jiwa, kesabaran dalam proses, dan keselarasan dengan alam."
        ),
        MenuItem(
            id = 4,
            name = "Caramel Macchiato",
            description = "Freshly steamed milk with vanilla-flavored syrup marked with bold espresso and topped with a buttery caramel drizzle.",
            price = 28_000,
            priceDisplay = 28_000.toRupiah(),
            category = Category.COFFEE,
            rating = 4.9,
            isTopPick = true,
            sizes = listOf("Short", "Tall", "Grande"),
            flavors = listOf("Hazelnut", "Caramel", "Vanilla"),
            volume = "200 ml",
            imageRes = R.drawable.macchiato,
            emoji = "☕",
            philosophy = "'Macchiato' berarti 'ditandai'. Lapisan vanila, susu, espresso, dan karamel mengajarkan bahwa keindahan hidup tercipta dari pengalaman yang berlapis-lapis."
        ),
        MenuItem(
            id = 5,
            name = "Cold Brew",
            description = "Slow-steeped in cold filtered water for 20 hours to extract a remarkably smooth, naturally sweet, low-acid coffee.",
            price = 24_000,
            priceDisplay = 24_000.toRupiah(),
            category = Category.COFFEE,
            rating = 4.8,
            isTopPick = false,
            sizes = listOf("Short", "Tall", "Grande"),
            flavors = listOf("Vanilla sweet cream", "Caramel", "Classic"),
            volume = "220 ml",
            imageRes = R.drawable.cold_brew,
            emoji = "🧊",
            philosophy = "Diseduh dingin selama 20 jam tanpa terburu-buru. Mengajarkan nilai kesabaran ekstra untuk mendapatkan hasil manis alami tanpa rasa asam yang tajam."
        ),
        MenuItem(
            id = 6,
            name = "Beef Croissant Sandwich",
            description = "Crispy croissant filled with smoked beef, fresh lettuce, and savory cheese sauce.",
            price = 35_000,
            priceDisplay = 35_000.toRupiah(),
            category = Category.MEALS,
            rating = 4.7,
            isTopPick = true,
            sizes = listOf("Single", "Double"),
            flavors = listOf("Original", "Spicy"),
            volume = "1 pcs",
            imageRes = R.drawable.sandwich,
            emoji = "🥪",
            philosophy = "Perpaduan keahlian pastry Prancis dan selera gurih modern. Setiap lipatan lapisan renyah melambangkan kerja keras dan dedikasi dalam menciptakan kelezatan."
        ),
        MenuItem(
            id = 7,
            name = "Chocolate Cookies",
            description = "Freshly baked handcrafted chocolate chip cookies with crisp golden edges and chewy, gooey chocolate centers.",
            price = 14_000,
            priceDisplay = 14_000.toRupiah(),
            category = Category.DESSERTS,
            rating = 4.9,
            isTopPick = true,
            sizes = listOf("Pack of 3", "Pack of 6"),
            flavors = listOf("Double Choc", "Walnut", "Sea Salt"),
            volume = "3 pcs",
            imageRes = R.drawable.coockies,
            emoji = "🍪",
            philosophy = "Biskuit cokelat klasik yang renyah di luar namun lembut di dalam. Mengingatkan kita bahwa kehangatan sejati selalu tersimpan di bagian terdalam."
        ),
        MenuItem(
            id = 8,
            name = "Butter Croissant",
            description = "Flaky, buttery French pastry baked fresh every morning to golden crisp perfection with airy honeycomb layers.",
            price = 20_000,
            priceDisplay = 20_000.toRupiah(),
            category = Category.DESSERTS,
            rating = 4.8,
            isTopPick = true,
            sizes = listOf("Regular", "Jumbo"),
            flavors = listOf("Butter", "Almond", "Chocolate"),
            volume = "1 pcs",
            imageRes = R.drawable.croissant,
            emoji = "🥐",
            philosophy = "Dibuat dari adonan mentega murni yang dipanggang berlapis. Melambangkan proses hidup yang membutuhkan waktu dan ketelitian untuk mekar dengan sempurna."
        ),
        MenuItem(
            id = 9,
            name = "Fudge Brownies",
            description = "Rich, decadent dark Belgian chocolate brownies with a glossy crinkle top and dense, intensely fudgy texture.",
            price = 18_000,
            priceDisplay = 18_000.toRupiah(),
            category = Category.DESSERTS,
            rating = 4.9,
            isTopPick = false,
            sizes = listOf("Single slice", "Twin pack"),
            flavors = listOf("Classic Dark", "Salted Caramel"),
            volume = "1 slice",
            imageRes = R.drawable.brownies,
            emoji = "🍫",
            philosophy = "Cokelat Belgia pekat yang padat dan manis. Melambangkan momen kebahagiaan manis yang mendalam di tengah kepenatan hari."
        ),
        MenuItem(
            id = 10,
            name = "Blueberry Muffin",
            description = "Fluffy golden muffin baked fresh daily, bursting with juicy wild blueberries and sweet crumb crust.",
            price = 16_000,
            priceDisplay = 16_000.toRupiah(),
            category = Category.DESSERTS,
            rating = 4.7,
            isTopPick = false,
            sizes = listOf("Regular", "Large"),
            flavors = listOf("Blueberry", "Choc Chip", "Banana"),
            volume = "1 pcs",
            imageRes = R.drawable.muffin,
            emoji = "🧁",
            philosophy = "Muffin empuk yang dipenuhi blueberry liar segar. Melambangkan kejutan manis yang selalu hadir jika kita bersyukur atas hal-hal kecil."
        ),
        MenuItem(
            id = 11,
            name = "Nasi Goreng Special",
            description = "Indonesian fried rice with sunny side up egg, chicken, and crackers.",
            price = 40_000,
            priceDisplay = 40_000.toRupiah(),
            category = Category.MEALS,
            rating = 4.8,
            isTopPick = true,
            sizes = listOf("Regular", "Large"),
            flavors = listOf("Spicy", "Medium", "Mild"),
            volume = "1 portion",
            imageRes = R.drawable.nasi_goreng,
            emoji = "🍛",
            philosophy = "Hidangan kebanggaan Nusantara yang menyatukan berbagai bumbu rempah. Melambangkan persatuan dan kehangatan kebersamaan keluarga."
        ),
        MenuItem(
            id = 12,
            name = "Spaghetti Carbonara",
            description = "Classic Italian pasta with creamy sauce, smoked beef, and parmesan cheese.",
            price = 45_000,
            priceDisplay = 45_000.toRupiah(),
            category = Category.MEALS,
            rating = 4.9,
            isTopPick = false,
            sizes = listOf("Regular", "Large"),
            flavors = listOf("Creamy", "Spicy"),
            volume = "1 portion",
            imageRes = R.drawable.spaghetti,
            emoji = "🍝",
            philosophy = "Resep klasik para pekerja tambang Italia ('Carbonaro'). Melambangkan kesederhanaan bahan yang mampu menghadirkan kelezatan mewah."
        ),
        MenuItem(
            id = 13,
            name = "Chicken Katsu Curry",
            description = "Crispy chicken katsu served with Japanese curry, potatoes, carrots, and rice.",
            price = 48_000,
            priceDisplay = 48_000.toRupiah(),
            category = Category.MEALS,
            rating = 4.8,
            isTopPick = true,
            sizes = listOf("Regular", "Large"),
            flavors = listOf("Spicy", "Original"),
            volume = "1 portion",
            imageRes = R.drawable.chicken_katsu,
            emoji = "🍛",
            philosophy = "Perpaduan katsu renyah dan kuah kari rempah yang hangat. Melambangkan ketangguhan diri yang dibalut kelembutan rasa."
        ),
        MenuItem(
            id = 14,
            name = "Lychee Tea",
            description = "Refreshing iced tea infused with sweet lychee syrup and fresh lychee fruit.",
            price = 22_000,
            priceDisplay = 22_000.toRupiah(),
            category = Category.NON_COFFEE,
            rating = 4.7,
            isTopPick = false,
            sizes = listOf("Regular", "Large"),
            flavors = listOf("Original", "Less Sugar"),
            volume = "180 ml",
            imageRes = R.drawable.lychee_tea,
            emoji = "🍹",
            philosophy = "Kesegaran teh es dipadu manisnya buah leci segar. Melambangkan kesegaran pikiran dan ketenangan jiwa di kala cuaca terik."
        ),
        MenuItem(
            id = 15,
            name = "Thai Tea",
            description = "Authentic sweet Thai milk tea with rich black tea and creamy condensed milk.",
            price = 25_000,
            priceDisplay = 25_000.toRupiah(),
            category = Category.NON_COFFEE,
            rating = 4.8,
            isTopPick = true,
            sizes = listOf("Regular", "Large"),
            flavors = listOf("Original", "Less Sugar"),
            volume = "180 ml",
            imageRes = R.drawable.thai_tea,
            emoji = "🧋",
            philosophy = "Teh hitam rempah beraroma khas dengan kelembutan susu kental manis. Melambangkan keberagaman budaya yang saling melengkapi."
        ),
        MenuItem(
            id = 16,
            name = "Mango Tea",
            description = "Sweet and tangy iced tea blended with fresh mango puree.",
            price = 24_000,
            priceDisplay = 24_000.toRupiah(),
            category = Category.NON_COFFEE,
            rating = 4.6,
            isTopPick = false,
            sizes = listOf("Regular", "Large"),
            flavors = listOf("Original", "Less Sugar"),
            volume = "180 ml",
            imageRes = R.drawable.mango_tea,
            emoji = "🥭",
            philosophy = "Sentuhan tropis mangga manis dalam seduhan teh alami. Melambangkan semangat muda yang ceria dan penuh energi positif."
        ),
        MenuItem(
            id = 17,
            name = "Red Velvet Latte",
            description = "Smooth and velvety red velvet drink with creamy steamed milk.",
            price = 26_000,
            priceDisplay = 26_000.toRupiah(),
            category = Category.NON_COFFEE,
            rating = 4.8,
            isTopPick = true,
            sizes = listOf("Short", "Tall", "Grande"),
            flavors = listOf("Original", "Vanilla", "Extra Cream"),
            volume = "180 ml",
            imageRes = R.drawable.red_velvet,
            emoji = "🍰",
            philosophy = "Warna merah anggun berbalut krim lembut. Melambangkan cinta, kasih sayang, dan keanggunan rasa dalam setiap tegukan."
        ),
        MenuItem(
            id = 18,
            name = "Bagel & Donut",
            description = "Soft, fluffy artisanal glazed donut and freshly baked golden bagel.",
            price = 18_000,
            priceDisplay = 18_000.toRupiah(),
            category = Category.DESSERTS,
            rating = 4.7,
            isTopPick = false,
            sizes = listOf("Single", "Double"),
            flavors = listOf("Glazed", "Chocolate"),
            volume = "1 pcs",
            imageRes = R.drawable.bagel,
            emoji = "🥯",
            philosophy = "Bentuk melingkar tanpa putus melambangkan keberlanjutan kebahagiaan dan kehangatan yang tak pernah usai."
        )
    )

    fun byId(id: Int): MenuItem? = menu.firstOrNull { it.id == id }
}

fun Int.toRupiah(): String =
    "Rp " + NumberFormat.getInstance(Locale.forLanguageTag("id-ID")).format(this)

// ---------- ViewModel ----------

class AppViewModel : ViewModel() {

    private val users = mutableStateListOf(User("Hani", "demo", "123456"))

    var currentUser by mutableStateOf<User?>(users.first())
        private set

    var activeTab by mutableStateOf("All")

    var selectedMenuItem by mutableStateOf<MenuItem?>(DummyData.menu.first())

    val drinks: List<MenuItem>
        get() = DummyData.menu.filter { it.category == Category.COFFEE || it.category == Category.NON_COFFEE }

    val foods: List<MenuItem>
        get() = DummyData.menu.filter { it.category == Category.MEALS || it.category == Category.DESSERTS }

    var selectedItem: MenuItem?
        get() = selectedMenuItem ?: DummyData.menu.first()
        set(value) {
            selectedMenuItem = value
        }

    fun selectItem(item: MenuItem) {
        selectedMenuItem = item
    }

    val cart = mutableStateListOf<CartItem>()
    private var nextCartId = 1

    val favorites = mutableStateListOf<Int>(1, 2, 7) // Default items 1, 2, 7 favorited

    var lastOrder by mutableStateOf<Order?>(null)
        private set

    val displayName: String
        get() = currentUser?.fullName?.trim()?.substringBefore(" ")?.ifBlank { "Hani" } ?: "Hani"

    val cartCount: Int
        get() = cart.sumOf { it.quantity }

    val cartSubtotal: Int
        get() = cart.sumOf { it.unitPrice * it.quantity }

    fun toggleFavorite(id: Int) {
        if (favorites.contains(id)) {
            favorites.remove(id)
        } else {
            favorites.add(id)
        }
    }

    fun isFavorite(id: Int): Boolean = favorites.contains(id)

    fun toggleFavorite(name: String) {
        val item = DummyData.menu.firstOrNull { it.name.equals(name, ignoreCase = true) }
        if (item != null) toggleFavorite(item.id)
    }

    fun isFavorite(name: String): Boolean {
        val item = DummyData.menu.firstOrNull { it.name.equals(name, ignoreCase = true) }
        return if (item != null) isFavorite(item.id) else false
    }

    fun register(fullName: String, username: String, password: String): String? {
        if (fullName.isBlank() || username.isBlank() || password.isBlank()) {
            return "Semua kolom wajib diisi"
        }
        if (password.length < 6) return "Password minimal 6 karakter"
        if (users.any { it.username.equals(username.trim(), ignoreCase = true) }) {
            return "Username sudah dipakai"
        }
        val user = User(fullName.trim(), username.trim(), password)
        users.add(user)
        currentUser = user
        return null
    }

    fun login(username: String, password: String): String? {
        if (username.isBlank() || password.isBlank()) return "Username dan password wajib diisi"
        val user = users.firstOrNull {
            it.username.equals(username.trim(), ignoreCase = true) && it.password == password
        } ?: return "Username atau password salah"
        currentUser = user
        return null
    }

    fun logout() {
        currentUser = null
        cart.clear()
    }

    fun updateName(newName: String) {
        val current = currentUser ?: return
        val updated = current.copy(fullName = newName.trim())
        val index = users.indexOf(current)
        if (index >= 0) users[index] = updated
        currentUser = updated
    }

    fun addToCart(menuItemId: Int, size: String, quantity: Int, unitPrice: Int) {
        val existing = cart.find { it.menuItemId == menuItemId && it.size == size }
        if (existing != null) {
            val index = cart.indexOf(existing)
            cart[index] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            cart.add(CartItem(nextCartId++, menuItemId, size, quantity, unitPrice))
        }
    }

    fun updateCartQuantity(cartId: Int, newQuantity: Int) {
        val index = cart.indexOfFirst { it.id == cartId }
        if (index != -1) {
            if (newQuantity <= 0) {
                cart.removeAt(index)
            } else {
                cart[index] = cart[index].copy(quantity = newQuantity)
            }
        }
    }

    fun placeOrder(receiver: String, address: String) {
        if (cart.isEmpty()) return
        val orderItems = cart.mapNotNull { cartItem ->
            DummyData.byId(cartItem.menuItemId)?.let { item ->
                OrderItem(
                    name = item.name,
                    size = cartItem.size,
                    quantity = cartItem.quantity,
                    unitPrice = cartItem.unitPrice,
                    imageRes = item.imageRes
                )
            }
        }
        lastOrder = Order(
            receiver = receiver.trim(),
            address = address.trim(),
            itemCount = cartCount,
            total = cartSubtotal + DELIVERY_FEE,
            items = orderItems
        )
        cart.clear()
    }
}
