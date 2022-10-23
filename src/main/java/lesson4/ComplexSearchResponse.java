package lesson4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "results",
        "offset",
        "number",
        "totalResults"
})
@Data
public class ComplexSearchResponse {

    @JsonProperty("results")
    private List<Result> results = null;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("totalResults")
    private Integer totalResults;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "title",
            "image",
            "imageType"
    })
    @Data
    public static class Result {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("image")
        private String image;
        @JsonProperty("imageType")
        private String imageType;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    }
}