package domain.user.info;

public record Address(String region, String town, String street,
                      String houseNumber, int flatNumber) {

    public String getRegion() {
        return region;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public int getFlatNumber() {
        return flatNumber;
    }
}
