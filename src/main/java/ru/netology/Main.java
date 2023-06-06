package ru.netology;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.util.List;

public class Main {
    private final static String REMOTE_SERVICE_URL =  "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static void main(String[] args) throws IOException, RuntimeException  {


        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URL); // объект запроса

        CloseableHttpResponse response = httpClient.execute(request);   // вызов удаленного сервиса

        // вывод заголовков
        //  Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        // вывод ответа
        ObjectMapper mapper = new ObjectMapper();
        List<CatsFacts> facts = mapper.readValue(response.getEntity().getContent(),
                new TypeReference<List<CatsFacts>>() {
                });

        facts.stream().filter(x -> x.getUpvotes() > 0).forEach(System.out::println);

    }
}
