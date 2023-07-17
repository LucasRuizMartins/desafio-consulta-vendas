package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>>  getReport(
			@RequestParam(name = "firstDate", defaultValue = "")String firstDate,
			@RequestParam(name = "lastDate", defaultValue = "") String lastDate,
			@RequestParam(name = "name" , defaultValue ="") String name,
			Pageable pageable) {
		Page<SaleReportDTO> dto = service.findBetweenDate(firstDate,lastDate,name,pageable);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity <?> getSummary(
			Pageable pageable,
			@RequestParam (name = "firstDate", defaultValue = "") String firstDate,
			@RequestParam (name = "lastDate", defaultValue = "" ) String lastDate ){
				Page<SaleSummaryDTO> dto = service.sumaryAmount(pageable, firstDate ,lastDate);
				return ResponseEntity.ok(dto);

	}
}
