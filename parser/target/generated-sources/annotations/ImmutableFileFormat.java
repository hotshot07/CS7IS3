//-no-import-rewrite

import java.lang.Object;
import java.lang.String;
import java.lang.Float;
import java.lang.Double;

/**
 * Immutable implementation of {@link FileFormat}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableFileFormat.builder()}.
 */
@org.immutables.value.Generated(from = "FileFormat", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableFileFormat extends FileFormat {
  private final int Index;
  private final java.lang.String Title;
  private final java.lang.String Author;
  private final java.lang.String Bibliography;
  private final java.lang.String Words;

  private ImmutableFileFormat(
      int Index,
      java.lang.String Title,
      java.lang.String Author,
      java.lang.String Bibliography,
      java.lang.String Words) {
    this.Index = Index;
    this.Title = Title;
    this.Author = Author;
    this.Bibliography = Bibliography;
    this.Words = Words;
  }

  /**
   * @return The value of the {@code Index} attribute
   */
  @Override
  public int Index() {
    return Index;
  }

  /**
   * @return The value of the {@code Title} attribute
   */
  @Override
  public java.lang.String Title() {
    return Title;
  }

  /**
   * @return The value of the {@code Author} attribute
   */
  @Override
  public java.lang.String Author() {
    return Author;
  }

  /**
   * @return The value of the {@code Bibliography} attribute
   */
  @Override
  public java.lang.String Bibliography() {
    return Bibliography;
  }

  /**
   * @return The value of the {@code Words} attribute
   */
  @Override
  public java.lang.String Words() {
    return Words;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FileFormat#Index() Index} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for Index
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFileFormat withIndex(int value) {
    if (this.Index == value) return this;
    return new ImmutableFileFormat(value, this.Title, this.Author, this.Bibliography, this.Words);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FileFormat#Title() Title} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for Title
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFileFormat withTitle(java.lang.String value) {
    java.lang.String newValue = java.util.Objects.requireNonNull(value, "Title");
    if (this.Title.equals(newValue)) return this;
    return new ImmutableFileFormat(this.Index, newValue, this.Author, this.Bibliography, this.Words);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FileFormat#Author() Author} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for Author
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFileFormat withAuthor(java.lang.String value) {
    java.lang.String newValue = java.util.Objects.requireNonNull(value, "Author");
    if (this.Author.equals(newValue)) return this;
    return new ImmutableFileFormat(this.Index, this.Title, newValue, this.Bibliography, this.Words);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FileFormat#Bibliography() Bibliography} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for Bibliography
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFileFormat withBibliography(java.lang.String value) {
    java.lang.String newValue = java.util.Objects.requireNonNull(value, "Bibliography");
    if (this.Bibliography.equals(newValue)) return this;
    return new ImmutableFileFormat(this.Index, this.Title, this.Author, newValue, this.Words);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FileFormat#Words() Words} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for Words
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFileFormat withWords(java.lang.String value) {
    java.lang.String newValue = java.util.Objects.requireNonNull(value, "Words");
    if (this.Words.equals(newValue)) return this;
    return new ImmutableFileFormat(this.Index, this.Title, this.Author, this.Bibliography, newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableFileFormat} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableFileFormat
        && equalTo((ImmutableFileFormat) another);
  }

  private boolean equalTo(ImmutableFileFormat another) {
    return Index == another.Index
        && Title.equals(another.Title)
        && Author.equals(another.Author)
        && Bibliography.equals(another.Bibliography)
        && Words.equals(another.Words);
  }

  /**
   * Computes a hash code from attributes: {@code Index}, {@code Title}, {@code Author}, {@code Bibliography}, {@code Words}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Index;
    h += (h << 5) + Title.hashCode();
    h += (h << 5) + Author.hashCode();
    h += (h << 5) + Bibliography.hashCode();
    h += (h << 5) + Words.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code FileFormat} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "FileFormat{"
        + "Index=" + Index
        + ", Title=" + Title
        + ", Author=" + Author
        + ", Bibliography=" + Bibliography
        + ", Words=" + Words
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link FileFormat} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable FileFormat instance
   */
  public static ImmutableFileFormat copyOf(FileFormat instance) {
    if (instance instanceof ImmutableFileFormat) {
      return (ImmutableFileFormat) instance;
    }
    return ImmutableFileFormat.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableFileFormat ImmutableFileFormat}.
   * <pre>
   * ImmutableFileFormat.builder()
   *    .Index(int) // required {@link FileFormat#Index() Index}
   *    .Title(String) // required {@link FileFormat#Title() Title}
   *    .Author(String) // required {@link FileFormat#Author() Author}
   *    .Bibliography(String) // required {@link FileFormat#Bibliography() Bibliography}
   *    .Words(String) // required {@link FileFormat#Words() Words}
   *    .build();
   * </pre>
   * @return A new ImmutableFileFormat builder
   */
  public static ImmutableFileFormat.Builder builder() {
    return new ImmutableFileFormat.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableFileFormat ImmutableFileFormat}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @org.immutables.value.Generated(from = "FileFormat", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_INDEX = 0x1L;
    private static final long INIT_BIT_TITLE = 0x2L;
    private static final long INIT_BIT_AUTHOR = 0x4L;
    private static final long INIT_BIT_BIBLIOGRAPHY = 0x8L;
    private static final long INIT_BIT_WORDS = 0x10L;
    private long initBits = 0x1fL;

    private int Index;
    private java.lang.String Title;
    private java.lang.String Author;
    private java.lang.String Bibliography;
    private java.lang.String Words;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code FileFormat} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(FileFormat instance) {
      java.util.Objects.requireNonNull(instance, "instance");
      Index(instance.Index());
      Title(instance.Title());
      Author(instance.Author());
      Bibliography(instance.Bibliography());
      Words(instance.Words());
      return this;
    }

    /**
     * Initializes the value for the {@link FileFormat#Index() Index} attribute.
     * @param Index The value for Index 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder Index(int Index) {
      this.Index = Index;
      initBits &= ~INIT_BIT_INDEX;
      return this;
    }

    /**
     * Initializes the value for the {@link FileFormat#Title() Title} attribute.
     * @param Title The value for Title 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder Title(java.lang.String Title) {
      this.Title = java.util.Objects.requireNonNull(Title, "Title");
      initBits &= ~INIT_BIT_TITLE;
      return this;
    }

    /**
     * Initializes the value for the {@link FileFormat#Author() Author} attribute.
     * @param Author The value for Author 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder Author(java.lang.String Author) {
      this.Author = java.util.Objects.requireNonNull(Author, "Author");
      initBits &= ~INIT_BIT_AUTHOR;
      return this;
    }

    /**
     * Initializes the value for the {@link FileFormat#Bibliography() Bibliography} attribute.
     * @param Bibliography The value for Bibliography 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder Bibliography(java.lang.String Bibliography) {
      this.Bibliography = java.util.Objects.requireNonNull(Bibliography, "Bibliography");
      initBits &= ~INIT_BIT_BIBLIOGRAPHY;
      return this;
    }

    /**
     * Initializes the value for the {@link FileFormat#Words() Words} attribute.
     * @param Words The value for Words 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder Words(java.lang.String Words) {
      this.Words = java.util.Objects.requireNonNull(Words, "Words");
      initBits &= ~INIT_BIT_WORDS;
      return this;
    }

    /**
     * Builds a new {@link ImmutableFileFormat ImmutableFileFormat}.
     * @return An immutable instance of FileFormat
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableFileFormat build() {
      if (initBits != 0) {
        throw new java.lang.IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableFileFormat(Index, Title, Author, Bibliography, Words);
    }

    private String formatRequiredAttributesMessage() {
      java.util.List<String> attributes = new java.util.ArrayList<>();
      if ((initBits & INIT_BIT_INDEX) != 0) attributes.add("Index");
      if ((initBits & INIT_BIT_TITLE) != 0) attributes.add("Title");
      if ((initBits & INIT_BIT_AUTHOR) != 0) attributes.add("Author");
      if ((initBits & INIT_BIT_BIBLIOGRAPHY) != 0) attributes.add("Bibliography");
      if ((initBits & INIT_BIT_WORDS) != 0) attributes.add("Words");
      return "Cannot build FileFormat, some of required attributes are not set " + attributes;
    }
  }
}
