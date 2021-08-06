package com.kaio.bookstore.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Getter
public class ValidationError extends StandartError {

	@Builder.Default
	private List<FieldMessage> erros = new ArrayList<>();
	
	public void addErrors(String fieldName, String message) {
		this.erros.add(new FieldMessage(fieldName, message));
	}
}
