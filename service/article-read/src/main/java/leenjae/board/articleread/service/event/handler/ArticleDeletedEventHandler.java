package leenjae.board.articleread.service.event.handler;

import leenjae.board.articleread.repository.ArticleIdListRepository;
import leenjae.board.articleread.repository.ArticleQueryModelRepository;
import leenjae.board.articleread.repository.BoardArticleCountRepository;
import leenjae.board.common.event.Event;
import leenjae.board.common.event.EventType;
import leenjae.board.common.event.payload.ArticleDeletedEventPayload;
import leenjae.board.common.event.payload.ArticleUpdatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {
    private final ArticleIdListRepository articleIdListRepository;
    private final ArticleQueryModelRepository articleQueryModelRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;


    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        ArticleDeletedEventPayload payload = event.getPayload();
        // 아래 순서에 대해서도, articleIdListRepository에서 먼저 delete 함으로써 사용자에게 진입점을 막아두는 것.
        // 만약 articleQueryModelRepository가 먼저 삭제가 된다면, 목록에는 있을 수 있는 상황~
        // ArticleCreate도 마찬가지로 add 순서가 사소하지만 중요할 수 있다.
        articleIdListRepository.delete(payload.getBoardId(), payload.getArticleId());
        articleQueryModelRepository.delete(payload.getArticleId());
        boardArticleCountRepository.createOrUpdate(payload.getBoardId(), payload.getBoardArticleCount());
    }

    @Override
    public boolean supports(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }
}
