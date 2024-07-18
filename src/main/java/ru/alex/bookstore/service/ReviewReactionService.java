package ru.alex.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.bookstore.database.entity.Reaction;
import ru.alex.bookstore.database.entity.ReviewReaction;
import ru.alex.bookstore.database.repository.ReviewReactionRepository;

import java.util.Optional;

@Service
public class ReviewReactionService {
    private final ReviewReactionRepository reviewReactionRepository;

    @Autowired
    public ReviewReactionService(ReviewReactionRepository reviewReactionRepository) {
        this.reviewReactionRepository = reviewReactionRepository;
    }

    public Optional<ReviewReaction> findByReviewAndUser(Integer reviewId,Integer userId){
        return reviewReactionRepository.findByReviewAndUser(reviewId,userId);
    }

    public void respondReview(Reaction reaction, Integer reviewId, Integer userId) {
        Optional<ReviewReaction> reviewReactionOptional = reviewReactionRepository.findByReviewAndUser(reviewId, userId);
        reviewReactionOptional.ifPresentOrElse(
                reviewReaction -> reviewReactionRepository.updateReaction(reaction, reviewId, userId),
                () -> reviewReactionRepository.addReaction(reaction.name(), reviewId, userId)
        );
    }

}
