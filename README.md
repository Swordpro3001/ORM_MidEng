# ORM_MidEng

## Overview of Components

The application follows a standard Spring MVC architecture with the following components:

1. **Models** - Entity classes mapped to database tables
2. **DTOs** - Data transfer objects for API requests and responses
3. **Repositories** - Database access interfaces
4. **Services** - Business logic components
5. **Controllers** - REST API endpoints

## Data Models

### DataWarehouse

The `DataWarehouse` entity represents storage facilities with location information and associated products.

```java
@Entity
@Data
@Table(name = "datawarehouse")
public class DataWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseID;
    
    private String warehouseName;
    private String warehouseAddress;
    private String warehousePostalCode;
    private String warehouseCity;
    private String warehouseCountry;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;
    
    @ManyToMany
    @JoinTable(
            name = "warehouse_products",
            joinColumns = @JoinColumn(name = "warehouse_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
}
```

### Product

The `Product` entity stores information about items that can be stored in warehouses.

```java
@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    private String productID;
    
    private String productName;
    private String productCategory;
    private int productQuantity;
    private String productUnit;
}
```

## Data Transfer Objects (DTOs)

### WarehouseWithProductIdsDTO

Used when creating a warehouse with existing products (referenced by IDs).

```java
@Data
public class WarehouseWithProductIdsDTO {
    private String warehouseName;
    private String warehouseAddress;
    private String warehousePostalCode;
    private String warehouseCity;
    private String warehouseCountry;
    private List<String> productIds;
}
```

### WarehouseWithProductsDTO

Used when creating a warehouse with new products (full product objects).

```java
@Data
public class WarehouseWithProductsDTO {
    private String warehouseName;
    private String warehouseAddress;
    private String warehousePostalCode;
    private String warehouseCity;
    private String warehouseCountry;
    private List<Product> products;
}
```

## REST API Endpoints

### Warehouse Management

#### Get All Warehouses
```
GET /warehouses
```
Returns a list of all warehouses.

#### Get Warehouse by ID
```
GET /warehouses/{id}
```
Returns a specific warehouse by its ID.

#### Update Warehouse
```
PUT /warehouses/{id}
```
Updates an existing warehouse or creates a new one if it doesn't exist.

#### Create Warehouse with Existing Products
```
POST /warehouses/with-products
```
Creates a new warehouse and associates it with existing products.

Example request body:
```json
{
  "warehouseName": "Central Warehouse",
  "warehouseAddress": "123 Main St",
  "warehousePostalCode": "10001",
  "warehouseCity": "New York",
  "warehouseCountry": "USA",
  "productIds": ["PROD-001", "PROD-002"]
}
```

#### Create Warehouse with New Products
```
POST /warehouses/with-new-products
```
Creates a new warehouse along with new products in a single operation.

Example request body:
```json
{
  "warehouseName": "Central Warehouse",
  "warehouseAddress": "123 Main St",
  "warehousePostalCode": "10001",
  "warehouseCity": "New York", 
  "warehouseCountry": "USA",
  "products": [
    {
      "productID": "PROD-003",
      "productName": "New Item",
      "productCategory": "Electronics",
      "productQuantity": 50,
      "productUnit": "pcs"
    }
  ]
}
```

#### Add Products to Warehouse
```
POST /warehouses/{warehouseId}/add-products
```
Adds existing products to a warehouse.

Example request body:
```json
["PROD-001", "PROD-004", "PROD-005"]
```

### Product Management

#### Create Product
```
POST /products
```
Creates a new product.

Example request body:
```json
{
  "productID": "PROD-001",
  "productName": "Laptop",
  "productCategory": "Electronics",
  "productQuantity": 100,
  "productUnit": "pcs"
}
```

#### Get Product by ID
```
GET /products/{id}
```
Returns a specific product by its ID.

#### Get All Products
```
GET /products
```
Returns a list of all products.

## Service Layer

The service layer handles business logic between the controllers and repositories.

### DataWarehouseService

This service implements operations for warehouse management:

```java
@Service
public class DataWarehouseService {
    // Gets all warehouses
    public List<DataWarehouse> getAllWarehouses() {
        return repository.findAll();
    }
    
    // Creates a warehouse with existing products
    public DataWarehouse createWarehouseWithExistingProducts(WarehouseWithProductIdsDTO request) {
        List<Product> products = productRepository.findAllById(request.getProductIds());
        DataWarehouse warehouse = new DataWarehouse();
        // Set warehouse properties
        warehouse.setProducts(products);
        return repository.save(warehouse);
    }
    
    // Adds products to an existing warehouse
    public DataWarehouse addProductsToWarehouse(Long warehouseId, List<String> productIds) {
        DataWarehouse warehouse = repository.findById(warehouseId)
            .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        
        List<Product> products = productRepository.findAllById(productIds);
        warehouse.getProducts().addAll(products);
        
        return repository.save(warehouse);
    }
    
    // Other methods...
}
```

### ProductService

This service implements operations for product management:

```java
@Service
public class ProductService {
    // Adds a new product
    public Product addProduct(Product product) {
        return repository.save(product);
    }
    
    // Gets a product by ID
    public Product getProductById(String id) {
        return repository.findByProductID(id);
    }
    
    // Gets all products
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
}
```

## Repository Layer

The repository interfaces extend JpaRepository to provide database operations:

```java
public interface DataWarehouseRepository extends JpaRepository<DataWarehouse, Long> {
}

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByProductID(String productID);
}
```

## Example Use Cases

### 1. Creating a New Product

```
POST /products
```
```json
{
  "productID": "PROD-001",
  "productName": "Laptop",
  "productCategory": "Electronics",
  "productQuantity": 100,
  "productUnit": "pcs"
}
```

### 2. Creating a Warehouse with Existing Products

```
POST /warehouses/with-products
```
```json
{
  "warehouseName": "Central Warehouse",
  "warehouseAddress": "123 Main St",
  "warehousePostalCode": "10001",
  "warehouseCity": "New York",
  "warehouseCountry": "USA",
  "productIds": ["PROD-001", "PROD-002"]
}
```

### 3. Adding Products to a Warehouse

```
POST /warehouses/1/add-products
```
```json
["PROD-003", "PROD-004"]
```

### 4. Getting All Warehouses with Their Products

```
GET /warehouses
```

## Notes on Implementation

1. The system uses Lombok annotations (`@Data`, `@RequiredArgsConstructor`) to reduce boilerplate code.
2. A many-to-many relationship is maintained between warehouses and products.
3. DTOs are used for structured data transfer between the client and the API.
4. Exception handling is implemented for cases like "warehouse not found."
5. All timestamps are automatically generated upon entity creation.
