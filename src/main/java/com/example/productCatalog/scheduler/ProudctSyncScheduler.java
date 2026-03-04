package com.example.productCatalog.scheduler;

import com.example.productCatalog.entity.Product;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.example.productCatalog.repository.ProductRepository;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.concurrent.BlockingQueue;

@Component
public class ProudctSyncScheduler {

    private final BlockingQueue<Product> queue;
    private final WebClient webClient;

    public ProudctSyncScheduler(BlockingQueue<Product>  queue, WebClient.Builder builder) {
        this.queue = queue;
        this.webClient = builder.baseUrl("https://httpbin.org").build();
    }

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void processQueue() {
        Product request;
        while ((request = queue.poll()) != null) {
            postAsync(request);
        }
    }

    private void postAsync(Product request) {
        webClient.post()
                .uri("/post")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> System.out.println("Posted successfully: " + response))
                .doOnError(error -> System.err.println("Error posting: " + error.getMessage()))
                .subscribe();
    }
}
