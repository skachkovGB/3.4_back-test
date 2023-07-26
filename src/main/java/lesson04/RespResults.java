package lesson04;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RespResults
{
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("image")
    private String image;

    @JsonProperty("imageType")
    private String imageType;

    @JsonProperty("nutrition")
    private RespNutrition nutrition;
}