package models;

public class Step {

    private long id;
    private String title;
    private String text;
    private long number;
    private long tutorialId;
    private String tutorialTitle;

    public Step() {
    }

    public Step(long id, String title, String text, long number, long tutorialId, String tutorialTitle) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.number = number;
        this.tutorialTitle = tutorialTitle;
        this.tutorialId = tutorialId;
    }

    public String getTutorialTitle() {
        return tutorialTitle;
    }

    public void setTutorialTitle(String tutorialTitle) {
        this.tutorialTitle = tutorialTitle;
    }

    public Long getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(Long tutorialId) {
        this.tutorialId = tutorialId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
