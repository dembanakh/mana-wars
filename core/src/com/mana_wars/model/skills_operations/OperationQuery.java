package com.mana_wars.model.skills_operations;

public interface OperationQuery<T, U> {
    OperationQuery<T, U> from(T source);
    OperationQuery<T, U> to(T target);
    U validate();
}
