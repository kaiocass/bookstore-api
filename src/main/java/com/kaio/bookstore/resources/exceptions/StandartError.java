package com.kaio.bookstore.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class StandartError {

	private Long timestamp;
	private Integer status;
	private String message;
}
