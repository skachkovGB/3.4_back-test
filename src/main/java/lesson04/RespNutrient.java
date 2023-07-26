package lesson04;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RespNutrient
{
    @JsonProperty("name")
    private String name;

    @JsonProperty("amount")
    private float amount;

    @JsonProperty("unit")
    private String unit;
}