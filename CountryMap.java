package SE_115_Maps;

public class CountryMap {
    private City[] cities;
    private String[][] routes;

    public CountryMap(City[] cities, String[][] routes) {
        this.cities = cities;
        this.routes = routes;
    }
    public City[] getCities() {
        return cities;
    }
    public String[][] getRoutes() {
        return routes;
    }
    public City getCityByName(String name) {
        for (City city : cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }
    public int getDistanceBetweenCities(String city1, String city2) {
        for (String[] route : routes) {
            if ((route[0].equals(city1) && route[1].equals(city2)) || (route[0].equals(city2) && route[1].equals(city1))) {
                return Integer.parseInt(route[2]);
            }
        }
        return -1;
    }
}
