package com.example.mainservice.domain.model;

import lombok.Getter;

@Getter
public abstract class CompositeKeyBaseEntity<KEY> {
    protected KEY key;

    protected CompositeKeyBaseEntity() {}

}

