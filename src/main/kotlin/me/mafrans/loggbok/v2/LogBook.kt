package me.mafrans.loggbok.v2

import com.mongodb.client.MongoClients
import dev.morphia.Datastore
import dev.morphia.Morphia
import fi.iki.elonen.NanoHTTPD
import me.mafrans.loggbok.v2.models.Author
import me.mafrans.loggbok.v2.models.LogEntry

class LogBook : NanoHTTPD(8080) {
    val datastore: Datastore

    init {
        datastore = Morphia.createDatastore(MongoClients.create(), "logbook")
        datastore.mapper.mapPackage("me.mafrans.loggbok.v2.models")
        datastore.ensureIndexes()
    }

    override fun start() {
        val author = Author("Malte")
        val entry = LogEntry(author, "Test Entry", "This is a Test Entry")

        datastore.save(author)
        datastore.save(entry)

        super.start(SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession?): Response {
        return newFixedLengthResponse(
            Response.Status.ACCEPTED,
            MIME_HTML,
            String(javaClass.classLoader.getResourceAsStream("index.html").readBytes()).replace(
                "<% Replace Me %>",
                datastore.find(LogEntry::class.java).iterator().toList().toString()
            )
        );
    }
}

fun main() {
    val logBook = LogBook()
    logBook.start()
}