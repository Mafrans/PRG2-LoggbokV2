package me.mafrans.loggbok.v2.models;


import dev.morphia.annotations.*;
import lombok.Getter;
import org.bson.types.ObjectId;

/**
 * Serializable class describing a generic author, with an object id and a name.
 *
 * @author malte
 * @version $Id: $Id
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

    /**
     * <p>Constructor for Author.</p>
     */
    public Author() {}

    /**
     * <p>Constructor for Author.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public Author(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }
}
