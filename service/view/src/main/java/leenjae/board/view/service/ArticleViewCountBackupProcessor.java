package leenjae.board.view.service;

import leenjae.board.common.event.EventType;
import leenjae.board.common.event.payload.ArticleViewedEventPayload;
import leenjae.board.common.outboxmessagerelay.OutboxEventPublisher;
import leenjae.board.view.entity.ArticleViewCount;
import leenjae.board.view.repository.ArticleViewCountBackupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ArticleViewCountBackupProcessor {
    private final OutboxEventPublisher outboxEventPublisher;
    private final ArticleViewCountBackupRepository articleViewCountBackupRepository;

    @Transactional
    public void backup(Long articleId, Long viewCount) {
        int result = articleViewCountBackupRepository.updateViewCount(articleId, viewCount);
        if (result == 0) {
            articleViewCountBackupRepository.findById(articleId)
                    .ifPresentOrElse(ignored -> {
                            },
                            () -> articleViewCountBackupRepository.save(ArticleViewCount.init(articleId, viewCount))
                    );
        }

        outboxEventPublisher.publish(
                EventType.ARTICLE_VIEWED,
                ArticleViewedEventPayload.builder()
                        .articleId(articleId)
                        .articleViewCount(viewCount)
                        .build(),
                articleId
        );
    }
}
