# 📏 QuantityMeasurementApp
A clean, object-oriented Java application that demonstrates unit comparison and conversion for length measurements using proper domain modeling and enum-based unit handling.<br>

---

# 🚀 Technologies Used
- Java (JDK 8+)
- JUnit (for unit testing from UC1 onwards)
- Object-Oriented Programming Principles
- Enum-based design
- DRY (Don't Repeat Yourself) Principle
- IDE: Eclipse 
---
# 📌 Project Overview

The Quantity Measurement Application is built using Java and follows incremental development through multiple use cases (UC1–UC5).

**The application supports:** 
- Length equality comparison<br>
- Cross-unit comparison<br>
- Extended unit support<br>
- Unit conversion functionality<br>
- Method overloading<br>
- Proper encapsulation and immutability<br>

---

# 🏗️ Project Structure
```
com.apps.quantitymeasurement
│
├── Length.java
└── QuantityMeasurementApp.java
```
---

# 📚 Supported Units
| Unit        | Symbol | Base Conversion (Inches) |
| ----------- | ------ | ------------------------ |
| Feet        | ft     | 12 inches                |
| Inches      | in     | 1 inch (base unit)       |
| Yards       | yd     | 36 inches                |
| Centimeters | cm     | 0.393701 inches          |

📌 Base unit used internally: **Inches**

---

# 🧪 Testing Approach

JUnit tests implemented<br>
Each use case validated using test-driven validation<br>
Ensures:
- Equality correctness
- Cross-unit comparison
- Conversion accuracy
- Zero value handling
---
# 🚀 Use Cases Implemented

## ✅ UC1 – FeetEquality

**Objective:** <br>
Verify equality between two values measured in feet.<br>

Example:
```
1.0 ft == 1.0 ft → true
2.0 ft == 3.0 ft → false
```
Focus:
- Basic equality logic
- Same-unit comparison
---
## ✅ UC2 – InchesEquality

**Objective:** <br>
Verify equality between two values measured in inches.

Example:
```
12.0 in == 12.0 in → true
10.0 in == 15.0 in → false
```
Focus:
- Same-unit validation
- Consistent equality implementation
---
## ✅ UC3 – GenericLength

**Objective:** <br>
Refactor to a generalized Length class with enum-based unit handling.<br>

### Enhancements:
- Introduced Length class
- Introduced nested LengthUnit enum
- Applied DRY Principle
- Removed duplicate conversion logic
- Centralized base-unit normalization
Example:
```
1.0 ft == 12.0 in → true
```
### DRY Implementation
Instead of writing separate logic for each unit comparison, all values are converted to a common base unit (inches) before comparison.<br>
This ensures:
- No duplicated comparison logic
- Cleaner, maintainable code
- Easy future extensibility
---
## ✅ UC4 – YardEquality

**Objective:** <br>
Extend system to support yard measurements.<br>
Now supports:
```
1.0 yd == 36.0 in → true
3.0 ft == 1.0 yd → true
```
Focus:
- Extended enum support
- Backward compatibility
- Cross-unit comparison enhancement
---
## ✅ UC5 – UnitConversion

**Objective:** <br>
Add unit conversion functionality using convertTo() method.<br>
Example Outputs:
```
convert(1.0, FEET, INCHES) → 12.0
convert(3.0, YARDS, FEET) → 9.0
convert(36.0, INCHES, YARDS) → 1.0
convert(1.0, CENTIMETERS, INCHES) → 0.39
convert(0.0, FEET, INCHES) → 0.0
```
Features Added:
- Conversion method inside Length
- Method overloading
- Zero-value handling
- Immutable object return
---

# 🧠 Design Principles Followed
### ✔ Object-Oriented Design
Encapsulation and clean separation of concerns.

### ✔ DRY (Don't Repeat Yourself)
Centralized base-unit conversion eliminates duplicate logic.

### ✔ Immutability
```
private final double value;
private final LengthUnit unit;
```
### ✔ Proper Equality Contract
Overrides:<br>
- equals()
- hashCode() <br>

Ensures reflexive, symmetric, and transitive behavior.

---
