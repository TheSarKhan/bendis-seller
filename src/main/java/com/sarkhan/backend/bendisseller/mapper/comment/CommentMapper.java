package com.sarkhan.backend.bendisseller.mapper.comment;


import com.sarkhan.backend.bendisseller.dto.comment.CommentRequest;
import com.sarkhan.backend.bendisseller.dto.comment.CommentResponse;
import com.sarkhan.backend.bendisseller.dto.comment.CommentResponseForMyComment;
import com.sarkhan.backend.bendisseller.dto.comment.UnCommentedProductResponse;
import com.sarkhan.backend.bendisseller.model.comment.Comment;
import com.sarkhan.backend.bendisseller.model.product.Product;

import java.util.List;

public class CommentMapper {
    public static Comment mapRequestToComment(CommentRequest request) {
        return Comment.builder()
                .productId(request.productId())
                .rating(request.rating())
                .content(request.content())
                .build();
    }

    public static CommentResponseForMyComment mapCommentToCommentResponse(Comment comment, Product product, boolean isFavorite) {
        return new CommentResponseForMyComment(
                comment.getProductId(),
                product.getColorAndSizes().getFirst().getImageUrls().getFirst(),
                product.getName(),
                product.getDescription(),
                product.getOriginalPrice(),
                product.getDiscountedPrice(),
                isFavorite,
                product.getRating(),
                product.getRatings().size(),
                comment.getId(),
                comment.getContent()
        );
    }

    public static CommentResponse mapCommentToCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getUserName(),
                comment.getContent(),
                comment.getUsefulCount(),
                comment.getRating(),
                comment.getUpdatedAt()==null?comment.getCreatedAt():comment.getUpdatedAt()
        );
    }

    public static List<CommentResponse> mapCommentsToCommentResponses(List<Comment> comments) {
        return comments.stream().map(CommentMapper::mapCommentToCommentResponse).toList();
    }

    public static UnCommentedProductResponse mapProductToUnCommentedProductResponse(Product product, boolean isFavorite) {
        return new UnCommentedProductResponse(
                product.getId(),
                product.getColorAndSizes().getFirst().getImageUrls().getFirst(),
                product.getName(),
                product.getDescription(),
                product.getOriginalPrice(),
                product.getDiscountedPrice(),
                isFavorite,
                product.getRating(),
                product.getRatings().size());
    }
}
