package GitDeneme;
public class City {
    private String name;

    public City(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    //To prevent the wrong method from being called
    @Override
    public String toString() {
        return name;
    }
}
