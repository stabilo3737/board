package com.kh.demo.web.req.comment;

public class ReqUpdateComment {

    private String contents;

    // Constructors
    public ReqUpdateComment() {
    }

    public ReqUpdateComment(String contents) {
        this.contents = contents;
    }

    // Getter and Setter
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    // toString() method for debugging purposes
    @Override
    public String toString() {
        return "ReqUpdateComment{" +
                "contents='" + contents + '\'' +
                '}';
    }
}
