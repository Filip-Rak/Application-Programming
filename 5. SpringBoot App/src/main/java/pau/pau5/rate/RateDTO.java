package pau.pau5.rate;

public class RateDTO
{
    // Attributes
    private int rating;
    private int classEmployeeId;
    private String comment;

    // Constructor
    RateDTO(int rating, int classEmployeeId, String comment)
    {
        this.rating = rating;
        this.classEmployeeId = classEmployeeId;
        this.comment = comment;
    }

    // Getters and Setters
    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public int getClassEmployeeId() {return classEmployeeId; }

    public void setClassEmployeeId(int classEmployeeId) { this.classEmployeeId = classEmployeeId; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }
}
