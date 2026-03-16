package com.apps.quantitymeasurement.applicationlayer;

import java.util.Scanner;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.util.ApplicationConfig;

public class QuantityMeasurementApp {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		// Repository selection using config
		ApplicationConfig config = ApplicationConfig.getInstance();
		IQuantityMeasurementRepository repository;

		if (config.isDatabaseRepository()) {
			repository = QuantityMeasurementDatabaseRepository.getInstance();
		} else {
			repository = QuantityMeasurementCacheRepository.getInstance();
		}

		// N-tier wiring
		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		System.out.println("===== Quantity Measurement System =====");

		boolean running = true;

		while (running) {

			printMenu();
			int choice = scanner.nextInt();
			scanner.nextLine();

			try {

				switch (choice) {

				case 1:
					performComparison(controller);
					break;

				case 2:
					performConversion(controller);
					break;

				case 3:
					performAddition(controller);
					break;

				case 4:
					performSubtraction(controller);
					break;

				case 5:
					performDivision(controller);
					break;

				case 6:
					showHistory(repository);
					break;

				case 0:
					running = false;
					System.out.println("Exiting application...");
					break;

				default:
					System.out.println("Invalid choice!");

				}

			} catch (QuantityMeasurementException e) {
				System.out.println("Error: " + e.getMessage());
			}

			System.out.println();
		}

