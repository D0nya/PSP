package Client.Models;

public class Book {
    private int Id;
    private String name;
    private String author;
    private String year;
    private String genre;

    public  Book()
    {
        Id = 0;
        name = "";
        author = "";
        year = "";
        genre = "";
    }
    public Book(int id, String name, String author, String year, String genre) {
        this.Id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public String toString()
    {
        return Id + " " + name + " " + author + " " + genre + " " + year;
    }

    public void Parse(String str)
    {
        String[] substring = str.split(" ");
        Id = Integer.parseInt(substring[0]);
        name = substring[1];
        author = substring[2];
        genre = substring[3];
        year = substring[4];
    }
}