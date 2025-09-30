package day9;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // doplneny import

@JsonIgnoreProperties(ignoreUnknown=true) // doplneny import

public class Student {

    //variables
    String name;
    String location;
    String phone;
    String[] courses;

    //constructors

    public Student() { } // ← dôležité pre deserializáciu

    public Student(String name, String location, String phone, String[] courses) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.courses = courses;
    }

    //getters & setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String[] getCourses() { return courses; }
    public void setCourses(String[] courses) { this.courses = courses; }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(location).append(" ").append(phone).append(" ");
        if (courses != null) {
            for (String course : courses) {
                sb.append(course).append(" ");
            }
        }
        return sb.toString();
    }
}