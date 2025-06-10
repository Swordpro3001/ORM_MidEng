-- =====================================================
-- Warehouse Management System Database Setup Script
-- =====================================================

-- Drop existing tables if they exist (for clean setup)
DROP TABLE IF EXISTS warehouse_products CASCADE;
DROP TABLE IF EXISTS datawarehouse CASCADE;
DROP TABLE IF EXISTS product CASCADE;

-- =====================================================
-- Create Product Table
-- =====================================================
CREATE TABLE product (
                         product_id VARCHAR(255) PRIMARY KEY,
                         product_name VARCHAR(255) NOT NULL,
                         product_category VARCHAR(255),
                         product_quantity INTEGER NOT NULL DEFAULT 0,
                         product_unit VARCHAR(50)
);

-- =====================================================
-- Create DataWarehouse Table
-- =====================================================
CREATE TABLE datawarehouse (
                               warehouse_id BIGSERIAL PRIMARY KEY,
                               warehouse_name VARCHAR(255) NOT NULL,
                               warehouse_address VARCHAR(500),
                               warehouse_postal_code VARCHAR(20),
                               warehouse_city VARCHAR(255),
                               warehouse_country VARCHAR(255),
                               timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Create Junction Table for Many-to-Many Relationship
-- =====================================================
CREATE TABLE warehouse_products (
                                    warehouse_id BIGINT REFERENCES datawarehouse(warehouse_id) ON DELETE CASCADE,
                                    product_id VARCHAR(255) REFERENCES product(product_id) ON DELETE CASCADE,
                                    PRIMARY KEY (warehouse_id, product_id)
);

-- =====================================================
-- Insert Sample Products
-- =====================================================
INSERT INTO product (product_id, product_name, product_category, product_quantity, product_unit) VALUES
-- Electronics
('ELEC001', 'Smartphone Samsung Galaxy S24', 'Electronics', 150, 'pieces'),
('ELEC002', 'Laptop Dell XPS 13', 'Electronics', 85, 'pieces'),
('ELEC003', 'Wireless Headphones Sony', 'Electronics', 200, 'pieces'),
('ELEC004', 'Tablet iPad Air', 'Electronics', 120, 'pieces'),
('ELEC005', 'Smart TV 55" LG OLED', 'Electronics', 45, 'pieces'),

-- Clothing
('CLOTH001', 'T-Shirt Cotton Basic', 'Clothing', 500, 'pieces'),
('CLOTH002', 'Jeans Denim Blue', 'Clothing', 300, 'pieces'),
('CLOTH003', 'Winter Jacket North Face', 'Clothing', 150, 'pieces'),
('CLOTH004', 'Running Shoes Nike', 'Clothing', 250, 'pairs'),
('CLOTH005', 'Dress Shirt Formal', 'Clothing', 180, 'pieces'),

-- Books
('BOOK001', 'Programming in Java', 'Books', 75, 'pieces'),
('BOOK002', 'Database Design Fundamentals', 'Books', 60, 'pieces'),
('BOOK003', 'Spring Boot in Action', 'Books', 90, 'pieces'),
('BOOK004', 'Clean Code', 'Books', 110, 'pieces'),
('BOOK005', 'System Design Interview', 'Books', 85, 'pieces'),

-- Home & Garden
('HOME001', 'Coffee Maker Deluxe', 'Home & Garden', 80, 'pieces'),
('HOME002', 'Vacuum Cleaner Dyson', 'Home & Garden', 45, 'pieces'),
('HOME003', 'Garden Hose 50ft', 'Home & Garden', 120, 'pieces'),
('HOME004', 'Dining Table Oak Wood', 'Home & Garden', 25, 'pieces'),
('HOME005', 'LED Desk Lamp', 'Home & Garden', 200, 'pieces'),

-- Sports & Outdoors
('SPORT001', 'Mountain Bike Trek', 'Sports & Outdoors', 35, 'pieces'),
('SPORT002', 'Tennis Racket Wilson', 'Sports & Outdoors', 65, 'pieces'),
('SPORT003', 'Camping Tent 4-Person', 'Sports & Outdoors', 40, 'pieces'),
('SPORT004', 'Yoga Mat Premium', 'Sports & Outdoors', 150, 'pieces'),
('SPORT005', 'Basketball Official', 'Sports & Outdoors', 100, 'pieces'),

-- Food & Beverages
('FOOD001', 'Organic Coffee Beans', 'Food & Beverages', 500, 'kg'),
('FOOD002', 'Premium Olive Oil', 'Food & Beverages', 200, 'liters'),
('FOOD003', 'Honey Natural Raw', 'Food & Beverages', 150, 'kg'),
('FOOD004', 'Pasta Whole Wheat', 'Food & Beverages', 800, 'kg'),
('FOOD005', 'Green Tea Matcha', 'Food & Beverages', 100, 'kg');

-- =====================================================
-- Insert Sample Warehouses
-- =====================================================
INSERT INTO datawarehouse (warehouse_name, warehouse_address, warehouse_postal_code, warehouse_city, warehouse_country) VALUES
                                                                                                                            ('Central Distribution Hub', '1234 Industrial Blvd', '12345', 'New York', 'USA'),
                                                                                                                            ('West Coast Logistics Center', '5678 Commerce Way', '90210', 'Los Angeles', 'USA'),
                                                                                                                            ('European Distribution Center', 'Hauptstraße 123', '10115', 'Berlin', 'Germany'),
                                                                                                                            ('Asia Pacific Hub', '789 Business District', '100001', 'Beijing', 'China'),
                                                                                                                            ('UK Regional Warehouse', '456 Warehouse Road', 'SW1A 1AA', 'London', 'United Kingdom'),
                                                                                                                            ('Canadian Operations Center', '321 Maple Street', 'K1A 0A6', 'Ottawa', 'Canada'),
                                                                                                                            ('Australian Distribution Point', '654 Outback Avenue', '2000', 'Sydney', 'Australia'),
                                                                                                                            ('Nordic Logistics Hub', 'Storgatan 987', '111 29', 'Stockholm', 'Sweden');

-- =====================================================
-- Create Warehouse-Product Associations
-- =====================================================

-- Central Distribution Hub (Warehouse ID: 1) - Mixed products
INSERT INTO warehouse_products (warehouse_id, product_id) VALUES
                                                              (1, 'ELEC001'), (1, 'ELEC002'), (1, 'ELEC003'),
                                                              (1, 'CLOTH001'), (1, 'CLOTH002'), (1, 'CLOTH004'),
                                                              (1, 'BOOK001'), (1, 'BOOK003'), (1, 'BOOK004'),
                                                              (1, 'HOME001'), (1, 'HOME005'),
                                                              (1, 'FOOD001'), (1, 'FOOD004');

-- West Coast Logistics Center (Warehouse ID: 2) - Electronics & Sports focus
INSERT INTO warehouse_products (warehouse_id, product_id) VALUES
                                                              (2, 'ELEC001'), (2, 'ELEC004'), (2, 'ELEC005'),
                                                              (2, 'SPORT001'), (2, 'SPORT002'), (2, 'SPORT003'), (2, 'SPORT004'),
                                                              (2, 'HOME002'), (2, 'HOME003'),
                                                              (2, 'CLOTH004'), (2, 'CLOTH005');

-- European Distribution Center (Warehouse ID: 3) - Books & Home products
INSERT INTO warehouse_products (warehouse_id, product_id) VALUES
                                                              (3, 'BOOK001'), (3, 'BOOK002'), (3, 'BOOK003'), (3, 'BOOK005'),
                                                              (3, 'HOME001'), (3, 'HOME002'), (3, 'HOME004'), (3, 'HOME005'),
                                                              (3, 'FOOD001'), (3, 'FOOD002'), (3, 'FOOD003'),
                                                              (3, 'CLOTH003'), (3, 'CLOTH005');

-- Asia Pacific Hub (Warehouse ID: 4) - Electronics & Clothing
INSERT INTO warehouse_products (warehouse_id, product_id) VALUES
                                                              (4, 'ELEC001'), (4, 'ELEC002'), (4, 'ELEC003'), (4, 'ELEC004'),
                                                              (4, 'CLOTH001'), (4, 'CLOTH002'), (4, 'CLOTH003'), (4, 'CLOTH004'),
                                                              (4, 'FOOD001'), (4, 'FOOD005'),
                                                              (4, 'SPORT005');

-- UK Regional Warehouse (Warehouse ID: 5) - Premium items
INSERT INTO warehouse_products (warehouse_id, product_id) VALUES
                                                              (5, 'ELEC002'), (5, 'ELEC005'),
                                                              (5, 'CLOTH003'), (5, 'CLOTH005'),
                                                              (5, 'BOOK004'), (5, 'BOOK005'),
                                                              (5, 'HOME002'), (5, 'HOME004'),
                                                              (5, 'FOOD002'), (5, 'FOOD003'),
                                                              (5, 'SPORT001'), (5, 'SPORT002');

-- Canadian Operations Center (Warehouse ID: 6) - Cold climate products
INSERT INTO warehouse_products (warehouse_id, product_id) VALUES
                                                              (6, 'CLOTH003'), (6, 'CLOTH004'),
                                                              (6, 'SPORT003'), (6, 'SPORT004'),
                                                              (6, 'HOME001'), (6, 'HOME005'),
                                                              (6, 'FOOD001'), (6, 'FOOD004'),
                                                              (6, 'ELEC003'), (6, 'BOOK001');

-- Australian Distribution Point (Warehouse ID: 7) - Outdoor & Sports
INSERT INTO warehouse_products (warehouse_id, product_id) VALUES
                                                              (7, 'SPORT001'), (7, 'SPORT002'), (7, 'SPORT003'), (7, 'SPORT005'),
                                                              (7, 'CLOTH001'), (7, 'CLOTH004'),
                                                              (7, 'HOME003'), (7, 'HOME005'),
                                                              (7, 'ELEC001'), (7, 'ELEC003'),
                                                              (7, 'FOOD001'), (7, 'FOOD005');

-- Nordic Logistics Hub (Warehouse ID: 8) - Tech & Books
INSERT INTO warehouse_products (warehouse_id, product_id) VALUES
                                                              (8, 'ELEC002'), (8, 'ELEC003'), (8, 'ELEC004'),
                                                              (8, 'BOOK001'), (8, 'BOOK002'), (8, 'BOOK003'), (8, 'BOOK004'),
                                                              (8, 'HOME001'), (8, 'HOME002'),
                                                              (8, 'CLOTH003'), (8, 'FOOD001');

-- =====================================================
-- Create Indexes for Better Performance
-- =====================================================
CREATE INDEX idx_product_category ON product(product_category);
CREATE INDEX idx_product_name ON product(product_name);
CREATE INDEX idx_warehouse_city ON datawarehouse(warehouse_city);
CREATE INDEX idx_warehouse_country ON datawarehouse(warehouse_country);
CREATE INDEX idx_warehouse_products_warehouse ON warehouse_products(warehouse_id);
CREATE INDEX idx_warehouse_products_product ON warehouse_products(product_id);

-- =====================================================
-- Verification Queries
-- =====================================================

-- Show all warehouses with product counts
SELECT
    w.warehouse_id,
    w.warehouse_name,
    w.warehouse_city,
    w.warehouse_country,
    COUNT(wp.product_id) as product_count
FROM datawarehouse w
         LEFT JOIN warehouse_products wp ON w.warehouse_id = wp.warehouse_id
GROUP BY w.warehouse_id, w.warehouse_name, w.warehouse_city, w.warehouse_country
ORDER BY w.warehouse_id;

-- Show products by category with total quantities
SELECT
    product_category,
    COUNT(*) as product_types,
    SUM(product_quantity) as total_quantity
FROM product
GROUP BY product_category
ORDER BY product_category;

-- Show warehouse distribution by country
SELECT
    warehouse_country,
    COUNT(*) as warehouse_count
FROM datawarehouse
GROUP BY warehouse_country
ORDER BY warehouse_count DESC;

-- Sample query to find products in a specific warehouse
SELECT
    w.warehouse_name,
    p.product_id,
    p.product_name,
    p.product_category,
    p.product_quantity,
    p.product_unit
FROM datawarehouse w
         JOIN warehouse_products wp ON w.warehouse_id = wp.warehouse_id
         JOIN product p ON wp.product_id = p.product_id
WHERE w.warehouse_id = 1
ORDER BY p.product_category, p.product_name;