package recommender;

/**
 * A custom linked list that stores user info. Each node in the list is of type
 * recommender.UserNode.
 * FILL IN CODE. Also, add other methods as needed.
 *
 * @author okarpenko
 *
 */
public class UsersList {
    private UserNode head = null;
    private UserNode tail = null; // ok to store tail here, will be handy for appending


    /** Insert the rating for the given userId and given movieId.
     *
     * @param userId  id of the user
     * @param movieId id of the movie
     * @param rating  rating given by this user to this movie
     */
    public void insert(int userId, int movieId, double rating) {
        UserNode current = head;


        if (head == null){
            UserNode newUser = new UserNode(movieId);
            head = newUser;
            tail = head;
            newUser.insert(movieId, rating);
        }else {
            while (current != null) {
                if (current.getId() == userId) {
                    current.insert(movieId, rating);
                    break;
                } else {
                    current = current.next();
                }
            }
        }
        if (current == null){
                UserNode newUser = new UserNode(userId);
                append(newUser);
                newUser.insert(movieId, rating);
            }
        }

        // Check if recommender.UserNode already exists;
        // if not, create it and append to this list.
        // Then call insert(movieId, rating) method on the recommender.UserNode
        // FILL IN CODE



    /**
     * Append a new node to the list
     * @param newNode a new node to append to the list
     */
    public void append(UserNode newNode) {

        tail.setNext(newNode);
        tail = newNode;
        // This is where tail will come in handy
        // FILL IN CODE
    }

    /** Return a recommender.UserNode given userId
     *
     * @param userId id of the user (as defined in ratings.csv)
     * @return recommender.UserNode for a given userId
     */
    public UserNode get(int userId) {
        UserNode current = head;

        while(current != null){
            if (current.getId() == userId){
                return current;
            }
            current = current.next();
        }

        return null; // don't forget to change it
    } // get method


    /**
     * The method computes the similarity between the user with the given userid
     * and all the other users. Finds the maximum similarity and returns the
     * "most similar user".
     * Calls computeSimilarity method in class MovieRatingsList/
     *
     * @param userid id of the user
     * @return the node that corresponds to the most similar user
     */
    public UserNode findMostSimilarUser(int userid) {
        UserNode mostSimilarUser = null;
        UserNode current = head;
        UserNode targetNode = null;
        double similarity = 0;

        while (current != null){
            if (current.getId() == userid){
                targetNode = current;
                current = head;
                break;
            }else{
                current = current.next();
            }
        }
        while (current != null){
            if(current.getId() == targetNode.getId()){
                current = current.next();
            }else if (current.computeSimilarity(targetNode) > similarity){
                mostSimilarUser = current;
                similarity = current.computeSimilarity(targetNode);
                current = current.next();
            }
            else {
                current = current.next();
            }
        }
        return mostSimilarUser;

    }

    /** Print recommender.UsersList to a file  with the given name in the following format:
     (userid) movieId:rating; movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating;
     (userid) movieId:rating; movieId:rating; movieId:rating; movieId:rating;
     Info for each userid should be printed on a separate line
     * @param filename name of the file where to output recommender.UsersList info
     */
    public void print(String filename) {
        // FILL IN CODE

    }
}