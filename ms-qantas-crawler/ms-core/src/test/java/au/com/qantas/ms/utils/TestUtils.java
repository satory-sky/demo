package au.com.qantas.ms.utils;

import au.com.qantas.ms.dto.ResponseDto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestUtils {
    private static ResponseDto treeHandle(
        Node parentNode, int currentDepth, ResponseDto parentResponseDto, int searchDepth) {
        ++currentDepth;
        parentResponseDto.setUrl(parentNode.getUrl());
        if (currentDepth <= searchDepth) {
            if (parentNode != null && parentNode.getNodes() != null) {
                for (Node node : parentNode.getNodes()) {
                    if (node != null) {
                        ResponseDto responseDto = new ResponseDto();
                        responseDto.setUrl(node.getUrl());
                        List<ResponseDto> result = new LinkedList<>(Arrays.asList(responseDto));
                        if (parentResponseDto.getNodes() != null) {
                            result.addAll(parentResponseDto.getNodes());
                        }
                        parentResponseDto.setNodes(result);

                        treeHandle(node, currentDepth, responseDto, searchDepth);
                    }
                }
            }
        }

        return parentResponseDto;
    }

    private Node getTestTree() {
        Node node2a = new Node();
        node2a.setUrl("url_2_a");
        Node node2b = new Node();
        node2b.setUrl("url_2_b");
        Node node2c = new Node();
        node2c.setUrl("url_2_c");
        Node node2d = new Node();
        node2d.setUrl("url_2_d");

        Node node2e = new Node();
        node2e.setUrl("url_2_e");
        Node node2f = new Node();
        node2f.setUrl("url_2_f");
        Node node2g = new Node();
        node2g.setUrl("url_2_g");
        Node node2h = new Node();
        node2h.setUrl("url_2_h");

        Node node2i = new Node();
        node2i.setUrl("url_2_i");
        Node node2j = new Node();
        node2j.setUrl("url_2_j");
        Node node2k = new Node();
        node2k.setUrl("url_2_k");
        Node node2l = new Node();
        node2l.setUrl("url_2_l");

        Node node2m = new Node();
        node2m.setUrl("url_2_m");
        Node node2n = new Node();
        node2n.setUrl("url_2_n");
        Node node2p = new Node();
        node2p.setUrl("url_2_p");
        Node node2q = new Node();
        node2q.setUrl("url_2_q");

        Node node1a = new Node();
        node1a.setUrl("url_1_a");
        node1a.setNodes(Arrays.asList(node2a, node2b, node2c, node2d));
        Node node1b = new Node();
        node1b.setUrl("url_1_b");
        node1b.setNodes(Arrays.asList(node2e, node2f, node2g, node2h));
        Node node1c = new Node();
        node1c.setUrl("url_1_c");
        node1c.setNodes(Arrays.asList(node2i, node2j, node2k, node2l));
        Node node1d = new Node();
        node1d.setUrl("url_1_d");
        node1d.setNodes(Arrays.asList(node2n, node2m, node2p, node2q));

        Node node = new Node();
        node.setUrl("url_0");
        node.setNodes(Arrays.asList(node1a, node1b, node1c, node1d));

        return node;
    }
}
