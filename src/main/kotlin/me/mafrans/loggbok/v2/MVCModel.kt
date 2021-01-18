package me.mafrans.loggbok.v2

import com.mongodb.client.MongoClients
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.query.experimental.filters.Filter
import dev.morphia.query.experimental.filters.Filters
import me.mafrans.loggbok.v2.models.Author
import me.mafrans.loggbok.v2.models.LogEntry

class MVCModel {
    /**
     * Morphia datastore, creates and synchronizes the database.
     */
    private val datastore = Morphia.createDatastore(MongoClients.create(), "logbook")

    init {
        datastore.mapper.mapPackage("me.mafrans.loggbok.v2.models")
        datastore.ensureIndexes()
    }

    /**
     * Add an author to the database.
     * @param author The author to add to the database.
     */
    fun addAuthor(author: Author) {
        datastore.save(author)
    }

    /**
     * Add an log entry to the database.
     * @param entry The entry to add to the database.
     */
    fun addEntry(entry: LogEntry) {
        datastore.save(entry)
    }

    /**
     * Get all log entries from the database.
     * Not cached.
     * @return A list of all log entries stored in the database.
     */
    fun getEntries(): List<LogEntry> {
        return datastore.find(LogEntry::class.java).iterator().toList() as List<LogEntry>
    }

    /**
     * Get all authors from the database.
     * Not cached.
     * @return A list of all authors stored in the database.
     */
    fun getAuthors(): List<Author> {
        return datastore.find(Author::class.java).iterator().toList() as List<Author>
    }

    /**
     * Find a specific author in the database, searching by name.
     * @param name The name of the author-
     * @return The author, or null if no author was found.
     */
    fun findAuthor(name: String): Author? {
        val authors = datastore.find(Author::class.java).filter(Filters.gte("name", name));
        if(authors.count() == 0L) return null
        return authors.first();
    }
}