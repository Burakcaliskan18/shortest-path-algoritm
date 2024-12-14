package GitDeneme;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
public class Main {
    public static void main(String[] args) {
        // Take the file name from the user
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the input file: ");
        String fileName = sc.nextLine();

        try {
            if(!validateFileFormat(fileName)){
                return;
            }
            //Creating the Map
            Scanner fileScanner = new Scanner (new File(fileName)) ;
            //1st Line - City Number
            int cityCount = Integer.parseInt(fileScanner.nextLine().trim());
            //2nd Line - City Objects
            String[] cityNames = fileScanner.nextLine().trim().split(" ");
            City[] cities = new City[cityCount];
            for (int i = 0; i < cityCount; i++) {
                cities[i] = new City (cityNames[i]);
            }
            //3rd Line - Route Number
            int routeCount = Integer.parseInt(fileScanner.nextLine().trim());
            //4th Line - Routes and Creating Country Map
            String[][] routes = new String[routeCount][3];
            for (int i = 0; i < routeCount; i++) {
                routes[i] = fileScanner.nextLine().trim().split(" ");
            }
            //Start and End Cities
            String[] startEnd = fileScanner.nextLine().trim().split(" ");
            String startCity = startEnd[0];
            String endCity = startEnd[1];

            //CountryMap Objects
            CountryMap countryMap = new CountryMap(cities,routes);

            //Checking Start and End cities
            if (countryMap.getCityByName(startCity) == null || countryMap.getCityByName(endCity) == null) {
                System.out.println("Error: Start or end city not found in the map.");
                return;
            }
            //Algorithm Place
        } catch(FileNotFoundException e) {
            System.out.println("Error: File not found." + fileName);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    // File format validation method (try-catch)
    public static boolean validateFileFormat(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            //1st Line City Number
            if(!fileScanner.hasNextLine()) {
                System.out.println("Error Line: 1 - Missing city count");
                return false;
            }
            String firstLine = fileScanner.nextLine();
            if(!isInteger(firstLine)){
                System.out.println("Error Line: 1 - City count must be an integer");
                return false;
            }
            int cityCount = Integer.parseInt(firstLine);
            //2nd Line City Labels
            if(!fileScanner.hasNextLine()){
                System.out.println("Error Line: 2 - Missing city labels");
                return false;
            }
            String secondLine = fileScanner.nextLine();
            String[] cities = secondLine.split(" ");
            if(cities.length != cityCount){
                System.out.println("Error Line: 2 - City label count does not match city count");
                return false;
            }
            //3rd Line Route Number
            if(!fileScanner.hasNextLine()){
                System.out.println("Error Line: 3 - Missing route count");
                return false;
            }
            String thirdLine = fileScanner.nextLine();
            if(!isInteger(thirdLine)){
                System.out.println("Error Line: 3 - Route count must be an integer");
                return false;
            }
            int routeCount = Integer.parseInt(thirdLine);
            //4th and Other Lines Routes
            for(int i=0;i<routeCount;i++){
                if(!fileScanner.hasNextLine()){
                    System.out.println("Error Line: " + (4+i) + "- Missing route definition");
                    return false;
                }
                String routLine = fileScanner.nextLine();
                String[] routeParts = routLine.split(" ");
                if(routeParts.length != 3 || !isInteger(routeParts[2])){
                    System.out.println("Error Line: " + (4+i) + "- Invalid route format");
                    return false;
                }
            }
            //Last Line Start and End Cities
            if(!fileScanner.hasNextLine()){
                System.out.println("Error Line: " + (4+routeCount) + "- Missing start and end cities");
                return false;
            }
            String lastLine = fileScanner.nextLine();
            String[] startEnd = lastLine.split(" ");
            if(startEnd.length != 2){
                System.out.println("Error Line: " + (4+routeCount) + "- Invalid start/end city format");
                return false;
            }
            return true;
        } catch(FileNotFoundException e){
            System.out.println("File not found:" + fileName);
            return false;
        }
    }
    public static boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}