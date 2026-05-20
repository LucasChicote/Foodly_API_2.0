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

    @Scheduled(fixedRate = 1800000)
    @Transactional
    public void processarKitsExpirados() {
        LocalDateTime agora = LocalDateTime.now();
        List<Produto> expirados = produtoRepository.findKitsExpirados(agora);

        for (Produto p : expirados) {
            p.setPrecoPromocional(null);
            p.setIsKitSustentavel(false);
            p.setDataExpiracao(null);
        }
        produtoRepository.saveAll(expirados);
    }
}