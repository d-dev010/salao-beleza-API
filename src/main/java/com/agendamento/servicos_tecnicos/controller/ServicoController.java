package com.agendamento.servicos_tecnicos.controller;

import com.agendamento.servicos_tecnicos.dto.BeautyServiceDTO;
import com.agendamento.servicos_tecnicos.dto.BeautyServiceCreateDTO;
import com.agendamento.servicos_tecnicos.service.BeautyServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beauty-services")
@RequiredArgsConstructor
public class BeautyServiceController {

    private final BeautyServiceService beautyServiceService;

    @PostMapping
    public ResponseEntity<BeautyServiceDTO> create(@Valid @RequestBody BeautyServiceCreateDTO dto) {
        BeautyServiceDTO created = beautyServiceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeautyServiceDTO> getById(@PathVariable Long id) {
        BeautyServiceDTO service = beautyServiceService.getById(id);
        return ResponseEntity.ok(service);
    }

    @GetMapping
    public ResponseEntity<List<BeautyServiceDTO>> list(@RequestParam(value = "category", required = false) String category,
                                                        @RequestParam(value = "priceMin", required = false) Double priceMin,
                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "limit", defaultValue = "20") int limit) {
        List<BeautyServiceDTO> services = beautyServiceService.list(category, priceMin, page, limit);
        return ResponseEntity.ok(services);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeautyServiceDTO> update(@PathVariable Long id,
                                                   @Valid @RequestBody BeautyServiceCreateDTO dto) {
        BeautyServiceDTO updated = beautyServiceService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        beautyServiceService.delete(id);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = beautyServiceService.getCategories();
        return ResponseEntity.ok(categories);
    }
}
