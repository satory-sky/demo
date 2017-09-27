package au.com.qantas.ms.dto;

import java.util.LinkedList;
import java.util.List;

public class ResponseDto {
    private String url;
    private String title;
    private List<ResponseDto> nodes = new LinkedList<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ResponseDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<ResponseDto> nodes) {
        this.nodes = nodes;
    }
}
