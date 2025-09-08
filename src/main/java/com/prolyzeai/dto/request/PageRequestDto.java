package com.prolyzeai.dto.request;

public record
PageRequestDto(int page,
               int pageSize,
               String searchText
)
{

}
