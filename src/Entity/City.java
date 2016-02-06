package Entity;

import java.util.ArrayList;

// TODO: Créer une méthode toString
public class City
{
    private Country country;
    private String name;
    private final ArrayList<Activity> activityList = new ArrayList<>();

    public City(String countryCode, String countryName, String cityName) {
        this.country = new Country(countryCode, countryName);
        this.name = cityName.toUpperCase();
    }

    public City(Country country, String cityName) {
        this.country = country;
        this.name = cityName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public ArrayList<Activity> getActivityList() {
        return this.activityList;
    }

    // Ajout d'une activité
    public void addActivity(Activity activity) {
        this.activityList.add(activity);
    }

    // Suppression d'une activité
    public void deleteActivity(Activity activity) {
        this.activityList.remove(activity);
    }
}