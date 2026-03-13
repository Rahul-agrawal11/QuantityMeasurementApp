package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

/**
 * Data access contract — ISP: only exposes what is needed. Swap implementations
 * (cache → DB) without touching service layer.
 */
public interface IQuantityMeasurementRepository {

	void save(QuantityMeasurementEntity entity);

	java.util.List<QuantityMeasurementEntity> getAllMeasurements();
	
	void deleteAll();

	public static void main(String[] args) {
		System.out.println("Testing IQuantityMeasurementRepository interface");
	}
}
