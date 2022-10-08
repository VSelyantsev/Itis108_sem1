package ru.kpfu.itis.net;

import ru.kpfu.itis.net.interfaces.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class Net implements HttpClient {

    public static URL url = null;
    public static HttpURLConnection connection = null;

    public static StringBuilder print(HttpURLConnection connection) {

        try (BufferedReader bf = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        )) {

            StringBuilder content = new StringBuilder();
            String input;
            while ((input = bf.readLine()) != null) {
                content.append(input + "\n");
            }

            connection.disconnect();
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void write(String jsonInputString, HttpURLConnection postConnection) {

        try (OutputStream os = postConnection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static StringBuilder getMethod(String inputPath) {

        try {
            url = new URL(inputPath);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            return print(connection);

        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static StringBuilder postMethod(String inputPath, String token) {

        try {
            URL postUrl = new URL(inputPath);
            connection = (HttpURLConnection) postUrl.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            connection.setDoOutput(true);

            String jsonInputString = "{\"name\":\"Tenali Ramakrishna\", \"gender\":\"male\", \"email\":\"tenali.ramakrishna@15653.com\", \"status\":\"active\"}";
            write(jsonInputString, connection);

            connection.getResponseCode();

            return print(connection);

        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static StringBuilder putMethod(String inputPath, String token) {

        try {
            url = new URL(inputPath);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            connection.setDoOutput(true);
            connection.connect();

            String jsonInputString =  "{\"name\":\"Max Tar\", \"gender\":\"male\", \"email\":\"ax@gmail.com\", \"status\":\"active\"}";
            write(jsonInputString, connection);

            connection.getResponseCode();

            return print(connection);

        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static StringBuilder deleteMethod(String inputPathForPutAndDeleteMethod, String token) {

        try {

            url = new URL(inputPathForPutAndDeleteMethod);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            return print(connection);

        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String mapAsString(Map<String, String> params) {

        StringBuilder result = new StringBuilder();

        for (String key : params.keySet()) {
            result.append(key + "=" + params.get(key) + "&");
        }

        return result.toString();
    }

    public static void setProperty(Map<String, String> headers) {
        try {
            for (String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(String u, Map<String, String> headers, Map<String, String> params) {
        try {
            url = new URL(u + "?" + mapAsString(params));
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            setProperty(headers);

            return print(connection).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String post(String u, Map<String, String> headers, Map<String, String> params) {
        PrintWriter pw = null;
        try {
            url = new URL(u);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            setProperty(headers);

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            pw = new PrintWriter(connection.getOutputStream());
            pw.print(mapAsString(params));
            pw.flush();

            return print(connection).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        String inputPathForGetAndPostMethods = "https://gorest.co.in/public/v2/users";
        String inputPathForPutAndDeleteMethod = "https://gorest.co.in/public/v2/users14";
        String token = "2dd4f8fdc819b70b341f402a3c958fa565d29690724394f3eb4a0e6b6f72860c";

        //System.out.println(getMethod(inputPathForGetAndPostMethods));
        //System.out.println(postMethod(inputPathForGetAndPostMethods, token));
        //System.out.println(putMethod(inputPathForPutAndDeleteMethod, token));
        //System.out.println(deleteMethod(inputPathForPutAndDeleteMethod, token));

        Map<String, String> headers = new HashMap<>();
        // for get method
        headers.put("Content-type", "application/x-www-form-urlencoded");
        //headers.put("Accept", "application/json");

        //for post method
        headers.put("accept", "*/*");
        headers.put("connection", "Keep-Alive");
        headers.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        headers.put("Charsert", "UTF-8");

        Map<String, String> params = new HashMap<>();
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");

        Net net = new Net();

        String urlForGet = "https://postman-echo.com/get";
        //System.out.println(net.get(urlForGet, headers, params));
        String urlForPost = "https://postman-echo.com/post";
        //System.out.println(net.post(urlForPost, headers, params));

    }


}
