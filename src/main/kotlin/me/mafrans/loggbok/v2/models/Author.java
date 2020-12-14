package me.mafrans.loggbok.v2.models;


import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

@Entity("authors")
@Indexes(
    @Index(fields = {@Field("name")})
)
public class Author {
    @Id
    public ObjectId id;
    public String name;

    public Author() {}

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
