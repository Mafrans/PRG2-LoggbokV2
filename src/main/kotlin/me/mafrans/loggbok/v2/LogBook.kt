package me.mafrans.loggbok.v2

import com.mongodb.client.MongoClients
import dev.morphia.Datastore
import dev.morphia.Morphia
import fi.iki.elonen.NanoHTTPD
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler
import me.mafrans.loggbok.v2.models.Author
import me.mafrans.loggbok.v2.models.LogEntry
import java.io.OutputStreamWriter
import java.io.StringWriter

class LogBook : NanoHTTPD(8080) {
    val datastore: Datastore
    val templateConfig: Configuration

    init {
        datastore = Morphia.createDatastore(MongoClients.create(), "logbook")
        datastore.mapper.mapPackage("me.mafrans.loggbok.v2.models")
        datastore.ensureIndexes()

        templateConfig = Configuration(Configuration.VERSION_2_3_30)
        templateConfig.setClassLoaderForTemplateLoading(javaClass.classLoader, "templates")
        templateConfig.defaultEncoding = "UTF-8"
        templateConfig.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        templateConfig.wrapUncheckedExceptions = true
        templateConfig.fallbackOnNullLoopVariable = false
    }

    override fun start() {
        val author = Author("Malte")
        val entry = LogEntry(author, "Test Entry", "This is a Test Entry")

        datastore.save(author)
        datastore.save(entry)

        super.start(SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession?): Response {
        val template = templateConfig.getTemplate("index.ftlh")
        var writer = StringWriter()
        template.process(mapOf("logEntries" to datastore.find(LogEntry::class.java).iterator().toList() as List<LogEntry>, "test" to Test("test title")), writer)

        return newFixedLengthResponse(
            Response.Status.ACCEPTED,
            MIME_HTML,
            writer.buffer.toString()
        );
    }
}

fun main() {
    val logBook = LogBook()
    logBook.start()
}