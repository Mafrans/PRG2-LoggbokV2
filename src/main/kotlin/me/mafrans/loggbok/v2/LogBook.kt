package me.mafrans.loggbok.v2

import fi.iki.elonen.NanoHTTPD

class LogBook : NanoHTTPD(8080) {

    override fun start() {
        super.start(SOCKET_READ_TIMEOUT, false);
    }

    override fun serve(session: IHTTPSession?): Response {
        return newFixedLengthResponse(
            Response.Status.ACCEPTED,
            MIME_HTML,
            String(javaClass.classLoader.getResourceAsStream("index.html").readBytes())
        );
    }
}

fun main() {
    LogBook();
}