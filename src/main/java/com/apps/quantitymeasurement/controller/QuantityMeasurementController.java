package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

import com.apps.quantitymeasurement.dto.*;

public class QuantityMeasurementController {

	private final IQuantityMeasurementService quantityMeasurementService;

	public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
	}

	// POST /api/quantity/compare
	public boolean performComparison(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		boolean result = quantityMeasurementService.compare(thisDTO, thatDTO);
		System.out.println("COMPARE | " + thisDTO.getValue() + " " + thisDTO.getUnit() + " == " + thatDTO.getValue()
				+ " " + thatDTO.getUnit() + " → " + result);
		return result;
	}

	// POST /api/quantity/convert
	public QuantityDTO performConversion(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityDTO result = quantityMeasurementService.convert(thisDTO, thatDTO);
		System.out.println("CONVERT | " + thisDTO.getValue() + " " + thisDTO.getUnit() + " → " + result.getValue() + " "
				+ result.getUnit());
		return result;
	}

	// POST /api/quantity/add
	public QuantityDTO performAddition(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityDTO result = quantityMeasurementService.add(thisDTO, thatDTO);
		System.out.println("ADD | " + thisDTO.getValue() + " " + thisDTO.getUnit() + " + " + thatDTO.getValue() + " "
				+ thatDTO.getUnit() + " = " + result.getValue() + " " + result.getUnit());
		return result;
	}

	public QuantityDTO performAddition(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetUnitDTO) {
		QuantityDTO result = quantityMeasurementService.add(thisDTO, thatDTO, targetUnitDTO);
		System.out.println("ADD | " + thisDTO.getValue() + " " + thisDTO.getUnit() + " + " + thatDTO.getValue() + " "
				+ thatDTO.getUnit() + " = " + result.getValue() + " " + result.getUnit() + " [target: "
				+ targetUnitDTO.getUnit() + "]");
		return result;
	}

	// POST /api/quantity/subtract
	public QuantityDTO performSubtraction(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityDTO result = quantityMeasurementService.subtract(thisDTO, thatDTO);
		System.out.println("SUBTRACT | " + thisDTO.getValue() + " " + thisDTO.getUnit() + " - " + thatDTO.getValue()
				+ " " + thatDTO.getUnit() + " = " + result.getValue() + " " + result.getUnit());
		return result;
	}

	public QuantityDTO performSubtraction(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetUnitDTO) {
		QuantityDTO result = quantityMeasurementService.subtract(thisDTO, thatDTO, targetUnitDTO);
		System.out.println("SUBTRACT | " + thisDTO.getValue() + " " + thisDTO.getUnit() + " - " + thatDTO.getValue()
				+ " " + thatDTO.getUnit() + " = " + result.getValue() + " " + result.getUnit() + " [target: "
				+ targetUnitDTO.getUnit() + "]");
		return result;
	}

	// POST /api/quantity/divide
	public double performDivision(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		double result = quantityMeasurementService.divide(thisDTO, thatDTO);
		System.out.println("DIVIDE | " + thisDTO.getValue() + " " + thisDTO.getUnit() + " ÷ " + thatDTO.getValue() + " "
				+ thatDTO.getUnit() + " = " + result);
		return result;
	}
}