		scanner.close();
	}

	// MENU

	private static void printMenu() {

		System.out.println("\nSelect Operation:");
		System.out.println("1. Compare Quantities");
		System.out.println("2. Convert Quantity");
		System.out.println("3. Add Quantities");
		System.out.println("4. Subtract Quantities");
		System.out.println("5. Divide Quantities");
		System.out.println("6. Show Operation History");
		System.out.println("0. Exit");

		System.out.print("Enter choice: ");
	}

	// GENERIC INPUT METHOD

	private static QuantityDTO readQuantity() {

		System.out.println("Select Unit Type:");
		System.out.println("1. Length");
		System.out.println("2. Weight");
		System.out.println("3. Volume");
		System.out.println("4. Temperature");

		int type = scanner.nextInt();

		System.out.print("Enter value: ");
		double value = scanner.nextDouble();

		switch (type) {

		case 1:
			return readLength(value);

		case 2:
			return readWeight(value);

		case 3:
			return readVolume(value);

		case 4:
			return readTemperature(value);

		default:
			System.out.println("Invalid type!");
			return null;
		}
	}

	// LENGTH

	private static QuantityDTO readLength(double value) {

		System.out.println("Select Length Unit:");
		System.out.println("1. FEET");
		System.out.println("2. INCHES");
		System.out.println("3. YARDS");
		System.out.println("4. CENTIMETERS");

		int unitChoice = scanner.nextInt();

		QuantityDTO.LengthUnit unit = null;

		switch (unitChoice) {
		case 1:
			unit = QuantityDTO.LengthUnit.FEET;
			break;

		case 2:
			unit = QuantityDTO.LengthUnit.INCHES;
			break;

		case 3:
			unit = QuantityDTO.LengthUnit.YARDS;
			break;

		case 4:
			unit = QuantityDTO.LengthUnit.CENTIMETERS;
			break;
		}

		return new QuantityDTO(value, unit);
	}

	// WEIGHT

	private static QuantityDTO readWeight(double value) {

		System.out.println("Select Weight Unit:");
		System.out.println("1. KILOGRAM");
		System.out.println("2. GRAM");
		System.out.println("3. POUND");
		System.out.println("4. MILIGRAM");
		System.out.println("5. TONNE");

		int unitChoice = scanner.nextInt();

		QuantityDTO.WeightUnit unit = null;

		switch (unitChoice) {
		case 1:
			unit = QuantityDTO.WeightUnit.KILOGRAM;
			break;

		case 2:
			unit = QuantityDTO.WeightUnit.GRAM;
			break;

		case 3:
			unit = QuantityDTO.WeightUnit.POUND;
			break;

		case 4:
			unit = QuantityDTO.WeightUnit.MILIGRAM;
			break;

		case 5:
			unit = QuantityDTO.WeightUnit.TONNE;
			break;
		}

		return new QuantityDTO(value, unit);
	}

	// VOLUME

	private static QuantityDTO readVolume(double value) {

		System.out.println("Select Volume Unit:");
		System.out.println("1. LITRE");
		System.out.println("2. MILLILITRE");
		System.out.println("3. GALLON");

		int unitChoice = scanner.nextInt();

		QuantityDTO.VolumeUnit unit = null;

		switch (unitChoice) {
		case 1:
			unit = QuantityDTO.VolumeUnit.LITRE;
			break;

		case 2:
			unit = QuantityDTO.VolumeUnit.MILLILITRE;
			break;

		case 3:
			unit = QuantityDTO.VolumeUnit.GALLON;
			break;
		}

		return new QuantityDTO(value, unit);
	}

	// TEMPERATURE

	private static QuantityDTO readTemperature(double value) {

		System.out.println("Select Temperature Unit:");
		System.out.println("1. CELSIUS");
		System.out.println("2. FAHRENHEIT");

		int unitChoice = scanner.nextInt();

		QuantityDTO.TemperatureUnit unit = null;

		switch (unitChoice) {
		case 1:
			unit = QuantityDTO.TemperatureUnit.CELSIUS;
			break;

		case 2:
			unit = QuantityDTO.TemperatureUnit.FAHRENHEIT;
			break;

		}

		return new QuantityDTO(value, unit);
	}

	private static QuantityDTO readTargetUnitDTO() {

		System.out.println("Select Unit Type:");
		System.out.println("1. Length");
		System.out.println("2. Weight");
		System.out.println("3. Volume");
		System.out.println("4. Temperature");

		int type = scanner.nextInt();

		switch (type) {

		case 1:
			System.out.println("Select Length Unit:");
			System.out.println("1. FEET");
			System.out.println("2. INCHES");
			System.out.println("3. YARDS");
			System.out.println("4. CENTIMETERS");

			int l = scanner.nextInt();

			switch (l) {
			case 1:
				return new QuantityDTO(0, QuantityDTO.LengthUnit.FEET);
			case 2:
				return new QuantityDTO(0, QuantityDTO.LengthUnit.INCHES);
			case 3:
				return new QuantityDTO(0, QuantityDTO.LengthUnit.YARDS);
			case 4:
				return new QuantityDTO(0, QuantityDTO.LengthUnit.CENTIMETERS);
			}
			break;

		case 2:
			System.out.println("Select Weight Unit:");
			System.out.println("1. KILOGRAM");
			System.out.println("2. GRAM");
			System.out.println("3. POUND");
			System.out.println("4. MILIGRAM");
			System.out.println("5. TONNE");

			int w = scanner.nextInt();

			switch (w) {
			case 1:
				return new QuantityDTO(0, QuantityDTO.WeightUnit.KILOGRAM);
			case 2:
				return new QuantityDTO(0, QuantityDTO.WeightUnit.GRAM);
			case 3:
				return new QuantityDTO(0, QuantityDTO.WeightUnit.POUND);
			case 4:
				return new QuantityDTO(0, QuantityDTO.WeightUnit.MILIGRAM);
			case 5:
				return new QuantityDTO(0, QuantityDTO.WeightUnit.TONNE);
			}
			break;

		case 3:
			System.out.println("Select Volume Unit:");
			System.out.println("1. LITRE");
			System.out.println("2. MILLILITRE");
			System.out.println("3. GALLON");

			int v = scanner.nextInt();

			switch (v) {
			case 1:
				return new QuantityDTO(0, QuantityDTO.VolumeUnit.LITRE);
			case 2:
				return new QuantityDTO(0, QuantityDTO.VolumeUnit.MILLILITRE);
			case 3:
				return new QuantityDTO(0, QuantityDTO.VolumeUnit.GALLON);
			}
			break;

		case 4:
			System.out.println("Select Temperature Unit:");
			System.out.println("1. CELSIUS");
			System.out.println("2. FAHRENHEIT");
			System.out.println("3. KELVIN");

			int t = scanner.nextInt();

			switch (t) {
			case 1:
				return new QuantityDTO(0, QuantityDTO.TemperatureUnit.CELSIUS);
			case 2:
				return new QuantityDTO(0, QuantityDTO.TemperatureUnit.FAHRENHEIT);

			}
			break;
		}

		return null;
	}

	// OPERATIONS

	private static void performComparison(QuantityMeasurementController controller) {

		System.out.println("First Quantity:");
		QuantityDTO q1 = readQuantity();

		System.out.println("Second Quantity:");
		QuantityDTO q2 = readQuantity();

		controller.performComparison(q1, q2);
	}

	private static void performConversion(QuantityMeasurementController controller) {

	    System.out.println("Source Quantity:");
	    QuantityDTO source = readQuantity();

	    System.out.println("Target Unit:");
	    QuantityDTO target = readTargetUnitDTO();

	    controller.performConversion(source, target);
	}

	private static void performAddition(QuantityMeasurementController controller) {

		System.out.println("First Quantity:");
		QuantityDTO q1 = readQuantity();

		System.out.println("Second Quantity:");
		QuantityDTO q2 = readQuantity();

		controller.performAddition(q1, q2);
	}

	private static void performSubtraction(QuantityMeasurementController controller) {

		System.out.println("First Quantity:");
		QuantityDTO q1 = readQuantity();

		System.out.println("Second Quantity:");
		QuantityDTO q2 = readQuantity();

		controller.performSubtraction(q1, q2);
	}

	private static void performDivision(QuantityMeasurementController controller) {

		System.out.println("First Quantity:");
		QuantityDTO q1 = readQuantity();

		System.out.println("Second Quantity:");
		QuantityDTO q2 = readQuantity();

		controller.performDivision(q1, q2);
	}

	// HISTORY

	private static void showHistory(IQuantityMeasurementRepository repository) {

		System.out.println("\n=== Operation History ===");

		repository.getAllMeasurements().forEach(System.out::println);
	}
}