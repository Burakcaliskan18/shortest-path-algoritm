package SE_115_Maps;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Main {
    private static int cityCount;
    private static int routeCount;
    private static City[] cities;
    private static String[][] routes;
    private static String startCity;
    private static String endCity;
    private static CountryMap countryMap;

    public static void main(String[] args) {

        // Take the file name from the user
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the input file: ");
        String fileName = sc.nextLine();

        try {
            if (!validateFileFormat(fileName)) {
                return;
            }

            // Reading the file and initializing the data
            Scanner fileScanner = new Scanner(new File(fileName));

            // 1st Line - City Number
            cityCount = Integer.parseInt(fileScanner.nextLine().trim());

            // 2nd Line - City Objects
            String[] cityNames = fileScanner.nextLine().trim().split(" ");
            cities = new City[cityCount];
            for (int i = 0; i < cityCount; i++) {
                cities[i] = new City(cityNames[i]);
            }

            // 3rd Line - Route Number
            routeCount = Integer.parseInt(fileScanner.nextLine().trim());

            // 4th Line - Routes
            routes = new String[routeCount][3];
            for (int i = 0; i < routeCount; i++) {
                routes[i] = fileScanner.nextLine().trim().split(" ");
            }

            // Last Line - Start and End Cities
            String[] startEnd = fileScanner.nextLine().trim().split(" ");
            startCity = startEnd[0];
            endCity = startEnd[1];

            // Create the CountryMap object
            countryMap = new CountryMap(cities, routes);

            // Validate start and end cities
            if (countryMap.getCityByName(startCity) == null || countryMap.getCityByName(endCity) == null) {
                System.out.println("Error: Start or end city not found in the map.");
                return;
            }

            WayFinder wayfinder = new WayFinder();
            wayfinder.findPath();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + fileName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // File format validation method
    public static boolean validateFileFormat(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));

            // 1st Line City Number
            if (!fileScanner.hasNextLine()) {
                System.out.println("Error Line: 1 - Missing city count");
                return false;
            }
            String firstLine = fileScanner.nextLine();
            if (!isInteger(firstLine)) {
                System.out.println("Error Line: 1 - City count must be an integer");
                return false;
            }
            int cityCount = Integer.parseInt(firstLine);

            // 2nd Line City Labels
            if (!fileScanner.hasNextLine()) {
                System.out.println("Error Line: 2 - Missing city labels");
                return false;
            }
            String secondLine = fileScanner.nextLine();
            String[] cities = secondLine.split(" ");
            if (cities.length != cityCount) {
                System.out.println("Error Line: 2 - The number of cities does not match city count");
                return false;
            }

            // 3rd Line Route Number
            if (!fileScanner.hasNextLine()) {
                System.out.println("Error Line: 3 - Missing route count");
                return false;
            }
            String thirdLine = fileScanner.nextLine();
            if (!isInteger(thirdLine)) {
                System.out.println("Error Line: 3 - Route count must be an integer");
                return false;
            }
            int routeCount = Integer.parseInt(thirdLine);

            // 4th and Other Lines Routes
            for (int i = 0; i < routeCount; i++) {
                if (!fileScanner.hasNextLine()) {
                    System.out.println("Error Line: " + (4 + i) + " - Missing route definition");
                    return false;
                }
                String routLine = fileScanner.nextLine();
                String[] routeParts = routLine.split(" ");
                if (routeParts.length != 3 || !isInteger(routeParts[2])) {
                    System.out.println("Error : There should be route at Line " + (4 + i) + " - Invalid route format");
                    return false;
                }
            }

            // Last Line Start and End Cities
            if (!fileScanner.hasNextLine()) {
                System.out.println("Error Line: " + (4 + routeCount) + " - Missing start and end cities");
                return false;
            }
            String lastLine = fileScanner.nextLine();
            String[] startEnd = lastLine.split(" ");
            if (startEnd.length != 2) {
                System.out.println("Error Line: " + (4 + routeCount) + " - Invalid start/end city format");
                return false;
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return false;
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Static getter methods
    public static int getCityCount() {
        return cityCount;
    }

    public static int getRouteCount() {
        return routeCount;
    }

    public static City[] getCities() {
        return cities;
    }

    public static String[][] getRoutes() {
        return routes;
    }

    public static String getStartCity() {
        return startCity;
    }

    public static String getEndCity() {
        return endCity;
    }

    public static CountryMap getCountryMap() {
        return countryMap;
    }
}
