package me.mafrans.loggbok.v2

import com.mongodb.client.MongoClients
import dev.morphia.Datastore
import dev.morphia.Morphia
import me.mafrans.loggbok.v2.models.Author
import me.mafrans.loggbok.v2.models.LogEntry

class MVCModel {
    val datastore: Datastore

    init {
        datastore = Morphia.createDatastore(MongoClients.create(), "logbook")
        datastore.mapper.mapPackage("me.mafrans.loggbok.v2.models")
        datastore.ensureIndexes()
    }

    fun addAuthor(author: Author) {
        datastore.save(author)
    }

    fun addEntry(entry: LogEntry) {
        datastore.save(entry)
    }

    fun getEntries(): List<LogEntry> {
        return datastore.find(LogEntry::class.java).iterator().toList() as List<LogEntry>
    }
}