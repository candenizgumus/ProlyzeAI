package com.prolyzeai.service;


import com.prolyzeai.dto.request.*;
import com.prolyzeai.entities.Auth;
import com.prolyzeai.entities.Company;
import com.prolyzeai.entities.Manager;
import com.prolyzeai.entities.enums.ECurrency;
import com.prolyzeai.entities.enums.EStatus;
import com.prolyzeai.entities.enums.EUserType;
import com.prolyzeai.exception.ErrorType;
import com.prolyzeai.exception.ProlyzeException;
import com.prolyzeai.repository.ItemRepository;
import com.prolyzeai.repository.ManagerRepository;
import com.prolyzeai.repository.View.AdminResponseView;
import com.prolyzeai.repository.View.ManagerResponseView;
import com.prolyzeai.utils.EmailService;
import com.prolyzeai.utils.LoginCodeGenerator;
import com.prolyzeai.utils.UtilMethods;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.prolyzeai.utils.UtilMethods.checkEmailFormat;
import static com.prolyzeai.utils.UtilMethods.checkPhoneFormat;


@Service
@RequiredArgsConstructor
public class ManagerService
{
    private final ManagerRepository managerRepository;
    private final EmailService emailService;
    private final AuthService authService;
    private final CompanyService companyService;
    private final CategoryService categoryService;

    @Transactional
    public Manager save(ManagerSaveRequestDto dto) {

        //Telefon numarası formatını kontrol ediyoruz.
        UtilMethods.checkPhoneFormat(dto.phoneNumber());

        //Random password oluşturma
        String password = LoginCodeGenerator.generateActivationCode(6);

        Auth auth = authService.save(new AuthSaveRequestDto(dto.email(), password, EUserType.MANAGER));

        //Company Olusturma. Şimdilik default "TRY" ile oluşturuyor.
        Company company = companyService.save(new CompanySaveRequestDto(dto.companyName(), dto.city(), dto.address(), ECurrency.TRY));

        //Herkesin kullanacağı kategorileri oluşturma
        saveDemoCategoriesForCompany(company);


        //Manager oluşturma
        Manager manager = managerRepository.save(Manager.builder()
                .auth(auth)
                .name(dto.name())
                .surname(dto.surname())
                .phoneNumber(dto.phoneNumber())
                .auth(auth)
                .company(company)
                .build());

        //Manager a mail gönderme.
        emailService.sendManagerInvitationEmail(dto.email(), dto.name(), dto.companyName(), password);

        return manager;
    }

    @Transactional
    public Boolean createAccount(ManagerCreateAccountRequestDto dto)
    {
        //Telefon numarası formatını kontrol ediyoruz.
        UtilMethods.checkPhoneFormat(dto.phoneNumber());

        Auth auth = authService.save(new AuthSaveRequestDto(dto.email(), dto.password(), EUserType.MANAGER));

        //Company Olusturma. Şimdilik default "TRY" ile oluşturuyor.
        Company company = companyService.save(new CompanySaveRequestDto(dto.companyName(), dto.city(), dto.address(), ECurrency.TRY));

        //Herkesin kullanacağı kategorileri oluşturma
        saveDemoCategoriesForCompany(company);

        //Manager oluşturma
        Manager manager = managerRepository.save(Manager.builder()
                .auth(auth)
                .name(dto.name())
                .surname(dto.surname())
                .phoneNumber(dto.phoneNumber())
                .auth(auth)
                .company(company)
                .build());

        //Manager a mail gönderme.
        emailService.sendManagerInvitationEmail(dto.email(), dto.name(), dto.companyName(), dto.password());

        return true;
    }

    public Manager createAccountForDemoData(ManagerCreateAccountRequestDto dto)
    {
        //Telefon numarası formatını kontrol ediyoruz.
        UtilMethods.checkPhoneFormat(dto.phoneNumber());

        Auth auth = authService.save(new AuthSaveRequestDto(dto.email(), dto.password(), EUserType.MANAGER));

        //Company Olusturma. Şimdilik default "TRY" ile oluşturuyor.
        Company company = companyService.save(new CompanySaveRequestDto(dto.companyName(), dto.city(), dto.address(), ECurrency.TRY));

        //Herkesin kullanacağı kategorileri oluşturma
        saveDemoCategoriesForCompany(company);

        //Manager oluşturma
        return managerRepository.save(Manager.builder()
            .auth(auth)
            .name(dto.name())
            .surname(dto.surname())
            .phoneNumber(dto.phoneNumber())
            .auth(auth)
            .company(company)
            .build());
    }

    private void saveDemoCategoriesForCompany(Company company)
    {
        categoryService.saveForDemoData("İşçilik", company);
        categoryService.saveForDemoData("Malzeme", company);
        categoryService.saveForDemoData("Yakıt", company);
        categoryService.saveForDemoData("Ulaşım", company);
        categoryService.saveForDemoData("Cari", company);
        categoryService.saveForDemoData("Personel", company);
        categoryService.saveForDemoData("Bakım", company);
        categoryService.saveForDemoData("Hammadde", company);
        categoryService.saveForDemoData("Vergi", company);
    }

    public Boolean delete(String id){
        Manager manager = managerRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.MANAGER_NOT_FOUND));
        manager.setStatus(EStatus.DELETED);
        managerRepository.save(manager);
        return true;
    }

    public Manager update(ManagerUpdateRequestDto dto) {

        //Checks phone format
        checkPhoneFormat(dto.phoneNumber());

        //Checks email format
        checkEmailFormat(dto.email());

        Manager manager = managerRepository.findById(UUID.fromString(dto.id())).orElseThrow(() -> new ProlyzeException(ErrorType.MANAGER_NOT_FOUND));

        //Checks if email is changed. If so, checks if email is taken
        if (!manager.getAuth().getEmail().equals(dto.email())) {
            if (authService.existsByEmail(dto.email())) {
                throw new ProlyzeException(ErrorType.EMAIL_TAKEN);
            }
        }

        manager.setName(dto.name());
        manager.setSurname(dto.surname());
        manager.setPhoneNumber(dto.phoneNumber());
        manager.getAuth().setEmail(dto.email());

        return managerRepository.save(manager);
    }

    public Manager findByAuth(Auth auth) {
        return managerRepository.findByAuth(auth).orElseThrow(() -> new ProlyzeException(ErrorType.MANAGER_NOT_FOUND));
    }

    public ManagerResponseView findViewById(String id)
    {
        return managerRepository.findViewById(UUID.fromString(id)).orElseThrow(() -> new ProlyzeException(ErrorType.MANAGER_NOT_FOUND));
    }


}
