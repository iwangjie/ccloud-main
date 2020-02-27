package com.ccloud.main.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * 自定义RequestWrapper
 *
 * @author wangjie
 */
public class CloudHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody = null;

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 转换为 JsonNode
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public JsonNode getRequestBody() throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readTree(new String(requestBody, "UTF-8"));
    }

    /**
     * 重新赋值 RequestBody
     *
     * @param jsonNode
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public void setRequestBody(JsonNode jsonNode) throws JsonProcessingException, UnsupportedEncodingException {
        this.requestBody = objectMapper.treeToValue(jsonNode, String.class).getBytes("UTF-8");
    }

    public CloudHttpServletRequestWrapper(HttpServletRequest request) {

        super(request);

        try {
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (requestBody == null) {
            requestBody = new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}