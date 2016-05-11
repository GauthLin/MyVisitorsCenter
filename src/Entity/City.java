package Entity;

import java.util.ArrayList;

/**
 * City class
 */
public class City
{
    private int id;
    private String name;
    private Country country;
    private final ArrayList<Activity> activityList = new ArrayList<>(); // Contains all the activity for this city

    public City(String name, Country country) {
        this.name = name.toUpperCase();
        this.country = country;
    }

    public City(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", activityList=" + activityList +
                '}';
    }
}