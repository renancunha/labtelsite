package com.labeltel.site.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Content.
 */
@Entity
@Table(name = "CONTENT")
public class Content implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "html")
    private String html;
    
    @Column(name = "label")
    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Content content = (Content) o;

        if ( ! Objects.equals(id, content.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", html='" + html + "'" +
                ", label='" + label + "'" +
                '}';
    }
}
