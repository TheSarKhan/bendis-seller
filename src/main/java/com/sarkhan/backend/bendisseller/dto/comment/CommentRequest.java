package com.sarkhan.backend.bendisseller.dto.comment;

public record CommentRequest(
        double rating,
        Long productId,
        String content){

}