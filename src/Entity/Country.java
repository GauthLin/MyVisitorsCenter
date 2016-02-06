package Entity;

import java.util.ArrayList;

// TODO: Créer une méthode toString
public class Country
{
    private String code;
    private String name;
    private ArrayList<City> cities = new ArrayList<>();

    public Country(String code, String name) {
        try {
            this.setCode(code);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) throws Exception {
        if (code.length() != 2) {
            throw new Exception("Le code du pays doit contenir 2 caractères.");
        }

        this.code = code.toUpperCase();
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
}
