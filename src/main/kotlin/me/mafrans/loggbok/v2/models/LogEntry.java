package me.mafrans.loggbok.v2.models;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

@Entity("entries")
@Indexes(
    @Index(fields = {@Field("title"), @Field("author")})
)
public class LogEntry {
    @Id
    private ObjectId id;

    @Reference
    private Author author;

    private String title;

    private String body;

    public LogEntry() {}
    public LogEntry(Author author, String title, String body) {
        this.author = author;
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "id=" + id +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
