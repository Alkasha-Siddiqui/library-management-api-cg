package com.capgemini.library.management.project.library_management.entity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private OffsetDateTime addedDateTime;

    //@Column(name="updated_date" )
    private OffsetDateTime updatedDateTime;


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
//        this.addedDateTime = addedDateTime;
        this.addedDateTime = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public OffsetDateTime getUpdatedDateTime() {
        return OffsetDateTime.from(updatedDateTime);
    }
    public void setUpdatedDateTime(OffsetDateTime updatedDateTime) {
//        this.updatedDateTime = updatedDateTime;
        this.updatedDateTime = OffsetDateTime.now(ZoneOffset.UTC);
    }


    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title +  ", author=" + author + ", isbn=" + isbn
                + ", publishYear=" + publishYear + ", genreIds=" + genreIds + ", addedDateTime=" + addedDateTime
                + ", updatedDateTime=" + updatedDateTime + "]";
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        return Objects.equals(addedDateTime, other.addedDateTime) && Objects.equals(author, other.author)
                && Objects.equals(id, other.id) && Objects.equals(title, other.title)
                && Objects.equals(genreIds, other.genreIds) && Objects.equals(isbn, other.isbn)
                && Objects.equals(publishYear, other.publishYear) && Objects.equals(updatedDateTime, other.updatedDateTime);
    }
}
