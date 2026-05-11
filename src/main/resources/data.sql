-- ========================
-- CATEGORIES
-- ========================
INSERT INTO category (id, name) VALUES (1, 'Electronics');
INSERT INTO category (id, name) VALUES (2, 'Books');
INSERT INTO category (id, name) VALUES (3, 'Clothing');
INSERT INTO category (id, name) VALUES (4, 'Home');
INSERT INTO category (id, name) VALUES (5, 'Sports');

-- ========================
-- PRODUCTS (10+)
-- ========================
INSERT INTO product (id, name, price, category_id) VALUES
(1, 'Laptop', 3000.00, 1),
(2, 'Smartphone', 2000.00, 1),
(3, 'Tablet', 1500.00, 1),
(4, 'Spring Boot Guide', 120.00, 2),
(5, 'Clean Code Book', 150.00, 2),
(6, 'T-Shirt', 80.00, 3),
(7, 'Jeans', 200.00, 3),
(8, 'Blender', 250.00, 4),
(9, 'Coffee Machine', 800.00, 4),
(10, 'Football Ball', 100.00, 5),
(11, 'Tennis Racket', 400.00, 5);

-- ========================
-- CUSTOMERS (10+)
-- ========================
INSERT INTO customer (id, first_name, last_name) VALUES
(1, 'John', 'Smith'),
(2, 'Anna', 'Bennett'),
(3, 'Peter', 'Turner'),
(4, 'Kate', 'Walker'),
(5, 'Mark', 'Harrison'),
(6, 'Olivia', 'Coleman'),
(7, 'Thomas', 'Mason'),
(8, 'Agnes', 'Peters'),
(9, 'Paul', 'Johnson'),
(10, 'Maggie', 'Davis');

-- ========================
-- ORDERS (15)
-- ========================
INSERT INTO orders (id, order_date, total_amount, status, customer_id) VALUES
(1, '2026-01-05', 3120.00, 'PAID', 1),
(2, '2026-01-10', 2000.00, 'SHIPPED', 1),
(3, '2026-01-15', 270.00, 'PAID', 2),
(4, '2026-01-20', 150.00, 'CANCELLED', 3),
(5, '2026-02-01', 500.00, 'PAID', 4),
(6, '2026-02-05', 800.00, 'SHIPPED', 5),
(7, '2026-02-10', 160.00, 'NEW', 6),
(8, '2026-02-12', 400.00, 'PAID', 7),
(9, '2026-02-15', 120.00, 'PAID', 8),
(10, '2026-02-18', 3000.00, 'SHIPPED', 9),
(11, '2026-03-01', 220.00, 'PAID', 10),
(12, '2026-03-05', 1500.00, 'NEW', 2),
(13, '2026-03-10', 100.00, 'PAID', 3),
(14, '2026-03-15', 200.00, 'PAID', 4),
(15, '2026-03-20', 450.00, 'SHIPPED', 5);

-- ========================
-- ORDER ITEMS (30+)
-- ========================
INSERT INTO order_item (id, quantity, unit_price, order_id, product_id) VALUES

-- Order 1
(1, 1, 3000.00, 1, 1),
(2, 1, 120.00, 1, 4),

-- Order 2
(3, 1, 2000.00, 2, 2),

-- Order 3
(4, 1, 120.00, 3, 4),
(5, 1, 150.00, 3, 5),

-- Order 4
(6, 1, 150.00, 4, 5),

-- Order 5
(7, 2, 200.00, 5, 7),
(8, 1, 100.00, 5, 10),

-- Order 6
(9, 1, 800.00, 6, 9),

-- Order 7
(10, 2, 80.00, 7, 6),

-- Order 8
(11, 1, 400.00, 8, 11),

-- Order 9
(12, 1, 120.00, 9, 4),

-- Order 10
(13, 1, 3000.00, 10, 1),

-- Order 11
(14, 1, 200.00, 11, 7),
(15, 1, 20.00, 11, 10),

-- Order 12
(16, 1, 1500.00, 12, 3),

-- Order 13
(17, 1, 100.00, 13, 10),

-- Order 14
(18, 1, 200.00, 14, 7),

-- Order 15
(19, 1, 250.00, 15, 8),
(20, 1, 200.00, 15, 7);