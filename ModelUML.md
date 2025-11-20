# Brew-Bite Café – (Model Layer)

```mermaid
classDiagram
direction LR

class MenuItem {
  - id : String
  - name : String
  - basePrice : double
  + getId() : String
  + getName() : String
  + getBasePrice() : double
  + calculatePrice(size : Size, customs : List~Customization~) : double
}

class Beverage {
  - iced : boolean
  - caffeineMg : int
}

class Pastry {
  - canBeWarmed : boolean
  - calories : int
}

class Size {
  <<enumeration>>
  SMALL
  MEDIUM
  LARGE
}

class Customization {
  - id : String
  - name : String
  - priceAdjustment : double
}

class Ingredient {
  - id : String
  - name : String
  - unit : String
}

class OrderItem {
  - menuItem : MenuItem
  - size : Size
  - quantity : int
  - customizations : List~Customization~
  + getSubtotal() : double
}

class OrderStatus {
  <<enumeration>>
  NEW
  IN_PROGRESS
  COMPLETED
  CANCELLED
}

class User {
  - id : String
  - name : String
  - role : String
}

class Order {
  - id : String
  - createdAt : LocalDateTime
  - status : OrderStatus
  - items : List~OrderItem~
  - createdBy : User
  + addItem(item : OrderItem) : void
  + getTotal() : double
}

class Inventory {
  - stock : Map~Ingredient, Integer~
  + getQuantity(ing : Ingredient) : int
  + addStock(ing : Ingredient, qty : int) : void
  + removeStock(ing : Ingredient, qty : int) : boolean
}

class Observable {
  - observers : List~Observer~
  + addObserver(o : Observer) : void
  + removeObserver(o : Observer) : void
  + notifyObservers() : void
}

class Observer {
  <<interface>>
  + update() : void
}

class InventoryManager {
  - inventory : Inventory
  + getInventory() : Inventory
  + restock(ing : Ingredient, qty : int) : void
  + consume(ing : Ingredient, qty : int) : boolean
}

class MenuManager {
  - menuItems : List~MenuItem~
  + addMenuItem(item : MenuItem) : void
  + findById(id : String) : MenuItem
  + getAll() : List~MenuItem~
}

class OrderManager {
  - orders : List~Order~
  + createOrder(user : User) : Order
  + addItem(orderId : String, item : OrderItem) : void
  + updateStatus(orderId : String, status : OrderStatus) : void
  + getActiveOrders() : List~Order~
}

MenuItem <|-- Beverage
MenuItem <|-- Pastry

Observable <|.. InventoryManager
Observable <|.. OrderManager
Observer <|.. User

Order "1" o-- "*" OrderItem
OrderItem "*" --> "1" MenuItem
Order "*" --> "1" User

MenuItem "*" o-- "*" Ingredient
MenuItem "1" o-- "*" Customization

Inventory "1" o-- "*" Ingredient
InventoryManager "1" --> "1" Inventory
MenuManager "1" --> "*" MenuItem
OrderManager "1" --> "*" Order
