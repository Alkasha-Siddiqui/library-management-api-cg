package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * BookSearchRequestDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-09T16:32:45.357940300+05:30[Asia/Calcutta]", comments = "Generator version: 7.9.0")
public class BookSearchRequestDTO {

  private String searchTerm;

  private Long genreId;

  private String author;

  private Integer publishYearFrom;

  private Integer publishYearTo;

  private Boolean availability;

  /**
   * Gets or Sets sortBy
   */
  public enum SortByEnum {
    TITLE("title"),
    
    AUTHOR("author"),
    
    PUBLISH_YEAR("publishYear");

    private String value;

    SortByEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static SortByEnum fromValue(String value) {
      for (SortByEnum b : SortByEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private SortByEnum sortBy;

  /**
   * Gets or Sets sortDirection
   */
  public enum SortDirectionEnum {
    ASC("asc"),
    
    DESC("desc");

    private String value;

    SortDirectionEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static SortDirectionEnum fromValue(String value) {
      for (SortDirectionEnum b : SortDirectionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private SortDirectionEnum sortDirection;

  private Integer page = 0;

  private Integer size = 20;

  public BookSearchRequestDTO searchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
    return this;
  }

  /**
   * Get searchTerm
   * @return searchTerm
   */
  
  @Schema(name = "searchTerm", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("searchTerm")
  public String getSearchTerm() {
    return searchTerm;
  }

  public void setSearchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  public BookSearchRequestDTO genreId(Long genreId) {
    this.genreId = genreId;
    return this;
  }

  /**
   * Get genreId
   * @return genreId
   */
  
  @Schema(name = "genreId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("genreId")
  public Long getGenreId() {
    return genreId;
  }

  public void setGenreId(Long genreId) {
    this.genreId = genreId;
  }

  public BookSearchRequestDTO author(String author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   */
  
  @Schema(name = "author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public BookSearchRequestDTO publishYearFrom(Integer publishYearFrom) {
    this.publishYearFrom = publishYearFrom;
    return this;
  }

  /**
   * Get publishYearFrom
   * @return publishYearFrom
   */
  
  @Schema(name = "publishYearFrom", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("publishYearFrom")
  public Integer getPublishYearFrom() {
    return publishYearFrom;
  }

  public void setPublishYearFrom(Integer publishYearFrom) {
    this.publishYearFrom = publishYearFrom;
  }

  public BookSearchRequestDTO publishYearTo(Integer publishYearTo) {
    this.publishYearTo = publishYearTo;
    return this;
  }

  /**
   * Get publishYearTo
   * @return publishYearTo
   */
  
  @Schema(name = "publishYearTo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("publishYearTo")
  public Integer getPublishYearTo() {
    return publishYearTo;
  }

  public void setPublishYearTo(Integer publishYearTo) {
    this.publishYearTo = publishYearTo;
  }

  public BookSearchRequestDTO availability(Boolean availability) {
    this.availability = availability;
    return this;
  }

  /**
   * Get availability
   * @return availability
   */
  
  @Schema(name = "availability", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("availability")
  public Boolean getAvailability() {
    return availability;
  }

  public void setAvailability(Boolean availability) {
    this.availability = availability;
  }

  public BookSearchRequestDTO sortBy(SortByEnum sortBy) {
    this.sortBy = sortBy;
    return this;
  }

  /**
   * Get sortBy
   * @return sortBy
   */
  
  @Schema(name = "sortBy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sortBy")
  public SortByEnum getSortBy() {
    return sortBy;
  }

  public void setSortBy(SortByEnum sortBy) {
    this.sortBy = sortBy;
  }

  public BookSearchRequestDTO sortDirection(SortDirectionEnum sortDirection) {
    this.sortDirection = sortDirection;
    return this;
  }

  /**
   * Get sortDirection
   * @return sortDirection
   */
  
  @Schema(name = "sortDirection", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sortDirection")
  public SortDirectionEnum getSortDirection() {
    return sortDirection;
  }

  public void setSortDirection(SortDirectionEnum sortDirection) {
    this.sortDirection = sortDirection;
  }

  public BookSearchRequestDTO page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   * minimum: 0
   * @return page
   */
  @Min(0) 
  @Schema(name = "page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public BookSearchRequestDTO size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * minimum: 1
   * maximum: 100
   * @return size
   */
  @Min(1) @Max(100) 
  @Schema(name = "size", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookSearchRequestDTO bookSearchRequestDTO = (BookSearchRequestDTO) o;
    return Objects.equals(this.searchTerm, bookSearchRequestDTO.searchTerm) &&
        Objects.equals(this.genreId, bookSearchRequestDTO.genreId) &&
        Objects.equals(this.author, bookSearchRequestDTO.author) &&
        Objects.equals(this.publishYearFrom, bookSearchRequestDTO.publishYearFrom) &&
        Objects.equals(this.publishYearTo, bookSearchRequestDTO.publishYearTo) &&
        Objects.equals(this.availability, bookSearchRequestDTO.availability) &&
        Objects.equals(this.sortBy, bookSearchRequestDTO.sortBy) &&
        Objects.equals(this.sortDirection, bookSearchRequestDTO.sortDirection) &&
        Objects.equals(this.page, bookSearchRequestDTO.page) &&
        Objects.equals(this.size, bookSearchRequestDTO.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(searchTerm, genreId, author, publishYearFrom, publishYearTo, availability, sortBy, sortDirection, page, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookSearchRequestDTO {\n");
    sb.append("    searchTerm: ").append(toIndentedString(searchTerm)).append("\n");
    sb.append("    genreId: ").append(toIndentedString(genreId)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    publishYearFrom: ").append(toIndentedString(publishYearFrom)).append("\n");
    sb.append("    publishYearTo: ").append(toIndentedString(publishYearTo)).append("\n");
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
    sb.append("    sortBy: ").append(toIndentedString(sortBy)).append("\n");
    sb.append("    sortDirection: ").append(toIndentedString(sortDirection)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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

