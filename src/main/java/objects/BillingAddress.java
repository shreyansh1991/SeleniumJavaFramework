package objects;

public class BillingAddress {
    private String firstName;
    private String lastName;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    private String streetAddress;
    private String city;
    private String postalCode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String email;
    private String country;
    private String state;

    public BillingAddress setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public BillingAddress setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }



    public BillingAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public BillingAddress setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public BillingAddress setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getEmail() {
        return email;
    }
}
