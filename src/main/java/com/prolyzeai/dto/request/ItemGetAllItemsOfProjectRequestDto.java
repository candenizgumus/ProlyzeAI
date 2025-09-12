package com.prolyzeai.dto.request;

public record ItemGetAllItemsOfProjectRequestDto(int page,
                                                 int pageSize,
                                                 String searchText,
                                                 String projectId
)
{
}
