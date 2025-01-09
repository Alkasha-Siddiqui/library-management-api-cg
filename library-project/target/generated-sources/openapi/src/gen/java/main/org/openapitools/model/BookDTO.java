package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * BookDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T16:32:45.357940300+05:30[Asia/Calcutta]", comments = "Generator version: 7.9.0")
public class BookDTO {

  private Long id;

  private String title;

  private String author;

  private String isbn;

  private Integer publishYear;

  @Valid
  private List<Long> genreIds = new ArrayList<>();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime addedDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedDate;

  public BookDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BookDTO(String title, String author, String isbn) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
  }

  public BookDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BookDTO title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   */
  @NotNull @Size(max = 200) 
  @Schema(name = "title", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BookDTO author(String author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   */
  @NotNull @Size(max = 100) 
  @Schema(name = "author", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("author")
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public BookDTO isbn(String isbn) {
    this.isbn = isbn;
    return this;
  }

  /**
   * Get isbn
   * @return isbn
   */
  @NotNull @Pattern(regexp = "^[0-9-]{13,17}$") 
  @Schema(name = "isbn", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("isbn")
  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public BookDTO publishYear(Integer publishYear) {
    this.publishYear = publishYear;
    return this;
  }

  /**
   * Get publishYear
   * minimum: 1000
   * maximum: 9999
   * @return publishYear
   */
  @Min(1000) @Max(9999) 
  @Schema(name = "publishYear", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("publishYear")
  public Integer getPublishYear() {
    return publishYear;
  }

  public void setPublishYear(Integer publishYear) {
    this.publishYear = publishYear;
  }

  public BookDTO genreIds(List<Long> genreIds) {
    this.genreIds = genreIds;
    return this;
  }

  public BookDTO addGenreIdsItem(Long genreIdsItem) {
    if (this.genreIds == null) {
      this.genreIds = new ArrayList<>();
    }
    this.genreIds.add(genreIdsItem);
    return this;
  }

  /**
   * Get genreIds
   * @return genreIds
   */
  
  @Schema(name = "genreIds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("genreIds")
  public List<Long> getGenreIds() {
    return genreIds;
  }

  public void setGenreIds(List<Long> genreIds) {
    this.genreIds = genreIds;
  }

  public BookDTO addedDate(OffsetDateTime addedDate) {
    this.addedDate = addedDate;
    return this;
  }

  /**
   * Get addedDate
   * @return addedDate
   */
  @Valid 
  @Schema(name = "addedDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("addedDate")
  public OffsetDateTime getAddedDate() {
    return addedDate;
  }

  public void setAddedDate(OffsetDateTime addedDate) {
    this.addedDate = addedDate;
  }

  public BookDTO updatedDate(OffsetDateTime updatedDate) {
    this.updatedDate = updatedDate;
    return this;
  }

  /**
   * Get updatedDate
   * @return updatedDate
   */
  @Valid 
  @Schema(name = "updatedDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedDate")
  public OffsetDateTime getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(OffsetDateTime updatedDate) {
    this.updatedDate = updatedDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookDTO bookDTO = (BookDTO) o;
    return Objects.equals(this.id, bookDTO.id) &&
        Objects.equals(this.title, bookDTO.title) &&
        Objects.equals(this.author, bookDTO.author) &&
        Objects.equals(this.isbn, bookDTO.isbn) &&
        Objects.equals(this.publishYear, bookDTO.publishYear) &&
        Objects.equals(this.genreIds, bookDTO.genreIds) &&
        Objects.equals(this.addedDate, bookDTO.addedDate) &&
        Objects.equals(this.updatedDate, bookDTO.updatedDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, isbn, publishYear, genreIds, addedDate, updatedDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    isbn: ").append(toIndentedString(isbn)).append("\n");
    sb.append("    publishYear: ").append(toIndentedString(publishYear)).append("\n");
    sb.append("    genreIds: ").append(toIndentedString(genreIds)).append("\n");
    sb.append("    addedDate: ").append(toIndentedString(addedDate)).append("\n");
    sb.append("    updatedDate: ").append(toIndentedString(updatedDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

