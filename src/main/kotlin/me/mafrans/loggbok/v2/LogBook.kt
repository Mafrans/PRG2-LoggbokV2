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

/**
 * MVC Controller class hosting a NanoHTTPD servlet and managing log entries.
 * @see fi.iki.elonen.NanoHTTPD
 */
class LogBook : NanoHTTPD(8080) {
    /**
     * MVC Model class, manages data.
     */
    private val model: MVCModel = MVCModel()

    /**
     * FreeMarker template configuration
     */
    private val templateConfig: Configuration = Configuration(Configuration.VERSION_2_3_30)

    init {
        templateConfig.setClassLoaderForTemplateLoading(javaClass.classLoader, "templates")
        templateConfig.defaultEncoding = "UTF-8"
        templateConfig.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        templateConfig.wrapUncheckedExceptions = true
        templateConfig.fallbackOnNullLoopVariable = false
    }

    /**
     * Overridden NanoHTTPD function serving data to the web browser on request.
     * @param session An IHTTPSession containing relevant session data, such as the request type and query parameters.
     */
    override fun serve(session: IHTTPSession?): Response {
        // Check if session exists
        if(session != null) {
            // If it does, check for any query parameters
            if(session.parms!!.isNotEmpty()) {
                // Get the relevant query parameters
                val authorString = session.parms["author"]
                val titleString = session.parms["title"]
                val bodyString = session.parms["body"]

                if(authorString != null && titleString != null && bodyString != null) {
                    // If there aren't any problems with the title and body, attempt to add the entry to the list
                    if (titleString.isNotEmpty() && bodyString.isNotEmpty()) {
                        // Find a relevant author from the model.
                        var author = model.findAuthor(authorString)

                        // If author doesn't exist, create a new one
                        if (author == null) {
                            author = Author(authorString);
                            model.addAuthor(author)
                        }

                        // Add the entry
                        model.addEntry(LogEntry(author, titleString, bodyString))
                    }
                }
            }
        }

        // Find and parse the index.ftlh page
        val template = templateConfig.getTemplate("index.ftlh")
        val writer = StringWriter()
        template.process(
            mapOf(
                "logEntries" to model.getEntries(),
                "authors" to model.getAuthors()
            ), writer
        )

        // Return the page as a response, rendering it on the client
        return newFixedLengthResponse(
            Response.Status.ACCEPTED,
            MIME_HTML,
            writer.toString()
        );
    }
}

/**
 * Main function, creates and starts the servlet.
 */
fun main() {
    val logBook = LogBook()
    logBook.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
}