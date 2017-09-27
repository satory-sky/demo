package au.com.qantas.ms.jsonapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ErrorObject extends ConcurrentHashMap<String, Object> {
    final transient List<Map<String, Object>> errors = new ArrayList<>();

    public ErrorObject() {
        this.put("errors", errors);
    }

    public ErrorObject(String status, String property, String title, String message) {
        addError(status, property, title, message);
    }

    private Map<String, Object> getError(String status, String property, String title, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status);
        error.put("detail", message);
        error.put("title", title);

        Map<String, Object> pointer = new HashMap<>();
        pointer.put("pointer", "data/attributes/" + property);
        error.put("source", pointer);

        return error;
    }

    public void addError(String status, String property, String title, String message) {
        this.errors.add(getError(status, property, title, message));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorObject)) return false;
        if (!super.equals(o)) return false;

        ErrorObject that = (ErrorObject) o;

        return errors.equals(that.errors);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + errors.hashCode();
        return result;
    }
}