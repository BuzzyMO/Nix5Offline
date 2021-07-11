package org.example.location.model;

public class Problem {
    private int id;
    private int fromId;
    private int toId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int from_id) {
        this.fromId = from_id;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int to_id) {
        this.toId = to_id;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                '}';
    }
}
