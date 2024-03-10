package microservice.v1.addressDB.address;

import org.springframework.http.HttpStatusCode;

public class Exceptions {
    public static class HttpErrorException extends Exception {
        private HttpStatusCode status_code;
        public HttpErrorException(String message, HttpStatusCode code) {
            super(message);
            this.status_code = code;
        }

        public HttpStatusCode getStatus_code() {
            return status_code;
        }
    }

    public static class EmptyResponseException extends Exception {
        public EmptyResponseException(String message) {
            super(message);
        }
    }
}
