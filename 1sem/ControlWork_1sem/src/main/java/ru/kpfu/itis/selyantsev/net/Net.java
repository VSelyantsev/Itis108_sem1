package ru.kpfu.itis.selyantsev.net;



import ru.kpfu.itis.selyantsev.interfaces.HttpClient;

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
            url = new URL(u + "?" + mapAsString(params));
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

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "*/*");


        Map<String, String> params = new HashMap<>();
        params.put("q", "kazan");
        params.put("appid", "6faded995f07a67bd6431a5176bb4640");

        Net net = new Net();

        String urlForGet = "https://api.openweathermap.org/data/2.5/weather?q={kazan}&appid={6faded995f07a67bd6431a5176bb4640}";
        //System.out.println(net.get(urlForGet, headers, params));
        String urlForPost = "https://api.openweathermap.org/data/2.5/weather";
        System.out.println(net.post(urlForPost, headers, params));



    }


}