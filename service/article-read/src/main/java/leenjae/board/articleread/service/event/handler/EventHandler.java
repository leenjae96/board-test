package leenjae.board.articleread.service.event.handler;

import leenjae.board.common.event.Event;
import leenjae.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> eventPayload);
    boolean supports(Event<T> event);
}
