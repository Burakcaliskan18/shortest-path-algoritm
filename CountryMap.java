package SE_115_Maps;

public class CountryMap {
    private City[] cities;
    private String[][] routes;

    public CountryMap(City[] cities, String[][] routes) {
        this.cities = cities;
        this.routes = routes;
    }
    public City getCityByName(String name) {
        for (City city : cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }
}
