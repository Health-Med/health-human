package br.com.healthemed.healthhuman.application.controller;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.healthemed.healthhuman.application.dto.CreatePatientRequest;
import br.com.healthemed.healthhuman.application.dto.PatientDto;
import br.com.healthemed.healthhuman.application.dto.ResponseQueryPage;
import br.com.healthemed.healthhuman.application.dto.UserDto;
import br.com.healthemed.healthhuman.application.dto.UserMapper;
import br.com.healthemed.healthhuman.domain.entity.Cpf;
import br.com.healthemed.healthhuman.domain.entity.Email;
import br.com.healthemed.healthhuman.domain.exception.UserNotFoundException;
import br.com.healthemed.healthhuman.domain.repository.IPatientEntityAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
	
	private final UserMapper mapper;

	private final IPatientEntityAdapter userAdapter;
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "List all patients, paginated", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = ResponseQueryPage.class)) })
	})
	@GetMapping
	@Operation(summary = "Query dos pacientes")
	public ResponseQueryPage pageAll(
			@Parameter(description = "Página a ser buscada", example = "1")
			@RequestParam(required = true, defaultValue = "0") Integer page,
			
			@Parameter(description = "Quantidade de itens por página", example = "5")
			@RequestParam(required = true, defaultValue = "10") Integer size) {		
		var pageFound = userAdapter.findAll(page, size);
		return new ResponseQueryPage(pageFound.map(mapper::toDto).toList(), page, 
				pageFound.getTotalPages(), pageFound.getTotalElements());
	}
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "List all patients, paginated", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = ResponseQueryPage.class)) })
	})
	@GetMapping("/{id}")
	@Operation(summary = "Query dos pacientes")
	public UserDto getById(
			@Parameter(description = "Id do paciente", example = "1")
			@PathVariable(required = true) String id) {		
		return userAdapter.findById(id)
				.map(mapper::toDto)
				.orElseThrow(UserNotFoundException::new);
	}
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Query patient by parameter", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = UserDto.class)) })
	})
	@GetMapping("/query")
	@Operation(summary = "Query dos pacientes")
	public UserDto query(
			@Parameter(description = "Email do paciente", example = "xpto@server.com")
			@RequestParam(required = false) String email,
			
			@Parameter(description = "Cpf do paciente", example = "597.676.898-93")
			@RequestParam(required = false) String cpf) throws BadRequestException {
		if (StringUtils.isNotEmpty(cpf)) {
			return userAdapter.findByCpf(new Cpf(cpf))
					.map(mapper::toDto)
					.orElseThrow(UserNotFoundException::new);
		}
		
		if (StringUtils.isNotEmpty(email)) {
			return userAdapter.findByEmail(new Email(email))
					.map(mapper::toDto)
					.orElseThrow(UserNotFoundException::new);
		}
		throw new BadRequestException("Ao menos um dos parâmetros "); 
	}

	@PostMapping
	@Operation(summary = "Criação de paciente")
	public PatientDto create(@RequestBody CreatePatientRequest request) {
		return Optional.ofNullable(userAdapter.create(request))
				.map(mapper::toDto)
				.orElseThrow(() -> new RuntimeException("Ocorreu uma falha xpto"));
	}
}
