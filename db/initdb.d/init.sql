-- tasks テーブルの存在を確認し、存在しない場合は作成
CREATE TABLE tasks
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255),
    content    VARCHAR(255),
    status     INT NOT NULL,
    parent_id  BIGINT,
    created_at TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES tasks (id)
);

-- app_users テーブルの存在を確認し、存在しない場合は作成
CREATE TABLE app_users
(
    id    BIGINT PRIMARY KEY,
    name  VARCHAR(255),
    email VARCHAR(255)
);

-- products テーブルの存在を確認し、存在しない場合は作成
CREATE TABLE products
(
    id    BIGINT PRIMARY KEY,
    name  VARCHAR(255),
    price DECIMAL
);

-- orders テーブルの存在を確認し、存在しない場合は作成
CREATE TABLE orders
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT,
    order_date   DATE,
    total_amount DECIMAL,
    version      BIGINT,
    FOREIGN KEY (user_id) REFERENCES app_users (id)
);
-- order_items テーブルの存在を確認し、存在しない場合は作成
CREATE TABLE order_items
(
    order_id         BIGINT,
    seq_no           INT,
    product_id       BIGINT,
    price            DECIMAL,
    quantity         INT,
    sub_total_amount DECIMAL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    PRIMARY KEY (order_id, seq_no)
);

-- tasks テーブルに初期データを挿入
INSERT INTO tasks (id, title, content, status, parent_id, created_at)
VALUES (1, 'メインタスク1', 'メインタスクの内容1', 0, NULL, CURRENT_TIMESTAMP),
       (2, 'メインタスク2', 'メインタスクの内容2', 1, NULL, CURRENT_TIMESTAMP),
       (3, 'サブタスク1', 'サブタスクの内容1', 0, 1, CURRENT_TIMESTAMP),
       (4, 'サブタスク2', 'サブタスクの内容2', 1, 1, CURRENT_TIMESTAMP),
       (5, 'サブタスク3', 'サブタスクの内容3', 0, 2, CURRENT_TIMESTAMP),
       (6, 'サブタスク1-1', 'サブタスク1のサブタスクの内容1', 0, 3, CURRENT_TIMESTAMP),
       (7, 'サブタスク1-2', 'サブタスク1のサブタスクの内容2', 1, 3, CURRENT_TIMESTAMP);
ALTER SEQUENCE tasks_id_seq RESTART WITH 100;

-- app_users テーブルに初期データを挿入
INSERT INTO app_users (id, name, email)
VALUES (1, 'John Doe', 'john.doe@example.com'),
       (2, 'Jane Smith', 'jane.smith@example.com');

-- products テーブルに初期データを挿入
INSERT INTO products (id, name, price)
VALUES (1, '商品A', 500.00),
       (2, '商品B', 1000.00),
       (3, '商品C', 1500.00);

-- orders テーブルに初期データを挿入
INSERT INTO orders (user_id, order_date, total_amount, version)
VALUES (1, '2023-01-01', 2000.00, 0),
       (2, '2023-01-02', 1500.00, 0);

-- order_items テーブルに初期データを挿入
INSERT INTO order_items (order_id,
                         seq_no,
                         product_id,
                         price,
                         quantity,
                         sub_total_amount)
VALUES (1, 1, 1, 500, 2, 1000.00),
       (1, 2, 2, 1000, 1, 1000.00),
       (2, 1, 3, 1500, 1, 1500.00);