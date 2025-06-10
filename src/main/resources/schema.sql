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