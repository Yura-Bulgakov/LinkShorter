package org.example.linkshorter.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "clicks")
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "click_date")
    private Timestamp clickDate;
    @ManyToOne
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    private ShortLink shortLink;

    public Click(Integer id, Timestamp clickDate, ShortLink shortLink) {
        this.id = id;
        this.clickDate = clickDate;
        this.shortLink = shortLink;
    }

    public Click() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getClickDate() {
        return clickDate;
    }

    public void setClickDate(Timestamp clickDate) {
        this.clickDate = clickDate;
    }

    public ShortLink getShortLink() {
        return shortLink;
    }

    public void setShortLink(ShortLink shortLink) {
        this.shortLink = shortLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Click click = (Click) o;
        return Objects.equals(id, click.id) && Objects.equals(clickDate, click.clickDate) && Objects.equals(shortLink, click.shortLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clickDate, shortLink);
    }
}