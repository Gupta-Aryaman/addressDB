package microservice.v1.addressDB.address;
import java.util.List;
import microservice.v1.addressDB.address.AddressTemplate;

public class FakerApiTemplate {
    private String status;
    private Integer code;
    private Integer total;
    private List<AddressTemplate> data;

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

    public List<AddressTemplate> getData() {
        return data;
    }

    public void setData(List<AddressTemplate> data) {
        this.data = data;
    }
}
