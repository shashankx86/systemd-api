package git.shashankx86.sd_api.controller.version;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserVersionController extends BaseVersionController {
    @Override
    protected boolean isSudo() {
        return false;
    }
}