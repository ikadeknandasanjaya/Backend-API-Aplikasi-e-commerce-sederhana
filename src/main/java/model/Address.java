package main.java.model;

public class Address {
    private int users;
    private String TYPE;
    private String line1;
    private String line2;
    private String city;
    private String province;
    private String postcode;

    public Address(String users, String TYPE, String line1, String line2, String city, String province, String postcode) {
        this.users = Integer.parseInt(users);
        this.TYPE = TYPE;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.province = province;
        this.postcode = postcode;
    }
    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public String getType() {
        return TYPE;
    }

    public void setType(String type) {
        this.TYPE = type;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }




}
