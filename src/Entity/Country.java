package Entity;

import java.util.ArrayList;

public class Country
{
    private int id;
    private String name;
    private ArrayList<City> cities = new ArrayList<>();

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeCity(City city) {
        this.cities.remove(city);
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cities=" + cities +
                '}';
    }
}
