package leenjae.board.common.event;

import leenjae.board.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    ARTICLE_CREATED(ArticleCreatedEventPayload.class, Topic.LEENJAE_BOARD_ARTICLE),
    ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.LEENJAE_BOARD_ARTICLE),
    ARTICLE_DELETED(ArticleDeletedEventPayload.class, Topic.LEENJAE_BOARD_ARTICLE),
    COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.LEENJAE_BOARD_COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.LEENJAE_BOARD_COMMENT),
    ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.LEENJAE_BOARD_LIKE),
    ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.LEENJAE_BOARD_LIKE),
    ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.LEENJAE_BOARD_VIEW)
    ;

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try{
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.type] type={}", type, e);
            return null;
        }
    }

    public static class Topic{
        public static final String LEENJAE_BOARD_ARTICLE = "leenjae-board-article";
        public static final String LEENJAE_BOARD_COMMENT = "leenjae-board-comment";
        public static final String LEENJAE_BOARD_LIKE = "leenjae-board-like";
        public static final String LEENJAE_BOARD_VIEW = "leenjae-board-view";
    }
}
