package com.prolyzeai.utils;

import com.prolyzeai.dto.request.*;
import com.prolyzeai.entities.Category;
import com.prolyzeai.entities.Manager;
import com.prolyzeai.entities.Project;
import com.prolyzeai.entities.enums.ECashFlowType;
import com.prolyzeai.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class DemoDataGenerator
{
    private final AuthService authService;
    private final AdminService adminService;
    private final ManagerService managerService;
    private final ProjectService projectService;
    private final CategoryService categoryService;
    private final ItemService itemService;
    private final CashFlowService cashFlowService;


    @PostConstruct
    public void generateDemoData() {
        generateAdmin();
    }

    //If admin is not created, creates admin.
    private void generateAdmin() {

        if (!authService.existsByEmail("prolyzeai@gmail.com"))
        {
            adminService.save(new AdminSaveRequestDto("Admin", "Admin", "prolyzeai@gmail.com", "9L4l4A\\/ugQT"));
        }

        generateMockData();

    }

    private void generateMockData() {

        Manager manager = managerService.createAccountForDemoData(
                new ManagerCreateAccountRequestDto(
                        "denizgumus1996@gmail.com", "1234", "Can Deniz", "Gumus",
                        "ProlyzeAI", "05305424321", "Istanbul", "Cumhuriyet Cad. No:1"));

        Project project1 = projectService.saveForDemoData(
                new ProjectSaveRequestDto("Forum Istanbul", "Dış cephe işleri", 1_000_000.0, LocalDate.now().minusMonths(2)), manager);
        Project project2 = projectService.saveForDemoData(
                new ProjectSaveRequestDto("Zorlu Center", "İç dekorasyon ve ince işler", 2_500_000.0, LocalDate.now().minusMonths(5)), manager);
        Project project3 = projectService.saveForDemoData(
                new ProjectSaveRequestDto("Ankara AVM", "Altyapı ve temel güçlendirme", 3_200_000.0, LocalDate.now().plusMonths(8)), manager);
        Project project4 = projectService.saveForDemoData(
                new ProjectSaveRequestDto("Emaar Square", "Mekanik tesisat ve havalandırma işleri", 1_800_000.0, LocalDate.now().minusMonths(3)), manager);
        Project project5 = projectService.saveForDemoData(
                new ProjectSaveRequestDto("İzmir Marina", "Peyzaj ve çevre düzenleme", 950_000.0, LocalDate.now().plusMonths(1)), manager);

        Category malzeme = categoryService.saveForDemoData("Malzeme", manager.getCompany());
        Category yakit = categoryService.saveForDemoData("Yakıt", manager.getCompany());
        Category iscilik = categoryService.saveForDemoData("İşçilik", manager.getCompany());

        List<Project> projects = List.of(project1, project2, project3, project4, project5);

        // Malzeme ve Yakıt açıklamaları
        List<String> malzemeItems = List.of(
                "Çelik profil", "Beton", "Tuğla", "Alçıpan", "Cam panel",
                "Ahşap kiriş", "İzolasyon malzemesi", "Boya", "Seramik karo", "Çatı kaplaması"
        );

        List<String> yakitItems = List.of(
                "Dizel yakıt", "Benzin", "LPG", "Jeneratör yakıtı", "Isıtma yakıtı",
                "Motor yağı", "Mazot", "Doğalgaz", "Jeneratör gazı", "Kalorifer yakıtı"
        );

        Random random = new Random();

        for (Project project : projects) {

            // Malzemeler
            for (String itemName : malzemeItems) {
                double unitPrice = 300 + random.nextDouble() * 5000; // 100 - 5100 arası
                int quantity = 1 + random.nextInt(20); // 1-20 adet
                itemService.save(new ItemSaveRequestDto(
                        malzeme.getId().toString(),
                        project.getId().toString(),
                        itemName,
                        unitPrice,
                        quantity
                ));
            }

            // Yakıtlar
            for (String itemName : yakitItems) {
                double unitPrice = 50 + random.nextDouble() * 500; // 50 - 550 arası
                int quantity = 1 + random.nextInt(50); // 1-50 adet
                itemService.save(new ItemSaveRequestDto(
                        yakit.getId().toString(),
                        project.getId().toString(),
                        itemName,
                        unitPrice,
                        quantity
                ));
            }
        }


        generateCashFlows(List.of(malzeme, yakit,iscilik));
    }

    private void generateCashFlows(List<Category> categories) {
        Random random = new Random();

        for (Category category : categories) {
            for (int i = 0; i < 20; i++) {
                ECashFlowType type = random.nextBoolean() ? ECashFlowType.CIKIS : ECashFlowType.GIRIS;

                String description = type == ECashFlowType.CIKIS
                        ? "Gider: " + category.getName()
                        : "Gelir: " + category.getName();

                // Tam sayı değerler
                int amount = type == ECashFlowType.CIKIS
                        ? 1_000 + random.nextInt(500_000) // 1000 - 500.000
                        : 5_000 + random.nextInt(1_000_000); // 5.000 - 1.000.000

                LocalDate date = LocalDate.now().minusDays(random.nextInt(365));

                cashFlowService.save(new CashFlowSaveRequestDto(
                        category.getId().toString(),
                        date,
                        description,
                        (double) amount, // CashFlow entity’si Double istiyor
                        type
                ));
            }
        }
    }





}
