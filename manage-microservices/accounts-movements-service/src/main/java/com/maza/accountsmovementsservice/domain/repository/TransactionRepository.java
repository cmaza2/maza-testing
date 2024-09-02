package com.maza.accountsmovementsservice.domain.repository;

import com.maza.accountsmovementsservice.infraestructure.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
    @Query(value = "select m from TransactionEntity m where m.idAccount=?1 order by m.idTransaction desc LIMIT 1")
    Optional<TransactionEntity> getBalance(Long idAccount);
    @Query(value = "select m from TransactionEntity m where m.date between ?1 and ?2 and m.idAccount in ?3 order by m.idTransaction desc")
    List<TransactionEntity> findMovementsByUserAndDate(LocalDate initDatefechaInicial, LocalDate finalDate, List<Long> accounts);
}
