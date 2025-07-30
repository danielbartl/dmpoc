package dev.jbaby.dmpoc.target;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customers")
@Data
public class CustomerDocument {

    @Id
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String street;

    private String city;

    private String state;

    private String country;

    private String zip;

    private String notes;
}