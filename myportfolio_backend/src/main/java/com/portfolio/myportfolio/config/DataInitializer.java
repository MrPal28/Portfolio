package com.portfolio.myportfolio.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.portfolio.myportfolio.entity.Blog;
import com.portfolio.myportfolio.entity.Project;
import com.portfolio.myportfolio.repo.BlogRepository;
import com.portfolio.myportfolio.repo.ProjectRepository;
import com.portfolio.myportfolio.service.AuthService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AuthService authService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user
        authService.createDefaultAdmin();

        // Initialize sample data if tables are empty
        if (projectRepository.count() == 0) {
            initializeProjects();
        }

        if (blogRepository.count() == 0) {
            initializeBlogs();
        }
    }

    private void initializeProjects() {
        // Sample project 1
        Project project1 = new Project();
        project1.setTitle("E-commerce API Gateway");
        project1.setOverview("Microservices-based API gateway for e-commerce platform");
        project1.setDescription("Built a scalable API gateway that handles authentication, rate limiting, and request routing for a microservices-based e-commerce platform. Implemented caching with Redis and message queuing with Kafka for improved performance and reliability.");
        project1.setTechnologies(Arrays.asList("Java", "Spring Boot", "Redis", "Kafka", "AWS", "Docker"));
        project1.setStartDate(LocalDate.of(2024, 1, 15));
        project1.setEndDate(LocalDate.of(2024, 3, 30));
        project1.setIsTeamProject(true);
        project1.setGithubUrl("https://github.com/arindam/ecommerce-api-gateway");
        project1.setIsPublished(true);

        // Sample project 2
        Project project2 = new Project();
        project2.setTitle("Real-time Chat System");
        project2.setOverview("Scalable real-time messaging system with WebSocket support");
        project2.setDescription("Developed a real-time chat application backend with WebSocket support, message persistence, and user authentication. Implemented horizontal scaling with Redis for session management and ensured high availability.");
        project2.setTechnologies(Arrays.asList("Java", "Spring Boot", "WebSocket", "Redis", "MySQL", "Docker"));
        project2.setStartDate(LocalDate.of(2023, 11, 1));
        project2.setEndDate(LocalDate.of(2023, 12, 20));
        project2.setIsTeamProject(false);
        project2.setGithubUrl("https://github.com/arindam/realtime-chat-system");
        project2.setIsPublished(true);

        // Sample project 3
        Project project3 = new Project();
        project3.setTitle("Payment Integration Service");
        project3.setOverview("Third-party payment gateway integration service");
        project3.setDescription("Created a unified payment service that integrates with multiple payment providers including Stripe, PayPal, and Razorpay. Implemented webhook handling, transaction logging, and retry mechanisms for failed payments.");
        project3.setTechnologies(Arrays.asList("Java", "Spring Boot", "REST API", "MySQL", "AWS Lambda"));
        project3.setStartDate(LocalDate.of(2023, 8, 1));
        project3.setEndDate(LocalDate.of(2023, 10, 15));
        project3.setIsTeamProject(false);
        project3.setGithubUrl("https://github.com/arindam/payment-integration-service");
        project3.setIsPublished(true);

        projectRepository.saveAll(Arrays.asList(project1, project2, project3));
    }

    private void initializeBlogs() {
        // Sample blog 1
        Blog blog1 = new Blog();
        blog1.setTitle("Building Scalable APIs with Spring Boot");
        blog1.setContent("APIs are the backbone of modern applications, and building them right is crucial for scalability and maintainability. In this post, I'll share my experience building scalable APIs using Spring Boot.\n\n## Key Principles\n\nWhen designing APIs, I follow these key principles:\n\n1. **RESTful Design**: Following REST principles ensures consistency and predictability\n2. **Error Handling**: Proper error responses help clients handle failures gracefully\n3. **Documentation**: Clear API documentation is essential for adoption\n4. **Versioning**: API versioning strategy prevents breaking changes\n\n## Performance Considerations\n\nFor high-performance APIs, consider:\n\n- **Caching**: Use Redis for frequently accessed data\n- **Database Optimization**: Proper indexing and query optimization\n- **Async Processing**: Use message queues for heavy operations\n- **Rate Limiting**: Protect your API from abuse\n\n## Security Best Practices\n\nSecurity should be built in from the start:\n\n- **Authentication**: JWT tokens for stateless authentication\n- **Authorization**: Role-based access control\n- **Input Validation**: Validate all inputs on both client and server\n- **HTTPS**: Always use HTTPS in production\n\nBuilding scalable APIs is an iterative process that requires careful planning and continuous improvement.");

        // Sample blog 2
        Blog blog2 = new Blog();
        blog2.setTitle("Microservices Architecture: Lessons Learned");
        blog2.setContent("Moving from monolithic to microservices architecture brings both opportunities and challenges. Here are the key lessons I've learned from implementing microservices in production.\n\n## Benefits of Microservices\n\n- **Scalability**: Scale individual services based on demand\n- **Technology Diversity**: Use the right tool for each service\n- **Team Autonomy**: Teams can work independently\n- **Fault Isolation**: Failures in one service don't bring down the entire system\n\n## Challenges\n\n- **Distributed Complexity**: Managing distributed systems is complex\n- **Data Consistency**: Maintaining consistency across services\n- **Network Latency**: Inter-service communication overhead\n- **Testing**: End-to-end testing becomes more complex\n\n## Best Practices\n\n1. **Start Simple**: Begin with a modular monolith\n2. **Define Clear Boundaries**: Services should have well-defined responsibilities\n3. **Use API Gateway**: Centralize cross-cutting concerns\n4. **Implement Circuit Breakers**: Handle service failures gracefully\n5. **Monitor Everything**: Comprehensive logging and monitoring\n\nThe key is to evolve your architecture gradually and learn from each iteration.");

        blogRepository.saveAll(Arrays.asList(blog1, blog2));
    }
}