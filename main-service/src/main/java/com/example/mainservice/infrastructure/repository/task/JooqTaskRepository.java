package com.example.mainservice.infrastructure.repository.task;

import com.example.mainservice.domain.model.task.Task;
import com.example.mainservice.domain.model.task.TaskStatus;
import com.example.mainservice.domain.model.valueobject.TaskId;
import com.example.mainservice.domain.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.mainservice.infrastructure.jooq.Tables.TASKS;

@Repository
@RequiredArgsConstructor
public class JooqTaskRepository implements TaskRepository {

    private final DSLContext dsl;

    @Override
    public Task findById(TaskId id) {
        List<Task> allTasks = findAllTasks();
        Map<TaskId, Task> hierarchicalTasks = buildTaskHierarchy(allTasks);
        return hierarchicalTasks.get(id);
    }

    @Override
    public void insert(Task task) {
        dsl.insertInto(TASKS)
                .set(TASKS.TITLE, task.getTitle())
                .set(TASKS.CONTENT, task.getContent())
                .set(TASKS.STATUS, task.getStatus().getCode())
                .set(TASKS.PARENT_ID, task.getParentId() == null ? null : task.getParentId().value())
                .set(TASKS.CREATED_AT, task.getCreatedAt())
                .execute();

        // サブタスクの登録 or 更新
        task.getSubTasks().forEach(t -> {
            if (t.getId().value() == null) {
                // サブタスクのIDがnullの場合は新規登録
                insert(t);
            } else {
                // サブタスクのIDがnullでない場合は更新
                update(t);
            }
        });
    }

    @Override
    public void update(Task task) {
        // タスクの更新
        dsl.update(TASKS)
                .set(TASKS.ID, task.getId() == null ? null : task.getId().value())
                .set(TASKS.TITLE, task.getTitle())
                .set(TASKS.CONTENT, task.getContent())
                .set(TASKS.STATUS, task.getStatus().getCode())
                .set(TASKS.PARENT_ID, task.getParentId() == null ? null : task.getParentId().value())
                .set(TASKS.CREATED_AT, task.getCreatedAt())
                .where(TASKS.ID.eq(task.getId().value()))
                .execute();

        // サブタスクの登録 or 更新
        task.getSubTasks().forEach(t -> {
            if (t.getId() == null) {
                // サブタスクのIDがnullの場合は新規登録
                insert(t);
            } else {
                // サブタスクのIDがnullでない場合は更新
                update(t);
            }
        });
    }

    public List<Task> findAllTasks() {
        return dsl.selectFrom(TASKS)
                .fetch()
                .stream()
                .map(this::recordToTask)
                .collect(Collectors.toList());
    }

    public Map<TaskId, Task> buildTaskHierarchy(List<Task> tasks) {
        // タスクのIDをキーとしてタスクをマップに格納
        Map<TaskId, Task> taskMap = tasks.stream()
                .collect(Collectors.toMap(Task::getId, task -> task));

        // ルートタスクのMapを作成
        Map<TaskId, Task> rootTasksMap = new HashMap<>();

        // タスクをループして親子関係を構築
        for (Task task : tasks) {
            // 親タスクIDがnullの場合はルートタスクとしてリストに追加
            if (task.getParentId() == null) {
                rootTasksMap.put(task.getId(), task);
            } else { // 親タスクIDがnullでない場合は、親タスクを取得してサブタスクとして追加
                Task parent = taskMap.get(task.getParentId());
                if (parent != null) {
                    parent.getSubTasks().add(task);
                }
            }
        }

        return rootTasksMap;
    }


    private Task recordToTask(Record taskRecord) {
        TaskId id = taskRecord.get(TASKS.ID) == null ? null : TaskId.of(taskRecord.get(TASKS.ID));
        String title = taskRecord.get(TASKS.TITLE);
        String content = taskRecord.get(TASKS.CONTENT);
        TaskStatus status = TaskStatus.valueOf(taskRecord.get(TASKS.STATUS));
        TaskId parentId = taskRecord.get(TASKS.PARENT_ID) == null ? null : TaskId.of(taskRecord.get(TASKS.PARENT_ID));
        LocalDateTime createdAt = taskRecord.get(TASKS.CREATED_AT);
        return Task.reconstruct(id, title, content, status, parentId, createdAt);
    }

}
