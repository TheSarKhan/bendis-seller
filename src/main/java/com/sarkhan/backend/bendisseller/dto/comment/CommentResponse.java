package com.sarkhan.backend.bendisseller.dto.comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String username,
        String content,
        Long usefulCount,
        double rating,
        LocalDateTime dateTime) {

}
