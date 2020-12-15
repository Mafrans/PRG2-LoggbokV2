package me.mafrans.loggbok.v2

import com.mongodb.client.MongoClients
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.query.experimental.filters.Filter
import dev.morphia.query.experimental.filters.Filters
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

    fun getAuthors(): List<Author> {
        return datastore.find(Author::class.java).iterator().toList() as List<Author>
    }

    fun findAuthor(name: String): Author? {
        val authors = datastore.find(Author::class.java).filter(Filters.gte("name", name));
        if(authors.count() == 0L) return null
        return authors.first();
    }
}