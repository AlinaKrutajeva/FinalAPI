package finalApi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
    @JsonProperty("id")
    @Setter
    @Getter
    private String id;
    @JsonProperty("name")
    @Setter
    @Getter
    private String name;
    @JsonProperty("description")
    @Setter
    @Getter
    private String description;
}