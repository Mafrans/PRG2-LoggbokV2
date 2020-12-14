package me.mafrans.loggbok.v2

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

class Database {
    companion object {
        var client: MongoClient? = null
        fun connect(uri: String) {
            client?.close()
            client = MongoClients.create(uri)

            Logs.db = client?.getDatabase("logs")
        }
    }

    class Logs {
        companion object {
            var db: MongoDatabase? = null
        }
    }
}