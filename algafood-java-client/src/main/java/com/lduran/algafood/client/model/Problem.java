package com.lduran.algafood.client.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Problem
{
	private Integer status;
	private OffsetDateTime timestamp;
	private String userMessage;
	private List<Object> objects = new ArrayList<>();
}
