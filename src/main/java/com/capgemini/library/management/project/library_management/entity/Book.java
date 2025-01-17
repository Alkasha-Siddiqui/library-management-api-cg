package com.capgemini.library.management.project.library_management.entity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    //@Column(name="book_title")
    private String title;

    //@Column(name="author")
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    //@Column(name="publish_year")
    private Integer publishYear;

    //@Column(name="genre_ids")
    private List<Long> genreIds;

    //@Column(name="added_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime addedDateTime = OffsetDateTime.now(ZoneOffset.UTC);


    //@Column(name="updated_date" )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime updatedDateTime = OffsetDateTime.now(ZoneOffset.UTC);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public OffsetDateTime getAddedDateTime() {
        return OffsetDateTime.from(addedDateTime);
    }
    public void setAddedDateTime(OffsetDateTime addedDateTime) {
        this.addedDateTime = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public OffsetDateTime getUpdatedDateTime() {
        return OffsetDateTime.from(updatedDateTime);
    }
    public void setUpdatedDateTime(OffsetDateTime updatedDateTime) {
        this.updatedDateTime = OffsetDateTime.now(ZoneOffset.UTC);
    }

}
