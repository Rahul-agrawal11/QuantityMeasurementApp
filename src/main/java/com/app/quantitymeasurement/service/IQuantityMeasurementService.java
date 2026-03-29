package com.app.quantitymeasurement.service;

import java.util.List;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {

	public QuantityMeasurementDTO compare(QuantityDTO thisDTO, QuantityDTO thatDTO);

	public QuantityMeasurementDTO convert(QuantityDTO thisDTO, QuantityDTO thatDTO);

	public QuantityMeasurementDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO);

	public QuantityMeasurementDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetUnitDTO);

	public QuantityMeasurementDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO);

	public QuantityMeasurementDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetUnitDTO);

	public QuantityMeasurementDTO divide(QuantityDTO thisDTO, QuantityDTO thatDTO);

	List<QuantityMeasurementDTO> getOperationHistory(String operation);

	List<QuantityMeasurementDTO> getMeasurementByType(String type);

	long getOperationCount(String operation);

	List<QuantityMeasurementDTO> getErrorHistory();

}
