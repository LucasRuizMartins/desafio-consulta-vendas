package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
            + "FROM Sale obj "
            + "WHERE (obj.date BETWEEN :minDate AND :maxDate) "
            + "AND (UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName , '%')))")

 Page<SaleReportDTO> reportSearch (LocalDate minDate,   LocalDate maxDate, String sellerName , Pageable pageable);

   // SaleReportDTO(Long id, Double amount, LocalDate date, String sellerName)
   @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) " +
           "FROM Sale obj " +
           "WHERE (obj.date BETWEEN :minDate AND :maxDate) " +
           "GROUP BY obj.seller.name "
   )
Page<SaleSummaryDTO>  summarySearch (LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
