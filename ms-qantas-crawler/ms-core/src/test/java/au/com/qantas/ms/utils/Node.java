package au.com.qantas.ms.utils;

import java.util.List;

public class Node {
    private String url;
    private List<Node> nodes;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
