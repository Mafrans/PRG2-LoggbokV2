package me.mafrans.loggbok.v2.models;


import dev.morphia.annotations.*;
import lombok.Getter;
import org.bson.types.ObjectId;

/**
 * Serializable class describing a generic author, with an object id and a name.
 */
@Entity("authors")
@Indexes(
    @Index(fields = {@Field("name")})
)
public class Author {
    @Id
    public ObjectId id;

    /**
     * The author's name
     */
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
