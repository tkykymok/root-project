package com.example.mainservice.domain.model.valueobject;


public record VersionKey(Long value) {
    public static VersionKey of(Long value) {
        return new VersionKey(value);
    }

    public VersionKey increment() {
        return new VersionKey(this.value + 1);
    }
}