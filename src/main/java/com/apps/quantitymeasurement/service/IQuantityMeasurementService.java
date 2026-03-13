package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;

public interface IQuantityMeasurementService {

	boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnit);

	QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);

	QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnit);

	double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
}
