package kosmo.notepad;

public class Note {

    private String title;
    private String content;

    public Note() {
        // Default constructor
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
