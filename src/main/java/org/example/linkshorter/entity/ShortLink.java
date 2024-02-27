package org.example.linkshorter.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "short_links")
public class ShortLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;
    @Column(name = "expiration_date")
    private Timestamp expirationDate;
    @ManyToOne
    @JoinColumn(name = "long_link_id", referencedColumnName = "id", nullable = false)
    private LongLink longLink;

    public ShortLink(Long id, String token, Timestamp creationDate, Timestamp expirationDate, LongLink longLink) {
        this.id = id;
        this.token = token;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.longLink = longLink;
    }

    public ShortLink() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LongLink getLongLink() {
        return longLink;
    }

    public void setLongLink(LongLink longLink) {
        this.longLink = longLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortLink shortLink = (ShortLink) o;
        return Objects.equals(id, shortLink.id) && Objects.equals(token, shortLink.token) && Objects.equals(creationDate, shortLink.creationDate) && Objects.equals(expirationDate, shortLink.expirationDate) && Objects.equals(longLink, shortLink.longLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, creationDate, expirationDate, longLink);
    }
}
