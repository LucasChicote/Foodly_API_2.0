package com.foodly.foodly.scheduler;

import com.foodly.foodly.model.Produto;
import com.foodly.foodly.repository.ProdutoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class KitsScheduler {

    private final ProdutoRepository produtoRepository;

    public KitsScheduler(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Scheduled(fixedRate = 1800000, initialDelay = 60000)
    @Transactional
    public void processarKitsExpirados() {
        try {
            LocalDateTime agora = LocalDateTime.now();
            List<Produto> expirados = produtoRepository.findKitsExpirados(agora);
            if (expirados != null && !expirados.isEmpty()) {
                for (Produto p : expirados) {
                    p.setPrecoPromocional(null);
                    p.setIsKitSustentavel(false);
                    p.setDataExpiracao(null);
                }
                produtoRepository.saveAll(expirados);
                System.out.println("[KitsScheduler] " + expirados.size() + " kits expirados updated.");
            } else {
                System.out.println("[KitsScheduler] No expired kits found.");
            }
        } catch (Exception e) {
            System.err.println("[KitsScheduler] Error: " + e.getMessage());
        }
    }
}