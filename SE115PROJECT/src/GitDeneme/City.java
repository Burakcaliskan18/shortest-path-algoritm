package GitDeneme;
public class City {
    private String label;

    public City(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    //To prevent the wrong method from being called
    @Override
    public String toString() {
        return label;
    }
}
