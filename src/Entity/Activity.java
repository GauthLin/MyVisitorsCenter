package Entity;

// TODO: Créer une méthode toString
public class Activity
{
    //private final int id;
    private String name;
    private String description;
    private Time time;
    private String type;
    private int rating;

    public Activity(String name, String description, int hours, int minutes, String type, int rating) {
        this.name = name;
        this.description = description;
        this.time = new Time(hours, minutes);
        this.type = type;

        try {
            this.setRating(rating);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
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

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) throws Exception {
        if (rating < 0) {
            throw new Exception("La cote d'une activité ne peut pas être inférieure à 0.");
        } else if (rating > 5) {
            throw new Exception("La cote d'une activité ne peut pas excéder 5.");
        }

        this.rating = rating;
    }
}