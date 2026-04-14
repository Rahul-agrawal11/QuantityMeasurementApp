package com.quantity.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.quantity.model.QuantityMeasurementEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuantityMeasurementDTO {

    private Double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private Double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String operation;

    private String resultString;
    private Double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    private String errorMessage;

    @JsonProperty("error")
    private boolean error;

    // ------------------- ENTITY → DTO -------------------
    public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
        if (entity == null)
            return null;

        return new QuantityMeasurementDTO(entity.getThisValue(), entity.getThisUnit(), entity.getThisMeasurementType(),
                entity.getThatValue(), entity.getThatUnit(), entity.getThatMeasurementType(), entity.getOperation(),
                entity.getResultString(), entity.getResultValue(), entity.getResultUnit(),
                entity.getResultMeasurementType(), entity.getErrorMessage(), entity.isError());
    }

    // ------------------- DTO → ENTITY -------------------
    public QuantityMeasurementEntity toEntity() {
        return QuantityMeasurementEntity.builder().thisValue(thisValue).thisUnit(thisUnit)
                .thisMeasurementType(thisMeasurementType)

                .thatValue(thatValue).thatUnit(thatUnit).thatMeasurementType(thatMeasurementType)

                .operation(operation)

                .resultString(resultString).resultValue(resultValue).resultUnit(resultUnit)
                .resultMeasurementType(resultMeasurementType)

                .errorMessage(errorMessage).isError(error).build();
    }

    // ------------------- LIST MAPPERS -------------------
    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        if (entities == null)
            return List.of();
        return entities.stream().map(QuantityMeasurementDTO::from).collect(Collectors.toList());
    }

    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
        if (dtos == null)
            return List.of();
        return dtos.stream().map(QuantityMeasurementDTO::toEntity).collect(Collectors.toList());
    }
}