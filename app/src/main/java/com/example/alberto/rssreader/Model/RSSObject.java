package com.example.alberto.rssreader.Model;

import java.util.List;

/**
 * Created by Alberto on 09/12/2017.
 */

public class RSSObject {

        private String status;
        private Feed feed;
        private List<Item> items;

        public RSSObject(String status, Feed feed, List<Item> items) {
            this.status = status;
            this.feed = feed;
            this.items = items;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Feed getFeed() {
            return feed;
        }

        public void setFeed(Feed feed) {
            this.feed = feed;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

}
