package org.example.linkshorter.entity;

import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "clicks")
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "click_date")
    private LocalDateTime clickDate;

    @Column(name = "client_Ip")
    private String clientIp;

    @ManyToOne
    @JoinColumn(name = "short_link_id", referencedColumnName = "id")
    private ShortLink shortLink;

    public Click(LocalDateTime clickDate, String clientIp, ShortLink shortLink) {
        this.clickDate = clickDate;
        this.clientIp = clientIp;
        this.shortLink = shortLink;
    }

    public Click() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getClickDate() {
        return clickDate;
    }

    public void setClickDate(LocalDateTime clickDate) {
        this.clickDate = clickDate;
    }

    public ShortLink getShortLink() {
        return shortLink;
    }

    public void setShortLink(ShortLink shortLink) {
        this.shortLink = shortLink;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Click click = (Click) o;
        return getId() != null && Objects.equals(getId(), click.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
