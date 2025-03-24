package MVC.model.pojo;

public class Abonnement {
    private int utilisateurId;
    private int discussionId;

    public Abonnement(int utilisateurId, int discussionId) {
        this.utilisateurId = utilisateurId;
        this.discussionId = discussionId;
    }

    public int getUtilisateurId() { return utilisateurId; }
    public int getDiscussionId() { return discussionId; }
}
