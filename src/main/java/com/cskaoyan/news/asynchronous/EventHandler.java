package com.cskaoyan.news.asynchronous;

import java.util.List;

public interface EventHandler {

   public List<EventType>   register();
   public Boolean  dealEvent(Event event);
}
