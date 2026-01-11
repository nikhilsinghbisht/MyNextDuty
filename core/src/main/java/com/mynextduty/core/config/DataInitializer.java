package com.mynextduty.core.config;

import com.mynextduty.core.entity.*;
import com.mynextduty.core.enums.LifeStage;
import com.mynextduty.core.enums.Priority;
import com.mynextduty.core.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final EducationLevelRepository educationLevelRepository;
    private final InterestRepository interestRepository;
    private final DutiesRepository dutiesRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        log.info("Initializing sample data...");

        // Create Categories
        Category financeCategory = createCategory("Finance", "Financial planning and management", "💰");
        Category careerCategory = createCategory("Career", "Professional development and career growth", "💼");
        Category healthCategory = createCategory("Health", "Health and wellness management", "🏥");
        Category educationCategory = createCategory("Education", "Learning and skill development", "📚");
        Category personalCategory = createCategory("Personal", "Personal development and life skills", "🌱");

        // Create Education Levels
        createEducationLevels();

        // Create Interests
        createInterests(financeCategory, careerCategory, healthCategory, educationCategory, personalCategory);

        // Create Sample Duties
        createSampleDuties(financeCategory, careerCategory, healthCategory, educationCategory, personalCategory);

        log.info("Sample data initialization completed!");
    }

    private Category createCategory(String name, String description, String icon) {
        Category category = Category.builder()
            .name(name)
            .description(description)
            .icon(icon)
            .build();
        return categoryRepository.save(category);
    }

    private void createEducationLevels() {
        List<EducationLevel> levels = Arrays.asList(
            EducationLevel.builder().levelCode("HS").levelName("High School").levelRank(1).isActive(true).build(),
            EducationLevel.builder().levelCode("UG").levelName("Undergraduate").levelRank(2).isActive(true).build(),
            EducationLevel.builder().levelCode("PG").levelName("Postgraduate").levelRank(3).isActive(true).build(),
            EducationLevel.builder().levelCode("PHD").levelName("Doctorate").levelRank(4).isActive(true).build()
        );
        educationLevelRepository.saveAll(levels);
    }

    private void createInterests(Category... categories) {
        // Finance interests
        createInterest("Investment", "Stock market and investment strategies", categories[0]);
        createInterest("Budgeting", "Personal budgeting and expense management", categories[0]);
        createInterest("Insurance", "Life and health insurance planning", categories[0]);

        // Career interests
        createInterest("Software Development", "Programming and software engineering", categories[1]);
        createInterest("Data Science", "Analytics and machine learning", categories[1]);
        createInterest("Marketing", "Digital marketing and brand management", categories[1]);

        // Health interests
        createInterest("Fitness", "Physical fitness and exercise", categories[2]);
        createInterest("Nutrition", "Healthy eating and diet planning", categories[2]);
        createInterest("Mental Health", "Stress management and mental wellness", categories[2]);
    }

    private Interest createInterest(String name, String description, Category category) {
        Interest interest = Interest.builder()
            .name(name)
            .description(description)
            .category(category)
            .build();
        return interestRepository.save(interest);
    }

    private void createSampleDuties(Category... categories) {
        // Critical Financial Duties for Early Career
        createDuty("Get Term Life Insurance", 
            "Secure term life insurance to protect your family's financial future. Essential for anyone with dependents.",
            categories[0], Priority.CRITICAL, LifeStage.EARLY_CAREER, 22, 35, 500.0, "1-2 weeks");

        createDuty("Build Emergency Fund", 
            "Save 3-6 months of expenses in a liquid savings account for unexpected situations.",
            categories[0], Priority.CRITICAL, LifeStage.EARLY_CAREER, 18, 30, 0.0, "6-12 months");

        // High Priority Career Duties for Students
        createDuty("Choose Career Path", 
            "Research and decide on a career path that aligns with your interests and market demand.",
            categories[1], Priority.HIGH, LifeStage.STUDENT, 16, 25, 0.0, "2-3 months");

        createDuty("Build Professional Network", 
            "Start networking with professionals in your field through LinkedIn and industry events.",
            categories[1], Priority.HIGH, LifeStage.STUDENT, 18, 30, 0.0, "Ongoing");

        // Investment Duties for Career Building
        createDuty("Start SIP Investment", 
            "Begin systematic investment plan (SIP) in mutual funds for long-term wealth creation.",
            categories[0], Priority.HIGH, LifeStage.CAREER_BUILDING, 25, 40, 1000.0, "1 day");

        createDuty("Learn About Stock Market", 
            "Understand basics of stock market investing before putting money in individual stocks.",
            categories[0], Priority.MEDIUM, LifeStage.EARLY_CAREER, 22, 35, 0.0, "2-3 months");

        // Health Duties
        createDuty("Get Health Insurance", 
            "Secure comprehensive health insurance to cover medical expenses.",
            categories[2], Priority.CRITICAL, LifeStage.EARLY_CAREER, 18, 65, 800.0, "1 week");

        createDuty("Regular Health Checkup", 
            "Schedule annual health checkups to monitor and maintain good health.",
            categories[2], Priority.HIGH, LifeStage.EARLY_CAREER, 18, 100, 200.0, "1 day");

        // Education and Skill Development
        createDuty("Learn Financial Literacy", 
            "Understand basics of personal finance, budgeting, and investment.",
            categories[3], Priority.HIGH, LifeStage.STUDENT, 16, 30, 0.0, "3-6 months");

        createDuty("Develop Technical Skills", 
            "Learn relevant technical skills for your chosen career path.",
            categories[3], Priority.HIGH, LifeStage.STUDENT, 16, 35, 100.0, "6-12 months");

        // Family Building Stage
        createDuty("Plan for Child's Education", 
            "Start saving for your child's higher education expenses.",
            categories[0], Priority.HIGH, LifeStage.FAMILY_BUILDING, 28, 45, 0.0, "Start immediately");

        createDuty("Increase Life Insurance Coverage", 
            "Increase life insurance coverage as family responsibilities grow.",
            categories[0], Priority.HIGH, LifeStage.FAMILY_BUILDING, 28, 45, 1000.0, "1 week");

        // Pre-retirement Planning
        createDuty("Maximize Retirement Savings", 
            "Increase contributions to retirement accounts like EPF, PPF, and NPS.",
            categories[0], Priority.CRITICAL, LifeStage.PRE_RETIREMENT, 50, 65, 0.0, "Ongoing");

        createDuty("Plan Healthcare for Retirement", 
            "Ensure adequate health insurance coverage for post-retirement medical needs.",
            categories[2], Priority.HIGH, LifeStage.PRE_RETIREMENT, 50, 65, 2000.0, "2-3 months");
    }

    private void createDuty(String title, String description, Category category, Priority priority, 
                           LifeStage lifeStage, Integer minAge, Integer maxAge, Double cost, String timeToComplete) {
        Duties duty = Duties.builder()
            .title(title)
            .description(description)
            .category(category)
            .priority(priority)
            .targetLifeStage(lifeStage)
            .minAge(minAge)
            .maxAge(maxAge)
            .estimatedCost(cost)
            .timeToComplete(timeToComplete)
            .isActive(true)
            .build();
        dutiesRepository.save(duty);
    }
}