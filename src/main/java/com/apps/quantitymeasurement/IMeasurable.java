/**
* UC14 Enhancements:

* 1. Added a functional interface SupportsArithmetic to indicate whether a measurable unit 
* 	 supports arithmetic operations like addition, subtraction, etc. This allows for more 
* 	 flexible and dynamic handling of operations based on unit type.
* 2. Added Lambda expression to the SupportsArithmetic interface to provide a default 
* 	 implementation that indicates all units support arithmetic operations by default.
* 	 This allows for easy customization of behavior for specific units without requiring 
* 	 changes to the interface itself.
* 3. Default method to support arithmetic operations, allowing specific units to override this 
* 	 behavior. This method can be used to indicate whether a specific unit supports arithmetic
* 	 operations, providing a mechanism for enforcing operation constraints at runtime.
* 4. Default method to validate operation support at runtime, allowing specific units to 
* 	 override this behavior.
*/

package com.apps.quantitymeasurement;

/**
* Functional interface to indicate whether a measurable unit supports arithmetic operations.
* This is used to determine if addition or other operations are valid for a given unit type.
* Functional interfaces are a key feature in Java 8 and later, allowing for more concise
* and flexible code through the use of lambda expressions.
*/

@FunctionalInterface
interface SupportsArithmetic {
	boolean isSupported();
}

public interface IMeasurable {
	
	// Default lambda – arithmetic supported
    SupportsArithmetic supportsArithmetic = () -> true;
	
    // PART 1: The following methods are Mandatory conversion methods for all measurable units
	public double getConversionFactor();
	
	public double convertToBaseUnit(double value);
	
	public double convertFromBaseUnit(double baseValue);
	
	public String getUnitName();
	
	// PART 2: The following methods are Optional and can be implemented by specific
	// measurable units if needed
    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }
    
    /**
    * Validate operation support at runtime. This is a optional method for specific
    * units to override if they do not support arithmetic operations
    *
    * The default implementation does nothing, allowing all units to support all operations by default.
    * Subclasses can override this method to throw an exception if a specific operation is not supported.
    *
    */
    default void validateOperationSupport(String operation) {
        // Default: allow operations
    }
	
	public static void main(String[] args) {
		System.out.println("IMeasurable Interface!");	
	}
	
}
