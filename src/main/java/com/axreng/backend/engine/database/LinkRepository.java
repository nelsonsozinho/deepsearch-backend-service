package com.axreng.backend.engine.database;

import com.axreng.backend.model.Link;

public interface LinkRepository extends Repository<Link, String> {

    Link findByUrl(String url);

}
