package com.shire42.backend.engine.database;

import com.shire42.backend.model.Entity;

import java.io.Serializable;

public interface Repository<T extends Entity, ID extends Serializable> {

    public ID add(final T type);

    public T findById(final ID id);


}
