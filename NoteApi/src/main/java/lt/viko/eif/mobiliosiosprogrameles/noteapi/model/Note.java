package lt.viko.eif.mobiliosiosprogrameles.noteapi.model;

public class Note {

    private int id;
    private Category category;
    private String title;
    private String text;
    private long date;

    public Note() {}
    public Note(int id, Category category, String title, String text, long date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
}
