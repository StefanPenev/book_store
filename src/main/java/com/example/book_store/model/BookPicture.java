package com.example.book_store.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pictures")
public class BookPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    public BookPicture(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "books_id")
    private Book book;
}
