package com.foodly.foodly.scheduler;

import com.foodly.foodly.model.Produto;
import com.foodly.foodly.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KitsScheduler {

    private final ProdutoRepository produtoRepository;

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
                System.out.println("[KitsScheduler] " + expirados.size() + " kits expirados atualizados.");
            } else {
                System.out.println("[KitsScheduler] Nenhum kit expirado encontrado.");
            }
        } catch (Exception e) {
            System.err.println("[KitsScheduler] Erro: " + e.getMessage());
        }
    }
}