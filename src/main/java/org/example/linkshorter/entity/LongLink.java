package org.example.linkshorter.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "long_links")
public class LongLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "long_link")
    private String longLink;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public LongLink(Integer id, String longLink, User user) {
        this.id = id;
        this.longLink = longLink;
        this.user = user;
    }

    public LongLink() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongLink longLink1 = (LongLink) o;
        return Objects.equals(id, longLink1.id) && Objects.equals(longLink, longLink1.longLink) && Objects.equals(user, longLink1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, longLink, user);
    }
}
