package com.labeltel.site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Project.
 */
@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @Size(max = 5000)        
    @Column(name = "name", length = 5000)
    private String name;

    @Size(max = 5000)        
    @Column(name = "description", length = 5000)
    private String description;
    
    @Column(name = "image")
    private String image;

    @Size(max = 50000)        
    @Column(name = "html", length = 50000)
    private String html;

    @ManyToMany
    @JoinTable(name = "PROJECT_TEAM",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="teams_id", referencedColumnName="ID"))
    private Set<User> teams = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Set<User> getTeams() {
        return teams;
    }

    public void setTeams(Set<User> users) {
        this.teams = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Project project = (Project) o;

        if ( ! Objects.equals(id, project.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", image='" + image + "'" +
                ", html='" + html + "'" +
                '}';
    }
}
