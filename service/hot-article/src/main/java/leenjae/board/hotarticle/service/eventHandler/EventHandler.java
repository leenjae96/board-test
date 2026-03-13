package leenjae.board.hotarticle.service.eventHandler;

import leenjae.board.common.event.Event;
import leenjae.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
    Long findArticleId(Event<T> event);
}
