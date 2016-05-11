package Entity;

/**
 * Activity class contains all the attribute for an activity
 */
public class Activity
{
    private int id;
    private String name;
    private String description;
    private Time time; // Use to get the time of activity
    private Category category;
    private int rating;
    private City city;

    public Activity(String name, String description, int hours, int minutes, Category category, int rating, City city) {
        this.name = name;
        this.description = description;
        this.time = new Time(hours, minutes);
        this.category = category;
        this.city = city;

        try {
            this.setRating(rating);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public int getId() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", category=" + category +
                ", rating=" + rating +
                ", city=" + city +
                '}';
    }
}