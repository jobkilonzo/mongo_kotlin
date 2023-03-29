import com.mongodb.*
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCursor
import com.mongodb.client.model.Filters.eq
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import modules.Accounts
import modules.Customers
import org.bson.Document
import org.bson.conversions.Bson

suspend fun main() {
    var name: String = ""
    var address: String = ""
    var birthdate: String = ""
    var email: String = ""
    var account: Array<Any?> = arrayOf()



    val uri = "MONGO_URL"
    val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()

    val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .serverApi(serverApi)
        .build()
    val mongoClient: MongoClient = MongoClients.create(settings)

    try {
        val database= mongoClient.getDatabase("sample_analytics")
        val customer = database.getCollection("customers")
        val accounts = database.getCollection("accounts")
        coroutineScope {
            launch {
                val cu = customer.find().forEach {
                    name = it["name"].toString()
                    address = it["address"].toString()
                    birthdate = it["birthdate"].toString()
                    email = it["email"].toString()
                    account = arrayOf(it["accounts"])
                }

                val customers = Customers(
                    name,
                    address,
                    birthdate,
                    email,
                    account)

                for (userAccount in customers.accounts){
                    val acc = accounts.find(eq( userAccount))

                }
                for (i in customers.accounts){
                    println(i)
                }

                //                    for (account in customers.accounts){
//
//                        val acc = accounts.find(eq( account))
//
//                        println(account)
//                    }

            }
        }
        println("Starting")

    }catch (me: MongoException){
        println(me)
    }
}