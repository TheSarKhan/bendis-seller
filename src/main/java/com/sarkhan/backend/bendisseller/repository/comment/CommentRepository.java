package com.sarkhan.backend.bendisseller.repository.comment;

import com.sarkhan.backend.bendisseller.model.comment.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getByProductId(Long productId);

    List<Comment> getByUserId(Long userId, Pageable pageable);

    List<Comment> getByUserId(Long userId);

    Optional<Comment> getByUserIdAndProductId(Long userId, Long productId);
}
