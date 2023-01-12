package ru.netology.model;

public class Post {
    private long id;
    private String content;
    private boolean isDeleted = false;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public PostDTO postToPostDTO() {
        PostDTO postDTO = new PostDTO();
        postDTO.setContent(this.content);
        return postDTO;
    }
}