package MVC.model.pojo;

public class Message {
    private int id;
    private String contenu;
    private int auteurId;
    private int filId; // Assurez-vous que c'est un int si vous utilisez un int

    // Constructeur
    public Message(int id, String contenu, int auteurId, int filId) {
        this.id = id;
        this.contenu = contenu;
        this.auteurId = auteurId;
        this.filId = filId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(int auteurId) {
        this.auteurId = auteurId;
    }

    public int getFilId() {
        return filId;
    }

    public void setFilId(int filId) {
        this.filId = filId;
    }
}