package com.capgemini.library.management.project.library_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @Size(max = 50)
    private String name;

    @Column(name = "description", length = 200)
    @Size(max = 200)
    private String description;

    @ManyToMany(mappedBy = "genres", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Book> books = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 50) String getName() {
        return name;
    }

    public void setName(@Size(max = 50) String name) {
        this.name = name;
    }

    public @Size(max = 200) String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 200) String description) {
        this.description = description;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}