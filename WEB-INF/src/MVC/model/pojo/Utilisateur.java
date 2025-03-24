package MVC.model.pojo;

import io.jsonwebtoken.lang.Objects;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private byte[] profileImage; // Changement de type pour stocker l'image en binaire

    // Constructeur
    public Utilisateur(int id, String nom, String email, String motDePasse, byte[] profileImage) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.profileImage = profileImage; // Utilisation du tableau de bytes
    }

    public byte[] getProfileImage() {
        return profileImage; // Retourne l'image en binaire
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage; // Méthode pour définir l'image en binaire
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id == that.id; // Comparer uniquement l'ID pour déterminer l'égalité
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id); // Utiliser l'ID pour générer le hash
    }
}