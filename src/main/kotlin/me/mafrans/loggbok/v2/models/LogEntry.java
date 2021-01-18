package me.mafrans.loggbok.v2.models;

import dev.morphia.annotations.*;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;

/**
 * Serializable class describing a log entry, this entry is stored in a database and rendered by the application.
 */
@Entity("entries")
@Indexes(
    @Index(fields = {@Field("title"), @Field("author")})
)
@ToString
public class LogEntry {
    @Id
    private ObjectId id;

    /**
     * The log entry's author, referenced from {@link Author}
     * @see Author
     */
    @Reference
    @Getter
    public Author author;

    /**
     * The log entry's title.
     */
    @Getter
    public String title;

    /**
     * The main text body of the log entry.
     */
    @Getter
    public String body;

    public LogEntry() {}
    public LogEntry(Author author, String title, String body) {
        this.author = author;
        this.title = title;
        this.body = body;
    }
}
