package me.mafrans.loggbok.v2.models;


import dev.morphia.annotations.*;
import lombok.Getter;
import org.bson.types.ObjectId;

@Entity("authors")
@Indexes(
    @Index(fields = {@Field("name")})
)
public class Author {
    @Id
    public ObjectId id;

    @Getter
    public String name;

    public Author() {}

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
