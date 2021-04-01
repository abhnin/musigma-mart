package musigmamart.account;

public interface AccountService {
    void register(String email, String password);
    Account find(String email);
    boolean exists(String email);
}
