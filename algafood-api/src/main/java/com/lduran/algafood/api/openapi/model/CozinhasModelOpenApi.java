package com.lduran.algafood.api.openapi.model;

import com.lduran.algafood.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaModel> {}
