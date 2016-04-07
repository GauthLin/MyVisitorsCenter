package Entity;

public class Time
{
    private int minutes;
    private int hours;

    public Time(int hours, int minutes) {
        this.minutes = minutes;
        this.hours = hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHours() {
        return this.hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getTotalMinutes() {
        return this.minutes + this.hours * 60;
    }

    public int getTotalSeconds() {
        return  this.minutes * 60 + this.hours * 3600;
    }

    @Override
    public String toString() {
        return "Time{" +
                "minutes=" + minutes +
                ", hours=" + hours +
                '}';
    }
}
