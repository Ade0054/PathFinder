package com.pathfinder.config;

import com.pathfinder.model.*;
import com.pathfinder.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/** Seeds reference data on startup if the tables are empty. Safe to run repeatedly. */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final RiasecCategoryRepository categoryRepository;
    private final CareerRecommendationRepository careerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedRoles();
        seedCategories();
        seedSampleCareers();
        seedDemoCounsellor();
    }

    private void seedRoles() {
        for (String r : List.of("STUDENT", "COUNSELLOR")) {
            roleRepository.findByRoleName(r)
                    .orElseGet(() -> roleRepository.save(Role.builder().roleName(r).build()));
        }
    }

    private void seedCategories() {
        seedCategory("R", "Realistic",
                "You enjoy hands-on, practical work — building, fixing, and working with tools or the outdoors.");
        seedCategory("I", "Investigative",
                "You like solving problems, asking questions, and understanding how and why things work.");
        seedCategory("A", "Artistic",
                "You value creativity and self-expression through design, writing, music, or the arts.");
        seedCategory("S", "Social",
                "You enjoy helping, teaching, and working closely with other people.");
        seedCategory("E", "Enterprising",
                "You like leading, persuading, and starting things — business, sales, and organising people.");
        seedCategory("C", "Conventional",
                "You prefer order, structure, and working carefully with data, records, and details.");
    }

    private void seedCategory(String code, String name, String summary) {
        if (!categoryRepository.existsByCode(code)) {
            categoryRepository.save(RiasecCategory.builder()
                    .code(code).categoryName(name).summaryTemplate(summary).build());
        }
    }

    /** A small starter set so a fresh install shows real results. Expand this in the DB later. */
    private void seedSampleCareers() {
        if (careerRepository.count() > 0) return;

        categoryRepository.findByCode("R").ifPresent(cat -> {
            careerRepository.save(career(cat, "Civil Engineer",
                    "Designs and supervises roads, bridges, and buildings.",
                    "English, Mathematics, Physics, Chemistry",
                    "Maths, Physics, Chemistry, English, Further Maths",
                    "University of Lagos, Ahmadu Bello University, University of Nigeria Nsukka"));
            careerRepository.save(career(cat, "Agricultural Scientist",
                    "Improves crop and livestock production using science.",
                    "English, Biology, Chemistry, Agricultural Science",
                    "Biology, Chemistry, Agric Science, Maths, English",
                    "University of Ibadan, FUNAAB, University of Agriculture Makurdi"));
        });

        categoryRepository.findByCode("I").ifPresent(cat ->
            careerRepository.save(career(cat, "Medical Doctor",
                    "Diagnoses and treats illness; cares for patients.",
                    "English, Biology, Chemistry, Physics",
                    "Biology, Chemistry, Physics, Maths, English",
                    "University of Ibadan, University of Lagos, ABU Zaria")));

        categoryRepository.findByCode("S").ifPresent(cat ->
            careerRepository.save(career(cat, "Teacher / Educator",
                    "Helps students learn and grow across subjects.",
                    "English, plus two teaching subjects",
                    "English, Maths/Subject area, plus two others",
                    "University of Nigeria Nsukka, OAU Ile-Ife, Lagos State University")));
    }

    private CareerRecommendation career(RiasecCategory cat, String title, String desc,
                                        String jamb, String waec, String unis) {
        return CareerRecommendation.builder()
                .category(cat).careerTitle(title).description(desc)
                .jambSubjects(jamb).waecSubjects(waec).universities(unis).build();
    }

    private void seedDemoCounsellor() {
        if (userRepository.existsByUsername("counsellor")) return;
        Role counsellorRole = roleRepository.findByRoleName("COUNSELLOR").orElseThrow();
        userRepository.save(User.builder()
                .username("counsellor")
                .password(passwordEncoder.encode("password123"))   // CHANGE before real use
                .email("counsellor@demo.local")
                .role(counsellorRole)
                .build());
    }
}
