public class Event extends Task{
    private String startDate;
    private String endDate;

    // Constructor
    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Methods
    @Override
    public String toString() {
        return "[E]" + super.toString();
    }
}
