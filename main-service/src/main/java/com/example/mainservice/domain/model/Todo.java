package com.example.mainservice.domain.model;

import com.example.mainservice.domain.model.valueobject.Title;
import com.example.mainservice.domain.model.valueobject.TodoId;
import com.example.shared.domain.model.SingleKeyBaseEntity;
import lombok.Getter;

@Getter
public class Todo extends SingleKeyBaseEntity<TodoId> {
    private Title title;

    // プライベートコンストラクタで直接のインスタンス化を防ぐ
    private Todo() {
    }

}
