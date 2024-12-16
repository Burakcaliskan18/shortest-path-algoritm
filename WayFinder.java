package SE_115_Maps;

import java.util.Formatter;

public class WayFinder {

    // Method to find a path and display the details
    public void findPath(int routeCount, String[][] routes, String startCity, String endCity) {

        // Call the pathfinding algorithm
        String roadSum = findingTheWay(routes, routeCount, startCity, endCity);
        Formatter f = null;

        try {
            f = new Formatter("output.txt");
            f.format(roadSum);
            System.out.println("File read is successful!");
        } catch (Exception e) {
            System.err.println("Something went wrong.");
        } finally {
            if (f != null) {
                f.close();

            }
        }
    }

    public static String findingTheWay(String[][] routes, int routeCount, String startCity, String endCity) {
        // Create a list of all unique cities
        String[] cities = new String[routeCount * 2]; // Maximum size, since each route can have 2 cities
        int citiesCount = 0;
        int minimumTime;

        // Populate the cities array
        for (int i = 0; i < routeCount; i++) {
            String fromCity = routes[i][0];
            String toCity = routes[i][1];
            if (!containsCity(cities, citiesCount, fromCity)) {
                cities[citiesCount++] = fromCity;
            }
            if (!containsCity(cities, citiesCount, toCity)) {
                cities[citiesCount++] = toCity;
            }
        }

        // Find the indices of the start and end cities
        int startCityIndex = findCityIndex(cities, citiesCount, startCity);
        int endCityIndex = findCityIndex(cities, citiesCount, endCity);

        if (startCityIndex == -1 || endCityIndex == -1) {
            String err = "Error: Start or end city not found.";
            System.out.println(err);
            return err;
        }

        // Create an array to keep track of the shortest distance to each city
        int[] distances = new int[citiesCount];
        int[] predecessors = new int[citiesCount]; // To track the path (previous city for each city)
        for (int i = 0; i < citiesCount; i++) {
            distances[i] = Integer.MAX_VALUE;
            predecessors[i] = -1; // Initialize predecessors to -1 (no predecessor)
        }

        distances[startCityIndex] = 0; // Starting city has distance 0

        boolean[] visited = new boolean[citiesCount]; // To track visited cities

        while (true) {
            // Find the city with the smallest distance that hasn't been visited yet
            int currentCityIndex = -1;
            int currentDistance = Integer.MAX_VALUE;
            for (int i = 0; i < citiesCount; i++) {
                if (!visited[i] && distances[i] < currentDistance) {
                    currentDistance = distances[i];
                    currentCityIndex = i;
                }
            }

            if (currentCityIndex == -1) {
                // If no valid city is found, no path exists
                String err = "Error: No path exists from " + startCity + " to " + endCity;
                System.out.println(err);
                return err;
            }

            // Mark the current city as visited
            visited[currentCityIndex] = true;

            // If we have reached the destination city, break
            if (currentCityIndex == endCityIndex) {
                minimumTime = distances[currentCityIndex];
                break;
            }

            // Explore the routes from the current city
            for (int i = 0; i < routeCount; i++) {
                // Check if the route starts from the current city
                if (routes[i][0].equals(cities[currentCityIndex])) {
                    String nextCity = routes[i][1];
                    int roadTime = Integer.parseInt(routes[i][2]);

                    // Find the index of the next city
                    int nextCityIndex = findCityIndex(cities, citiesCount, nextCity);
                    if (nextCityIndex == -1) continue; // Skip if the next city is not found

                    // Calculate the new distance to the next city
                    int newDistance = distances[currentCityIndex] + roadTime;

                    // If the new distance is shorter, update the distance and predecessor
                    if (newDistance < distances[nextCityIndex]) {
                        distances[nextCityIndex] = newDistance;
                        predecessors[nextCityIndex] = currentCityIndex; // Update predecessor
                    }
                }
            }
        }

        // Construct the path using the predecessors array
        String pathString = "";
        int traceIndex = endCityIndex;
        while (traceIndex != -1) {
            if (pathString.isEmpty()) {
                pathString = cities[traceIndex];
            } else {
                pathString = cities[traceIndex] + " -> " + pathString;
            }
            traceIndex = predecessors[traceIndex]; // Move to the predecessor
        }

        return "Fastest Way: " + pathString + "\nTotal Time: " + minimumTime + " min";
    }


    // Helper method to check if a city is already in the cities array
    private static boolean containsCity(String[] cities, int citiesCount, String city) {
        for (int i = 0; i < citiesCount; i++) {
            if (cities[i].equals(city)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to find the index of a city in the cities array
    private static int findCityIndex(String[] cities, int citiesCount, String city) {
        for (int i = 0; i < citiesCount; i++) {
            if (cities[i].equals(city)) {

                return i;
            }
        }
        return -1; // City not found
    }


}
