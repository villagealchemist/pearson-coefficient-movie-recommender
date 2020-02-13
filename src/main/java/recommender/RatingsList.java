package recommender; /**
 * recommender.RatingsList.
 * A class that stores movie ratings for a user in a custom singly linked list of
 * recommender.RatingNode objects. Has various methods to manipulate the list. Stores
 * only the head of the list (no tail! no size!). The list should be sorted by
 * rating (from highest to smallest).
 * Fill in code in the methods of this class.
 * Do not modify signatures of methods. Not all methods are needed to compute recommendations,
 * but all methods are required for the assignment.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Math;

public class RatingsList implements Iterable<RatingNode> {

    private RatingNode head;
    // Note: you are *not* allowed to store the tail or the size of this list


    /**
     * Changes the rating for a given movie to newRating. The position of the
     * node within the list should be changed accordingly, so that the list
     * remains sorted by rating (from largest to smallest).
     *
     * @param movieId id of the movie
     * @param newRating new rating of this movie
     */
    public void setRating(int movieId, double newRating) {

        RatingNode current = head;
        RatingNode previous = head;

        while (current != null) {
            if (current.getMovieId() == movieId) {
                current.setMovieRating(newRating);
                break;
            }
            previous = current;
            current = current.next();
        }
        if (current == null){
            insertByRating(movieId, newRating);
        } else{
            if (current == head){
                head = current.next();
            }else {
                previous.setNext(current.next());
            }
            insertByRating(movieId, newRating);
        }
    }



    /**
     * Return the rating for a given movie. If the movie is not in the list,
     * returns -1.
     * @param movieId movie id
     * @return rating of a movie with this movie id
     */
    public double getRating(int movieId) {

        RatingNode current = head;

        while(current != null) {
            if (current.getMovieId() == movieId) {
                return current.getMovieRating();
            }else {
                current = current.next();
            }
        }
        return -1;
    }


    /**
     * Insert a new node (with a given movie id and a given rating) into the list.
     * Insert it in the right place based on the value of the rating. Assume
     * the list is sorted by the value of ratings, from highest to smallest. The
     * list should remain sorted after this insert operation.
     *
     * @param movieId id of the movie
     * @param rating rating of the movie
     */
    public void insertByRating(int movieId, double rating) {

        RatingNode newNode = new RatingNode(movieId, rating);

        if (head == null){
            head = newNode;
        }else if (head.next() == null && head.getMovieRating() < rating) {
            newNode.setNext(head);
            head = newNode;
        }else if(head.getMovieRating() < rating) {
            newNode.setNext(head);
            head = newNode;
        }else if (head.getMovieRating() == rating){
            if (head.getMovieId() < movieId){
                newNode.setNext(head);
                head = newNode;
            }
        }else {
            RatingNode previous = head;
            RatingNode current = head.next();
            while (current != null){
                if (current.getMovieRating() > rating){
                    previous = current;
                    current = current.next();

                }else if (current.getMovieRating() == rating){
                    if (current.getMovieId() < movieId){
                        previous.setNext(newNode);
                        newNode.setNext(current);
                        break;
                    }else{
                        previous = current;
                        current = current.next();
                    }
                }else{
                    previous.setNext(newNode);
                    newNode.setNext(current);
                    break;
                }
            }
            if (current == null) {
                previous.setNext(newNode);
            }
        }
    }



    /**
     * Computes similarity between two lists of ratings using Pearson correlation.
     * https://en.wikipedia.org/wiki/Pearson_correlation_coefficient
     * Note: You are allowed to use a HashMap for this method.
     *
     * @param otherList another RatingList
     * @return similarity computed using Pearson correlation
     */
    public double computeSimilarity(RatingsList otherList) {
        double similarity = 0;
        HashMap<Integer, Double> ratingsMap = new HashMap<>();
        RatingNode current = head;
        RatingNode otherCur = otherList.head;

        while (otherCur != null){
            ratingsMap.put(otherCur.getMovieId(), otherCur.getMovieRating());
            otherCur = otherCur.next();
        }

        //Using E in place of sigma notation for commentary
        double sumXY = 0; //Exy
        double sumX = 0; //Ex
        double sumY = 0; //Ey
        double sumX2 = 0; //Ey^2
        double sumY2 = 0; //Ex^2
        double n = 0;    //n

        //for pearson correlation x comes from current and y comes from ratingsMap
        while (current!= null){
            if (ratingsMap.containsKey(current.getMovieId())){
                sumXY = sumXY + (current.getMovieRating() * ratingsMap.get(current.getMovieId()));

                sumX = sumX + current.getMovieRating();

                sumY = sumY + ratingsMap.get(current.getMovieId());

                sumX2 = sumX2 + (current.getMovieRating() * current.getMovieRating());

                sumY2 = sumY2 + (ratingsMap.get(current.getMovieId()) * ratingsMap.get(current.getMovieId()));

                n++;

            }
            current = current.next();
        }

        double numerator = n * sumXY - (sumX * sumY); //computes numerator of pearson
        //computes denominator of pearson
        double denominator = (Math.sqrt(n * sumX2 - Math.pow(sumX, 2))) * (Math.sqrt(n * sumY2 - Math.pow(sumY, 2)));

        if (denominator == 0.0){
            return 0.0;
        }
        similarity = numerator / denominator;

        return similarity;
    }
    /**
     * Returns a sublist of this list where the rating values are in the range
     * from begRating to endRating, inclusive.
     *
     * @param begRating lower bound for ratings in the resulting list
     * @param endRating upper bound for ratings in the resulting list
     * @return sublist of the recommender.RatingsList that contains only nodes with
     * rating in the given interval
     */
    public RatingsList sublist(int begRating, int endRating) {

        RatingsList res = new RatingsList();

        return res;
    }

    /** Traverses the list and prints the ratings list in the following format:
     *  movieId:rating; movieId:rating; movieId:rating;  */
    public void print() {

        RatingNode current = head;

        while (current != null){
            System.out.print(current.getMovieId()+":"+current.getMovieRating()+"; ");
            current = current.next();
        }
    }

    /**
     * Returns the middle node in the list - the one half way into the list.
     * Needs to have the running time O(n), and should be done in one pass
     * using slow & fast pointers (as described in class).
     *
     * @return the middle recommender.RatingNode
     */
    public RatingNode getMiddleNode() {
        RatingNode fast = head;
        RatingNode slow = head;

        while (fast.next() != null && fast.next().next() != null){
            fast = fast.next().next();
            slow = slow.next();
        }
        return slow; // don't forget to change it
    }

    /**
     * Returns the median rating (the number that is halfway into the sorted
     * list). To compute it, find the middle node and return it's rating. If the
     * middle node is null, return -1.
     *
     * @return rating stored in the node in the middle of the list
     */
    public double getMedianRating() {

        return getMiddleNode().getMovieRating();
    }

    /**
     * Returns a recommender.RatingsList that contains n best rated movies. These are
     * essentially first n movies from the beginning of the list. If the list is
     * shorter than size n, it will return the whole list.
     *
     * @param n the maximum number of movies to return
     * @return recommender.RatingsList containing movies rated as 5
     */
    public RatingsList getNBestRankedMovies(int n) {
        RatingsList bestMovies = new RatingsList();
        RatingNode endNode = head;
        bestMovies.head = head;

        for (int i = 0; i < n-1; i++){
            endNode = endNode.next();
        }

        endNode.setNext(null);

        return bestMovies; // don't forget to change
    }

    /**
     * * Returns a recommender.RatingsList that contains n worst rated movies for this user.
     * Essentially, these are the last n movies from the end of the list.
     * Note: This method should compute the result in one pass. Do not use the size variable.
     * Note: To find the n-th node from the end of the list, use the technique we discussed in class:
     * use two pointers, where first, you move only one pointer so that pointers are n-nodes apart,
     * and then move both pointers together until the first pointer reaches null; when it happens,
     * the second pointer would be pointing at the correct node.
     * Do NOT use reverse(). Do NOT destroy the list.
     *
     * @param n the maximum number of movies to return
     * @return recommender.RatingsList containing n lowest ranked movies (ranked by this user)
     */
    public RatingsList getNWorstRankedMovies(int n) {

        RatingsList worstMovies = new RatingsList();
        RatingNode current = head;
        RatingNode previous = head;
        int length = 0;

        while (current.next() != null){
            current = current.next();
            length++;
        }
        for (int i = 0; i < length - (n-1); n++){
            previous = previous.next();
        }
        RatingNode worstCurrent = previous;
        worstMovies.head = worstCurrent;


        return worstMovies;
    }

    /**
     * Return a new list that is the reverse of the original list. The returned
     * list is sorted from lowest ranked movies to the highest rated movies.
     * Use only one additional recommender.RatingsList (the one you return) and constant amount
     * of memory. You may NOT use arrays, ArrayList and other built-in Java Collections classes.
     * Read description carefully for requirements regarding implementation of this method.
     *
     * @param head head of the RatingList to reverse
     * @return reversed list
     */
    public RatingsList reverse(RatingNode head) {

        RatingsList r = new RatingsList();
        RatingNode current = head;
        RatingNode curNext = head.next();

        while (current != null){
            current.setNext(r.head);
            r.head = current;
            current = curNext;
            if (curNext != null) {
                curNext = curNext.next();
            }
        }

        return r;
    }

    /**
     * Returns an iterator for the list
     * @return iterator
     */
    public Iterator<RatingNode> iterator() {

        return new RatingsListIterator(0);
    }

    // ------------------------------------------------------
    /**
     * Inner class, RatingsListIterator
     * The iterator for the ratings list. Allows iterating over the recommender.RatingNode-s of
     * the list.
     */
    private class RatingsListIterator implements Iterator<RatingNode> {

        RatingNode indexNode;
        RatingNode current;

        /**
         * Creates a new the iterator starting at a given index
         * @param index index
         */
        public RatingsListIterator(int index) {

            indexNode = head;

            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next();
            }
        }

        /**
         * Checks if there is a "next" element of the list
         * @return true, if there is "next" and false otherwise
         */
        public boolean hasNext() {

            return indexNode != null;
        }
        /**
         * Returns the "next" node and advances the iterator
         * @return next node
         */
        public RatingNode next() {
            if (!hasNext()) {
                System.out.println("No next element.");
                throw new NoSuchElementException();
            }
            RatingNode myNode = indexNode;
            indexNode = indexNode.next();
            return myNode;
        }

        public void remove() {
            // No need to implement for this assignment
            throw new UnsupportedOperationException();
        }

    }

}