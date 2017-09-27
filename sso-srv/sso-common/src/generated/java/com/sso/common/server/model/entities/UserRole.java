package com.sso.common.server.model.entities;
// Generated by Hibernate Tools 5.0.0.Alpha1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * UserRole generated by hbm2java
 */
@Entity
@Table(name="user_role"
    ,schema="public"
    , uniqueConstraints = @UniqueConstraint(columnNames={"user_id", "role_id"}) 
)
public class UserRole  implements java.io.Serializable {


     private Long id;
     private Role role;
     private User user;

    public UserRole() {
    }

    public UserRole(Role role, User user) {
       this.role = role;
       this.user = user;
    }
   
     @SequenceGenerator(name="generator", sequenceName="user_role_seq")@Id @GeneratedValue(strategy=SEQUENCE, generator="generator")

    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="role_id", nullable=false)
    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }




}

