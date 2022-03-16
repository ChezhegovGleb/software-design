package chezhegov.application

import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import chezhegov.product.PriceTable
import chezhegov.product.ProductTable
import chezhegov.user.UserTable

fun Application.configureDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/sd_reactive",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "123456"
    )

    transaction {
        SchemaUtils.create(
            ProductTable,
            PriceTable,
            UserTable
        )
    }
}
