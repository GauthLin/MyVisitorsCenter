package Entity;

// TODO: Créer une méthode toString
public class Activity
{
    //private final int id;
    private String name;
    private String description;
    private Time time;
    private String type;

    public Activity(String name, String description, int hours, int minutes, String type) {
        this.name = name;
        this.description = description;
        this.time = new Time(hours, minutes);
        this.type = type;
    }

//    public int getId() {
//        return this.id;
//    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getTime() {
        return this.time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}