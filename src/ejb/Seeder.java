package ejb;

import entity.Group;
import entity.User;
import entity.LoginUser;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Singleton
@Startup
public class Seeder{

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init (){
        Group group = new Group("regulars");
        User user = new User("A", "A", "labas", "87000", "Limoges", "0666666666", new Date(), 32000.00, 25.00);
        LoginUser loginUser = new LoginUser("mail@mail.fr", "CA978112CA1BBDCAFAC231B39A23DC4DA786EFF8147C4E72B9807785AFEE48BB");

        em.persist(group);

        loginUser.addGroup(group);

        em.persist(loginUser);

        user.setLoginUser(loginUser);

        em.persist(user);
    }
}
