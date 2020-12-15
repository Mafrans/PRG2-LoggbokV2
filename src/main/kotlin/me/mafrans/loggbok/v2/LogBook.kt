package me.mafrans.loggbok.v2

import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.ResponseException
import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import me.mafrans.loggbok.v2.models.Author
import me.mafrans.loggbok.v2.models.LogEntry
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.StringWriter
import java.nio.charset.Charset
import java.util.*


class LogBook : NanoHTTPD(8080) {
    val model: MVCModel = MVCModel()
    val templateConfig: Configuration

    init {
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

        super.start(SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession?): Response {
        if(session != null) {
            if(session.parms!!.isNotEmpty()) {
                val authorString = session.parms["author"]
                val titleString = session.parms["title"]
                val bodyString = session.parms["body"]

                if(authorString != null && titleString != null && bodyString != null) {
                    println(authorString)
                    var author = model.findAuthor(authorString)

                    if (author == null) {
                        author = Author(authorString);
                        model.addAuthor(author)
                    }

                    if (titleString.isNotEmpty() && bodyString.isNotEmpty()) {
                        model.addEntry(LogEntry(author, titleString, bodyString))
                    }
                }
            }
        }

        val template = templateConfig.getTemplate("index.ftlh")
        var writer = StringWriter()
        template.process(
            mapOf(
                "logEntries" to model.getEntries(),
                "authors" to model.getAuthors()
            ), writer
        )

        return newFixedLengthResponse(
            Response.Status.ACCEPTED,
            MIME_HTML,
            writer.toString()
        );
    }
}

fun main() {
    val logBook = LogBook()
    logBook.start()
}