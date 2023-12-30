package com.example.mainservice.domain.model.task;

import com.example.mainservice.domain.model.valueobject.TaskId;
import com.example.shared.domain.model.SingleKeyBaseEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Task extends SingleKeyBaseEntity<TaskId> {
    private String title;
    private String content;
    private TaskStatus status;
    private TaskId parentId;
    private LocalDateTime createdAt;
    private List<Task> subTasks;

    // プライベートコンストラクタで直接のインスタンス化を防ぐ
    private Task() {
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static Task reconstruct(TaskId taskId, String title, String content,
                                   TaskStatus status, TaskId parentId, LocalDateTime createdAt) {
        Task task = new Task();
        task.id = taskId;
        task.title = title;
        task.content = content;
        task.status = status;
        task.parentId = parentId;
        task.createdAt = createdAt;
        task.subTasks = new ArrayList<>();
        return task;
    }

    public void addSubTask(String title, String content) {
        subTasks.add(createSubTask(title, content));
    }

    private Task createSubTask(String title, String content) {
        Task task = new Task();
        task.title = title;
        task.content = content;
        task.status = TaskStatus.UNCOMPLETED;
        task.parentId = this.getId();
        task.createdAt = LocalDateTime.now();
        task.subTasks = new ArrayList<>();
        return task;
    }
}
