package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {
	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> findBetweenDate(String firstDate, String lastDate, String sellerMame, Pageable pageable) {
		LocalDate firstDay;
		LocalDate lastDay;
		try {
			lastDay = LocalDate.parse(lastDate);
		} catch (DateTimeParseException e) {
			lastDay = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		try {
			firstDay = LocalDate.parse(firstDate);
		} catch (DateTimeParseException e) {
			firstDay = lastDay.minusYears(1L);
		}
		return repository.reportSearch(firstDay, lastDay,sellerMame,  pageable);
	}

	public Page<SaleSummaryDTO> sumaryAmount(Pageable pageable, String firstDate, String lastDate	) {
		LocalDate firstDay;
		LocalDate lastDay;
		try {
			lastDay = LocalDate.parse(lastDate);
		} catch (DateTimeParseException e) {
			lastDay = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		try {
			firstDay = LocalDate.parse(firstDate);
		} catch (DateTimeParseException e) {
			firstDay = lastDay.minusYears(1L);
		}
		return repository.summarySearch(firstDay, lastDay, pageable);
	}
}
