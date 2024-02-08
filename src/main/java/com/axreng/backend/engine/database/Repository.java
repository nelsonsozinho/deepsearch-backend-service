package com.axreng.backend.engine.database;

import com.axreng.backend.model.Entity;

import java.io.Serializable;

public interface Repository<T extends Entity, ID extends Serializable> {

    public ID add(final T type);

    public T findById(final ID id);


}
