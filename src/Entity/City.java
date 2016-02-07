package Entity;

import java.util.ArrayList;

// TODO: Créer une méthode toString
public class City
{
    private String name;
    private final ArrayList<Activity> activityList = new ArrayList<>();

    public City(String cityName) {
        this.name = cityName.toUpperCase();
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
}