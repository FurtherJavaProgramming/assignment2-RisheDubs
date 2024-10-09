package injector;

import repository.InMemoryUserRepository;
import service.user.UserService;

public class DependencyInjector {
    public static UserService getUserService() {
        return new UserService(new InMemoryUserRepository());
    }

    // Other services can be added here...
}
