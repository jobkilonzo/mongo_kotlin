package modules

data class Customers(
    val name: String,
    val address: String,
    val birthdate: String,
    val email: String,
    val accounts: Array<Any?>
)