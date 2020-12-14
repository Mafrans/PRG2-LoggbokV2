package me.mafrans.loggbok.v2.models;

import dev.morphia.annotations.*;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Entity("entries")
@Indexes(
    @Index(fields = {@Field("title"), @Field("author")})
)
@ToString
public class LogEntry {
    @Id
    private ObjectId id;

    @Reference
    @Getter
    public Author author;

    @Getter
    public String title;

    @Getter
    public String body;

    public LogEntry() {}
    public LogEntry(Author author, String title, String body) {
        this.author = author;
        this.title = title;
        this.body = body;
    }
}
