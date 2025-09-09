package com.prolyzeai.dto.request;

public record ManagerCreateAccountRequestDto(String email,String password, String name, String surname, String companyName, String phoneNumber, String city, String address)
{

}
