package me.jinseong.springtestdemo.domain;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class Study {
    private int limitCount;
    private String name;
    private LocalDateTime openedDateTime;
    @ManyToOne
    private Member owner;

    public Study(int limitCount, String name) {
        this.limitCount = limitCount;
        this.name = name;
    }

    public Study(int limitCount) {
        if (limitCount < 0) {
            throw new IllegalArgumentException("limitCount 는 0보다 커야 합니다.");
        }
        this.limitCount = limitCount;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }
}
