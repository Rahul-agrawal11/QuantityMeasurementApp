package com.quantity.service;

import com.quantity.dto.QuantityDTO;
import com.quantity.dto.QuantityMeasurementDTO;

import java.util.List;

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
