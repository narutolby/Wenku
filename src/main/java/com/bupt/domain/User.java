package com.bupt.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-21
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USER")
/*@Access(AccessType.PROPERTY)*/
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String userPasswd;
   /* @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_DOWNLOAD_RESOURCE", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> downloadResources = new HashSet<Resource>();*/
    @Column(name="mark", columnDefinition = "int(11) default 200")
    private int userMark = 200;

    public int getUserMark() {
        return userMark;
    }

    public void setUserMark(int userMark) {
        this.userMark = userMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

  /*  public Set<Resource> getDownloadResources() {
        return downloadResources;
    }

    public void setDownloadResources(Set<Resource> downloadResources) {
        this.downloadResources = downloadResources;
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }
}
