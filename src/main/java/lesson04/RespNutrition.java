package lesson04;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RespNutrition
{
    @JsonProperty("nutrients")
    private List<RespNutrient> nutrients;
}