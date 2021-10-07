public class FileFormat {
  private final int Index;
  private final String Title;
  private final String Author;
  private final String Bibliography;
  private final String Words;

  FileFormat(int Index, String Title, String Author, String Bibliography, String Words) {
    this.Index = Index;
    this.Title = Title;
    this.Author = Author;
    this.Bibliography = Bibliography;
    this.Words = Words;
  }

  public int getIndex() {
    return Index;
  }

  public String getAuthor() {
    return Author;
  }

  public String getBibliography() {
    return Bibliography;
  }

  public String getTitle() {
    return Title;
  }

  public String getWords() {
    return Words;
  }
}
