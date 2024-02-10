package com.shire42.backend.engine.database;

import com.shire42.backend.model.Link;

public interface LinkRepository extends Repository<Link, String> {

    Link findByUrl(String url);

}
