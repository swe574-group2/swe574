package plugin.cat.accounts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tolgacaner on 05/11/16.
 */
@RestController
@RequestMapping(value = "/accounts")
public class AccountsController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getItWorks() {
        return "<html><body><h1>It works!</h1><h2>This is the accounts service. And it really works if you are seeing this.</h2></body></html>";
    }

}
