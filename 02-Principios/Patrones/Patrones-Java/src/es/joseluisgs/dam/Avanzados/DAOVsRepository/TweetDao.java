package es.joseluisgs.dam.Avanzados.DAOVsRepository;

import java.util.List;

public interface TweetDao {

    List<Tweet> fetchTweets(String email);

}