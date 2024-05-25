package kosmo.notepad;

public class Note
{
    // Attributes
    private String title;
    private String content;

    // Constructors
    public Note() {}

    public Note(String title, String content)
    {
        this.title = title;
        this.content = content;
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
