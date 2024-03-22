package org.example.linkshorter.entity;

import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "long_links")
public class LongLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "long_link")
    private String longLink;
    @Column(name = "forbidden")
    private boolean forbidden;
    @OneToMany(mappedBy = "longLink")
    private Set<ShortLink> shortLinks = new HashSet<>();

    public LongLink(String longLink) {
        this.longLink = longLink;
    }

    public LongLink() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }

    public boolean isForbidden() {
        return forbidden;
    }

    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }

    public Set<ShortLink> getShortLinks() {
        return shortLinks;
    }

    public void setShortLinks(Set<ShortLink> shortLinks) {
        this.shortLinks = shortLinks;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LongLink longLink = (LongLink) o;
        return getId() != null && Objects.equals(getId(), longLink.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
