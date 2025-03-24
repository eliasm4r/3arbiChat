package MVC.model.pojo;

import java.sql.Timestamp;

public class FilDiscussion {
    private int id;
    private String titre;
    private Timestamp dateCreation;
    private int idCreateur;
    private String filCode; // Champ pour le code

    public FilDiscussion(int id, String titre, Timestamp dateCreation, int idCreateur, String filCode) {
        this.id = id;
        this.titre = titre;
        this.dateCreation = dateCreation;
        this.idCreateur = idCreateur;
        this.filCode = filCode; // Initialisation du code
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public int getIdCreateur() {
        return idCreateur;
    }

    public String getFilCode() {
        return filCode; // Getter pour le code
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    public void setFilCode(String filCode) {
        this.filCode = filCode;
    }
}