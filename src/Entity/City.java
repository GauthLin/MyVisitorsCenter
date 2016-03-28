package Entity;

import java.util.ArrayList;

// TODO: Créer une méthode toString
public class City
{
    private int id;
    private String name;
    private int country_id;
    private final ArrayList<Activity> activityList = new ArrayList<>();

    public City(String name, int country_id) {
        this.name = name.toUpperCase();
        this.country_id = country_id;
    }

    public City(int id, String name, int country_id) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
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

    public int getCountryId() {
        return country_id;
    }

    public void setCountryId(int country_id) {
        this.country_id = country_id;
    }
}