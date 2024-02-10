package com.shire42.backend.engine.database;

import com.shire42.backend.model.Link;

import java.util.HashSet;
import java.util.Set;

public class LinkRepositoryConcrete implements LinkRepository {

    private static LinkRepositoryConcrete linkDatabase;


    private final Set<Link> links;


    private LinkRepositoryConcrete() {
        this.links = new HashSet<>();
    }

    public static LinkRepositoryConcrete getInstance() {
        if(linkDatabase == null) {
            linkDatabase = new LinkRepositoryConcrete();
        }
        return linkDatabase;
    }

    @Override
    public synchronized String add(final Link link) {
        this.links.add(link);
        return link.getId();    }

    @Override
    public synchronized Link findById(final String id) {
        return links.stream().filter(e -> e.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public Link findByUrl(final String url) {
        return links.stream().filter(link -> link.getLink().equals(url))
                .findAny()
                .orElse(null);
    }
}
