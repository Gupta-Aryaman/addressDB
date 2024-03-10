package microservice.v1.addressDB.address;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FakerApiTemplate {
    @JsonProperty(required = true)
    private String status;
    @JsonProperty(required = true)
    private Integer code;
    @JsonProperty(required = true)
    private Integer total;
    @JsonProperty(required = true)
    private List<Address> data;

    public FakerApiTemplate(String status, Integer code, Integer total, List<Address> data) {
        this.status = status;
        this.code = code;
        this.total = total;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }
}
