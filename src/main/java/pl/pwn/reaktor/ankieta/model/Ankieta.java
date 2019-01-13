package pl.pwn.reaktor.ankieta.model;

import javax.persistence.*;

@Entity
@Table
public class Ankieta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String mail;
    private String phone;
    private Boolean java;
    private Boolean python;
    private Boolean other;
    @Column(name = "other_desc")
    private String otherDesc;
    private String language;
    private String course;

    // dla zainteresowanych - lombok

    public Ankieta() {
    }

    public Ankieta(String name, String lastName, String mail, String phone, Boolean java, Boolean python, Boolean other, String otherDesc, String language, String course) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.phone = phone;
        this.java = java;
        this.python = python;
        this.other = other;
        this.otherDesc = otherDesc;
        this.language = language;
        this.course = course;
    }

    public Ankieta(long id, String name, String lastName, String mail, String phone, Boolean java, Boolean python, Boolean other, String otherDesc, String language,
                   String course) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.phone = phone;
        this.java = java;
        this.python = python;
        this.other = other;
        this.otherDesc = otherDesc;
        this.language = language;
        this.course = course;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getJava() {
        return java;
    }

    public void setJava(Boolean java) {
        this.java = java;
    }

    public Boolean getPython() {
        return python;
    }

    public void setPython(Boolean python) {
        this.python = python;
    }

    public Boolean getOther() {
        return other;
    }

    public void setOther(Boolean other) {
        this.other = other;
    }

    public String getOtherDesc() {
        return otherDesc;
    }

    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Ankieta{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", lastName='" + lastName + '\'' +
               ", mail='" + mail + '\'' +
               ", phone='" + phone + '\'' +
               ", java=" + java +
               ", python=" + python +
               ", other=" + other +
               ", otherDesc='" + otherDesc + '\'' +
               ", language='" + language + '\'' +
               ", course='" + course + '\'' +
               '}';
    }
}
